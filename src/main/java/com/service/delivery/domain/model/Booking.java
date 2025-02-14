package com.service.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private UUID id;
    private UUID deliveryId;
    private UUID timeSlotId;
    private ZonedDateTime date;
}
