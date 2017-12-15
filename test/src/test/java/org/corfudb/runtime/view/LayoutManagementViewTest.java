package org.corfudb.runtime.view;

import org.corfudb.infrastructure.TestLayoutBuilder;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.exceptions.LayoutModificationException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by box on 12/13/17.
 */
public class LayoutManagementViewTest extends AbstractViewTest{


    @Test
    public void removeNodeTest() throws Exception {
        addServer(SERVERS.PORT_0);
        addServer(SERVERS.PORT_1);
        addServer(SERVERS.PORT_2);

        Layout l = new TestLayoutBuilder()
                .setEpoch(1L)
                .addLayoutServer(SERVERS.PORT_0)
                .addLayoutServer(SERVERS.PORT_1)
                .addLayoutServer(SERVERS.PORT_2)
                .addSequencer(SERVERS.PORT_0)
                .addSequencer(SERVERS.PORT_1)
                .addSequencer(SERVERS.PORT_2)
                .buildSegment()
                .buildStripe()
                .addLogUnit(SERVERS.PORT_0)
                .addLogUnit(SERVERS.PORT_1)
                .addLogUnit(SERVERS.PORT_2)
                .addToSegment()
                .addToLayout()
                .build();
        bootstrapAllServers(l);

        CorfuRuntime r = getRuntime().connect();

        // Remove one node from a three node cluster
        r.getLayoutManagementView().removeNode(l, getEndpoint(SERVERS.PORT_2));
        Layout l2 = r.getLayoutView().getLayout();
        assertThat(l2.getAllServers()).doesNotContain(getEndpoint(SERVERS.PORT_2));

        // Remove a node from a two node cluster. Verify that a remove operation won't
        // cause the loss of redundancy
        assertThatThrownBy(() -> r.getLayoutManagementView().removeNode(l2,
                getEndpoint(SERVERS.PORT_1))).isInstanceOf(LayoutModificationException.class);
    }
}
