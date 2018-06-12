package com.meteoradesigner.util.crud;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * This @code{UserDeleteSupport} class extends @code{DeleteSupport} class and declares the
 * implementation.
 */
@Component
public class UserDeleteSupport extends DeleteSupport<Integer> {

    @Override
    public Consumer<Integer> getDeleteSupportConsumer() {
        return (id) -> userRepository.deleteById(id);
    }
}