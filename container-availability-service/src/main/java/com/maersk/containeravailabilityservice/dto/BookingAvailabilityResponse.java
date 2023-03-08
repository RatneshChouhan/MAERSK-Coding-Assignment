package com.maersk.containeravailabilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingAvailabilityResponse {
    private boolean available;
}
