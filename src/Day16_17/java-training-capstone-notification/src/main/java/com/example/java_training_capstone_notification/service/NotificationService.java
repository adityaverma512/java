package com.example.java_training_capstone_notification.service;

import com.example.java_training_capstone_notification.inDTO.OrderNotificationInDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for sending order-related email notifications to customers.
 * This service handles various types of order events and sends appropriately formatted
 * email notifications based on the event type and order information.
 *
 * <p>Supported notification types:
 * <ul>
 *   <li>Order Creation - Sent when a new order is successfully created</li>
 *   <li>Order Status Update - Sent when an order's status changes (confirmed, shipped, delivered, etc.)</li>
 *   <li>Order Shipped - Sent when an order is shipped</li>
 *   <li>Order Delivered - Sent when an order is delivered</li>
 *   <li>Order Cancelled - Sent when an order is cancelled</li>
 * </ul>
 *
 * <p>All email notifications include comprehensive order details and are customized
 * based on the specific event type. Status update notifications include status-specific
 * messages to provide customers with relevant information about their order's progress.
 *
 * <p>Error handling:
 * - All public methods include try-catch blocks to handle email sending failures
 * - Detailed error logging with order ID context for troubleshooting
 * - Graceful failure handling to prevent service disruption
 * @see OrderNotificationInDTO
 * @see JavaMailSender
 */
@Service
public class NotificationService {

    /**
     * Logger instance for this class.
     * Used for logging notification sending events, success confirmations, and error details.
     */
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Spring Mail sender for sending email notifications.
     * Configured through the MailConfig to work with SMTP servers.
     * Injected via Spring's dependency injection.
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email notification when a new order is created.
     * This method creates a confirmation email with complete order details
     * including product information, pricing, shipping address, and order timestamp.
     *
     * <p>The email includes:
     * <ul>
     *   <li>Order confirmation message</li>
     *   <li>Complete order details (ID, product, quantity, pricing)</li>
     *   <li>Shipping address</li>
     *   <li>Order creation timestamp</li>
     *   <li>Promise to keep customer updated</li>
     * </ul>
     *
     * @param orderNotification the order notification containing all order details
     *                         and customer information
     *
     * @throws RuntimeException if email sending fails (logged but not propagated)
     *
     * @see #buildOrderCreatedEmailBody(OrderNotificationInDTO)
     * @see #sendEmail(String, String, String)
     */
    public void sendOrderCreatedNotification(OrderNotificationInDTO orderNotification) {
        try {
            logger.info("Sending order creation notification email for order ID: {}", orderNotification.getOrderId());

            String subject = "Order Confirmation - Order #" + orderNotification.getOrderId();
            String body = buildOrderCreatedEmailBody(orderNotification);

            sendEmail(orderNotification.getCustomerEmail(), subject, body);

            logger.info("Order creation notification sent successfully for order ID: {}", orderNotification.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to send order creation notification for order ID: {}. Error: {}",
                    orderNotification.getOrderId(), e.getMessage(), e);
        }
    }

    /**
     * Sends an email notification when an order's status is updated.
     * This method creates a status update email with order details and
     * status-specific messages to inform customers about their order's progress.
     *
     * <p>Status-specific messages include:
     * <ul>
     *   <li>CONFIRMED - Order preparation message</li>
     *   <li>SHIPPED - Shipment notification with shipping address</li>
     *   <li>DELIVERED - Delivery confirmation</li>
     *   <li>CANCELLED - Cancellation notice with support contact suggestion</li>
     * </ul>
     *
     * @param orderNotification the order notification containing updated status
     *                         and order information
     *
     * @throws RuntimeException if email sending fails (logged but not propagated)
     *
     * @see #buildOrderStatusUpdateEmailBody(OrderNotificationInDTO)
     * @see #sendEmail(String, String, String)
     */
    public void sendOrderStatusUpdatedNotification(OrderNotificationInDTO orderNotification) {
        try {
            logger.info("Sending order status update notification email for order ID: {} with status: {}",
                    orderNotification.getOrderId(), orderNotification.getStatus());

            String subject = "Order Status Update - Order #" + orderNotification.getOrderId();
            String body = buildOrderStatusUpdateEmailBody(orderNotification);

            sendEmail(orderNotification.getCustomerEmail(), subject, body);

            logger.info("Order status update notification sent successfully for order ID: {}", orderNotification.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to send order status update notification for order ID: {}. Error: {}",
                    orderNotification.getOrderId(), e.getMessage(), e);
        }
    }

    /**
     * Sends an email using the configured JavaMailSender.
     * This is a private utility method that handles the actual email sending
     * with proper error handling and logging.
     *
     * <p>Email configuration:
     * <ul>
     *   <li>From address: noreply@orderservice.com</li>
     *   <li>Plain text format</li>
     *   <li>Simple mail message format</li>
     * </ul>
     *
     * @param to the recipient's email address
     * @param subject the email subject line
     * @param body the email body content (plain text)
     *
     * @throws RuntimeException if email sending fails, propagated to caller
     *                         for appropriate error handling
     *
     * @see SimpleMailMessage
     * @see JavaMailSender#send(SimpleMailMessage)
     */
    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@orderservice.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            logger.debug("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to: {}. Error: {}", to, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Builds the email body content for order creation notifications.
     * Creates a comprehensive, customer-friendly email with all order details
     * and a welcoming tone to confirm the successful order placement.
     *
     * @param orderNotification the order notification containing all order details
     * @return formatted email body as a string with proper line breaks and spacing
     */
    private String buildOrderCreatedEmailBody(OrderNotificationInDTO orderNotification) {
        StringBuilder body = new StringBuilder();
        body.append("Dear Customer,\n\n");
        body.append("Thank you for your order! Your order has been successfully created.\n\n");
        body.append("Order Details:\n");
        body.append("Order ID: ").append(orderNotification.getOrderId()).append("\n");
        body.append("Product: ").append(orderNotification.getProductName()).append("\n");
        body.append("Quantity: ").append(orderNotification.getQuantity()).append("\n");
        body.append("Unit Price: $").append(orderNotification.getUnitPrice()).append("\n");
        body.append("Total Amount: $").append(orderNotification.getTotalAmount()).append("\n");
        body.append("Status: ").append(orderNotification.getStatus()).append("\n");
        body.append("Shipping Address: ").append(orderNotification.getShippingAddress()).append("\n");
        body.append("Order Date: ").append(orderNotification.getTimestamp()).append("\n\n");
        body.append("We will keep you updated on your order status.\n\n");
        body.append("Thank you for choosing our service!\n\n");
        body.append("Best regards,\n");
        body.append("Order Service Team");

        return body.toString();
    }

    /**
     * Builds the email body content for order status update notifications.
     * Creates a status-specific email that informs customers about their order's
     * progress with relevant information based on the new status.
     *
     * @param orderNotification the order notification containing the updated status
     *                         and order information
     * @return formatted email body as a string with status-specific messaging
     */
    private String buildOrderStatusUpdateEmailBody(OrderNotificationInDTO orderNotification) {
        StringBuilder body = new StringBuilder();
        body.append("Dear Customer,\n\n");
        body.append("Your order status has been updated.\n\n");
        body.append("Order Details:\n");
        body.append("Order ID: ").append(orderNotification.getOrderId()).append("\n");
        body.append("Product: ").append(orderNotification.getProductName()).append("\n");
        body.append("New Status: ").append(orderNotification.getStatus()).append("\n");
        body.append("Update Time: ").append(orderNotification.getTimestamp()).append("\n\n");

        switch (orderNotification.getStatus()) {
            case "CONFIRMED":
                body.append("Great news! Your order has been confirmed and is being prepared for shipment.\n");
                break;
            case "SHIPPED":
                body.append("Your order has been shipped and is on its way to you!\n");
                body.append("Shipping Address: ").append(orderNotification.getShippingAddress()).append("\n");
                break;
            case "DELIVERED":
                body.append("Your order has been delivered successfully. We hope you enjoy your purchase!\n");
                break;
            case "CANCELLED":
                body.append("Your order has been cancelled. If you have any questions, please contact our support team.\n");
                break;
        }

        body.append("\nThank you for choosing our service!\n\n");
        body.append("Best regards,\n");
        body.append("Order Service Team");

        return body.toString();
    }
}