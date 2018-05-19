/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservices.implementation;

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
 * in Usages.
 */
public class UsagesInner {
    /** The Retrofit service to perform REST calls. */
    private UsagesService service;
    /** The service client containing this operation class. */
    private RecoveryServicesClientImpl client;

    /**
     * Initializes an instance of UsagesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public UsagesInner(Retrofit retrofit, RecoveryServicesClientImpl client) {
        this.service = retrofit.create(UsagesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Usages to be
     * used by Retrofit to perform actually REST calls.
     */
    interface UsagesService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.recoveryservices.Usages listByVaults" })
        @GET("Subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.RecoveryServices/vaults/{vaultName}/usages")
        Observable<Response<ResponseBody>> listByVaults(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("vaultName") String vaultName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Fetches the usages of the vault.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the List&lt;VaultUsageInner&gt; object if successful.
     */
    public List<VaultUsageInner> listByVaults(String resourceGroupName, String vaultName) {
        return listByVaultsWithServiceResponseAsync(resourceGroupName, vaultName).toBlocking().single().body();
    }

    /**
     * Fetches the usages of the vault.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<VaultUsageInner>> listByVaultsAsync(String resourceGroupName, String vaultName, final ServiceCallback<List<VaultUsageInner>> serviceCallback) {
        return ServiceFuture.fromResponse(listByVaultsWithServiceResponseAsync(resourceGroupName, vaultName), serviceCallback);
    }

    /**
     * Fetches the usages of the vault.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;VaultUsageInner&gt; object
     */
    public Observable<List<VaultUsageInner>> listByVaultsAsync(String resourceGroupName, String vaultName) {
        return listByVaultsWithServiceResponseAsync(resourceGroupName, vaultName).map(new Func1<ServiceResponse<List<VaultUsageInner>>, List<VaultUsageInner>>() {
            @Override
            public List<VaultUsageInner> call(ServiceResponse<List<VaultUsageInner>> response) {
                return response.body();
            }
        });
    }

    /**
     * Fetches the usages of the vault.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the List&lt;VaultUsageInner&gt; object
     */
    public Observable<ServiceResponse<List<VaultUsageInner>>> listByVaultsWithServiceResponseAsync(String resourceGroupName, String vaultName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (vaultName == null) {
            throw new IllegalArgumentException("Parameter vaultName is required and cannot be null.");
        }
        final String apiVersion = "2016-06-01";
        return service.listByVaults(this.client.subscriptionId(), resourceGroupName, vaultName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<List<VaultUsageInner>>>>() {
                @Override
                public Observable<ServiceResponse<List<VaultUsageInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<VaultUsageInner>> result = listByVaultsDelegate(response);
                        ServiceResponse<List<VaultUsageInner>> clientResponse = new ServiceResponse<List<VaultUsageInner>>(result.body().items(), result.response());
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<VaultUsageInner>> listByVaultsDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<VaultUsageInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<VaultUsageInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
