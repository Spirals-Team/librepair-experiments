package com.d4dl.hellofib.helloworld;

import lombok.Data;

import javax.persistence.Entity;

/**
 * A typical hello world entity that has an attribute for the name of the world and the name
 * of the greeter.
 */
@Entity
@Data
public class HelloWorld {
    private String name;
    private String greeter;
}
