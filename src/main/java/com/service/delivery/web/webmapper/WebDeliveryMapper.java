package com.service.delivery.web.webmapper;

import com.service.delivery.domain.exception.delivery.InvalidDeliveryModeException;
import com.service.delivery.domain.model.Delivery;
import com.service.delivery.domain.model.DeliveryMode;
import com.service.delivery.web.dto.request.DeliveryRequest;
import com.service.delivery.web.dto.response.DeliveryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface WebDeliveryMapper {
    WebDeliveryMapper INSTANCE = Mappers.getMapper(WebDeliveryMapper.class);

    default Delivery toDomain(DeliveryRequest request, boolean validateDate) {
        if (request == null) {
            return null;
        }
        // Convertir le mode String en Enum
        DeliveryMode mode;
        try {
            mode = DeliveryMode.valueOf(request.mode().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidDeliveryModeException("Invalid delivery mode: " + request.mode());
        }

        ZonedDateTime deliveryDate = ZonedDateTime.parse(request.deliveryDate());
        return new Delivery(null, mode, deliveryDate, validateDate);
    }

    default DeliveryResponse toResponse(Delivery delivery){
        if (delivery == null) {
            return null;
        }
        return new DeliveryResponse(delivery.getId(), delivery.getMode(), delivery.getDeliveryDate());
    }
}
