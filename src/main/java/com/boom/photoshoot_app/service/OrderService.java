package com.boom.photoshoot_app.service;

import com.boom.photoshoot_app.data.models.Order;
import com.boom.photoshoot_app.data.models.OrderState;
import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.data.payloads.request.*;
import com.boom.photoshoot_app.data.repository.OrderRepository;
import com.boom.photoshoot_app.exception.ResourceNotFoundException;
import com.boom.photoshoot_app.exception.ResourceUpdateNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PhotographerService photographerService;

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setName(orderRequest.getName());
        order.setSurname(orderRequest.getSurname());
        order.setEmail(orderRequest.getEmail());
        order.setCellNumber(orderRequest.getCellNumber());
        order.setPhotoType(orderRequest.getPhotoType());
        order.setTitle(orderRequest.getTitle());
        order.setLogisticInfo(orderRequest.getLogisticInfo());
        order.setDateTime(orderRequest.getDateTime());

        if (order.isWithinBusinessHours()) {
            order.setState(OrderState.PENDING);
        } else {
            order.setState(OrderState.UNSCHEDULED);
        }
        orderRepository.save(order);

        return order;
    }

    @Override
    public Order scheduleOrder(Long orderId, OrderScheduleRequest orderScheduleRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (!order.canChangeState()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            order.setDateTime(orderScheduleRequest.getDateTime());
            if (order.isWithinBusinessHours()) {
                order.setState(OrderState.PENDING);
            } else {
                order.setState(OrderState.UNSCHEDULED);
            }

            orderRepository.save(order);
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order assignPhotographer(Long orderId, OrderAssignPhotographerRequest orderAssignPhotographerRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (!order.canChangeState()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            if (!order.canBeAssigned()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            Photographer photographer = photographerService.getPhotographer(orderAssignPhotographerRequest.getPhotographerId());

            order.setPhotographer(photographer);
            order.setState(OrderState.ASSIGNED);
            orderRepository.save(order);
            return order;
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    @Override
    public Order uploadPhotos(Long orderId, OrderUploadPhotosRequest orderUploadPhotosRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (!order.canChangeState()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            if (!order.canBeUploaded()) {
                throw new ResourceUpdateNotAllowedException("Order", "status", order.canBeUploaded());
            }

            // upload photos from orderUploadPhotosRequest.getPhotos()
            order.setState(OrderState.UPLOADED);
            orderRepository.save(order);
            return order;
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    @Override
    public Order verifyOrder(Long orderId, OrderVerifiedRequest orderVerifiedRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (!order.canChangeState()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            if (!order.canBeCompleted()) {
                throw new ResourceUpdateNotAllowedException("Order", "id", orderId);
            }

            if (orderVerifiedRequest.isVerified()) {
                order.setState(OrderState.COMPLETED);
            } else {
                order.setState(OrderState.ASSIGNED);
            }

            orderRepository.save(order);
            return order;
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            order.setState(OrderState.CANCELLED);
            orderRepository.save(order);
            return order;
        } else {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
