package com.boom.photoshoot_app.service;

import com.boom.photoshoot_app.data.models.Order;
import com.boom.photoshoot_app.data.payloads.request.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderServiceInterface {
    Order createOrder(OrderRequest orderRequest);
    Order scheduleOrder(Long orderId, OrderScheduleRequest orderScheduleRequest);
    Order assignPhotographer(Long orderId, OrderAssignPhotographerRequest orderAssignPhotographerRequest);
    Order uploadPhotos(Long orderId, OrderUploadPhotosRequest orderUploadPhotosRequest);
    Order verifyOrder(Long orderId, OrderVerifiedRequest orderVerifiedRequest);
    Order cancelOrder(Long orderId);
    Order getOrder(Long orderId);
    List<Order> getAllOrders();
}
