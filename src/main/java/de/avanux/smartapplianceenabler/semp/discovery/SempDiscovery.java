/*
 * Copyright (C) 2017 Axel Müller <axel.mueller@avanux.de>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package de.avanux.smartapplianceenabler.semp.discovery;

import de.avanux.smartapplianceenabler.SmartApplianceEnabler;
import org.fourthline.cling.DefaultUpnpServiceConfiguration;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.transport.impl.apache.StreamServerConfigurationImpl;
import org.fourthline.cling.transport.spi.NetworkAddressFactory;
import org.fourthline.cling.transport.spi.StreamClient;
import org.fourthline.cling.transport.spi.StreamServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SempDiscovery implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SempDiscovery.class);
    private UpnpServiceConfiguration serviceConfiguration;
    private String sempServerUrl;

    public SempDiscovery() {
        serviceConfiguration = createServiceConfiguration();
        // FIXME make port configurable
        sempServerUrl = "http://" + findMyIpAddress() + ":8080";
        logger.info("SEMP UPnP will redirect to " + sempServerUrl);
    }

    public void run() {
        try {
            final ExecutorService executorService = Executors.newSingleThreadExecutor();
            final UpnpService upnpService = new UpnpServiceImpl(serviceConfiguration);
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(createDevice());
        }
        catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private UpnpServiceConfiguration createServiceConfiguration() {
        return new DefaultUpnpServiceConfiguration() {
            @Override
            public DeviceDescriptorBinder getDeviceDescriptorBinderUDA10() {
                return new SempDeviceDescriptorBinderImpl(this, sempServerUrl);
            }

            @Override
            public StreamClient createStreamClient() {
                // disable the client in order to avoid requesting descriptors from UPnP devices
                return null;
            }

            @Override
            public StreamServer createStreamServer(NetworkAddressFactory networkAddressFactory) {
                return new org.fourthline.cling.transport.impl.apache.StreamServerImpl(
                        new StreamServerConfigurationImpl()
                );
            }
        };
    }
    
    private LocalDevice createDevice()
            throws ValidationException, LocalServiceBindingException, IOException {

        DeviceIdentity identity =
                new DeviceIdentity(
                        UDN.uniqueSystemIdentifier(SmartApplianceEnabler.class.getSimpleName())
                );

        DeviceType type = new SmartApplianceEnablerDeviceType();

        DeviceDetails details =
                new DeviceDetails(
                        SmartApplianceEnabler.class.getSimpleName(),
                        new ManufacturerDetails(SmartApplianceEnabler.MANUFACTURER_NAME, URI.create(SmartApplianceEnabler.MANUFACTURER_URI)),
                        new ModelDetails(
                                SmartApplianceEnabler.class.getSimpleName(),
                                SmartApplianceEnabler.DESCRIPTION,
                                SmartApplianceEnabler.VERSION,
                                URI.create(SmartApplianceEnabler.MODEL_URI)
                        )
                );

        return new LocalDevice(identity, type, details, (Icon) null, (LocalService) null);
    }

    private String findMyIpAddress() {
        NetworkAddressFactory networkAddressFactory = this.serviceConfiguration.createNetworkAddressFactory();
        Iterator<InetAddress> bindAddresses = networkAddressFactory.getBindAddresses();
        while(bindAddresses.hasNext()) {
            return bindAddresses.next().toString().substring(1); // strip leading /
        }
        return "127.0.0.1";
    }
}
