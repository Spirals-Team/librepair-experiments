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
package actor4j.core.actors;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Predicate;

import actor4j.core.ActorSystem;
import actor4j.core.PseudoActorCell;
import actor4j.core.messages.ActorMessage;
import rx.Observable;

public abstract class PseudoActor extends ActorWithRxStash {
	public PseudoActor(ActorSystem system, boolean blocking) {
		this(null, system, blocking);
	}
	
	public PseudoActor(String name, ActorSystem system, boolean blocking) {
		super(name);
		
		PseudoActorCell cell = new PseudoActorCell(system, this, blocking);
		setCell(cell);
		cell.system_addCell(cell);
		/* preStart */
		preStart();
	}

	public boolean run() {
		return ((PseudoActorCell)cell).run();
	}
	
	public boolean runOnce() {
		return ((PseudoActorCell)cell).runOnce();
	}
	
	public Observable<ActorMessage<?>> runWithRx() {
		return ((PseudoActorCell)cell).runWithRx();
	}
	
	public ActorMessage<?> await() {
		return ((PseudoActorCell)cell).await();
	}
	
	public ActorMessage<?> await(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return ((PseudoActorCell)cell).await(timeout, unit);
	}
	
	public <T> T await(Predicate<ActorMessage<?>> predicate, Function<ActorMessage<?>, T> action, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
		return ((PseudoActorCell)cell).await(predicate, action, timeout, unit);
	}
	
	public void reset() {
		((PseudoActorCell)cell).reset();
	}
}
