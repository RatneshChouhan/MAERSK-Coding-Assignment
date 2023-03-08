package com.maersk.containeravailabilityservice.contorller;

import com.maersk.containeravailabilityservice.dto.BookingAvailabilityResponse;
import com.maersk.containeravailabilityservice.dto.BookingRequest;
import com.maersk.containeravailabilityservice.dto.ContainerType;
import com.maersk.containeravailabilityservice.model.Booking;
import com.maersk.containeravailabilityservice.service.AvailibilityService;
import com.maersk.containeravailabilityservice.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ContainerAvailabilityController.class)
class ContainerAvailabilityControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AvailibilityService mockAvailibilityService;
    @MockBean
    private BookingService mockBookingService;

    @Test
    void testCheckContainerAvailability() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setContainerType(ContainerType.DRY);
        bookingRequest.setContainerSize(20);
        bookingRequest.setOrigin("Southampton");
        bookingRequest.setDestination("Singapore");
        bookingRequest.setQuantity(5);
        bookingRequest.setTimestamp("2020-10-12T13:53:09Z");

        BookingAvailabilityResponse availabilityResponse = new BookingAvailabilityResponse();
        availabilityResponse.setAvailable(false);

        when(mockAvailibilityService.checkAvailability(Mockito.any(BookingRequest.class)))
                .thenReturn(Mono.just(true));

        webTestClient.post()
                .uri("/api/bookings/checkAvailability")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookingAvailabilityResponse.class)
                .value(response -> assertThat(response.isAvailable()).isEqualTo(true));
    }

    @Test
    public void testCheckContainerAvailability_AvailabilityServiceReturnsNoItem() throws Exception {
        // Setup
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setContainerSize(20);
        bookingRequest.setContainerType(ContainerType.REEFER);
        bookingRequest.setOrigin("origin");
        bookingRequest.setDestination("destination");
        bookingRequest.setQuantity(10);
        bookingRequest.setTimestamp("timestamp");

        when(mockAvailibilityService.checkAvailability(any(BookingRequest.class))).thenReturn(Mono.empty());

        // Run the test
        webTestClient.post()
                .uri("/api/bookings/checkAvailability")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void testCreateBooking() throws Exception {
        // Prepare Test Data
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setContainerSize(20);
        bookingRequest.setContainerType(ContainerType.REEFER);
        bookingRequest.setOrigin("origin");
        bookingRequest.setDestination("destination");
        bookingRequest.setQuantity(10);
        bookingRequest.setTimestamp("2020-10-12T13:53:09Z");

        // Mock the bookingService to return a saved booking with bookingRef = 12345
        Booking savedBooking = new Booking();
        savedBooking.setBookingRef("957000001");
        when(mockBookingService.createBooking(any(BookingRequest.class))).thenReturn(Mono.just(savedBooking));

        // Perform the HTTP request and assert the response
        webTestClient.post()
                .uri("/api/bookings/bookContainer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.bookingRef").isEqualTo("957000001");
    }
}
