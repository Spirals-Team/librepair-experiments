/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.management.monitor.ErrorResponseException;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in DiagnosticSettingsCategorys.
 */
public class DiagnosticSettingsCategorysInner {
    /** The Retrofit service to perform REST calls. */
    private DiagnosticSettingsCategorysService service;
    /** The service client containing this operation class. */
    private MonitorManagementClientImpl client;

    /**
     * Initializes an instance of DiagnosticSettingsCategorysInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public DiagnosticSettingsCategorysInner(Retrofit retrofit, MonitorManagementClientImpl client) {
        this.service = retrofit.create(DiagnosticSettingsCategorysService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for DiagnosticSettingsCategorys to be
     * used by Retrofit to perform actually REST calls.
     */
    interface DiagnosticSettingsCategorysService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.monitor.DiagnosticSettingsCategorys get" })
        @GET("{resourceUri}/providers/microsoft.insights/diagnosticSettingsCategories/{name}")
        Observable<Response<ResponseBody>> get(@Path(value = "resourceUri", encoded = true) String resourceUri, @Path("name") String name, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.monitor.DiagnosticSettingsCategorys list" })
        @GET("{resourceUri}/providers/microsoft.insights/diagnosticSettingsCategories")
        Observable<Response<ResponseBody>> list(@Path(value = "resourceUri", encoded = true) String resourceUri, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets the diagnostic settings category for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @param name The name of the diagnostic setting.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the DiagnosticSettingsCategoryResourceInner object if successful.
     */
    public DiagnosticSettingsCategoryResourceInner get(String resourceUri, String name) {
        return getWithServiceResponseAsync(resourceUri, name).toBlocking().single().body();
    }

    /**
     * Gets the diagnostic settings category for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @param name The name of the diagnostic setting.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<DiagnosticSettingsCategoryResourceInner> getAsync(String resourceUri, String name, final ServiceCallback<DiagnosticSettingsCategoryResourceInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceUri, name), serviceCallback);
    }

    /**
     * Gets the diagnostic settings category for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @param name The name of the diagnostic setting.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the DiagnosticSettingsCategoryResourceInner object
     */
    public Observable<DiagnosticSettingsCategoryResourceInner> getAsync(String resourceUri, String name) {
        return getWithServiceResponseAsync(resourceUri, name).map(new Func1<ServiceResponse<DiagnosticSettingsCategoryResourceInner>, DiagnosticSettingsCategoryResourceInner>() {
            @Override
            public DiagnosticSettingsCategoryResourceInner call(ServiceResponse<DiagnosticSettingsCategoryResourceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the diagnostic settings category for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @param name The name of the diagnostic setting.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the DiagnosticSettingsCategoryResourceInner object
     */
    public Observable<ServiceResponse<DiagnosticSettingsCategoryResourceInner>> getWithServiceResponseAsync(String resourceUri, String name) {
        if (resourceUri == null) {
            throw new IllegalArgumentException("Parameter resourceUri is required and cannot be null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("Parameter name is required and cannot be null.");
        }
        final String apiVersion = "2017-05-01-preview";
        return service.get(resourceUri, name, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<DiagnosticSettingsCategoryResourceInner>>>() {
                @Override
                public Observable<ServiceResponse<DiagnosticSettingsCategoryResourceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<DiagnosticSettingsCategoryResourceInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<DiagnosticSettingsCategoryResourceInner> getDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<DiagnosticSettingsCategoryResourceInner, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<DiagnosticSettingsCategoryResourceInner>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Lists the diagnostic settings categories for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the DiagnosticSettingsCategoryResourceCollectionInner object if successful.
     */
    public DiagnosticSettingsCategoryResourceCollectionInner list(String resourceUri) {
        return listWithServiceResponseAsync(resourceUri).toBlocking().single().body();
    }

    /**
     * Lists the diagnostic settings categories for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<DiagnosticSettingsCategoryResourceCollectionInner> listAsync(String resourceUri, final ServiceCallback<DiagnosticSettingsCategoryResourceCollectionInner> serviceCallback) {
        return ServiceFuture.fromResponse(listWithServiceResponseAsync(resourceUri), serviceCallback);
    }

    /**
     * Lists the diagnostic settings categories for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the DiagnosticSettingsCategoryResourceCollectionInner object
     */
    public Observable<DiagnosticSettingsCategoryResourceCollectionInner> listAsync(String resourceUri) {
        return listWithServiceResponseAsync(resourceUri).map(new Func1<ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner>, DiagnosticSettingsCategoryResourceCollectionInner>() {
            @Override
            public DiagnosticSettingsCategoryResourceCollectionInner call(ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Lists the diagnostic settings categories for the specified resource.
     *
     * @param resourceUri The identifier of the resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the DiagnosticSettingsCategoryResourceCollectionInner object
     */
    public Observable<ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner>> listWithServiceResponseAsync(String resourceUri) {
        if (resourceUri == null) {
            throw new IllegalArgumentException("Parameter resourceUri is required and cannot be null.");
        }
        final String apiVersion = "2017-05-01-preview";
        return service.list(resourceUri, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner>>>() {
                @Override
                public Observable<ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner> clientResponse = listDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<DiagnosticSettingsCategoryResourceCollectionInner> listDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<DiagnosticSettingsCategoryResourceCollectionInner, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<DiagnosticSettingsCategoryResourceCollectionInner>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

}
