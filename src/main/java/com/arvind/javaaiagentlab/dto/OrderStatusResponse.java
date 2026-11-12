package com.arvind.javaaiagentlab.dto;

public record OrderStatusResponse(
        String orderId,
        String orderStatus,
        String paymentStatus,
        boolean cancellable
) {
}