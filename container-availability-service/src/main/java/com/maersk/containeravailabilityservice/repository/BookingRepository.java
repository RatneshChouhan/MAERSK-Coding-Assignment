package com.maersk.containeravailabilityservice.repository;

import com.maersk.containeravailabilityservice.model.Booking;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, String> {
}
