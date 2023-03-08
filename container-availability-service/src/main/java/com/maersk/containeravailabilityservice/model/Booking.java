package com.maersk.containeravailabilityservice.model;

import com.maersk.containeravailabilityservice.dto.ContainerType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("bookings")
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @PrimaryKey("booking_ref")
    private String bookingRef;

    @Column("container_size")
    @NotNull
    @Min(20)
    @Max(40)
    private Integer containerSize;

    @Column("container_type")
    @NotNull
    private ContainerType containerType;

    @Column("origin")
    @NotNull
    @Size(min = 5, max = 20)
    private String origin;

    @Column("destination")
    @NotNull
    @Size(min = 5, max = 20)
    private String destination;

    @Column("quantity")
    @NotNull
    @Min(1)
    @Max(100)
    private Integer quantity;

    @Column("timestamp")
    @NotNull
    private String timestamp;
}
