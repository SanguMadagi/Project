package com.rentease.service;

import com.rentease.model.Booking;
import com.rentease.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    // Create new booking
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Update booking status
    public Booking updateBookingStatus(String id, String status) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if(existing.isPresent()) {
            Booking b = existing.get();
            b.setPaymentStatus(status);
            return bookingRepository.save(b);
        }
        return null;
    }

    // Get bookings for a product
    public List<Booking> getBookingsByProduct(String productId) {
        return bookingRepository.findByProductId(productId);
    }

    // Get bookings by renter
    public List<Booking> getBookingsByRenter(String renterId) {
        return bookingRepository.findByRenterId(renterId);
    }
}
