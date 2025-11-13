package com.rentease.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;

    private String productId;
    private String renterId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String paymentStatus = "PENDING"; // PENDING, PAID, CANCELLED

    // Constructors
    public Booking() {}

    public Booking(String productId, String renterId, LocalDateTime startDate, LocalDateTime endDate) {
        this.productId = productId;
        this.renterId = renterId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = "PENDING";
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getRenterId() { return renterId; }
    public void setRenterId(String renterId) { this.renterId = renterId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
