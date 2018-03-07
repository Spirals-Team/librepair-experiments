/*******************************************************************************
 *
 *    Copyright (C) 2015-2018 the BBoxDB project
 *  
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License. 
 *    
 *******************************************************************************/
package org.bboxdb.distribution.partitioner;

import java.util.Collection;
import java.util.Set;

import org.bboxdb.commons.math.BoundingBox;
import org.bboxdb.distribution.membership.BBoxDBInstance;
import org.bboxdb.distribution.region.DistributionRegion;
import org.bboxdb.distribution.region.DistributionRegionCallback;
import org.bboxdb.distribution.region.DistributionRegionIdMapper;
import org.bboxdb.distribution.zookeeper.ZookeeperException;
import org.bboxdb.misc.BBoxDBException;

public class QuadtreeSpacePartitioner implements SpacePartitioner {

	@Override
	public DistributionRegion getRootNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void allocateSystemsToRegion(DistributionRegion region, Set<BBoxDBInstance> allocationSystems)
			throws ZookeeperException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void splitRegion(DistributionRegion regionToSplit, 
			final Collection<BoundingBox> samples) throws BBoxDBException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void splitComplete(DistributionRegion regionToSplit) throws BBoxDBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean registerCallback(DistributionRegionCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregisterCallback(DistributionRegionCallback callback) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMergingSupported(final DistributionRegion distributionRegion) {
		return ! distributionRegion.isLeafRegion();
	}
	
	@Override
	public boolean isSplittingSupported(final DistributionRegion distributionRegion) {
		return distributionRegion.isLeafRegion();
	}

	@Override
	public void prepareMerge(final DistributionRegion regionToMerge) 
			throws BBoxDBException {
		
		throw new IllegalArgumentException("Unable to merge region, this is not supported");
	}
	
	@Override
	public void mergeComplete(final DistributionRegion regionToMerge) throws BBoxDBException {
		throw new IllegalArgumentException("Unable to merge region, this is not supported");
	}

	@Override
	public DistributionRegionIdMapper getDistributionRegionIdMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(SpacePartitionerContext spacePartitionerContext) throws ZookeeperException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
}
