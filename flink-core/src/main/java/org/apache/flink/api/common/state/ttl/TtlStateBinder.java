package org.apache.flink.api.common.state.ttl;

import org.apache.flink.api.common.state.AggregatingState;
import org.apache.flink.api.common.state.AggregatingStateDescriptor;
import org.apache.flink.api.common.state.FoldingState;
import org.apache.flink.api.common.state.FoldingStateDescriptor;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.state.StateBinder;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeutils.CompositeSerializer;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.LongSerializer;

import java.util.Arrays;
import java.util.List;

/**
 * This state factory wraps state objects, produced by original factory, with TTL logic.
 */
public class TtlStateBinder implements StateBinder {
	private final StateBinder originalStateBinder;
	private final TtlConfig ttlConfig;
	private final TtlTimeProvider timeProvider;

	public TtlStateBinder(StateBinder originalStateBinder, TtlConfig ttlConfig) {
		this(originalStateBinder, ttlConfig, System::currentTimeMillis);
	}

	public TtlStateBinder(StateBinder originalStateBinder, TtlConfig ttlConfig, TtlTimeProvider timeProvider) {
		this.originalStateBinder = originalStateBinder;
		this.ttlConfig = ttlConfig;
		this.timeProvider = timeProvider;
	}

	@Override
	public <T> ValueState<T> createValueState(ValueStateDescriptor<T> stateDesc) throws Exception {
		T defVal = stateDesc.getDefaultValue();
		TtlValue<T> ttlDefVal = defVal == null ? null : new TtlValue<>(defVal, Long.MAX_VALUE);
		ValueStateDescriptor<TtlValue<T>> ttlDescriptor = new ValueStateDescriptor<>(
			stateDesc.getName(),
			new TtlSerializer<>(stateDesc.getSerializer()),
			ttlDefVal);
		return new TtlValueState<>(originalStateBinder.createValueState(ttlDescriptor), ttlConfig, timeProvider);
	}

	@Override
	public <T> ListState<T> createListState(ListStateDescriptor<T> stateDesc) throws Exception {
		ListStateDescriptor<TtlValue<T>> ttlDescriptor = new ListStateDescriptor<>(
			stateDesc.getName(), new TtlSerializer<>(stateDesc.getElementSerializer()));
		return new TtlListState<>(originalStateBinder.createListState(ttlDescriptor), ttlConfig, timeProvider);
	}

	@Override
	public <MK, MV> MapState<MK, MV> createMapState(MapStateDescriptor<MK, MV> stateDesc) throws Exception {
		MapStateDescriptor<MK, TtlValue<MV>> ttlDescriptor = new MapStateDescriptor<>(
			stateDesc.getName(), stateDesc.getKeySerializer(), new TtlSerializer<>(stateDesc.getValueSerializer()));
		return new TtlMapState<>(originalStateBinder.createMapState(ttlDescriptor), ttlConfig, timeProvider);
	}

	@Override
	public <T> ReducingState<T> createReducingState(ReducingStateDescriptor<T> stateDesc) throws Exception {
		ReducingStateDescriptor<TtlValue<T>> ttlDescriptor = new ReducingStateDescriptor<>(
			stateDesc.getName(),
			new TtlReduceFunction<>(stateDesc.getReduceFunction(), ttlConfig, timeProvider),
			new TtlSerializer<>(stateDesc.getSerializer()));
		return new TtlReducingState<>(
			originalStateBinder.createReducingState(ttlDescriptor),
			ttlConfig, timeProvider);
	}

	@Override
	public <IN, ACC, OUT> AggregatingState<IN, OUT> createAggregatingState(
		AggregatingStateDescriptor<IN, ACC, OUT> stateDesc) throws Exception {
		AggregatingStateDescriptor<IN, TtlValue<ACC>, OUT> ttlDescriptor = new AggregatingStateDescriptor<>(
			stateDesc.getName(),
			new TtlAggregateFunction<>(stateDesc.getAggregateFunction(), ttlConfig, timeProvider),
			new TtlSerializer<>(stateDesc.getSerializer()));
		return originalStateBinder.createAggregatingState(ttlDescriptor);
	}

	@Override
	public <T, ACC> FoldingState<T, ACC> createFoldingState(FoldingStateDescriptor<T, ACC> stateDesc) throws Exception {
		ACC initAcc = stateDesc.getDefaultValue();
		TtlValue<ACC> ttlInitAcc = initAcc == null ? null : new TtlValue<>(initAcc, Long.MAX_VALUE);
		FoldingStateDescriptor<T, TtlValue<ACC>> ttlDescriptor = new FoldingStateDescriptor<>(
			stateDesc.getName(),
			ttlInitAcc,
			new TtlFoldFunction<>(stateDesc.getFoldFunction(), ttlConfig, timeProvider),
			new TtlSerializer<>(stateDesc.getSerializer()));
		return new TtlFoldingState<>(
			originalStateBinder.createFoldingState(ttlDescriptor),
			ttlConfig, timeProvider);
	}

	private static class TtlSerializer<T> extends CompositeSerializer<TtlValue<T>> {
		TtlSerializer(TypeSerializer<T> userValueSerializer) {
			super(Arrays.asList(userValueSerializer, new LongSerializer()));
		}

		@Override
		@SuppressWarnings("unchecked")
		protected TtlValue<T> composeValue(List values) {
			return new TtlValue<>((T) values.get(0), (Long) values.get(1));
		}

		@Override
		protected List decomposeValue(TtlValue<T> v) {
			return Arrays.asList(v.getUserValue(), v.getExpirationTimestamp());
		}

		@Override
		@SuppressWarnings("unchecked")
		protected CompositeSerializer<TtlValue<T>> createSerializerInstance(List<TypeSerializer> typeSerializers) {
			return new TtlSerializer<>(typeSerializers.get(0));
		}
	}

}
