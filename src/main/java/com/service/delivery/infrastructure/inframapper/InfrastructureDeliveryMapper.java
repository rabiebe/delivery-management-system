package com.service.delivery.infrastructure.inframapper;

import com.service.delivery.domain.model.Delivery;
import com.service.delivery.infrastructure.entity.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface InfrastructureDeliveryMapper {
    InfrastructureDeliveryMapper INSTANCE = Mappers.getMapper(InfrastructureDeliveryMapper.class);

    default Delivery toDomain(DeliveryEntity entity, boolean validDate){
        if(entity == null) return null;
        // Convert Instant to ZonedDateTime (using UTC as the default time zone)
        ZonedDateTime deliveryDate = entity.getDeliveryDate() != null
                ? entity.getDeliveryDate().atZone(ZoneOffset.UTC)
                : null;
        return new Delivery(null, entity.getMode(), deliveryDate, validDate);
    }


    default DeliveryEntity toEntity(Delivery delivery){
        if(delivery == null) return null;
        // Convert ZonedDateTime to Instant
        Instant deliveryDate = delivery.getDeliveryDate() != null
                ? delivery.getDeliveryDate().toInstant()
                : null;
        return new DeliveryEntity(null, delivery.getMode(), deliveryDate);
    }

}
