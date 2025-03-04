package com.service.delivery.domain.model;


import com.service.delivery.domain.exception.delivery.InvalidDeliveryDateException;
import com.service.delivery.domain.exception.delivery.InvalidDeliveryModeException;
import lombok.Getter;
import java.time.ZonedDateTime;
import java.util.UUID;


@Getter
public class Delivery {

    private final UUID id;
    private final DeliveryMode mode;
    private final ZonedDateTime deliveryDate;

    private static final int MAX_MONTHS_IN_FUTURE = 3;



    public Delivery(UUID id, DeliveryMode mode, ZonedDateTime deliveryDate, boolean validateDate) {
        if (mode == null) {
            throw new InvalidDeliveryModeException("Delivery mode cannot be null.");
        }

        if (validateDate && !isValidDeliveryDate(deliveryDate)) {
            throw new InvalidDeliveryDateException(
                    "Delivery date must be in the future and within " + MAX_MONTHS_IN_FUTURE + " months.");
        }
        this.id = id != null ? id : UUID.randomUUID();
        this.mode = mode;
        this.deliveryDate = deliveryDate;
    }

    private boolean isValidDeliveryDate(ZonedDateTime deliveryDate) {
        if (deliveryDate == null) {
            return false;
        }
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime maxAllowedDate = now.plusMonths(MAX_MONTHS_IN_FUTURE);
        return deliveryDate.isAfter(now) && deliveryDate.isBefore(maxAllowedDate);
    }

    public Delivery updateDate(ZonedDateTime newDate) {
        if (!isValidDeliveryDate(newDate)) {
            throw new InvalidDeliveryDateException(
                    "New delivery date must be in the future and within " + MAX_MONTHS_IN_FUTURE + " months.");
        }
        return new Delivery(this.id, this.mode, newDate, true);
    }
}


