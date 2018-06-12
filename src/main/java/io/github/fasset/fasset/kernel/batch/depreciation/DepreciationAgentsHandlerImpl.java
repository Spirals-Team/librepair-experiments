/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.batch.depreciation;

import io.github.fasset.fasset.kernel.batch.depreciation.agent.Agent;
import io.github.fasset.fasset.model.FixedAsset;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

/**
 * This object encapsulates the AgentsDepreciationChain and manages the addition of an Agent to
 * the chain and sends a depreciation request to the chain for processing
 */
@Component("depreciationAgentsHandler")
public class DepreciationAgentsHandlerImpl implements DepreciationAgentsHandler {

    private DepreciationAgentsChainImpl depreciationAgentsChain;

    public DepreciationAgentsHandlerImpl(DepreciationAgentsChainImpl depreciationAgentsChain) {
        this.depreciationAgentsChain = depreciationAgentsChain;
    }

    public void setDepreciationAgent(Agent agent) {

        depreciationAgentsChain.addAgent(agent);
    }

    @Override
    public void sendRequest(FixedAsset asset, YearMonth month, DepreciationProceeds depreciationProceeds) {

        depreciationAgentsChain.execute(asset, month, depreciationProceeds);
    }
}
