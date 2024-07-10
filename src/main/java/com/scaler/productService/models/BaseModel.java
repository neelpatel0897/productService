package com.scaler.productService.models;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
// @MappedSuperclass
public abstract class BaseModel implements Serializable {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}