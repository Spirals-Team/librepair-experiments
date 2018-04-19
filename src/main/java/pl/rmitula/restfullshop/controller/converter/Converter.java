package pl.rmitula.restfullshop.controller.converter;

import pl.rmitula.restfullshop.model.Category;
import pl.rmitula.restfullshop.model.Order;
import pl.rmitula.restfullshop.model.Product;
import pl.rmitula.restfullshop.model.User;
import pl.rmitula.restfullshop.model.dto.CategoryDto;
import pl.rmitula.restfullshop.model.dto.OrderDto;
import pl.rmitula.restfullshop.model.dto.ProductDto;
import pl.rmitula.restfullshop.model.dto.UserDto;

public class Converter {

    public static User fromUserDto(UserDto userDto) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserDto toUserDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setUsername(user.getUsername());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(null);
        return userDto;
    }

    public static Product fromProductDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(productDto.getName());
        product.setQuanityInStock(productDto.getQuanityInStock());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public static ProductDto toProductDto(Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategory(product.getCategory().getId());//?
        productDto.setName(product.getName());
        productDto.setQuanityInStock(product.getQuanityInStock());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


    public static Category fromCategoryDto(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public static OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProductId(order.getProduct().getId());
        orderDto.setQuanity(order.getQuantity());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
}
