package com.example.gradle.IntegrationTest;

import com.example.gradle.DTO.AddressDto;
import com.example.gradle.DTO.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String path) {
        return "http://localhost:" + port + "/api/user" + path;
    }

    @Test
    void testAddAndGetUser() {
        UserDto user = UserDto.builder()
                .name("Integration User")
                .addresses(Arrays.asList(
                        new AddressDto(0, "Pune", "MH"),
                        new AddressDto(0, "Mumbai", "MH")
                )).build();

        ResponseEntity<String> postResponse = restTemplate.postForEntity(url(""), user, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        ResponseEntity<UserDto[]> getResponse = restTemplate.getForEntity(url(""), UserDto[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().length > 0);
    }

    @Test
    void testGetUserNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/999"), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() {
        ResponseEntity<String> response = restTemplate.exchange(url("/999"), HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void testUpdateUser() {
        // Create user first
        UserDto user = UserDto.builder()
                .name("Original User")
                .addresses(Arrays.asList(new AddressDto(0, "CityA", "StateA")))
                .build();
        ResponseEntity<String> postResponse = restTemplate.postForEntity(url(""), user, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        // Fetch user list to get created user ID
        ResponseEntity<UserDto[]> getResponse = restTemplate.getForEntity(url(""), UserDto[].class);
        int userId = getResponse.getBody()[0].getId();

        //updated user info
        UserDto updatedUser = UserDto.builder()
                .name("Updated User")
                .addresses(Arrays.asList(new AddressDto(0, "CityA", "StateA")))
                .build();

        // 4. Send PUT request to update user
        HttpEntity<UserDto> requestUpdate = new HttpEntity<>(updatedUser);
        ResponseEntity<UserDto> updateResponse = restTemplate.exchange(url("/" + userId), HttpMethod.PUT, requestUpdate, UserDto.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Updated User", updateResponse.getBody().getName());
        assertEquals("CityB", updateResponse.getBody().getAddresses().get(0).getCity());
    }

}
