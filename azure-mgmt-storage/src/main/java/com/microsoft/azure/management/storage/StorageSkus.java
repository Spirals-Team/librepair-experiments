/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.collection.SupportsListing;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.implementation.SkusInner;
import com.microsoft.azure.management.storage.implementation.StorageManager;

/**
 * Entry point to storage service SKUs.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_5_0)
public interface StorageSkus
        extends
        SupportsListing<StorageSku>,
        HasInner<SkusInner>,
        HasManager<StorageManager> {
}