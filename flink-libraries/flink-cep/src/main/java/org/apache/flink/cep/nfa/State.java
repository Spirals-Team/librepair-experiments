/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.cep.nfa;

import org.apache.flink.cep.pattern.conditions.IterativeCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a state of the {@link NFA}.
 *
 * <p>Each state is identified by a name and a state type. Furthermore, it contains a collection of
 * state transitions. The state transitions describe under which conditions it is possible to enter
 * a new state.
 *
 * @param <T> Type of the input events
 */
public class State<T> implements Serializable {

	private static final long serialVersionUID = 6658700025989097781L;

	/**
	 * Set of valid state types.
	 */
	public enum StateType {
		START,	// the starting state of the NFA
		FINAL,	// the final state of the NFA
		NORMAL,	// an intermediate (neither start nor stop) state of the NFA
		STOP	// a stop state that, if reached, it invalidates the partial match
	}

	private final String name;
	private final Collection<StateTransition<T>> stateTransitions;

	private StateType stateType;

	public State(final String name, final StateType stateType) {
		this.name = name;
		this.stateType = stateType;

		stateTransitions = new ArrayList<>(4);
	}

	public String getName() {
		return name;
	}

	public Collection<StateTransition<T>> getStateTransitions() {
		return stateTransitions;
	}

	public StateType getStateType() {
		return stateType;
	}

	public boolean isFinal() {
		return stateType == StateType.FINAL;
	}

	public boolean isStart() {
		return stateType == StateType.START;
	}

	public boolean isStop() {
		return stateType == StateType.STOP;
	}

	public void makeStart() {
		this.stateType = StateType.START;
	}

	public void addStateTransition(
			final StateTransitionAction action,
			final State<T> targetState,
			final IterativeCondition<T> condition) {
		stateTransitions.add(new StateTransition<>(this, action, targetState, condition));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		State<T> other = (State<T>) obj;
		return name.equals(other.name) &&
				stateType == other.stateType &&
				stateTransitions.equals(other.stateTransitions);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + stateType.hashCode();
		result = 31 * result + Objects.hashCode(stateTransitions);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(4);
		builder.append(stateType).append(" State ").append(name).append(" [").append(System.lineSeparator());
		for (StateTransition<T> stateTransition: stateTransitions) {
			builder.append("\t").append(stateTransition).append(",").append(System.lineSeparator());
		}
		builder.append("])");
		return builder.toString();
	}
}
