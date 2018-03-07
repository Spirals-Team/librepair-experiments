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
package org.bboxdb.distribution.statistics;

import java.util.Collection;
import java.util.List;

import org.bboxdb.commons.concurrent.ExceptionSafeRunnable;
import org.bboxdb.commons.math.BoundingBox;
import org.bboxdb.distribution.DistributionGroupName;
import org.bboxdb.distribution.membership.BBoxDBInstance;
import org.bboxdb.distribution.membership.ZookeeperBBoxDBInstanceAdapter;
import org.bboxdb.distribution.partitioner.DistributionGroupZookeeperAdapter;
import org.bboxdb.distribution.partitioner.SpacePartitioner;
import org.bboxdb.distribution.partitioner.SpacePartitionerCache;
import org.bboxdb.distribution.region.DistributionRegion;
import org.bboxdb.distribution.region.DistributionRegionHelper;
import org.bboxdb.distribution.region.DistributionRegionIdMapper;
import org.bboxdb.distribution.zookeeper.ZookeeperClient;
import org.bboxdb.distribution.zookeeper.ZookeeperClientFactory;
import org.bboxdb.distribution.zookeeper.ZookeeperException;
import org.bboxdb.misc.BBoxDBException;
import org.bboxdb.storage.StorageManagerException;
import org.bboxdb.storage.sstable.SSTableConst;
import org.bboxdb.storage.tuplestore.manager.TupleStoreManagerRegistry;
import org.bboxdb.storage.tuplestore.manager.TupleStoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsUpdateThread extends ExceptionSafeRunnable {

	/**
	 * The Logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(StatisticsUpdateThread.class);
	
	/**
	 * The storage registry
	 */
	private TupleStoreManagerRegistry storageRegistry;

	/**
	 * The distribution group adapter
	 */
	private final DistributionGroupZookeeperAdapter adapter;
	
	public StatisticsUpdateThread(final TupleStoreManagerRegistry storageRegistry) {
		this.storageRegistry = storageRegistry;
		adapter = ZookeeperClientFactory.getDistributionGroupAdapter();
	}
	
	@Override
	protected void beginHook() {
		logger.info("Starting statistics update thread");
	}
	
	@Override
	protected void endHook() {
		logger.info("Statistics update thread is done");
	}

	@Override
	protected void runThread() {
		try {
			while(! Thread.currentThread().isInterrupted()) {
				updateNodeStats();
				updateRegionStatistics();
				Thread.sleep(SSTableConst.THREAD_STATISTICS_DELAY);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
	
	/**
	 * Update the local node stats (diskspace, memory)
	 * @throws ZookeeperException 
	 */
	private void updateNodeStats() {
		try {
			final BBoxDBInstance instance = ZookeeperClientFactory.getLocalInstanceName();

			logger.debug("Update zookeeper node stats");

			final ZookeeperClient zookeeperClient = ZookeeperClientFactory.getZookeeperClient();
			
			final ZookeeperBBoxDBInstanceAdapter zookeeperBBoxDBInstanceAdapter 
				= new ZookeeperBBoxDBInstanceAdapter(zookeeperClient);

			zookeeperBBoxDBInstanceAdapter.updateNodeInfo(instance);
		} catch (ZookeeperException e) {
			logger.error("Got exception while updating local node stats", e);
		}
	}
	
	/**
	 * Update the statistics of the region
	 */
	private void updateRegionStatistics() {
		
		try {
			final List<DistributionGroupName> allDistributionGroups = adapter.getDistributionGroups();
			for(final DistributionGroupName distributionGroup : allDistributionGroups) {
				final String fullname = distributionGroup.getFullname();
				
				final SpacePartitioner spacePartitioner = SpacePartitionerCache
						.getInstance().getSpacePartitionerForGroupName(fullname);
				
				final DistributionRegionIdMapper regionIdMapper = spacePartitioner.getDistributionRegionIdMapper();
				
				final Collection<Long> allIds = regionIdMapper.getRegionIdsForRegion(BoundingBox.FULL_SPACE);
				
				for(final long id : allIds) {
					updateRegionStatistics(distributionGroup, id);
				}
			}

		} catch (Exception e) {
			logger.error("Got exception while updating statistics", e);
		}
	}

	/**
	 * Update region statistics
	 * 
	 * @param distributionGroup
	 * @param regionId
	 * @throws ZookeeperException 
	 * @throws StorageManagerException 
	 * @throws InterruptedException 
	 */
	private void updateRegionStatistics(final DistributionGroupName distributionGroup, final long regionId) 
			throws BBoxDBException, ZookeeperException, StorageManagerException, InterruptedException {
		
		final String fullname = distributionGroup.getFullname();
		final SpacePartitioner spacePartitioner = SpacePartitionerCache.getInstance()
				.getSpacePartitionerForGroupName(fullname);
		
		final DistributionRegion distributionRegion = spacePartitioner.getRootNode();

		final DistributionRegion regionToSplit = DistributionRegionHelper.getDistributionRegionForNamePrefix(
				distributionRegion, regionId);
				
		final long totalSize = TupleStoreUtil.getSizeOfDistributionGroupAndRegionId(storageRegistry, 
				distributionGroup, regionId);
		
		final long totalTuples = TupleStoreUtil.getTuplesInDistributionGroupAndRegionId(storageRegistry, 
				distributionGroup, regionId);
		
		final long totalSizeInMb = totalSize / (1024 * 1024);
		
		logger.info("Updating region statistics: {} / {}. Size in MB: {} / Tuples: {}", 
				distributionGroup, regionId, totalSizeInMb, totalTuples);
										
		adapter.updateRegionStatistics(regionToSplit, ZookeeperClientFactory.getLocalInstanceName(), 
				totalSizeInMb, totalTuples);
	}
}
