/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.kubernetescluster.samples;

import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.containerservice.ContainerServiceVMSizeTypes;
import com.microsoft.azure.management.containerservice.KubernetesCluster;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.samples.SSHShell;
import com.microsoft.azure.management.samples.Utils;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Azure Container Service (AKS) sample for managing a Kubernetes cluster.
 *   - Create an Azure Container Service (AKS) with managed Kubernetes cluster
 *   - Create a SSH private/public key
 *   - Update the number of agent virtual machines in the Kubernetes cluster
 */
public class ManageKubernetesCluster {

    /**
     * Main function which runs the actual sample.
     *
     * @param azure instance of the azure client
     * @param clientId secondary service principal client ID
     * @param secret secondary service principal secret
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure, String clientId, String secret) {
        final String rgName = SdkContext.randomResourceName("rgaks", 15);
        final String aksName = SdkContext.randomResourceName("akssample", 30);
        final Region region = Region.US_EAST;
        String servicePrincipalClientId = clientId; // replace with a real service principal client id
        String servicePrincipalSecret = secret; // and corresponding secret
        final String rootUserName = "aksuser";

        try {

            //=============================================================
            // If service principal client id and secret are not set via the local variables, attempt to read the service
            //     principal client id and secret from a secondary ".azureauth" file set through an environment variable.
            //
            //     If the environment variable was not set then reuse the main service principal set for running this sample.

            if (servicePrincipalClientId.isEmpty() || servicePrincipalSecret.isEmpty()) {
                servicePrincipalClientId = System.getenv("AZURE_CLIENT_ID");
                servicePrincipalSecret = System.getenv("AZURE_CLIENT_SECRET");
                if (servicePrincipalClientId.isEmpty() || servicePrincipalSecret.isEmpty()) {
                    String envSecondaryServicePrincipal = System.getenv("AZURE_AUTH_LOCATION_2");

                    if (envSecondaryServicePrincipal == null || !envSecondaryServicePrincipal.isEmpty() || !Files.exists(Paths.get(envSecondaryServicePrincipal))) {
                        envSecondaryServicePrincipal = System.getenv("AZURE_AUTH_LOCATION");
                    }

                    servicePrincipalClientId = Utils.getSecondaryServicePrincipalClientID(envSecondaryServicePrincipal);
                    servicePrincipalSecret = Utils.getSecondaryServicePrincipalSecret(envSecondaryServicePrincipal);
                }
            }


            //=============================================================
            // Create an SSH private/public key pair to be used when creating the container service

            System.out.println("Creating an SSH private and public key pair");

            SSHShell.SshPublicPrivateKey sshKeys = SSHShell.generateSSHKeys("", "ACS");
            System.out.println("SSH private key value: " + sshKeys.getSshPrivateKey());
            System.out.println("SSH public key value: " + sshKeys.getSshPublicKey());


            //=============================================================
            // Create a Kubernetes cluster with one agent pool of size one

            System.out.println("Creating a Kubernetes cluster");

            Date t1 = new Date();

            KubernetesCluster kubernetesCluster = azure.kubernetesClusters().define(aksName)
                .withRegion(region)
                .withNewResourceGroup(rgName)
                .withLatestVersion()
                .withRootUsername(rootUserName)
                .withSshKey(sshKeys.getSshPublicKey())
                .withServicePrincipalClientId(servicePrincipalClientId)
                .withServicePrincipalSecret(servicePrincipalSecret)
                .defineAgentPool("agentpool")
                    .withVirtualMachineCount(1)
                    .withVirtualMachineSize(ContainerServiceVMSizeTypes.STANDARD_D1_V2)
                    .attach()
                .withDnsPrefix("dns-" + aksName)
                .create();

            Date t2 = new Date();
            System.out.println("Created Azure Container Service (AKS) resource: (took " + ((t2.getTime() - t1.getTime()) / 1000) + " seconds) " + kubernetesCluster.id());
            Utils.print(kubernetesCluster);

            //=============================================================
            // Update a Kubernetes cluster agent pool to two machines

            System.out.println("Updating a Kubernetes cluster agent pool to two virtual machines");

            t1 = new Date();

            kubernetesCluster.update()
                .withAgentVirtualMachineCount(2)
                .apply();

            t2 = new Date();
            System.out.println("Updated Azure Container Service (AKS) resource: (took " + ((t2.getTime() - t1.getTime()) / 1000) + " seconds) " + kubernetesCluster.id());
            Utils.print(kubernetesCluster);

            return true;
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
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
     *
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));

            Azure azure = Azure.configure()
                .withLogLevel(LogLevel.BODY)
                .authenticate(credFile)
                .withDefaultSubscription();

            // Print selected subscription
            System.out.println("Selected subscription: " + azure.subscriptionId());

            runSample(azure, "", "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}