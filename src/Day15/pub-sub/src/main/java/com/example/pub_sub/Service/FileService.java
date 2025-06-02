package com.example.pub_sub.Service;


import com.google.cloud.NoCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.bucket-name}")
    private String bucketName;

    @Value("${gcp.pubsub.topic}")
    private String topicId;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String uploadFile(MultipartFile file) throws Exception {
        logger.info("Starting file upload for: {}", file.getOriginalFilename());

        // Upload to GCS emulator
        Storage storage = StorageOptions.newBuilder()
                .setHost("http://localhost:4443")
                .setProjectId(projectId)
                .setCredentials(NoCredentials.getInstance())
                .build()
                .getService();

        BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        logger.info("File uploaded to GCS successfully");

        // Publish to Pub/Sub using HTTP API
        String messageId = publishMessageViaHttp(file);

        return "Successfully uploaded: " + file.getOriginalFilename() +
                " to bucket: " + bucketName +
                " (Pub/Sub Message ID: " + messageId + ")";
    }

    private String publishMessageViaHttp(MultipartFile file) throws Exception {
        logger.info("Publishing message via HTTP API...");

        // Create the message payload
        String messageData = "File uploaded: " + file.getOriginalFilename();
        String encodedData = Base64.getEncoder().encodeToString(messageData.getBytes());

        // Create the request body
        Map<String, Object> messageMap = Map.of(
                "data", encodedData,
                "attributes", Map.of(
                        "fileName", file.getOriginalFilename(),
                        "bucketName", bucketName,
                        "fileSize", String.valueOf(file.getSize())
                )
        );

        Map<String, Object> requestBody = Map.of("messages", List.of(messageMap));
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        String url = String.format("http://localhost:8085/v1/projects/%s/topics/%s:publish",
                projectId, topicId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            logger.info("HTTP Response Status: {}", response.statusCode());
            logger.info("HTTP Response Body: {}", response.body());

            if (response.statusCode() == 200) {
                @SuppressWarnings("unchecked")
                Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
                @SuppressWarnings("unchecked")
                List<String> messageIds = (List<String>) responseMap.get("messageIds");

                String messageId = messageIds != null && !messageIds.isEmpty() ?
                        messageIds.get(0) : "unknown";

                logger.info("Message published successfully with ID: {}", messageId);
                return messageId;
            } else {
                throw new RuntimeException("Failed to publish message. Status: " +
                        response.statusCode() + ", Body: " + response.body());
            }

        } catch (Exception e) {
            logger.error("Error publishing message via HTTP", e);
            throw e;
        }
    }
}
