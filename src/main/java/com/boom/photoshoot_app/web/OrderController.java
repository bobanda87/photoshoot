package com.boom.photoshoot_app.web;

import com.boom.photoshoot_app.data.models.Order;
import com.boom.photoshoot_app.data.payloads.request.*;
import com.boom.photoshoot_app.data.payloads.response.OrderResponse;
import com.boom.photoshoot_app.service.OrderService;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found, please check if the document that you are looking for exists"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
}
)
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders () {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Order> getOrderById (@PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@Validated @RequestBody OrderRequest orderRequest) {
        Order newOrder = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<Order> scheduleOrder(@PathVariable Long id, @Validated @RequestBody OrderScheduleRequest orderScheduleRequest) {
        Order order = orderService.scheduleOrder(id, orderScheduleRequest);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/assign/{id}")
    public ResponseEntity<Order> assignPhotographer(@PathVariable Long id, @Validated @RequestBody OrderAssignPhotographerRequest orderAssignPhotographerRequest) {
        Order order = orderService.assignPhotographer(id, orderAssignPhotographerRequest);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<Order> uploadPhotos(@PathVariable Long id, @Validated @RequestBody OrderUploadPhotosRequest orderUploadPhotosRequest) {
        Order order = orderService.uploadPhotos(id, orderUploadPhotosRequest);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<Order> verifyOrder(@PathVariable Long id, @Validated @RequestBody OrderVerifiedRequest orderVerifiedRequest) {
        Order order = orderService.verifyOrder(id, orderVerifiedRequest);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
}
