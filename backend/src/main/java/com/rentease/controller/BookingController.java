package com.rentease.controller;

import com.rentease.model.Booking;
import com.rentease.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable String id, @RequestParam String status) {
        Booking updated = bookingService.updateBookingStatus(id, status);
        if(updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Booking>> getBookingsByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(bookingService.getBookingsByProduct(productId));
    }

    @GetMapping("/renter/{renterId}")
    public ResponseEntity<List<Booking>> getBookingsByRenter(@PathVariable String renterId) {
        return ResponseEntity.ok(bookingService.getBookingsByRenter(renterId));
    }
}
