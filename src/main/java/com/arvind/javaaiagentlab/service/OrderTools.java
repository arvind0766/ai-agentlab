
package com.arvind.javaaiagentlab.service;

import com.arvind.javaaiagentlab.dto.IncidentContext;
import com.arvind.javaaiagentlab.dto.OrderStatusResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderTools {

    @Tool(description = "Get order status, payment status, and cancellation eligibility using an order ID")
    public OrderStatusResponse getOrderStatus(String orderId) {

        if ("ORD-101".equals(orderId)) {
            return new OrderStatusResponse(
                    "ORD-101",
                    "SHIPPED",
                    "PAID",
                    false
            );
        }

        if ("ORD-102".equals(orderId)) {
            return new OrderStatusResponse(
                    "ORD-102",
                    "PROCESSING",
                    "PAID",
                    true
            );
        }

        return new OrderStatusResponse(
                orderId,
                "NOT_FOUND",
                "UNKNOWN",
                false
        );
    }
}
