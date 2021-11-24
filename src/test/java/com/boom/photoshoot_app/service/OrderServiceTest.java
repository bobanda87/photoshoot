package com.boom.photoshoot_app.service;

import com.boom.photoshoot_app.data.models.Order;
import com.boom.photoshoot_app.data.models.OrderState;
import com.boom.photoshoot_app.data.models.PhotoType;
import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.data.payloads.request.OrderAssignPhotographerRequest;
import com.boom.photoshoot_app.data.payloads.request.OrderRequest;
import com.boom.photoshoot_app.data.payloads.request.OrderUploadPhotosRequest;
import com.boom.photoshoot_app.data.payloads.request.OrderVerifiedRequest;
import com.boom.photoshoot_app.data.repository.OrderRepository;
import com.boom.photoshoot_app.data.repository.PhotographerRepository;
import com.boom.photoshoot_app.exception.ResourceUpdateNotAllowedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE )
public class OrderServiceTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private OrderService orderService;

    private OrderRequest orderRequest;

    @BeforeEach
    public void setUp() {
        orderRequest = new OrderRequest("a", "b", "test@domain.com", "+1111111", PhotoType.Food);
        LocalDateTime localDateTime = LocalDateTime.parse("2021-11-22T19:00:00.000");
        orderRequest.setDateTime(localDateTime);
    }


    @Test
    public void createOrderWithinBusinessHoursTest() {
        Order order = orderService.createOrder(orderRequest);
        assertEquals(order.getState(), OrderState.PENDING);
    }

    @Test
    public void createOrderNotWithinBusinessHoursTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2021-11-22T21:00:00.000");
        orderRequest.setDateTime(localDateTime);

        Order order = orderService.createOrder(orderRequest);
        assertEquals(order.getState(), OrderState.UNSCHEDULED);
    }

    @Test
    public void assignPhotographerTest() {
        Order order = orderService.createOrder(orderRequest);

        Photographer photographer = new Photographer(1L, "a", "b");
        photographerRepository.save(photographer);

        OrderAssignPhotographerRequest photographerRequest = new OrderAssignPhotographerRequest();
        photographerRequest.setPhotographerId(photographer.getId());

        Order updatedOrder = orderService.assignPhotographer(order.getId(), photographerRequest);
        assertEquals(updatedOrder.getState(), OrderState.ASSIGNED);
    }

    @Test
    public void assignPhotographerNotAllowedTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.UNSCHEDULED);
        orderRepository.save(order);

        Photographer photographer = new Photographer(1L, "a", "b");
        photographerRepository.save(photographer);

        OrderAssignPhotographerRequest photographerRequest = new OrderAssignPhotographerRequest();
        photographerRequest.setPhotographerId(photographer.getId());

        assertThrows(ResourceUpdateNotAllowedException.class, () -> {
            orderService.assignPhotographer(order.getId(), photographerRequest);
        });
    }

    @Test
    public void uploadPhotosTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.ASSIGNED);
        orderRepository.save(order);

        OrderUploadPhotosRequest orderUploadPhotosRequest = new OrderUploadPhotosRequest();
        orderUploadPhotosRequest.setPhotosZip("test".getBytes());
        Order updatedOrder = orderService.uploadPhotos(order.getId(), orderUploadPhotosRequest);

        assertEquals(updatedOrder.getState(), OrderState.UPLOADED);
    }

    @Test
    public void uploadPhotosNotAllowedTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.UNSCHEDULED);
        orderRepository.save(order);

        OrderUploadPhotosRequest orderUploadPhotosRequest = new OrderUploadPhotosRequest();
        orderUploadPhotosRequest.setPhotosZip("test".getBytes());

        assertThrows(ResourceUpdateNotAllowedException.class, () -> {
            orderService.uploadPhotos(order.getId(), orderUploadPhotosRequest);
        });
    }

    @Test
    public void verifyOrderTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.UPLOADED);
        orderRepository.save(order);

        OrderVerifiedRequest orderVerifiedRequest = new OrderVerifiedRequest();
        orderVerifiedRequest.setVerified(true);
        Order updatedOrder = orderService.verifyOrder(order.getId(), orderVerifiedRequest);

        assertEquals(updatedOrder.getState(), OrderState.COMPLETED);
    }

    @Test
    public void verifyOrderNotAllowedTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.UNSCHEDULED);
        orderRepository.save(order);

        OrderVerifiedRequest orderVerifiedRequest = new OrderVerifiedRequest();
        orderVerifiedRequest.setVerified(true);

        assertThrows(ResourceUpdateNotAllowedException.class, () -> {
            orderService.verifyOrder(order.getId(), orderVerifiedRequest);
        });
    }

    @Test
    public void verifyOrderNotApprovedTest() {
        Order order = orderService.createOrder(orderRequest);
        order.setState(OrderState.UPLOADED);
        orderRepository.save(order);

        OrderVerifiedRequest orderVerifiedRequest = new OrderVerifiedRequest();
        orderVerifiedRequest.setVerified(false);

        Order updatedOrder = orderService.verifyOrder(order.getId(), orderVerifiedRequest);

        assertEquals(updatedOrder.getState(), OrderState.ASSIGNED);
    }

    @Test
    public void cancelOrderTest() {
        Order order = orderService.createOrder(orderRequest);

        Order updatedOrder = orderService.cancelOrder(order.getId());

        assertEquals(updatedOrder.getState(), OrderState.CANCELLED);
    }
}
