package com.application.microservices.product.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String description, String skuCode, BigDecimal price) {

}
