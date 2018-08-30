/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.service.security;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import org.thingsboard.server.common.data.Customer;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.asset.Asset;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.common.data.id.AssetId;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.EntityIdFactory;
import org.thingsboard.server.common.data.id.RuleChainId;
import org.thingsboard.server.common.data.id.RuleNodeId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.rule.RuleChain;
import org.thingsboard.server.common.data.rule.RuleNode;
import org.thingsboard.server.controller.HttpValidationCallback;
import org.thingsboard.server.dao.alarm.AlarmService;
import org.thingsboard.server.dao.asset.AssetService;
import org.thingsboard.server.dao.customer.CustomerService;
import org.thingsboard.server.dao.device.DeviceService;
import org.thingsboard.server.dao.rule.RuleChainService;
import org.thingsboard.server.dao.tenant.TenantService;
import org.thingsboard.server.dao.user.UserService;
import org.thingsboard.server.service.security.model.SecurityUser;
import org.thingsboard.server.service.telemetry.exception.ToErrorResponseEntity;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * Created by ashvayka on 27.03.18.
 */
@Component
public class AccessValidator {

    public static final String CUSTOMER_USER_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION = "Customer user is not allowed to perform this operation!";
    public static final String SYSTEM_ADMINISTRATOR_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION = "System administrator is not allowed to perform this operation!";
    public static final String DEVICE_WITH_REQUESTED_ID_NOT_FOUND = "Device with requested id wasn't found!";

    @Autowired
    protected TenantService tenantService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected DeviceService deviceService;

    @Autowired
    protected AssetService assetService;

    @Autowired
    protected AlarmService alarmService;

    @Autowired
    protected RuleChainService ruleChainService;

    private ExecutorService executor;

    @PostConstruct
    public void initExecutor() {
        executor = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public DeferredResult<ResponseEntity> validateEntityAndCallback(SecurityUser currentUser, String entityType, String entityIdStr,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, EntityId> onSuccess) throws ThingsboardException {
        return validateEntityAndCallback(currentUser, entityType, entityIdStr, onSuccess, (result, t) -> handleError(t, result, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public DeferredResult<ResponseEntity> validateEntityAndCallback(SecurityUser currentUser, String entityType, String entityIdStr,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, EntityId> onSuccess,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, Throwable> onFailure) throws ThingsboardException {
        return validateEntityAndCallback(currentUser, EntityIdFactory.getByTypeAndId(entityType, entityIdStr),
                onSuccess, onFailure);
    }

    public DeferredResult<ResponseEntity> validateEntityAndCallback(SecurityUser currentUser, EntityId entityId,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, EntityId> onSuccess) throws ThingsboardException {
        return validateEntityAndCallback(currentUser, entityId, onSuccess, (result, t) -> handleError(t, result, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public DeferredResult<ResponseEntity> validateEntityAndCallback(SecurityUser currentUser, EntityId entityId,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, EntityId> onSuccess,
                                                                    BiConsumer<DeferredResult<ResponseEntity>, Throwable> onFailure) throws ThingsboardException {

        final DeferredResult<ResponseEntity> response = new DeferredResult<>();

        validate(currentUser, entityId, new HttpValidationCallback(response,
                new FutureCallback<DeferredResult<ResponseEntity>>() {
                    @Override
                    public void onSuccess(@Nullable DeferredResult<ResponseEntity> result) {
                        onSuccess.accept(response, entityId);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        onFailure.accept(response, t);
                    }
                }));

        return response;
    }

    public void validate(SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        switch (entityId.getEntityType()) {
            case DEVICE:
                validateDevice(currentUser, entityId, callback);
                return;
            case ASSET:
                validateAsset(currentUser, entityId, callback);
                return;
            case RULE_CHAIN:
                validateRuleChain(currentUser, entityId, callback);
                return;
            case CUSTOMER:
                validateCustomer(currentUser, entityId, callback);
                return;
            case TENANT:
                validateTenant(currentUser, entityId, callback);
                return;
            default:
                //TODO: add support of other entities
                throw new IllegalStateException("Not Implemented!");
        }
    }

    private void validateDevice(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isSystemAdmin()) {
            callback.onSuccess(ValidationResult.accessDenied(SYSTEM_ADMINISTRATOR_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else {
            ListenableFuture<Device> deviceFuture = deviceService.findDeviceByIdAsync(new DeviceId(entityId.getId()));
            Futures.addCallback(deviceFuture, getCallback(callback, device -> {
                if (device == null) {
                    return ValidationResult.entityNotFound(DEVICE_WITH_REQUESTED_ID_NOT_FOUND);
                } else {
                    if (!device.getTenantId().equals(currentUser.getTenantId())) {
                        return ValidationResult.accessDenied("Device doesn't belong to the current Tenant!");
                    } else if (currentUser.isCustomerUser() && !device.getCustomerId().equals(currentUser.getCustomerId())) {
                        return ValidationResult.accessDenied("Device doesn't belong to the current Customer!");
                    } else {
                        return ValidationResult.ok(device);
                    }
                }
            }), executor);
        }
    }

    private void validateAsset(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isSystemAdmin()) {
            callback.onSuccess(ValidationResult.accessDenied(SYSTEM_ADMINISTRATOR_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else {
            ListenableFuture<Asset> assetFuture = assetService.findAssetByIdAsync(new AssetId(entityId.getId()));
            Futures.addCallback(assetFuture, getCallback(callback, asset -> {
                if (asset == null) {
                    return ValidationResult.entityNotFound("Asset with requested id wasn't found!");
                } else {
                    if (!asset.getTenantId().equals(currentUser.getTenantId())) {
                        return ValidationResult.accessDenied("Asset doesn't belong to the current Tenant!");
                    } else if (currentUser.isCustomerUser() && !asset.getCustomerId().equals(currentUser.getCustomerId())) {
                        return ValidationResult.accessDenied("Asset doesn't belong to the current Customer!");
                    } else {
                        return ValidationResult.ok(asset);
                    }
                }
            }), executor);
        }
    }

    private void validateRuleChain(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isCustomerUser()) {
            callback.onSuccess(ValidationResult.accessDenied(CUSTOMER_USER_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else {
            ListenableFuture<RuleChain> ruleChainFuture = ruleChainService.findRuleChainByIdAsync(new RuleChainId(entityId.getId()));
            Futures.addCallback(ruleChainFuture, getCallback(callback, ruleChain -> {
                if (ruleChain == null) {
                    return ValidationResult.entityNotFound("Rule chain with requested id wasn't found!");
                } else {
                    if (currentUser.isTenantAdmin() && !ruleChain.getTenantId().equals(currentUser.getTenantId())) {
                        return ValidationResult.accessDenied("Rule chain doesn't belong to the current Tenant!");
                    } else if (currentUser.isSystemAdmin() && !ruleChain.getTenantId().isNullUid()) {
                        return ValidationResult.accessDenied("Rule chain is not in system scope!");
                    } else {
                        return ValidationResult.ok(ruleChain);
                    }
                }
            }), executor);
        }
    }

    private void validateRule(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isCustomerUser()) {
            callback.onSuccess(ValidationResult.accessDenied(CUSTOMER_USER_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else {
            ListenableFuture<RuleNode> ruleNodeFuture = ruleChainService.findRuleNodeByIdAsync(new RuleNodeId(entityId.getId()));
            Futures.addCallback(ruleNodeFuture, getCallback(callback, ruleNodeTmp -> {
                RuleNode ruleNode = ruleNodeTmp;
                if (ruleNode == null) {
                    return ValidationResult.entityNotFound("Rule node with requested id wasn't found!");
                } else if (ruleNode.getRuleChainId() == null) {
                    return ValidationResult.entityNotFound("Rule chain with requested node id wasn't found!");
                } else {
                    //TODO: make async
                    RuleChain ruleChain = ruleChainService.findRuleChainById(ruleNode.getRuleChainId());
                    if (currentUser.isTenantAdmin() && !ruleChain.getTenantId().equals(currentUser.getTenantId())) {
                        return ValidationResult.accessDenied("Rule chain doesn't belong to the current Tenant!");
                    } else if (currentUser.isSystemAdmin() && !ruleChain.getTenantId().isNullUid()) {
                        return ValidationResult.accessDenied("Rule chain is not in system scope!");
                    } else {
                        return ValidationResult.ok(ruleNode);
                    }
                }
            }), executor);
        }
    }

    private void validateCustomer(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isSystemAdmin()) {
            callback.onSuccess(ValidationResult.accessDenied(SYSTEM_ADMINISTRATOR_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else {
            ListenableFuture<Customer> customerFuture = customerService.findCustomerByIdAsync(new CustomerId(entityId.getId()));
            Futures.addCallback(customerFuture, getCallback(callback, customer -> {
                if (customer == null) {
                    return ValidationResult.entityNotFound("Customer with requested id wasn't found!");
                } else {
                    if (!customer.getTenantId().equals(currentUser.getTenantId())) {
                        return ValidationResult.accessDenied("Customer doesn't belong to the current Tenant!");
                    } else if (currentUser.isCustomerUser() && !customer.getId().equals(currentUser.getCustomerId())) {
                        return ValidationResult.accessDenied("Customer doesn't relate to the currently authorized customer user!");
                    } else {
                        return ValidationResult.ok(customer);
                    }
                }
            }), executor);
        }
    }

    private void validateTenant(final SecurityUser currentUser, EntityId entityId, FutureCallback<ValidationResult> callback) {
        if (currentUser.isCustomerUser()) {
            callback.onSuccess(ValidationResult.accessDenied(CUSTOMER_USER_IS_NOT_ALLOWED_TO_PERFORM_THIS_OPERATION));
        } else if (currentUser.isSystemAdmin()) {
            callback.onSuccess(ValidationResult.ok(null));
        } else {
            ListenableFuture<Tenant> tenantFuture = tenantService.findTenantByIdAsync(new TenantId(entityId.getId()));
            Futures.addCallback(tenantFuture, getCallback(callback, tenant -> {
                if (tenant == null) {
                    return ValidationResult.entityNotFound("Tenant with requested id wasn't found!");
                } else if (!tenant.getId().equals(currentUser.getTenantId())) {
                    return ValidationResult.accessDenied("Tenant doesn't relate to the currently authorized user!");
                } else {
                    return ValidationResult.ok(tenant);
                }
            }), executor);
        }
    }

    private <T, V> FutureCallback<T> getCallback(FutureCallback<ValidationResult> callback, Function<T, ValidationResult<V>> transformer) {
        return new FutureCallback<T>() {
            @Override
            public void onSuccess(@Nullable T result) {
                callback.onSuccess(transformer.apply(result));
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        };
    }

    public static void handleError(Throwable e, final DeferredResult<ResponseEntity> response, HttpStatus defaultErrorStatus) {
        ResponseEntity responseEntity;
        if (e != null && e instanceof ToErrorResponseEntity) {
            responseEntity = ((ToErrorResponseEntity) e).toErrorResponseEntity();
        } else if (e != null && e instanceof IllegalArgumentException) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(defaultErrorStatus);
        }
        response.setResult(responseEntity);
    }
}
