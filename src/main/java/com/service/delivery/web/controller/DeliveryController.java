package com.service.delivery.web.controller;

import com.service.delivery.application.ports.in.DeliveryService;
import com.service.delivery.domain.model.Delivery;
import com.service.delivery.web.dto.request.DeliveryRequest;
import com.service.delivery.web.dto.response.DeliveryResponse;
import com.service.delivery.web.webmapper.WebDeliveryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final WebDeliveryMapper deliveryMapper;

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@Valid @RequestBody DeliveryRequest request) {
        Delivery delivery = deliveryMapper.toDomain(request, true);
        Delivery createdDelivery = deliveryService.createDelivery(delivery);
        return ResponseEntity.ok(deliveryMapper.toResponse(createdDelivery));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable UUID id) {
        return deliveryService.getDeliveryById(id)
                .map(deliveryMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
        List<DeliveryResponse> deliveries = deliveryService.getAllDeliveries()
                .stream()
                .map(deliveryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(deliveries);
    }
}
