package com.maersk.containeravailabilityservice.service;

import com.maersk.containeravailabilityservice.dto.BookingRequest;
import com.maersk.containeravailabilityservice.dto.CheckAvailableResonse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AvailibilityService {

    private final WebClient webClient;

    public AvailibilityService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Boolean> checkAvailability(BookingRequest bookingRequest) {

        return webClient.post()
                .uri("https://maersk.com/api/bookings/checkAvailable")
                .bodyValue(bookingRequest)
                .retrieve()
                .bodyToMono(CheckAvailableResonse.class)
                .map(response -> response.getAvailableSpace())
                .map(availableSpace -> availableSpace > 0);
    }
}
