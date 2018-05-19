/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.network.implementation.SecurityRuleInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.Set;

/**
 * A network security rule in a network security group.
 */
@Fluent
public interface NetworkSecurityRule extends
    HasInner<SecurityRuleInner>,
    ChildResource<NetworkSecurityGroup> {

    /**
     * @return the direction of the network traffic that the network security rule applies to.
     */
    SecurityRuleDirection direction();

    /**
     * @return the network protocol the rule applies to
     */
    SecurityRuleProtocol protocol();

    /**
     * @return the user-defined description of the security rule
     */
    String description();

    /**
     * @return the type of access the rule enforces
     */
    SecurityRuleAccess access();

    /**
     * @return the source address prefix the rule applies to, expressed using the CIDR notation in the format: "###.###.###.###/##",
     * and "*" means "any"
     */
    String sourceAddressPrefix();

    /**
     * @return the source port range that the rule applies to, in the format "##-##", where "*" means "any"
     */
    String sourcePortRange();

    /**
     * @return the destination address prefix the rule applies to, expressed using the CIDR notation in the format: "###.###.###.###/##",
     * and "*" means "any"
     */
    String destinationAddressPrefix();

    /**
     * @return the destination port range that the rule applies to, in the format "##-##", where "*" means any
     */
    String destinationPortRange();

    /**
     * @return the priority number of this rule based on which this rule will be applied relative to the priority numbers of any other rules specified
     * for this network security group
     */
    int priority();

    /**
     * @return list of application security group ids specified as source
     */
    Set<String> sourceApplicationSecurityGroupIds();

    /**
     * @return list of application security group ids specified as destination
     */
    Set<String> destinationApplicationSecurityGroupIds();

    /**
     * The entirety of a network security rule definition.
     * @param <ParentT> the return type of the final {@link Attachable#attach()}
     */
    interface Definition<ParentT> extends
        DefinitionStages.Blank<ParentT>,
        DefinitionStages.WithAttach<ParentT>,
        DefinitionStages.WithDirectionAccess<ParentT>,
        DefinitionStages.WithSourceAddressOrSecurityGroup<ParentT>,
        DefinitionStages.WithSourcePort<ParentT>,
        DefinitionStages.WithDestinationAddressOrSecurityGroup<ParentT>,
        DefinitionStages.WithDestinationPort<ParentT>,
        DefinitionStages.WithProtocol<ParentT> {
    }

    /**
     * Grouping of security rule definition stages applicable as part of a network security group creation.
     */
    interface DefinitionStages {
        /**
         * The first stage of a security rule definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithDirectionAccess<ParentT> {
        }

        /**
         * The stage of the security rule definition allowing the protocol that the rule applies to to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithProtocol<ParentT> {
            /**
             * Specifies the protocol that this rule applies to.
             * @param protocol one of the supported protocols
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withProtocol(SecurityRuleProtocol protocol);

            /**
             * Makes this rule apply to any supported protocol.
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withAnyProtocol();
        }

        /**
         * The stage of the network rule definition allowing the destination port(s) to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDestinationPort<ParentT> {
            /**
             * Specifies the destination port to which this rule applies.
             * @param port the destination port number
             * @return the next stage of the definition
             */
            WithProtocol<ParentT> toPort(int port);

            /**
             * Makes this rule apply to any destination port.
             * @return the next stage of the definition
             */
            @Method
            WithProtocol<ParentT> toAnyPort();

            /**
             * Specifies the destination port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            WithProtocol<ParentT> toPortRange(int from, int to);
        }

        /**
         * The stage of the network rule definition allowing the destination address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * DestinationAddressPrefixes, DestinationAddressPrefix, DestinationApplicationSecurityGroups.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDestinationAddressOrSecurityGroup<ParentT> {
            /**
             * Specifies the traffic destination address range to which this rule applies.
             * @param cidr an IP address range expressed in the CIDR notation
             * @return the next stage of the definition
             */
            WithDestinationPort<ParentT> toAddress(String cidr);

            /**
             * Makes the rule apply to any traffic destination address.
             * @return the next stage of the definition
             */
            @Method
            WithDestinationPort<ParentT> toAnyAddress();

            /**
             * Sets the application security group specified as destination.
             * @param id  application security group id
             * @return the next stage of the definition
             */
            WithDestinationPort<ParentT> withDestinationApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule definition allowing the source port(s) to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithSourcePort<ParentT> {
            /**
             * Specifies the source port to which this rule applies.
             * @param port the source port number
             * @return the next stage of the definition
             */
            WithDestinationAddressOrSecurityGroup<ParentT> fromPort(int port);

            /**
             * Makes this rule apply to any source port.
             * @return the next stage of the definition
             */
            @Method
            WithDestinationAddressOrSecurityGroup<ParentT> fromAnyPort();

            /**
             * Specifies the source port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            WithDestinationAddressOrSecurityGroup<ParentT> fromPortRange(int from, int to);
        }

        /**
         * The stage of the network rule definition allowing the source address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * SourceAddressPrefixes, SourceAddressPrefix, SourceApplicationSecurityGroups.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithSourceAddressOrSecurityGroup<ParentT> {
            /**
             * Specifies the traffic source address prefix to which this rule applies.
             * @param cidr an IP address prefix expressed in the CIDR notation
             * @return the next stage of the definition
             */
            @Method
            WithSourcePort<ParentT> fromAddress(String cidr);

            /**
             * Specifies that the rule applies to any traffic source address.
             * @return the next stage of the definition
             */
            @Method
            WithSourcePort<ParentT> fromAnyAddress();

            /**
             * Sets the application security group specified as source.
             * @param id  application security group id
             * @return the next stage of the definition
             */
            WithSourcePort<ParentT> withSourceApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule definition allowing the direction and the access type to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDirectionAccess<ParentT> {
            /**
             * Allows inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> allowInbound();

            /**
             * Allows outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> allowOutbound();

            /**
             * Blocks inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> denyInbound();

            /**
             * Blocks outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> denyOutbound();
        }

        /**
         * The stage of the network rule definition allowing the priority to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithPriority<ParentT> {
            /**
             * Specifies the priority to assign to this rule.
             * <p>
             * Security rules are applied in the order of their assigned priority.
             * @param priority the priority number in the range 100 to 4096
             * @return the next stage
             */
            WithAttach<ParentT> withPriority(int priority);
        }

        /**
         * The stage of the network rule definition allowing the description to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDescription<ParentT> {
            /**
             * Specifies a description for this security rule.
             * @param description the text description to associate with this security rule
             * @return the next stage
             */
            WithAttach<ParentT> withDescription(String description);
        }

        /** The final stage of the security rule definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the security rule definition
         * can be attached to the parent network security group definition using {@link WithAttach#attach()}.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends
            Attachable.InDefinition<ParentT>,
            WithPriority<ParentT>,
            WithDescription<ParentT> {
        }
    }

    /** The entirety of a network security rule definition as part of a network security group update.
     * @param <ParentT> the return type of the final {@link UpdateDefinitionStages.WithAttach#attach()}
     */
    interface UpdateDefinition<ParentT> extends
        UpdateDefinitionStages.Blank<ParentT>,
        UpdateDefinitionStages.WithDirectionAccess<ParentT>,
        UpdateDefinitionStages.WithSourceAddressOrSecurityGroup<ParentT>,
        UpdateDefinitionStages.WithSourcePort<ParentT>,
        UpdateDefinitionStages.WithDestinationAddressOrSecurityGroup<ParentT>,
        UpdateDefinitionStages.WithDestinationPort<ParentT>,
        UpdateDefinitionStages.WithProtocol<ParentT>,
        UpdateDefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of security rule definition stages applicable as part of a network security group update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of a security rule description as part of an update of a networking security group.
         * @param <ParentT> the return type of the final {@link Attachable#attach()}
         */
        interface Blank<ParentT> extends WithDirectionAccess<ParentT> {
        }

        /**
         * The stage of the network rule description allowing the direction and the access type to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDirectionAccess<ParentT> {
            /**
             * Allows inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> allowInbound();

            /**
             * Allows outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> allowOutbound();

            /**
             * Blocks inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> denyInbound();

            /**
             * Blocks outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            WithSourceAddressOrSecurityGroup<ParentT> denyOutbound();
        }

        /**
         * The stage of the network rule definition allowing the source address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * SourceAddressPrefixes, SourceAddressPrefix, SourceApplicationSecurityGroups.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithSourceAddressOrSecurityGroup<ParentT> {
            /**
             * Specifies the traffic source address prefix to which this rule applies.
             * @param cidr an IP address prefix expressed in the CIDR notation
             * @return the next stage of the definition
             */
            @Method
            WithSourcePort<ParentT> fromAddress(String cidr);

            /**
             * Specifies that the rule applies to any traffic source address.
             * @return the next stage of the definition
             */
            @Method
            WithSourcePort<ParentT> fromAnyAddress();

            /**
             * Sets the application security group specified as source.
             * @param id  application security group id
             * @return the next stage of the update
             */
            WithSourcePort<ParentT> withSourceApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule definition allowing the source port(s) to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithSourcePort<ParentT> {
            /**
             * Specifies the source port to which this rule applies.
             * @param port the source port number
             * @return the next stage of the definition
             */
            WithDestinationAddressOrSecurityGroup<ParentT> fromPort(int port);

            /**
             * Makes this rule apply to any source port.
             * @return the next stage of the definition
             */
            @Method
            WithDestinationAddressOrSecurityGroup<ParentT> fromAnyPort();

            /**
             * Specifies the source port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            WithDestinationAddressOrSecurityGroup<ParentT> fromPortRange(int from, int to);
        }

        /**
         * The stage of the network rule definition allowing the destination address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * DestinationAddressPrefixes, DestinationAddressPrefix, DestinationApplicationSecurityGroups.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDestinationAddressOrSecurityGroup<ParentT> {
            /**
             * Specifies the traffic destination address range to which this rule applies.
             * @param cidr an IP address range expressed in the CIDR notation
             * @return the next stage of the definition
             */
            WithDestinationPort<ParentT> toAddress(String cidr);

            /**
             * Makes the rule apply to any traffic destination address.
             * @return the next stage of the definition
             */
            @Method
            WithDestinationPort<ParentT> toAnyAddress();

            /**
             * Sets the application security group specified as destination.
             * @param id  application security group id
             * @return the next stage of the definition
             */
            WithDestinationPort<ParentT> withDestinationApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule definition allowing the destination port(s) to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithDestinationPort<ParentT> {
            /**
             * Specifies the destination port to which this rule applies.
             * @param port the destination port number
             * @return the next stage of the definition
             */
            WithProtocol<ParentT> toPort(int port);

            /**
             * Makes this rule apply to any destination port.
             * @return the next stage of the definition
             */
            @Method
            WithProtocol<ParentT> toAnyPort();

            /**
             * Specifies the destination port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            WithProtocol<ParentT> toPortRange(int from, int to);
        }

        /**
         * The stage of the security rule definition allowing the protocol that the rule applies to to be specified.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithProtocol<ParentT> {
            /**
             * Specifies the protocol that this rule applies to.
             * @param protocol one of the supported protocols
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withProtocol(SecurityRuleProtocol protocol);

            /**
             * Makes this rule apply to any supported protocol.
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withAnyProtocol();
        }

        /** The final stage of the security rule definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the security rule definition
         * can be attached to the parent network security group definition using {@link WithAttach#attach()}.
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends Attachable.InUpdate<ParentT> {

            /**
             * Specifies the priority to assign to this rule.
             * <p>
             * Security rules are applied in the order of their assigned priority.
             * @param priority the priority number in the range 100 to 4096
             * @return the next stage of the update
             */
            WithAttach<ParentT> withPriority(int priority);

            /**
             * Specifies a description for this security rule.
             * @param descrtiption a text description to associate with the security rule
             * @return the next stage
             */
            WithAttach<ParentT> withDescription(String descrtiption);
        }
    }

    /**
     * The entirety of a security rule update as part of a network security group update.
     */
    interface Update extends
        UpdateStages.WithDirectionAccess,
        UpdateStages.WithSourceAddressOrSecurityGroup,
        UpdateStages.WithSourcePort,
        UpdateStages.WithDestinationAddressOrSecurityGroup,
        UpdateStages.WithDestinationPort,
        UpdateStages.WithProtocol,
        Settable<NetworkSecurityGroup.Update> {

        /**
         * Specifies the priority to assign to this security rule.
         * <p>
         * Security rules are applied in the order of their assigned priority.
         * @param priority the priority number in the range 100 to 4096
         * @return the next stage of the update
         */
        Update withPriority(int priority);

        /** Specifies a description for this security rule.
         * @param description a text description to associate with this security rule
         * @return the next stage
         */
        Update withDescription(String description);
    }

    /**
     * Grouping of security rule update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the network rule description allowing the direction and the access type to be specified.
         */
        interface WithDirectionAccess {
            /**
             * Allows inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            Update allowInbound();

            /**
             * Allows outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            Update allowOutbound();

            /**
             * Blocks inbound traffic.
             * @return the next stage of the definition
             */
            @Method
            Update denyInbound();

            /**
             * Blocks outbound traffic.
             * @return the next stage of the definition
             */
            @Method
            Update denyOutbound();
        }

        /**
         * The stage of the network rule description allowing the source address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * SourceAddressPrefixes, SourceAddressPrefix, SourceApplicationSecurityGroups.
         */
        interface WithSourceAddressOrSecurityGroup {
            /**
             * Specifies the traffic source address prefix to which this rule applies.
             * @param cidr an IP address prefix expressed in the CIDR notation
             * @return the next stage of the definition
             */
            @Method
            Update fromAddress(String cidr);

            /**
             * Specifies that the rule applies to any traffic source address.
             * @return the next stage of the definition
             */
            @Method
            Update fromAnyAddress();

            /**
             * Sets the application security group specified as source.
             * @param id  application security group id
             * @return the next stage of the update
             */
            Update withSourceApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule description allowing the source port(s) to be specified.
         */
        interface WithSourcePort {
            /**
             * Specifies the source port to which this rule applies.
             * @param port the source port number
             * @return the next stage of the definition
             */
            Update fromPort(int port);

            /**
             * Makes this rule apply to any source port.
             * @return the next stage of the definition
             */
            @Method
            Update fromAnyPort();

            /**
             * Specifies the source port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            Update fromPortRange(int from, int to);
        }

        /**
         * The stage of the network rule description allowing the destination address to be specified.
         * Note: network security rule must specify a non empty value for exactly one of:
         * DestinationAddressPrefixes, DestinationAddressPrefix, DestinationApplicationSecurityGroups.
         */
        interface WithDestinationAddressOrSecurityGroup {
            /**
             * Specifies the traffic destination address range to which this rule applies.
             * @param cidr an IP address range expressed in the CIDR notation
             * @return the next stage of the update
             */
            Update toAddress(String cidr);

            /**
             * Makes the rule apply to any traffic destination address.
             * @return the next stage of the update
             */
            @Method
            Update toAnyAddress();

            /**
             * Sets the application security group specified as destination.
             * @param id  application security group id
             * @return the next stage of the update
             */
            Update withDestinationApplicationSecurityGroup(String id);
        }

        /**
         * The stage of the network rule description allowing the destination port(s) to be specified.
         */
        interface WithDestinationPort {
            /**
             * Specifies the destination port to which this rule applies.
             * @param port the destination port number
             * @return the next stage of the definition
             */
            Update toPort(int port);

            /**
             * Makes this rule apply to any destination port.
             * @return the next stage of the definition
             */
            @Method
            Update toAnyPort();

            /**
             * Specifies the destination port range to which this rule applies.
             * @param from the starting port number
             * @param to the ending port number
             * @return the next stage of the definition
             */
            Update toPortRange(int from, int to);
        }

        /**
         * The stage of the security rule description allowing the protocol that the rule applies to to be specified.
         */
        interface WithProtocol {
            /**
             * Specifies the protocol that this rule applies to.
             * @param protocol one of the supported protocols
             * @return the next stage of the definition
             */
            Update withProtocol(SecurityRuleProtocol protocol);

            /**
             * Makes this rule apply to any supported protocol.
             * @return the next stage of the definition
             */
            Update withAnyProtocol();
        }
    }

 }
