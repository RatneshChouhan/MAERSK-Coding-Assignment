package com.maersk.containeravailabilityservice.contorller;

import com.maersk.containeravailabilityservice.dto.BookingAvailabilityResponse;
import com.maersk.containeravailabilityservice.dto.BookingRequest;
import com.maersk.containeravailabilityservice.service.BookingService;
import com.maersk.containeravailabilityservice.service.AvailibilityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/bookings/")
public class ContainerAvailabilityController {
    private final AvailibilityService availibilityService;
    private final BookingService bookingService;

    public ContainerAvailabilityController(AvailibilityService availibilityService, BookingService bookingService) {
        this.availibilityService = availibilityService;
        this.bookingService = bookingService;
    }

    @PostMapping("checkAvailability")
    public Mono<BookingAvailabilityResponse> checkContainerAvailability(@RequestBody @Valid BookingRequest bookingRequest) {
        return availibilityService.checkAvailability(bookingRequest)
                .map(availabilityResponse -> new BookingAvailabilityResponse(availabilityResponse));
    }

    @PostMapping("bookContainer")
    public Mono<ResponseEntity<Map<String, String>>> createBooking(@RequestBody @Valid BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest)
                .map(savedBooking -> {
                    Map<String, String> responseMap = new HashMap<>();
                    responseMap.put("bookingRef", savedBooking.getBookingRef().toString());
                    log.info("Successfully created booking with ref {}", savedBooking.getBookingRef());
                    return ResponseEntity.ok(responseMap);
                })
                .onErrorResume(exception -> {
                    log.error("Error saving booking details", exception);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Collections.singletonMap("message", "Sorry there was a problem processing your request")));
                });
    }
}