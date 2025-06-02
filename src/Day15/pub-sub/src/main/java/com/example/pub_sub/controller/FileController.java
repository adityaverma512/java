package com.example.pub_sub.controller;

import com.example.pub_sub.Service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Value("${gcp.project-id}")
    private String projectId;

    @Value("${gcp.pubsub.topic}")
    private String topicId;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.warn("Received empty file upload request");
            return ResponseEntity.badRequest().body("File is empty");
        }

        logger.info("Received file: {}, size: {}, content-type: {}",
                file.getOriginalFilename(), file.getSize(), file.getContentType());

        try {
            String result = fileService.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error uploading file: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("File upload service is running!");
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        try {
            StringBuilder result = new StringBuilder();
            result.append("=== Configuration Debug ===\n");
            result.append("Project ID: ").append(projectId).append("\n");
            result.append("Topic ID: ").append(topicId).append("\n");
            result.append("Full Topic Path: projects/").append(projectId).append("/topics/").append(topicId).append("\n\n");

            // Test topic access
            String topicUrl = String.format("http://localhost:8085/v1/projects/%s/topics/%s",
                    projectId, topicId);
            result.append("Testing Topic URL: ").append(topicUrl).append("\n");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(topicUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            result.append("Topic Access Status: ").append(response.statusCode()).append("\n");
            result.append("Topic Access Response: ").append(response.body()).append("\n\n");

            // Test list all topics
            String listTopicsUrl = String.format("http://localhost:8085/v1/projects/%s/topics", projectId);
            HttpRequest listRequest = HttpRequest.newBuilder()
                    .uri(URI.create(listTopicsUrl))
                    .GET()
                    .build();

            HttpResponse<String> listResponse = httpClient.send(listRequest,
                    HttpResponse.BodyHandlers.ofString());

            result.append("All Topics Status: ").append(listResponse.statusCode()).append("\n");
            result.append("All Topics Response: ").append(listResponse.body()).append("\n");

            return ResponseEntity.ok(result.toString());

        } catch (Exception e) {
            logger.error("Debug endpoint failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Debug failed: " + e.getMessage());
        }
    }

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic() {
        try {
            String url = String.format("http://localhost:8085/v1/projects/%s/topics/%s",
                    projectId, topicId);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .PUT(HttpRequest.BodyPublishers.ofString("{}"))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            logger.info("Topic creation attempt - Status: {}, Response: {}",
                    response.statusCode(), response.body());

            return ResponseEntity.ok(String.format(
                    "Topic creation attempt - Status: %d, Response: %s",
                    response.statusCode(), response.body()));

        } catch (Exception e) {
            logger.error("Topic creation failed: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Topic creation failed: " + e.getMessage());
        }
    }
}
