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
 * in Invoices.
 */
public class InvoicesInner {
    /** The Retrofit service to perform REST calls. */
    private InvoicesService service;
    /** The service client containing this operation class. */
    private BillingManagementClientImpl client;

    /**
     * Initializes an instance of InvoicesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public InvoicesInner(Retrofit retrofit, BillingManagementClientImpl client) {
        this.service = retrofit.create(InvoicesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Invoices to be
     * used by Retrofit to perform actually REST calls.
     */
    interface InvoicesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.Invoices list" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Billing/invoices")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Query("$expand") String expand, @Query("$filter") String filter, @Query("$skiptoken") String skiptoken, @Query("$top") Integer top, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.Invoices get" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Billing/invoices/{invoiceName}")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("invoiceName") String invoiceName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.Invoices getLatest" })
        @GET("subscriptions/{subscriptionId}/providers/Microsoft.Billing/invoices/latest")
        Observable<Response<ResponseBody>> getLatest(@Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.billing.Invoices listNext" })
        @GET
        Observable<Response<ResponseBody>> listNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;InvoiceInner&gt; object if successful.
     */
    public PagedList<InvoiceInner> list() {
        ServiceResponse<Page<InvoiceInner>> response = listSinglePageAsync().toBlocking().single();
        return new PagedList<InvoiceInner>(response.body()) {
            @Override
            public Page<InvoiceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<InvoiceInner>> listAsync(final ListOperationCallback<InvoiceInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(),
            new Func1<String, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<Page<InvoiceInner>> listAsync() {
        return listWithServiceResponseAsync()
            .map(new Func1<ServiceResponse<Page<InvoiceInner>>, Page<InvoiceInner>>() {
                @Override
                public Page<InvoiceInner> call(ServiceResponse<Page<InvoiceInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listWithServiceResponseAsync() {
        return listSinglePageAsync()
            .concatMap(new Func1<ServiceResponse<Page<InvoiceInner>>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(ServiceResponse<Page<InvoiceInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;InvoiceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listSinglePageAsync() {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        final String expand = null;
        final String filter = null;
        final String skiptoken = null;
        final Integer top = null;
        return service.list(this.client.subscriptionId(), this.client.apiVersion(), expand, filter, skiptoken, top, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<InvoiceInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<InvoiceInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param expand May be used to expand the downloadUrl property within a list of invoices. This enables download links to be generated for multiple invoices at once. By default, downloadURLs are not included when listing invoices.
     * @param filter May be used to filter invoices by invoicePeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N invoices.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;InvoiceInner&gt; object if successful.
     */
    public PagedList<InvoiceInner> list(final String expand, final String filter, final String skiptoken, final Integer top) {
        ServiceResponse<Page<InvoiceInner>> response = listSinglePageAsync(expand, filter, skiptoken, top).toBlocking().single();
        return new PagedList<InvoiceInner>(response.body()) {
            @Override
            public Page<InvoiceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param expand May be used to expand the downloadUrl property within a list of invoices. This enables download links to be generated for multiple invoices at once. By default, downloadURLs are not included when listing invoices.
     * @param filter May be used to filter invoices by invoicePeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N invoices.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<InvoiceInner>> listAsync(final String expand, final String filter, final String skiptoken, final Integer top, final ListOperationCallback<InvoiceInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(expand, filter, skiptoken, top),
            new Func1<String, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param expand May be used to expand the downloadUrl property within a list of invoices. This enables download links to be generated for multiple invoices at once. By default, downloadURLs are not included when listing invoices.
     * @param filter May be used to filter invoices by invoicePeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N invoices.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<Page<InvoiceInner>> listAsync(final String expand, final String filter, final String skiptoken, final Integer top) {
        return listWithServiceResponseAsync(expand, filter, skiptoken, top)
            .map(new Func1<ServiceResponse<Page<InvoiceInner>>, Page<InvoiceInner>>() {
                @Override
                public Page<InvoiceInner> call(ServiceResponse<Page<InvoiceInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param expand May be used to expand the downloadUrl property within a list of invoices. This enables download links to be generated for multiple invoices at once. By default, downloadURLs are not included when listing invoices.
     * @param filter May be used to filter invoices by invoicePeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
     * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
     * @param top May be used to limit the number of results to the most recent N invoices.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listWithServiceResponseAsync(final String expand, final String filter, final String skiptoken, final Integer top) {
        return listSinglePageAsync(expand, filter, skiptoken, top)
            .concatMap(new Func1<ServiceResponse<Page<InvoiceInner>>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(ServiceResponse<Page<InvoiceInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
    ServiceResponse<PageImpl<InvoiceInner>> * @param expand May be used to expand the downloadUrl property within a list of invoices. This enables download links to be generated for multiple invoices at once. By default, downloadURLs are not included when listing invoices.
    ServiceResponse<PageImpl<InvoiceInner>> * @param filter May be used to filter invoices by invoicePeriodEndDate. The filter supports 'eq', 'lt', 'gt', 'le', 'ge', and 'and'. It does not currently support 'ne', 'or', or 'not'.
    ServiceResponse<PageImpl<InvoiceInner>> * @param skiptoken Skiptoken is only used if a previous operation returned a partial result. If a previous response contains a nextLink element, the value of the nextLink element will include a skiptoken parameter that specifies a starting point to use for subsequent calls.
    ServiceResponse<PageImpl<InvoiceInner>> * @param top May be used to limit the number of results to the most recent N invoices.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;InvoiceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listSinglePageAsync(final String expand, final String filter, final String skiptoken, final Integer top) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.list(this.client.subscriptionId(), this.client.apiVersion(), expand, filter, skiptoken, top, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<InvoiceInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<InvoiceInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<InvoiceInner>> listDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<InvoiceInner>, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<InvoiceInner>>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Gets a named invoice resource. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @param invoiceName The name of an invoice resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the InvoiceInner object if successful.
     */
    public InvoiceInner get(String invoiceName) {
        return getWithServiceResponseAsync(invoiceName).toBlocking().single().body();
    }

    /**
     * Gets a named invoice resource. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @param invoiceName The name of an invoice resource.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<InvoiceInner> getAsync(String invoiceName, final ServiceCallback<InvoiceInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(invoiceName), serviceCallback);
    }

    /**
     * Gets a named invoice resource. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @param invoiceName The name of an invoice resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the InvoiceInner object
     */
    public Observable<InvoiceInner> getAsync(String invoiceName) {
        return getWithServiceResponseAsync(invoiceName).map(new Func1<ServiceResponse<InvoiceInner>, InvoiceInner>() {
            @Override
            public InvoiceInner call(ServiceResponse<InvoiceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets a named invoice resource. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @param invoiceName The name of an invoice resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the InvoiceInner object
     */
    public Observable<ServiceResponse<InvoiceInner>> getWithServiceResponseAsync(String invoiceName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (invoiceName == null) {
            throw new IllegalArgumentException("Parameter invoiceName is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.get(this.client.subscriptionId(), invoiceName, this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<InvoiceInner>>>() {
                @Override
                public Observable<ServiceResponse<InvoiceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<InvoiceInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<InvoiceInner> getDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<InvoiceInner, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<InvoiceInner>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Gets the most recent invoice. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the InvoiceInner object if successful.
     */
    public InvoiceInner getLatest() {
        return getLatestWithServiceResponseAsync().toBlocking().single().body();
    }

    /**
     * Gets the most recent invoice. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<InvoiceInner> getLatestAsync(final ServiceCallback<InvoiceInner> serviceCallback) {
        return ServiceFuture.fromResponse(getLatestWithServiceResponseAsync(), serviceCallback);
    }

    /**
     * Gets the most recent invoice. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the InvoiceInner object
     */
    public Observable<InvoiceInner> getLatestAsync() {
        return getLatestWithServiceResponseAsync().map(new Func1<ServiceResponse<InvoiceInner>, InvoiceInner>() {
            @Override
            public InvoiceInner call(ServiceResponse<InvoiceInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets the most recent invoice. When getting a single invoice, the downloadUrl property is expanded automatically.
     *
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the InvoiceInner object
     */
    public Observable<ServiceResponse<InvoiceInner>> getLatestWithServiceResponseAsync() {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (this.client.apiVersion() == null) {
            throw new IllegalArgumentException("Parameter this.client.apiVersion() is required and cannot be null.");
        }
        return service.getLatest(this.client.subscriptionId(), this.client.apiVersion(), this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<InvoiceInner>>>() {
                @Override
                public Observable<ServiceResponse<InvoiceInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<InvoiceInner> clientResponse = getLatestDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<InvoiceInner> getLatestDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<InvoiceInner, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<InvoiceInner>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws ErrorResponseException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;InvoiceInner&gt; object if successful.
     */
    public PagedList<InvoiceInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<InvoiceInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<InvoiceInner>(response.body()) {
            @Override
            public Page<InvoiceInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<InvoiceInner>> listNextAsync(final String nextPageLink, final ServiceFuture<List<InvoiceInner>> serviceFuture, final ListOperationCallback<InvoiceInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<Page<InvoiceInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<InvoiceInner>>, Page<InvoiceInner>>() {
                @Override
                public Page<InvoiceInner> call(ServiceResponse<Page<InvoiceInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;InvoiceInner&gt; object
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<InvoiceInner>>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(ServiceResponse<Page<InvoiceInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * Lists the available invoices for a subscription in reverse chronological order beginning with the most recent invoice. In preview, invoices are available via this API only for invoice periods which end December 1, 2016 or later.
     *
    ServiceResponse<PageImpl<InvoiceInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;InvoiceInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<InvoiceInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<InvoiceInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<InvoiceInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<InvoiceInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<InvoiceInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<InvoiceInner>> listNextDelegate(Response<ResponseBody> response) throws ErrorResponseException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<InvoiceInner>, ErrorResponseException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<InvoiceInner>>() { }.getType())
                .registerError(ErrorResponseException.class)
                .build(response);
    }

}
