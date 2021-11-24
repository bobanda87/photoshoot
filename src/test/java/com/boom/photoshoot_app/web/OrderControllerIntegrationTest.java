package com.boom.photoshoot_app.web;

import com.boom.photoshoot_app.data.models.Order;
import com.boom.photoshoot_app.data.models.PhotoType;
import com.boom.photoshoot_app.data.models.Photographer;
import com.boom.photoshoot_app.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    private List<Order> orders = new ArrayList<>();;

    @BeforeEach
    public void setUp() {
        Order order = new Order(1L, "a", "b", "test@domain.com", "+1111111", PhotoType.Food);
        this.orders.add(order);
    }

    @Test
    public void getAllOrderssListTest() throws Exception {
        when(orderService.getAllOrders()).thenReturn(orders);
        this.mockMvc.perform(get("/order/all")).andExpect(status().isOk());
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        Order order = this.orders.get(0);
        when(orderService.getOrder(order.getId())).thenReturn(order);
        this.mockMvc.perform(get("/order/find/" + order.getId())).andExpect(status().isOk());
    }

    @Test
    public void addOrderTest() throws Exception {
        String body = "{\"name\": \"c\", \"surname\": \"d\", \"email\" : \"test@domain.com\", \"cellNumber\": \"222222\", \"photoType\": \"Food\"}";

        this.mockMvc.perform(post("/order/add").content(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
    }

    @Test
    public void scheduleOrderTest() throws Exception {
        Order order = this.orders.get(0);

        String body = "{\"dateTime\": \"2021-11-22T19:07:47.167\"}";

        this.mockMvc.perform(put("/order/schedule/" + order.getId()).content(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void assignPhotographerTest() throws Exception {
        Photographer photographer = new Photographer(1L, "a", "b");

        String body = "{\"photographerId\": 1}";

        this.mockMvc.perform(put("/order/assign/" + photographer.getId()).content(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
