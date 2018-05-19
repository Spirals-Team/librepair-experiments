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
import com.microsoft.rest.Validator;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in BackupVaultConfigs.
 */
public class BackupVaultConfigsInner {
    /** The Retrofit service to perform REST calls. */
    private BackupVaultConfigsService service;
    /** The service client containing this operation class. */
    private RecoveryServicesClientImpl client;

    /**
     * Initializes an instance of BackupVaultConfigsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public BackupVaultConfigsInner(Retrofit retrofit, RecoveryServicesClientImpl client) {
        this.service = retrofit.create(BackupVaultConfigsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for BackupVaultConfigs to be
     * used by Retrofit to perform actually REST calls.
     */
    interface BackupVaultConfigsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.recoveryservices.BackupVaultConfigs get" })
        @GET("Subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.RecoveryServices/vaults/{vaultName}/backupconfig/vaultconfig")
        Observable<Response<ResponseBody>> get(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("vaultName") String vaultName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.recoveryservices.BackupVaultConfigs update" })
        @PATCH("Subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.RecoveryServices/vaults/{vaultName}/backupconfig/vaultconfig")
        Observable<Response<ResponseBody>> update(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("vaultName") String vaultName, @Query("api-version") String apiVersion, @Body BackupVaultConfigInner backupVaultConfig, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Fetches vault config.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the BackupVaultConfigInner object if successful.
     */
    public BackupVaultConfigInner get(String resourceGroupName, String vaultName) {
        return getWithServiceResponseAsync(resourceGroupName, vaultName).toBlocking().single().body();
    }

    /**
     * Fetches vault config.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<BackupVaultConfigInner> getAsync(String resourceGroupName, String vaultName, final ServiceCallback<BackupVaultConfigInner> serviceCallback) {
        return ServiceFuture.fromResponse(getWithServiceResponseAsync(resourceGroupName, vaultName), serviceCallback);
    }

    /**
     * Fetches vault config.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BackupVaultConfigInner object
     */
    public Observable<BackupVaultConfigInner> getAsync(String resourceGroupName, String vaultName) {
        return getWithServiceResponseAsync(resourceGroupName, vaultName).map(new Func1<ServiceResponse<BackupVaultConfigInner>, BackupVaultConfigInner>() {
            @Override
            public BackupVaultConfigInner call(ServiceResponse<BackupVaultConfigInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Fetches vault config.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BackupVaultConfigInner object
     */
    public Observable<ServiceResponse<BackupVaultConfigInner>> getWithServiceResponseAsync(String resourceGroupName, String vaultName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (vaultName == null) {
            throw new IllegalArgumentException("Parameter vaultName is required and cannot be null.");
        }
        final String apiVersion = "2016-12-01";
        return service.get(this.client.subscriptionId(), resourceGroupName, vaultName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<BackupVaultConfigInner>>>() {
                @Override
                public Observable<ServiceResponse<BackupVaultConfigInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<BackupVaultConfigInner> clientResponse = getDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<BackupVaultConfigInner> getDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<BackupVaultConfigInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<BackupVaultConfigInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * Updates vault config model type.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param backupVaultConfig Backup vault config.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the BackupVaultConfigInner object if successful.
     */
    public BackupVaultConfigInner update(String resourceGroupName, String vaultName, BackupVaultConfigInner backupVaultConfig) {
        return updateWithServiceResponseAsync(resourceGroupName, vaultName, backupVaultConfig).toBlocking().single().body();
    }

    /**
     * Updates vault config model type.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param backupVaultConfig Backup vault config.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<BackupVaultConfigInner> updateAsync(String resourceGroupName, String vaultName, BackupVaultConfigInner backupVaultConfig, final ServiceCallback<BackupVaultConfigInner> serviceCallback) {
        return ServiceFuture.fromResponse(updateWithServiceResponseAsync(resourceGroupName, vaultName, backupVaultConfig), serviceCallback);
    }

    /**
     * Updates vault config model type.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param backupVaultConfig Backup vault config.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BackupVaultConfigInner object
     */
    public Observable<BackupVaultConfigInner> updateAsync(String resourceGroupName, String vaultName, BackupVaultConfigInner backupVaultConfig) {
        return updateWithServiceResponseAsync(resourceGroupName, vaultName, backupVaultConfig).map(new Func1<ServiceResponse<BackupVaultConfigInner>, BackupVaultConfigInner>() {
            @Override
            public BackupVaultConfigInner call(ServiceResponse<BackupVaultConfigInner> response) {
                return response.body();
            }
        });
    }

    /**
     * Updates vault config model type.
     *
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param vaultName The name of the recovery services vault.
     * @param backupVaultConfig Backup vault config.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the BackupVaultConfigInner object
     */
    public Observable<ServiceResponse<BackupVaultConfigInner>> updateWithServiceResponseAsync(String resourceGroupName, String vaultName, BackupVaultConfigInner backupVaultConfig) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (vaultName == null) {
            throw new IllegalArgumentException("Parameter vaultName is required and cannot be null.");
        }
        if (backupVaultConfig == null) {
            throw new IllegalArgumentException("Parameter backupVaultConfig is required and cannot be null.");
        }
        Validator.validate(backupVaultConfig);
        final String apiVersion = "2016-12-01";
        return service.update(this.client.subscriptionId(), resourceGroupName, vaultName, apiVersion, backupVaultConfig, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<BackupVaultConfigInner>>>() {
                @Override
                public Observable<ServiceResponse<BackupVaultConfigInner>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<BackupVaultConfigInner> clientResponse = updateDelegate(response);
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<BackupVaultConfigInner> updateDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<BackupVaultConfigInner, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<BackupVaultConfigInner>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
