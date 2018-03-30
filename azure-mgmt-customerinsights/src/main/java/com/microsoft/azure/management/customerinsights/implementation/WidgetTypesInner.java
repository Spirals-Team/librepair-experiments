/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.customerinsights.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceFuture;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in WidgetTypes.
 */
public class WidgetTypesInner {
    /** The Retrofit service to perform REST calls. */
    private WidgetTypesService service;
    /** The service client containing this operation class. */
    private CustomerInsightsManagementClientImpl client;

    /**
     * Initializes an instance of WidgetTypesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public WidgetTypesInner(Retrofit retrofit, CustomerInsightsManagementClientImpl client) {
        this.service = retrofit.create(WidgetTypesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for WidgetTypes to be
     * used by Retrofit to perform actually REST calls.
     */
    interface WidgetTypesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.customerinsights.WidgetTypes listByHub" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.CustomerInsights/hubs/{hubName}/widgetTypes")
        Observable<Response<ResponseBody>> listByHub(@Path("resourceGroupName") String resourceGroupName, @Path("hubName") String hubName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.customerinsights.WidgetTypes get" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.CustomerInsights/hubs/{hubName}/widgetTypes/{widgetTypeName}")
        Observable<Response<ResponseBody>> get(@Path("resourceGroupName") String resourceGroupName, @Path("hubName") String hubName, @Path("widgetTypeName") String widgetTypeName, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.customerinsights.WidgetTypes listByHubNext" })
        @GET
        Observable<Response<ResponseBody>> listByHubNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;WidgetTypeResourceFormatInner&gt; object if successful.
     */
    public PagedList<WidgetTypeResourceFormatInner> listByHub(final String resourceGroupName, final String hubName) {
        ServiceResponse<Page<WidgetTypeResourceFormatInner>> response = listByHubSinglePageAsync(resourceGroupName, hubName).toBlocking().single();
        return new PagedList<WidgetTypeResourceFormatInner>(response.body()) {
            @Override
            public Page<WidgetTypeResourceFormatInner> nextPage(String nextPageLink) {
                return listByHubNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<WidgetTypeResourceFormatInner>> listByHubAsync(final String resourceGroupName, final String hubName, final ListOperationCallback<WidgetTypeResourceFormatInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listByHubSinglePageAsync(resourceGroupName, hubName),
            new Func1<String, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(String nextPageLink) {
                    return listByHubNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;WidgetTypeResourceFormatInner&gt; object
     */
    public Observable<Page<WidgetTypeResourceFormatInner>> listByHubAsync(final String resourceGroupName, final String hubName) {
        return listByHubWithServiceResponseAsync(resourceGroupName, hubName)
            .map(new Func1<ServiceResponse<Page<WidgetTypeResourceFormatInner>>, Page<WidgetTypeResourceFormatInner>>() {
                @Override
                public Page<WidgetTypeResourceFormatInner> call(ServiceResponse<Page<WidgetTypeResourceFormatInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;WidgetTypeResourceFormatInner&gt; object
     */
    public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> listByHubWithServiceResponseAsync(final String resourceGroupName, final String hubName) {
        return listByHubSinglePageAsync(resourceGroupName, hubName)
            .concatMap(new Func1<ServiceResponse<Page<WidgetTypeResourceFormatInner>>, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(ServiceResponse<Page<WidgetTypeResourceFormatInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listByHubNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all available widget types in the specified hub.
     *
    ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> * @param resourceGroupName The name of the resource group.
    ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> * @param hubName The name of the hub.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;WidgetTypeResourceFormatInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> listByHubSinglePageAsync(final String resourceGroupName, final String hubName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (hubName == null) {
            throw new IllegalArgumentException("Parameter hubName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.listByHub(resourceGroupName, hubName, this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> result = listByHubDelegate(response);
                        return Observable.just(new ServiceResponse<Page<WidgetTypeResourceFormatInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> listByHubDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<WidgetTypeResourceFormatInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<WidgetTypeResourceFormatInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets a widget type in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @param widgetTypeName The name of the widget type.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the WidgetTypeResourceFormatInner object if successful.
     */
    public WidgetTypeResourceFormatInner get(String resourceGroupName, String hubName, String widgetTypeName) {
        return getWithServiceResponseAsync(resourceGroupName, hubName, widgetTypeName).toBlocking().single().body();
    }

    /**
     * Gets a widget type in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @param widgetTypeName The name of the widget type.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<WidgetTypeResourceFormatInner> getAsync(String resourceGroupName, String hubName, String widgetTypeName, final ServiceCallback<WidgetTypeResourceFormatInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, hubName, widgetTypeName), serviceCallback);
    }

    /**
     * Gets a widget type in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @param widgetTypeName The name of the widget type.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the WidgetTypeResourceFormatInner object
     */
    public Observable<WidgetTypeResourceFormatInner> getAsync(String resourceGroupName, String hubName, String widgetTypeName) {
        return getWithServiceResponseAsync(resourceGroupName, hubName, widgetTypeName).map(new Func1<ServiceResponse<WidgetTypeResourceFormatInner>, WidgetTypeResourceFormatInner>() {
            @Override
            public WidgetTypeResourceFormatInner call(ServiceResponse<WidgetTypeResourceFormatInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets a widget type in the specified hub.
     *
     * @param resourceGroupName The name of the resource group.
     * @param hubName The name of the hub.
     * @param widgetTypeName The name of the widget type.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the WidgetTypeResourceFormatInner object
     */
    public Observable<ServiceResponse<WidgetTypeResourceFormatInner>> getWithServiceResponseAsync(String resourceGroupName, String hubName, String widgetTypeName) {
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (hubName == null) {
            throw new IllegalArgumentException("Parameter hubName is required and cannot be null.");
        }
        if (widgetTypeName == null) {
            throw new IllegalArgumentException("Parameter widgetTypeName is required and cannot be null.");
        }
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.get(resourceGroupName, hubName, widgetTypeName, this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<WidgetTypeResourceFormatInner>>>() {
                @Override
                public Observable<ServiceResponse<WidgetTypeResourceFormatInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<WidgetTypeResourceFormatInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<WidgetTypeResourceFormatInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<WidgetTypeResourceFormatInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<WidgetTypeResourceFormatInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;WidgetTypeResourceFormatInner&gt; object if successful.
     */
    public PagedList<WidgetTypeResourceFormatInner> listByHubNext(final String nextPageLink) {
        ServiceResponse<Page<WidgetTypeResourceFormatInner>> response = listByHubNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<WidgetTypeResourceFormatInner>(response.body()) {
            @Override
            public Page<WidgetTypeResourceFormatInner> nextPage(String nextPageLink) {
                return listByHubNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<WidgetTypeResourceFormatInner>> listByHubNextAsync(final String nextPageLink, final ServiceFuture<List<WidgetTypeResourceFormatInner>> serviceFuture, final ListOperationCallback<WidgetTypeResourceFormatInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listByHubNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(String nextPageLink) {
                    return listByHubNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;WidgetTypeResourceFormatInner&gt; object
     */
    public Observable<Page<WidgetTypeResourceFormatInner>> listByHubNextAsync(final String nextPageLink) {
        return listByHubNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<WidgetTypeResourceFormatInner>>, Page<WidgetTypeResourceFormatInner>>() {
                @Override
                public Page<WidgetTypeResourceFormatInner> call(ServiceResponse<Page<WidgetTypeResourceFormatInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Gets all available widget types in the specified hub.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;WidgetTypeResourceFormatInner&gt; object
     */
    public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> listByHubNextWithServiceResponseAsync(final String nextPageLink) {
        return listByHubNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<WidgetTypeResourceFormatInner>>, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(ServiceResponse<Page<WidgetTypeResourceFormatInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listByHubNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Gets all available widget types in the specified hub.
     *
    ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;WidgetTypeResourceFormatInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> listByHubNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listByHubNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<WidgetTypeResourceFormatInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> result = listByHubNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<WidgetTypeResourceFormatInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<WidgetTypeResourceFormatInner>> listByHubNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<WidgetTypeResourceFormatInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<WidgetTypeResourceFormatInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
