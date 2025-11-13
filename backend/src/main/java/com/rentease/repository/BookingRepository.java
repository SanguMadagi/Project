package com.rentease.repository;

import com.rentease.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByProductId(String productId);
    List<Booking> findByRenterId(String renterId);
}
