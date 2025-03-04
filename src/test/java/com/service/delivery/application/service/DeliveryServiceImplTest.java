package com.service.delivery.application.service;

import com.service.delivery.application.exception.delivery.InvalidDeliveryException;
import com.service.delivery.application.exception.delivery.NullDeliveryException;
import com.service.delivery.application.ports.out.DeliveryRepository;
import com.service.delivery.domain.model.Delivery;
import com.service.delivery.domain.model.DeliveryMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;


    @Test
    void testCreateDelivery_ShouldReturnSavedDelivery() {
        // Arrange
        Delivery delivery = createValidDelivery(true);
        when(deliveryRepository.save(delivery)).thenReturn(delivery);

        // Act
        Delivery result = deliveryService.createDelivery(delivery);

        // Assert
        assertNotNull(result);
        assertEquals(delivery, result);

        // Verify interactions
        verify(deliveryRepository, times(1)).save(delivery);
    }

    @Test
    void testCreateDelivery_ShouldThrowNullDeliveryException_WhenDeliveryIsNull() {
        assertThrows(NullDeliveryException.class, () -> deliveryService.createDelivery(null));
        verify(deliveryRepository, times(0)).save(any());
    }

    @Test
    void testCreateDelivery_ShouldThrowInvalidDeliveryException_WhenModeIsNull() {
        Delivery delivery = new Delivery(UUID.randomUUID(), null, ZonedDateTime.now(), true);
        assertThrows(InvalidDeliveryException.class, () -> deliveryService.createDelivery(delivery));
        verify(deliveryRepository, times(0)).save(delivery);
    }

    @Test
    void testCreateDelivery_ShouldThrowInvalidDeliveryException_WhenDateIsNull() {
        Delivery delivery = new Delivery(UUID.randomUUID(), DeliveryMode.DELIVERY, null, true);
        assertThrows(InvalidDeliveryException.class, () -> deliveryService.createDelivery(delivery));
        verify(deliveryRepository, times(0)).save(delivery);
    }

    @Test
    void testGetDeliveryById_ShouldReturnDelivery_WhenDeliveryExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Delivery delivery = createValidDelivery(false);
        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery));

        // Act
        Optional<Delivery> result = deliveryService.getDeliveryById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(delivery, result.get());

        // Verify interactions
        verify(deliveryRepository, times(1)).findById(id);
    }

    @Test
    void testGetDeliveryById_ShouldThrowNullDeliveryException_WhenIdIsNull() {
        assertThrows(NullDeliveryException.class, () -> deliveryService.getDeliveryById(null));
        verify(deliveryRepository, times(0)).findById(any());
    }

    @Test
    void testGetDeliveryById_ShouldReturnEmptyOptional_WhenDeliveryNotFound() {
        UUID id = UUID.randomUUID();
        when(deliveryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Delivery> result = deliveryService.getDeliveryById(id);

        assertTrue(result.isEmpty());
        verify(deliveryRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllDeliveries_ShouldReturnAllDeliveries(){
        //Arrange
        Delivery delivery = createValidDelivery(false);
        List<Delivery> deliveries = List.of(delivery);

        when(deliveryRepository.findAll()).thenReturn(deliveries);

        //Act
        List<Delivery> result = deliveryService.getAllDeliveries();

        //Asset
        assertNotNull(result);
        assertEquals(deliveries, result);
        assertEquals(delivery, result.getFirst());
        //Verify interactions
        verify(deliveryRepository, times(1)).findAll();
    }

    private Delivery createValidDelivery(boolean validDate) {
        return new Delivery(UUID.randomUUID(), DeliveryMode.DELIVERY, ZonedDateTime.now(), validDate);
    }
}
