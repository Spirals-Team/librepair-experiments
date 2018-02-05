package io.descoped.client.api.builder;

import io.descoped.client.api.builder.impl.BuilderImpl;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 07/11/2017
 */
public class APIClient {

    public static Builder builder() {
        return new BuilderImpl();
    }

}
