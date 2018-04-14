package com.person.vincent.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: vincent
 * Date: 2018-04-11 10:01:00
 * Comment:
 */

@Data
@Alias("ProductDetail")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    private Long id;
    private Product product;
    private Integer status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Set<Comment> comments = new HashSet<>();
    private Set<ProductColor> colors = new HashSet<>();
    private Set<ProductSpecification> specifications = new HashSet<>();
    private Set<Banner> banners = new HashSet<>();
    private Set<ProductImage> images = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();
}
