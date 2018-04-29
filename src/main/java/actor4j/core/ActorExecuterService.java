/*
 * Copyright (c) 2015-2017, David A. Bauer. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package actor4j.core;

import static actor4j.core.utils.ActorLogger.logger;
import static actor4j.core.utils.ActorUtils.actorLabel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import actor4j.core.actors.Actor;
import actor4j.core.messages.ActorMessage;
import actor4j.core.persistence.ActorPersistenceService;
import actor4j.core.safety.ErrorHandler;
import actor4j.core.safety.SafetyManager;

public class ActorExecuterService {
	protected ActorSystemImpl system;
	
	protected SafetyManager safetyManager;
	
	protected List<ActorThread> actorThreads;
	
	protected CountDownLatch countDownLatch;
	protected Runnable onTermination;
	
	protected AtomicBoolean started;
	
	protected ActorTimerExecuterService globalTimerExecuterService;
	protected ActorTimerExecuterService timerExecuterService;
	protected ExecutorService clientExecuterService;
	protected ExecutorService resourceExecuterService;
	
	protected ActorPersistenceService persistenceService;
	
	protected int maxResourceThreads;
	
	public ActorExecuterService(final ActorSystemImpl system) {
		super();
		
		this.system = system;
		
		actorThreads = new ArrayList<>();
		
		started = new AtomicBoolean();
		
		maxResourceThreads = 200;
		
		safetyManager = new SafetyManager();
		safetyManager.setErrorHandler(new ErrorHandler() {
			@Override
			public void handle(Throwable t, String message, UUID uuid) {
				if (message!=null) {
					if (message.equals("initialization")) {
						logger().error(
							String.format("%s - Safety (%s) - Exception in initialization of an actor", 
								system.name, Thread.currentThread().getName()));
					}
					else if (message.equals("actor") || message.equals("resource")) {
						Actor actor = system.cells.get(uuid).actor;
							logger().error(
								String.format("%s - Safety (%s) - Exception in actor: %s", 
									system.name, Thread.currentThread().getName(), actorLabel(actor)));
					}
					else if (message.equals("pseudo")) {
						Actor actor = system.pseudoCells.get(uuid).actor;
							logger().error(
								String.format("%s - Safety (%s) - Exception in actor: %s", 
									system.name, Thread.currentThread().getName(), actorLabel(actor)));
					}
				}
				else {
					logger().fatal(
						String.format("%s - Safety (%s) - Exception in ActorThread", 
								system.name, Thread.currentThread().getName()));
				}
				
				t.printStackTrace();
			}
		});
	}
	
	protected void reset() {
		actorThreads.clear();
		
		started.set(false);
	}
	
	public SafetyManager getSafetyManager() {
		return safetyManager;
	}

	public List<ActorThread> getActorThreads() {
		return actorThreads;
	}

	public void run(Runnable onStartup) {
		start(onStartup, null);
	}
	
	public void start(Runnable onStartup, Runnable onTermination) {
		if (system.cells.size()==0)
			return;
		
		int poolSize = Runtime.getRuntime().availableProcessors();
		
		globalTimerExecuterService = new ActorTimerExecuterService(system, 1, "actor4j-global-timer-thread");
		timerExecuterService = new ActorTimerExecuterService(system, poolSize);
		
		resourceExecuterService = new ThreadPoolExecutor(poolSize, maxResourceThreads, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ActorThreadFactory("actor4j-resource-thread"));
		if (system.clientMode)
			clientExecuterService = new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
		
		if (system.persistenceMode) {
			persistenceService = new ActorPersistenceService(system.wrapper, system.parallelismMin, system.parallelismFactor, system.databaseHost, system.databasePort, system.databaseName);
			persistenceService.start();
		}
		
		this.onTermination = onTermination;
		
		countDownLatch = new CountDownLatch(system.parallelismMin*system.parallelismFactor);
		for (int i=0; i<system.parallelismMin*system.parallelismFactor; i++) {
			try {
				Constructor<? extends ActorThread> c2 = system.actorThreadClass.getConstructor(ActorSystemImpl.class);
				ActorThread t = c2.newInstance(system);
			
				t.onTermination = new Runnable() {
					@Override
					public void run() {
						countDownLatch.countDown();
					}
				};
				actorThreads.add(t);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		system.messageDispatcher.beforeRun(actorThreads);
		for (ActorThread t : actorThreads)
			t.start();
		
		/*
		 * necessary before executing onStartup; 
		 * creating of childrens in Actor::preStart: childrens needs to register at the dispatcher
		 * (see also ActorSystemImpl::internal_addCell)
		 */
		started.set(true);
		
		if (onStartup!=null)
			onStartup.run();
	}
	
	public boolean isStarted() {
		return started.get();
	}

	public ActorTimer timer() {
		return timerExecuterService;
	}
	
	public ActorTimer globalTimer() {
		return globalTimerExecuterService;
	}

	public void clientViaAlias(final ActorMessage<?> message, final String alias) {
		if (system.clientRunnable!=null)
			clientExecuterService.submit(new Runnable() {
				@Override
				public void run() {
					system.clientRunnable.runViaAlias(message, alias);
				}
			});
	}
	
	public void clientViaPath(final ActorMessage<?> message, final ActorServiceNode node, final String path) {
		if (system.clientRunnable!=null)
			clientExecuterService.submit(new Runnable() {
				@Override
				public void run() {
					system.clientRunnable.runViaPath(message, node, path);
				}
			});
	}
	
	public void resource(final ActorMessage<?> message) {
		final ResourceActorCell cell = (ResourceActorCell)system.cells.get(message.dest);
		if (cell!=null && cell.beforeRun(message)) {
			resourceExecuterService.submit(new Runnable() {
				@Override
				public void run() {
					cell.run(message);
				}
			});
		}
	}
	
	public void shutdown(boolean await) {
		globalTimerExecuterService.shutdown();
		timerExecuterService.shutdown();
		
		resourceExecuterService.shutdown();
		if (system.clientMode)
			clientExecuterService.shutdown();

		if (actorThreads.size()>0) {
			for (ActorThread t : actorThreads)
				t.interrupt();
		}
		
		if (onTermination!=null || await) {
			Thread waitOnTermination = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					if (onTermination!=null)
						onTermination.run();
				}
			});
			
			waitOnTermination.start();
			if (await)
				try {
					waitOnTermination.join();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
		}
		
		if (system.persistenceMode)
			persistenceService.shutdown();
		
		reset();
	}
	
	public long getCount() {
		long sum = 0;
		for (ActorThread t : actorThreads)
			sum += t.getCount();
		
		return sum;
	}
	public List<Long> getCounts() {
		List<Long> list = new ArrayList<>();
		for (ActorThread t : actorThreads)
			list.add(t.getCount());
		return list;
	}
}
