package com.example.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OrderServiceAppTest {
    @Test
    void createsOrderForKnownCustomer() {
        assertEquals(
                "{\"orderId\":\"ord-1\",\"customerId\":\"42\"}",
                OrderServiceApp.createOrder(new OrderServiceApp.Customer("42")));
    }

    @Test
    void handlesMissingCustomerSafely() {
        assertEquals("{\"error\":\"customer is required\"}", OrderServiceApp.createOrder(null));
    }
}
