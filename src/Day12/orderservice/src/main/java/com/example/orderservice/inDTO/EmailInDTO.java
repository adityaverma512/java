package com.example.orderservice.inDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

/**
 * Data Transfer Object for incoming email data.
 * Contains recipient email, subject, and body of the email with validation constraints.
 */
public class EmailInDTO {

    /**
     * Recipient email address.
     * Must be a valid non-blank Gmail address (e.g. user@gmail.com).
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Only Gmail addresses are accepted")
    private String to;

    /**
     * Email subject.
     * Must be a non-blank string containing letters, spaces, hyphens or apostrophes,
     * between 2 and 30 characters long.
     */
    @NotBlank(message = "Subject is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Subject should be valid")
    private String subject;

    /**
     * Email body content.
     * Must be a non-blank string containing letters, spaces, hyphens or apostrophes,
     * between 2 and 30 characters long.
     */
    @NotBlank(message = "Body is required")
    @Pattern(regexp = "^[A-Za-z\\s-']{2,30}$", message = "Body should be valid")
    private String body;

    /**
     * Default no-argument constructor.
     */
    public EmailInDTO() {
    }

    /**
     * Constructs an EmailInDTO with the specified recipient, subject, and body.
     *
     * @param to      the recipient Gmail address
     * @param subject the subject of the email
     * @param body    the content body of the email
     */
    public EmailInDTO(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Gets the recipient email address.
     *
     * @return the recipient Gmail address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the recipient email address.
     *
     * @param to the recipient Gmail address to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets the email subject.
     *
     * @return the email subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the email subject.
     *
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the email body content.
     *
     * @return the email body content
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the email body content.
     *
     * @param body the body content to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Checks equality of this EmailInDTO with another object.
     *
     * @param o the other object to compare
     * @return true if all fields are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailInDTO that = (EmailInDTO) o;
        return Objects.equals(to, that.to) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
    }

    /**
     * Returns the hash code for this EmailInDTO.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(to, subject, body);
    }

    /**
     * Returns a string representation of the EmailInDTO.
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "EmailInDTO{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
