package com.maersk.containeravailabilityservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    @NotNull
    @Min(20)
    @Max(40)
    private Integer containerSize;

    @NotNull
    private ContainerType containerType;

    @NotBlank
    @Size(min = 5, max = 20)
    private String origin;

    @NotBlank
    @Size(min = 5, max = 20)
    private String destination;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer quantity;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private String timestamp;
}

