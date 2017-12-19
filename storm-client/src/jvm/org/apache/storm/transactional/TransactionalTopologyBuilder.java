/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.transactional;

import org.apache.storm.coordination.BatchBoltExecutor;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.coordination.CoordinatedBolt;
import org.apache.storm.coordination.CoordinatedBolt.IdStreamSpec;
import org.apache.storm.coordination.CoordinatedBolt.SourceArgs;
import org.apache.storm.generated.GlobalStreamId;
import org.apache.storm.generated.Grouping;
import org.apache.storm.generated.SharedMemory;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.grouping.CustomStreamGrouping;
import org.apache.storm.grouping.PartialKeyGrouping;
import org.apache.storm.topology.BaseConfigurationDeclarer;
import org.apache.storm.topology.BasicBoltExecutor;
import org.apache.storm.topology.BoltDeclarer;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.coordination.IBatchBolt;
import org.apache.storm.topology.InputDeclarer;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.SpoutDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.transactional.partitioned.IOpaquePartitionedTransactionalSpout;
import org.apache.storm.transactional.partitioned.IPartitionedTransactionalSpout;
import org.apache.storm.transactional.partitioned.OpaquePartitionedTransactionalSpoutExecutor;
import org.apache.storm.transactional.partitioned.PartitionedTransactionalSpoutExecutor;
import org.apache.storm.tuple.Fields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Trident subsumes the functionality provided by transactional topologies, so this 
 * class is deprecated.
 * 
 */
@Deprecated
public class TransactionalTopologyBuilder {
    final String id;
    final String spoutId;
    final ITransactionalSpout spout;
    final Map<String, Component> bolts = new HashMap<>();
    final Integer spoutParallelism;
    final Map<String, Object> spoutConf = new HashMap<>();
    final Set<SharedMemory> spoutSharedMemory = new HashSet<>();

    // id is used to store the state of this transactionalspout in zookeeper
    // it would be very dangerous to have 2 topologies active with the same id in the same cluster    
    public TransactionalTopologyBuilder(String id, String spoutId, ITransactionalSpout spout, Number spoutParallelism) {
        this.id = id;
        this.spoutId = spoutId;
        this.spout = spout;
        this.spoutParallelism = (spoutParallelism == null) ? null : spoutParallelism.intValue();
    }
    
    public TransactionalTopologyBuilder(String id, String spoutId, ITransactionalSpout spout) {
        this(id, spoutId, spout, null);
    }

    public TransactionalTopologyBuilder(String id, String spoutId, IPartitionedTransactionalSpout spout, Number spoutParallelism) {
        this(id, spoutId, new PartitionedTransactionalSpoutExecutor(spout), spoutParallelism);
    }
    
    public TransactionalTopologyBuilder(String id, String spoutId, IPartitionedTransactionalSpout spout) {
        this(id, spoutId, spout, null);
    }
    
    public TransactionalTopologyBuilder(String id, String spoutId, IOpaquePartitionedTransactionalSpout spout, Number spoutParallelism) {
        this(id, spoutId, new OpaquePartitionedTransactionalSpoutExecutor(spout), spoutParallelism);
    }
    
    public TransactionalTopologyBuilder(String id, String spoutId, IOpaquePartitionedTransactionalSpout spout) {
        this(id, spoutId, spout, null);
    }
    
    public SpoutDeclarer getSpoutDeclarer() {
        return new SpoutDeclarerImpl();
    }
    
    public BoltDeclarer setBolt(String id, IBatchBolt bolt) {
        return setBolt(id, bolt, null);
    }
    
    public BoltDeclarer setBolt(String id, IBatchBolt bolt, Number parallelism) {
        return setBolt(id, new BatchBoltExecutor(bolt), parallelism, bolt instanceof ICommitter);
    }

    public BoltDeclarer setCommitterBolt(String id, IBatchBolt bolt) {
        return setCommitterBolt(id, bolt, null);
    }
    
    public BoltDeclarer setCommitterBolt(String id, IBatchBolt bolt, Number parallelism) {
        return setBolt(id, new BatchBoltExecutor(bolt), parallelism, true);
    }      
    
    public BoltDeclarer setBolt(String id, IBasicBolt bolt) {
        return setBolt(id, bolt, null);
    }    
    
    public BoltDeclarer setBolt(String id, IBasicBolt bolt, Number parallelism) {
        return setBolt(id, new BasicBoltExecutor(bolt), parallelism, false);
    }
    
    private BoltDeclarer setBolt(String id, IRichBolt bolt, Number parallelism, boolean committer) {
        Integer p = null;
        if(parallelism!=null) p = parallelism.intValue();
        Component component = new Component(bolt, p, committer);
        bolts.put(id, component);
        return new BoltDeclarerImpl(component);
    }
    
    public TopologyBuilder buildTopologyBuilder() {
        String coordinator = spoutId + "/coordinator";
        TopologyBuilder builder = new TopologyBuilder();
        SpoutDeclarer declarer = builder.setSpout(coordinator, new TransactionalSpoutCoordinator(spout));
        for (SharedMemory request: spoutSharedMemory) {
            declarer.addSharedMemory(request);
        }
        if (!spoutConf.isEmpty()) {
            declarer.addConfigurations(spoutConf);
        }
        declarer.addConfiguration(Config.TOPOLOGY_TRANSACTIONAL_ID, id);

        BoltDeclarer emitterDeclarer = 
                builder.setBolt(spoutId,
                        new CoordinatedBolt(new TransactionalSpoutBatchExecutor(spout),
                                             null,
                                             null),
                    spoutParallelism)
                .allGrouping(coordinator, TransactionalSpoutCoordinator.TRANSACTION_BATCH_STREAM_ID)
                .addConfiguration(Config.TOPOLOGY_TRANSACTIONAL_ID, id);
        if(spout instanceof ICommitterTransactionalSpout) {
            emitterDeclarer.allGrouping(coordinator, TransactionalSpoutCoordinator.TRANSACTION_COMMIT_STREAM_ID);
        }
        for(String id: bolts.keySet()) {
            Component component = bolts.get(id);
            Map<String, SourceArgs> coordinatedArgs = new HashMap<String, SourceArgs>();
            for(String c: componentBoltSubscriptions(component)) {
                coordinatedArgs.put(c, SourceArgs.all());
            }
            
            IdStreamSpec idSpec = null;
            if(component.committer) {
                idSpec = IdStreamSpec.makeDetectSpec(coordinator, TransactionalSpoutCoordinator.TRANSACTION_COMMIT_STREAM_ID);          
            }
            BoltDeclarer input = builder.setBolt(id,
                                                  new CoordinatedBolt(component.bolt,
                                                                      coordinatedArgs,
                                                                      idSpec),
                                                  component.parallelism);
            for (SharedMemory request: component.sharedMemory) {
                input.addSharedMemory(request);
            }
            if (!component.componentConf.isEmpty()) {
                input.addConfigurations(component.componentConf);
            }
            for(String c: componentBoltSubscriptions(component)) {
                input.directGrouping(c, Constants.COORDINATED_STREAM_ID);
            }
            for(InputDeclaration d: component.declarations) {
                d.declare(input);
            }
            if(component.committer) {
                input.allGrouping(coordinator, TransactionalSpoutCoordinator.TRANSACTION_COMMIT_STREAM_ID);                
            }
        }
        return builder;
    }
    
    public StormTopology buildTopology() {
        return buildTopologyBuilder().createTopology();
    }
    
    private Set<String> componentBoltSubscriptions(Component component) {
        Set<String> ret = new HashSet<String>();
        for(InputDeclaration d: component.declarations) {
            ret.add(d.getComponent());
        }
        return ret;
    }

    private static class Component {
        public final IRichBolt bolt;
        public final Integer parallelism;
        public final List<InputDeclaration> declarations = new ArrayList<>();
        public final Map<String, Object> componentConf = new HashMap<>();
        public final boolean committer;
        public final Set<SharedMemory> sharedMemory = new HashSet<>();

        public Component(IRichBolt bolt, Integer parallelism, boolean committer) {
            this.bolt = bolt;
            this.parallelism = parallelism;
            this.committer = committer;
        }
    }
    
    private static interface InputDeclaration {
        void declare(InputDeclarer declarer);
        String getComponent();
    }
    
    private class SpoutDeclarerImpl extends BaseConfigurationDeclarer<SpoutDeclarer> implements SpoutDeclarer {
        @Override
        public SpoutDeclarer addConfigurations(Map<String, Object> conf) {
            if (conf != null) {
                spoutConf.putAll(conf);
            }
            return this;
        }

        @Override
        public SpoutDeclarerImpl addResources(Map<String, Double> resources) {
            if (resources != null) {
                Map<String, Double> currentResources = (Map<String, Double>) spoutConf.computeIfAbsent(
                    Config.TOPOLOGY_COMPONENT_RESOURCES_MAP, (k) -> new HashMap<>());
                currentResources.putAll(resources);
            }
            return this;
        }

        @Override
        public SpoutDeclarer addSharedMemory(SharedMemory request) {
            spoutSharedMemory.add(request);
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public SpoutDeclarer addResource(String resourceName, Number resourceValue) {
            Map<String, Double> resourcesMap = (Map<String, Double>) spoutConf.computeIfAbsent(Config.TOPOLOGY_COMPONENT_RESOURCES_MAP,
                (k) -> new HashMap<>());

            resourcesMap.put(resourceName, resourceValue.doubleValue());
            return this;
        }
    }
    
    private static class BoltDeclarerImpl extends BaseConfigurationDeclarer<BoltDeclarer> implements BoltDeclarer {
        Component component;
        
        public BoltDeclarerImpl(Component component) {
            this.component = component;
        }
        
        @Override
        public BoltDeclarer fieldsGrouping(final String component, final Fields fields) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.fieldsGrouping(component, fields);
                }

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer fieldsGrouping(final String component, final String streamId, final Fields fields) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.fieldsGrouping(component, streamId, fields);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer globalGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.globalGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer globalGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.globalGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer shuffleGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.shuffleGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer shuffleGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.shuffleGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer localOrShuffleGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.localOrShuffleGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer localOrShuffleGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.localOrShuffleGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }
        
        @Override
        public BoltDeclarer noneGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.noneGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer noneGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.noneGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer allGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.allGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer allGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.allGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer directGrouping(final String component) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.directGrouping(component);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer directGrouping(final String component, final String streamId) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.directGrouping(component, streamId);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer partialKeyGrouping(String componentId, Fields fields) {
            return customGrouping(componentId, new PartialKeyGrouping(fields));
        }

        @Override
        public BoltDeclarer partialKeyGrouping(String componentId, String streamId, Fields fields) {
            return customGrouping(componentId, streamId, new PartialKeyGrouping(fields));
        }

        @Override
        public BoltDeclarer customGrouping(final String component, final CustomStreamGrouping grouping) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.customGrouping(component, grouping);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;        
        }

        @Override
        public BoltDeclarer customGrouping(final String component, final String streamId, final CustomStreamGrouping grouping) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.customGrouping(component, streamId, grouping);
                }                

                @Override
                public String getComponent() {
                    return component;
                }                
            });
            return this;
        }

        @Override
        public BoltDeclarer grouping(final GlobalStreamId stream, final Grouping grouping) {
            addDeclaration(new InputDeclaration() {
                @Override
                public void declare(InputDeclarer declarer) {
                    declarer.grouping(stream, grouping);
                }                

                @Override
                public String getComponent() {
                    return stream.get_componentId();
                }                
            });
            return this;
        }
        
        private void addDeclaration(InputDeclaration declaration) {
            component.declarations.add(declaration);
        }

        @Override
        public BoltDeclarer addConfigurations(Map<String, Object> conf) {
            if (conf != null) {
                component.componentConf.putAll(conf);
            }
            return this;
        }

        @Override
        public BoltDeclarer addResources(Map<String, Double> resources) {
            if (resources != null) {
                Map<String, Double> currentResources = (Map<String, Double>) component.componentConf.computeIfAbsent(
                    Config.TOPOLOGY_COMPONENT_RESOURCES_MAP, (k) -> new HashMap<>());
                currentResources.putAll(resources);
            }
            return this;
        }
        @Override
        public BoltDeclarer addSharedMemory(SharedMemory request) {
            component.sharedMemory.add(request);
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public BoltDeclarer addResource(String resourceName, Number resourceValue) {
            Map<String, Double> resourcesMap = (Map<String, Double>) component.componentConf.computeIfAbsent(
                Config.TOPOLOGY_COMPONENT_RESOURCES_MAP, (k) -> new HashMap<>());

            resourcesMap.put(resourceName, resourceValue.doubleValue());
            return this;
        }
    }
}
