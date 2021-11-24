package com.boom.photoshoot_app.data.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE )
public class OrderEntityTest {
    @Test
    public void dateWithinBusinessHoursTest() {
        Order order = new Order();
        LocalDateTime localDateTime = LocalDateTime.parse("2021-11-22T19:07:47.167");
        order.setDateTime(localDateTime);
        assertTrue(order.isWithinBusinessHours());
    }

    @Test
    public void dateNotWithinBusinessHoursTest() {
        Order order = new Order();
        LocalDateTime localDateTime = LocalDateTime.parse("2021-11-22T20:07:47.167");
        order.setDateTime(localDateTime);
        assertFalse(order.isWithinBusinessHours());
    }
}
