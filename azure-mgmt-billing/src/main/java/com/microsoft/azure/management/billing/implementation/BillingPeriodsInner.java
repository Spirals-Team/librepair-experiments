/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.billing.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceFuture;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.management.billing.ErrorResponseException;
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
 * in BillingPeriods.
 */
public class BillingPeriodsInner {
    /** The Retrofit service to perform REST calls. */
    private BillingPeriodsService service;
    /** The service client containing this operation class. */
    private BillingManagementClientImpl client;

    /**
     * Initializes an instance of BillingPeriodsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public BillingPeriodsInner(Retrofit retrofit, BillingManagementClientImpl client) {
        this.service = retrofit.create(BillingPeriodsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for BillingPeriods to be
     * used by Retrofit to perform actually REST calls.
     */
    interface BillingPeriodsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.BillingPeriods list" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Billing/billingPeriods")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Query("$filter") String filter, @Query("$skiptoken") String skiptoken, @Query("$top") Integer top, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.BillingPeriods get" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Billing/billingPeriods/{billingPeriodName}")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("billingPeriodName") String billingPeriodName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.BillingPeriods listNext" })
        @GET
        Observable<Response<ResponseBody>> listNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;BillingPeriodInner&gt; object if successful.
     */
    public PagedList<BillingPeriodInner> list() {
        ServiceResponse<Page<BillingPeriodInner>> response = listSinglePageAsync().toBlocking().single();
        return new PagedList<BillingPeriodInner>(response.body()) {
            @Override
            public Page<BillingPeriodInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<BillingPeriodInner>> listAsync(final ListOperationCallback<BillingPeriodInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(),
            new Func1<String, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<Page<BillingPeriodInner>> listAsync() {
        return listWithServiceResponseAsync()
            .map(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Page<BillingPeriodInner>>() {
                @Override
                public Page<BillingPeriodInner> call(ServiceResponse<Page<BillingPeriodInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listWithServiceResponseAsync() {
        return listSinglePageAsync()
            .concatMap(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(ServiceResponse<Page<BillingPeriodInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;BillingPeriodInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listSinglePageAsync() {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final String filter = null;
        final String skiptoken = null;
        final Integer top = null;
        return service.list(this.client.subscriptionId(), this.client.apiVersion(), filter, skiptoken, top, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<BillingPeriodInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<BillingPeriodInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param filter May be used to filter billing periods by billingPeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N billing periods.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;BillingPeriodInner&gt; object if successful.
     */
    public PagedList<BillingPeriodInner> list(final String filter, final String skiptoken, final Integer top) {
        ServiceResponse<Page<BillingPeriodInner>> response = listSinglePageAsync(filter, skiptoken, top).toBlocking().single();
        return new PagedList<BillingPeriodInner>(response.body()) {
            @Override
            public Page<BillingPeriodInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param filter May be used to filter billing periods by billingPeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N billing periods.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<BillingPeriodInner>> listAsync(final String filter, final String skiptoken, final Integer top, final ListOperationCallback<BillingPeriodInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(filter, skiptoken, top),
            new Func1<String, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param filter May be used to filter billing periods by billingPeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N billing periods.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<Page<BillingPeriodInner>> listAsync(final String filter, final String skiptoken, final Integer top) {
        return listWithServiceResponseAsync(filter, skiptoken, top)
            .map(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Page<BillingPeriodInner>>() {
                @Override
                public Page<BillingPeriodInner> call(ServiceResponse<Page<BillingPeriodInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param filter May be used to filter billing periods by billingPeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N billing periods.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listWithServiceResponseAsync(final String filter, final String skiptoken, final Integer top) {
        return listSinglePageAsync(filter, skiptoken, top)
            .concatMap(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(ServiceResponse<Page<BillingPeriodInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
    ServiceResponse<PageImpl<BillingPeriodInner>> * @param filter May be used to filter billing periods by billingPeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
    ServiceResponse<PageImpl<BillingPeriodInner>> * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
    ServiceResponse<PageImpl<BillingPeriodInner>> * @param top May be used to limit the number of results to the most recent N billing periods.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;BillingPeriodInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listSinglePageAsync(final String filter, final String skiptoken, final Integer top) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.list(this.client.subscriptionId(), this.client.apiVersion(), filter, skiptoken, top, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<BillingPeriodInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<BillingPeriodInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<BillingPeriodInner>> listDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<BillingPeriodInner>, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<BillingPeriodInner>>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Gets a named billing period.
     *
     * @param billingPeriodName The name of a BillingPeriod resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the BillingPeriodInner object if successful.
     */
    public BillingPeriodInner get(String billingPeriodName) {
        return getWithServiceResponseAsync(billingPeriodName).toBlocking().single().body();
    }

    /**
     * Gets a named billing period.
     *
     * @param billingPeriodName The name of a BillingPeriod resource.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<BillingPeriodInner> getAsync(String billingPeriodName, final ServiceCallback<BillingPeriodInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(billingPeriodName), serviceCallback);
    }

    /**
     * Gets a named billing period.
     *
     * @param billingPeriodName The name of a BillingPeriod resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BillingPeriodInner object
     */
    public Observable<BillingPeriodInner> getAsync(String billingPeriodName) {
        return getWithServiceResponseAsync(billingPeriodName).map(new Func1<ServiceResponse<BillingPeriodInner>, BillingPeriodInner>() {
            @Override
            public BillingPeriodInner call(ServiceResponse<BillingPeriodInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets a named billing period.
     *
     * @param billingPeriodName The name of a BillingPeriod resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BillingPeriodInner object
     */
    public Observable<ServiceResponse<BillingPeriodInner>> getWithServiceResponseAsync(String billingPeriodName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (billingPeriodName == null) {
            throw new IllegalArgumentException("Parameter billingPeriodName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.get(this.client.subscriptionId(), billingPeriodName, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<BillingPeriodInner>>>() {
                @Override
                public Observable<ServiceResponse<BillingPeriodInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<BillingPeriodInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<BillingPeriodInner> getDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<BillingPeriodInner, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<BillingPeriodInner>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;BillingPeriodInner&gt; object if successful.
     */
    public PagedList<BillingPeriodInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<BillingPeriodInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<BillingPeriodInner>(response.body()) {
            @Override
            public Page<BillingPeriodInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<BillingPeriodInner>> listNextAsync(final String nextPageLink, final ServiceFuture<List<BillingPeriodInner>> serviceFuture, final ListOperationCallback<BillingPeriodInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<Page<BillingPeriodInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Page<BillingPeriodInner>>() {
                @Override
                public Page<BillingPeriodInner> call(ServiceResponse<Page<BillingPeriodInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;BillingPeriodInner&gt; object
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<BillingPeriodInner>>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(ServiceResponse<Page<BillingPeriodInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available billing periods for a subscription in reverse chronological order.
     *
    ServiceResponse<PageImpl<BillingPeriodInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;BillingPeriodInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<BillingPeriodInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<BillingPeriodInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<BillingPeriodInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<BillingPeriodInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<BillingPeriodInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<BillingPeriodInner>> listNextDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<BillingPeriodInner>, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<BillingPeriodInner>>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

}
