/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.CloudException;
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
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in RestorableDroppedDatabases.
 */
public class RestorableDroppedDatabasesInner {
    /** The Retrofit service to perform REST calls. */
    private RestorableDroppedDatabasesService service;
    /** The service client containing this operation class. */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of RestorableDroppedDatabasesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public RestorableDroppedDatabasesInner(Retrofit retrofit, SqlManagementClientImpl client) {
        this.service = retrofit.create(RestorableDroppedDatabasesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for RestorableDroppedDatabases to be
     * used by Retrofit to perform actually REST calls.
     */
    interface RestorableDroppedDatabasesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.sql.RestorableDroppedDatabases get" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/restorableDroppedDatabases/{restorableDroppededDatabaseId}")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Path("restorableDroppededDatabaseId") String restorableDroppededDatabaseId, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.sql.RestorableDroppedDatabases listByServer" })
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/restorableDroppedDatabases")
        Observable<Response<ResponseBody>> listByServer(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Gets a deleted database that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param restorableDroppededDatabaseId The id of the deleted database in the form of databaseName,deletionTimeInFileTimeFormat
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the RestorableDroppedDatabaseInner object if successful.
     */
    public RestorableDroppedDatabaseInner get(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        return getWithServiceResponseAsync(resourceGroupName, serverName, restorableDroppededDatabaseId).toBlocking().single().body();
    }

    /**
     * Gets a deleted database that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param restorableDroppededDatabaseId The id of the deleted database in the form of databaseName,deletionTimeInFileTimeFormat
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<RestorableDroppedDatabaseInner> getAsync(String resourceGroupName, String serverName, String restorableDroppededDatabaseId, final ServiceCallback<RestorableDroppedDatabaseInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, serverName, restorableDroppededDatabaseId), serviceCallback);
    }

    /**
     * Gets a deleted database that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param restorableDroppededDatabaseId The id of the deleted database in the form of databaseName,deletionTimeInFileTimeFormat
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the RestorableDroppedDatabaseInner object
     */
    public Observable<RestorableDroppedDatabaseInner> getAsync(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        return getWithServiceResponseAsync(resourceGroupName, serverName, restorableDroppededDatabaseId).map(new Func1<ServiceResponse<RestorableDroppedDatabaseInner>, RestorableDroppedDatabaseInner>() {
            @Override
            public RestorableDroppedDatabaseInner call(ServiceResponse<RestorableDroppedDatabaseInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets a deleted database that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param restorableDroppededDatabaseId The id of the deleted database in the form of databaseName,deletionTimeInFileTimeFormat
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the RestorableDroppedDatabaseInner object
     */
    public Observable<ServiceResponse<RestorableDroppedDatabaseInner>> getWithServiceResponseAsync(String resourceGroupName, String serverName, String restorableDroppededDatabaseId) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        if (restorableDroppededDatabaseId == null) {
            throw new IllegalArgumentException("Parameter restorableDroppededDatabaseId is required and cannot be null.");
        }
        final String apiVersion = "2014-04-01";
        return service.get(this.client.subscriptionId(), resourceGroupName, serverName, restorableDroppededDatabaseId, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<RestorableDroppedDatabaseInner>>>() {
                @Override
                public Observable<ServiceResponse<RestorableDroppedDatabaseInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<RestorableDroppedDatabaseInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<RestorableDroppedDatabaseInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<RestorableDroppedDatabaseInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<RestorableDroppedDatabaseInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Gets a list of deleted databases that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the List&lt;RestorableDroppedDatabaseInner&gt; object if successful.
     */
    public List<RestorableDroppedDatabaseInner> listByServer(String resourceGroupName, String serverName) {
        return listByServerWithServiceResponseAsync(resourceGroupName, serverName).toBlocking().single().body();
    }

    /**
     * Gets a list of deleted databases that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<RestorableDroppedDatabaseInner>> listByServerAsync(String resourceGroupName, String serverName, final ServiceCallback<List<RestorableDroppedDatabaseInner>> serviceCallback) {
        return ServiceFuture.fromResponse(listByServerWithServiceResponseAsync(resourceGroupName, serverName), serviceCallback);
    }

    /**
     * Gets a list of deleted databases that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;RestorableDroppedDatabaseInner&gt; object
     */
    public Observable<List<RestorableDroppedDatabaseInner>> listByServerAsync(String resourceGroupName, String serverName) {
        return listByServerWithServiceResponseAsync(resourceGroupName, serverName).map(new Func1<ServiceResponse<List<RestorableDroppedDatabaseInner>>, List<RestorableDroppedDatabaseInner>>() {
            @Override
            public List<RestorableDroppedDatabaseInner> call(ServiceResponse<List<RestorableDroppedDatabaseInner>> response) {
                return response.body();
            }
        });
    }

    /**
     * Gets a list of deleted databases that can be restored.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value from the Azure Resource Manager API or the portal.
     * @param serverName The name of the server.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;RestorableDroppedDatabaseInner&gt; object
     */
    public Observable<ServiceResponse<List<RestorableDroppedDatabaseInner>>> listByServerWithServiceResponseAsync(String resourceGroupName, String serverName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        final String apiVersion = "2014-04-01";
        return service.listByServer(this.client.subscriptionId(), resourceGroupName, serverName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<List<RestorableDroppedDatabaseInner>>>>() {
                @Override
                public Observable<ServiceResponse<List<RestorableDroppedDatabaseInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<RestorableDroppedDatabaseInner>> result = listByServerDelegate(response);
                        ServiceResponse<List<RestorableDroppedDatabaseInner>> clientResponse = new ServiceResponse<List<RestorableDroppedDatabaseInner>>(result.body().items(), result.response());
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<RestorableDroppedDatabaseInner>> listByServerDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<RestorableDroppedDatabaseInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<RestorableDroppedDatabaseInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
