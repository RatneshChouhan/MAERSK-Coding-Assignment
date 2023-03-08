package com.maersk.containeravailabilityservice.service;

import com.maersk.containeravailabilityservice.dto.BookingRequest;
import com.maersk.containeravailabilityservice.model.Booking;
import com.maersk.containeravailabilityservice.repository.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private AtomicLong bookingRefCounter = new AtomicLong(957000000L);

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<Booking> createBooking(BookingRequest bookingRequest) {
        String bookingRef = generateBookingRef();

        Booking booking = new Booking(bookingRef,
                bookingRequest.getContainerSize(),
                bookingRequest.getContainerType(),
                bookingRequest.getOrigin(),
                bookingRequest.getDestination(),
                bookingRequest.getQuantity(),
                bookingRequest.getTimestamp());

        return bookingRepository.save(booking);
    }

    private String generateBookingRef() {
        long bookingRef = bookingRefCounter.incrementAndGet();
        return String.format("957%06d", bookingRef);
    }
}
