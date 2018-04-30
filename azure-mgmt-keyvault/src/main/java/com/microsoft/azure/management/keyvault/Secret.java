/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.SecretAttributes;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Observable;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure Key Vault secret.
 */
@Beta(SinceVersion.V1_6_0)
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Secret extends
        Indexable,
        HasInner<SecretBundle>,
        HasId,
        HasName,
        Updatable<Secret.Update> {
    /**
     * @return the secret value
     */
    String value();

    /**
     * @return the secret management attributes
     */
    SecretAttributes attributes();

    /**
     * @return application specific metadata in the form of key-value pairs
     */
    Map<String, String> tags();

    /**
     * @return type of the secret value such as a password
     */
    String contentType();

    /**
     * @return the corresponding key backing the KV certificate if this is a
     * secret backing a KV certificate
     */
    String kid();

    /**
     * @return true if the secret's lifetime is managed by key vault. If this is a key
     * backing a certificate, then managed will be true
     */
    boolean managed();

    /**
     * @return a list of individual secret versions with the same secret name
     */
    @Method
    PagedList<Secret> listVersions();

    /**
     * @return a list of individual secret versions with the same secret name
     */
    @Method
    Observable<Secret> listVersionsAsync();

    /**
     * Container interface for all the definitions.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithValue,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of secret definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of a secret definition.
         */
        interface Blank extends WithValue {
        }

        /**
         * The stage of a secret definition allowing to specify the secret value.
         */
        interface WithValue {
            /**
             * Specifies the secret value.
             * @param value the string value of the secret
             * @return the next stage of the definition
             */
            WithCreate withValue(String value);
        }

        /**
         * The stage of a secret definition allowing to specify the secret content type.
         */
        interface WithContentType {
            /**
             * Specifies the secret content type.
             * @param contentType the content type
             * @return the next stage of the definition
             */
            WithCreate withContentType(String contentType);
        }

        /**
         * The stage of a secret definition allowing to specify the secret attributes.
         */
        interface WithAttributes {
            /**
             * Specifies the secret attributes.
             * @param attributes the object attributes managed by Key Vault service
             * @return the next stage of the definition
             */
            WithCreate withAttributes(Attributes attributes);
        }

        /**
         * The stage of a secret definition allowing to specify the tags.
         */
        interface WithTags {
            /**
             * Specifies the tags on the secret.
             * @param tags the key value pair of the tags
             * @return the next stage of the definition
             */
            WithCreate withTags(Map<String, String> tags);
        }

        /**
         * The stage of the secret definition which contains all the minimum required inputs for
         * the secret to be created but also allows for any optional settings to be specified.
         */
        interface WithCreate extends
                Creatable<Secret>,
                WithContentType,
                WithAttributes,
                WithTags {

        }
    }

    /**
     * Grouping of secret update stages.
     */
    interface UpdateStages {
        /**
         * The stage of a secret update allowing to create a new version of the secret value.
         */
        interface WithValue {
            /**
             * Specifies the new version of the value to be added.
             * @param value the value for the new version
             * @return the next stage of the secret update
             */
            Update withValue(String value);
        }

        /**
         * The stage of a secret update allowing to set the secret to a different version.
         */
        interface WithVersion {
            /**
             * Specifies the version the secret show use.
             * @param version the version of the secret
             * @return the next stage of the secret update
             */
            Update withVersion(String version);
        }

        /**
         * The stage of a secret update allowing to set the content type of the secret.
         */
        interface WithContentType {
            /**
             * Specifies the secret content type.
             * @param contentType the content type
             * @return the next stage of the update
             */
            Update withContentType(String contentType);
        }

        /**
         * The stage of a secret update allowing to specify the secret attributes.
         */
        interface WithAttributes {
            /**
             * Specifies the secret attributes.
             * @param attributes the object attributes managed by Key Vault service
             * @return the next stage of the update
             */
            Update withAttributes(Attributes attributes);
        }

        /**
         * The stage of a secret update allowing to specify the tags.
         */
        interface WithTags {
            /**
             * Specifies the tags on the secret.
             * @param tags the key value pair of the tags
             * @return the next stage of the update
             */
            Update withTags(Map<String, String> tags);
        }
    }

    /**
     * The template for a secret update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<Secret>,
            UpdateStages.WithValue,
            UpdateStages.WithVersion,
            UpdateStages.WithAttributes,
            UpdateStages.WithContentType,
            UpdateStages.WithTags {
    }
}

