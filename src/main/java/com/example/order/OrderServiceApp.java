package com.example.order;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class OrderServiceApp {
    record Customer(String id) {}

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);
        server.createContext("/orders", OrderServiceApp::handleOrders);
        server.start();
        System.out.println("order-service listening on 8082");
    }

    static void handleOrders(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && "/orders".equals(path)) {
            write(exchange, 201, createOrder(new Customer("42")));
            return;
        }
        if ("GET".equals(exchange.getRequestMethod()) && path.startsWith("/orders/user/")) {
            write(exchange, 200, "[]");
            return;
        }
        if ("GET".equals(exchange.getRequestMethod()) && path.startsWith("/orders/")) {
            write(exchange, 200, "{\"id\":\"1001\"}");
            return;
        }
        write(exchange, 404, "{\"error\":\"not found\"}");
    }

    public static String createOrder(Customer customer) {
        if (customer == null || customer.id() == null || customer.id().isBlank()) {
            return "{\"error\":\"customer is required\"}";
        }
        String customerId = customer.id();
        return "{\"orderId\":\"ord-1\",\"customerId\":\"" + customerId + "\"}";
    }

    private static void write(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
