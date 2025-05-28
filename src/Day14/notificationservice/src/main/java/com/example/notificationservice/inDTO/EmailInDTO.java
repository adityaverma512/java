package com.example.notificationservice.inDTO;

import java.util.Objects;

/**
 * Data Transfer Object for incoming email data.
 * Encapsulates the recipient email address, subject, and body of the email.
 */
public class EmailInDTO {
    /**
     * Recipient email address.
     */
    private String to;

    /**
     * Subject of the email.
     */
    private String subject;

    /**
     * Body content of the email.
     */
    private String body;

    /**
     * Default constructor.
     */
    public EmailInDTO() {
    }

    /**
     * Constructs an EmailInDTO with specified recipient, subject, and body.
     *
     * @param to      recipient email address
     * @param subject subject of the email
     * @param body    body content of the email
     */
    public EmailInDTO(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Returns the recipient email address.
     *
     * @return recipient email address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the recipient email address.
     *
     * @param to recipient email address
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns the subject of the email.
     *
     * @return subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     *
     * @param subject subject of the email
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the body content of the email.
     *
     * @return body content of the email
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body content of the email.
     *
     * @param body body content of the email
     */
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailInDTO that = (EmailInDTO) o;
        return Objects.equals(to, that.to) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, subject, body);
    }

    @Override
    public String toString() {
        return "EmailInDTO{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
