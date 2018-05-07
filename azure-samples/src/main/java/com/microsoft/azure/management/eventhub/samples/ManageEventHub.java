/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.eventhub.samples;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.eventhub.EventHub;
import com.microsoft.azure.management.eventhub.EventHubConsumerGroup;
import com.microsoft.azure.management.eventhub.EventHubNamespace;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountSkuType;
import com.microsoft.rest.LogLevel;

import java.io.File;
/**
 * Azure Event Hub sample for managing event hub -
 *   - Create an event hub namespace
 *   - Create an event hub in the namespace with data capture enabled along with a consumer group and rule
 *   - List consumer groups in the event hub
 *   - Create a second event hub in the namespace
 *   - Create a consumer group in the second event hub
 *   - List consumer groups in the second event hub
 *   - Create an event hub namespace along with event hub.
 */
public class ManageEventHub {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure) {
        final String rgName = SdkContext.randomResourceName("rgNEMV_", 24);
        final String namespaceName1 = SdkContext.randomResourceName("ns", 14);
        final String namespaceName2 = SdkContext.randomResourceName("ns", 14);
        final String storageAccountName = SdkContext.randomResourceName("stg", 14);
        final String eventHubName1 = SdkContext.randomResourceName("eh", 14);
        final String eventHubName2 = SdkContext.randomResourceName("eh", 14);
        try {

            //============================================================
            // Create an event hub namespace
            //
            System.out.println("Creating a namespace");

            EventHubNamespace namespace1 = azure.eventHubNamespaces()
                    .define(namespaceName1)
                        .withRegion(Region.US_EAST2)
                        .withNewResourceGroup(rgName)
                        .create();

            Utils.print(namespace1);
            System.out.println("Created a namespace");
            //============================================================
            // Create an event hub in the namespace with data capture enabled, with consumer group and auth rule
            //

            Creatable<StorageAccount> storageAccountCreatable = azure.storageAccounts()
                    .define(storageAccountName)
                        .withRegion(Region.US_EAST2)
                        .withExistingResourceGroup(rgName)
                        .withSku(StorageAccountSkuType.STANDARD_LRS);

            System.out.println("Creating an event hub with data capture enabled with a consumer group and rule in it");

            EventHub eventHub1 = azure.eventHubs()
                    .define(eventHubName1)
                        .withExistingNamespace(namespace1)
                        // Optional - configure data capture
                        .withNewStorageAccountForCapturedData(storageAccountCreatable, "datacpt")
                        .withDataCaptureEnabled()
                        // Optional - create one consumer group in event hub
                        .withNewConsumerGroup("cg1", "sometadata")
                        // Optional - create an authorization rule for event hub
                        .withNewListenRule("listenrule1")
                        .create();

            System.out.println("Created an event hub with data capture enabled with a consumer group and rule in it");
            Utils.print(eventHub1);

            //============================================================
            // Retrieve consumer groups in the event hub
            //
            System.out.println("Retrieving consumer groups");

            PagedList<EventHubConsumerGroup> consumerGroups = eventHub1.listConsumerGroups();

            System.out.println("Retrieved consumer groups");
            for (EventHubConsumerGroup group : consumerGroups) {
                Utils.print(group);
            }

            //============================================================
            // Create another event hub in the namespace using event hub accessor in namespace accessor
            //

            System.out.println("Creating another event hub in the namespace");

            EventHub eventHub2 = azure.eventHubNamespaces()
                    .eventHubs()
                    .define(eventHubName2)
                        .withExistingNamespace(namespace1)
                        .create();

            System.out.println("Created second event hub");
            Utils.print(eventHub2);

            //============================================================
            // Create a consumer group in the event hub using consumer group accessor in event hub accessor
            //

            System.out.println("Creating a consumer group in the second event hub");

            EventHubConsumerGroup consumerGroup2 = azure.eventHubNamespaces()
                    .eventHubs()
                    .consumerGroups()
                    .define("cg2")
                        .withExistingEventHub(eventHub2)
                        // Optional
                        .withUserMetadata("sometadata")
                        .create();

            System.out.println("Created a consumer group in the second event hub");
            Utils.print(consumerGroup2);

            //============================================================
            // Retrieve consumer groups in the event hub
            //
            System.out.println("Retrieving consumer groups in the second event hub");

            consumerGroups = eventHub2.listConsumerGroups();

            System.out.println("Retrieved consumer groups in the seoond event hub");
            for (EventHubConsumerGroup group : consumerGroups) {
                Utils.print(group);
            }

            //============================================================
            // Create an event hub namespace with event hub
            //

            System.out.println("Creating an event hub namespace along with event hub");

            EventHubNamespace namespace2 = azure.eventHubNamespaces()
                    .define(namespaceName2)
                        .withRegion(Region.US_EAST2)
                        .withExistingResourceGroup(rgName)
                        .withNewEventHub(eventHubName2)
                        .create();

            System.out.println("Created an event hub namespace along with event hub");
            Utils.print(namespace2);
            for (EventHub eh : namespace2.listEventHubs()) {
                Utils.print(eh);
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().deleteByName(rgName);
                System.out.println("Deleted Resource Group: " + rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BASIC)
                    .authenticate(credFile)
                    .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
