package org.corfudb.test;

import org.corfudb.util.NodeLocator;
import org.corfudb.util.NodeLocator.Protocol;

// Magic number check disabled to make this constants more readable.
@SuppressWarnings("checkstyle:magicnumber")
public class CorfuTestServers {

    public static final String SERVER_HOST = "server";

    public static final NodeLocator SERVER_0 =  NodeLocator.builder()
                                            .protocol(Protocol.LOCAL)
                                            .host(SERVER_HOST)
                                            .port(0)
                                            .build();
}
