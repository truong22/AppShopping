package com.dailycodework.dreamshops.reponse;

import com.dailycodework.dreamshops.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductResponse {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
