package org.apache.flink.streaming.tests;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.tests.artificialstate.eventpayload.ComplexPayload;

import static org.apache.flink.streaming.tests.DataStreamAllroundTestProgram.createArtificialKeyedStateMapper;
import static org.apache.flink.streaming.tests.DataStreamAllroundTestProgram.createEventSource;
import static org.apache.flink.streaming.tests.DataStreamAllroundTestProgram.createSemanticsCheckMapper;
import static org.apache.flink.streaming.tests.DataStreamAllroundTestProgram.createTimestampExtractor;
import static org.apache.flink.streaming.tests.DataStreamAllroundTestProgram.setupEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * Test upgrade of stateful job.
 */
public class StatefulStreamJobUpgradeTestProgram {
	private static final String TEST_JOB_VARIANT_ORIGINAL = "original";
	private static final String TEST_JOB_VARIANT_UPGRADED = "upgraded";

	private static final JoinFunction<Event, ComplexPayload, ComplexPayload> SIMPLE_STATE_UPDATE =
		(Event first, ComplexPayload second) -> new ComplexPayload(first);
	private static final JoinFunction<Event, ComplexPayload, ComplexPayload> LAST_EVENT_STATE_UPDATE =
		(Event first, ComplexPayload second) ->
			(second != null && first.getEventTime() <= second.getEventTime()) ? second : new ComplexPayload(first);

	private static final ConfigOption<String> TEST_JOB_VARIANT = ConfigOptions
		.key("test.job.variant")
		.defaultValue(TEST_JOB_VARIANT_ORIGINAL)
		.withDescription(String.format("This configures the job variant to test. Can be '%s' or '%s'",
			TEST_JOB_VARIANT_ORIGINAL, TEST_JOB_VARIANT_UPGRADED));

	public static void main(String[] args) throws Exception {
		final ParameterTool pt = ParameterTool.fromArgs(args);

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		setupEnvironment(env, pt);

		KeyedStream<Event, Integer> source = env.addSource(createEventSource(pt))
			.assignTimestampsAndWatermarks(createTimestampExtractor(pt))
			.keyBy(Event::getKey);

		List<TypeSerializer<ComplexPayload>> stateSer =
			Collections.singletonList(new KryoSerializer<>(ComplexPayload.class, env.getConfig()));

		boolean isOriginal = pt.get(TEST_JOB_VARIANT.key()).equals(TEST_JOB_VARIANT_ORIGINAL);
		KeyedStream<Event, Integer> afterStatefulOperations = isOriginal ?
			applyOriginalStatefulOperations(source, stateSer) :
			applyUpgradedStatefulOperations(source, stateSer);

		afterStatefulOperations
			.flatMap(createSemanticsCheckMapper(pt))
			.name("SemanticsCheckMapper")
			.addSink(new PrintSinkFunction<>());

		env.execute("General purpose test job");
	}

	private static KeyedStream<Event, Integer> applyOriginalStatefulOperations(
		KeyedStream<Event, Integer> source,
		List<TypeSerializer<ComplexPayload>> stateSer) {
		source = applyTestStatefulOperator("stateMap1", SIMPLE_STATE_UPDATE, source, stateSer);
		return applyTestStatefulOperator("stateMap2", LAST_EVENT_STATE_UPDATE, source, stateSer);
	}

	private static KeyedStream<Event, Integer> applyUpgradedStatefulOperations(
		KeyedStream<Event, Integer> source,
		List<TypeSerializer<ComplexPayload>> stateSer) {
		source = applyTestStatefulOperator("stateMap2", SIMPLE_STATE_UPDATE, source, stateSer);
		source = applyTestStatefulOperator("stateMap1", LAST_EVENT_STATE_UPDATE, source, stateSer);
		return applyTestStatefulOperator("stateMap3", SIMPLE_STATE_UPDATE, source, stateSer);
	}

	private static KeyedStream<Event, Integer> applyTestStatefulOperator(
		String name,
		JoinFunction<Event, ComplexPayload, ComplexPayload> stateFunc,
		KeyedStream<Event, Integer> source,
		List<TypeSerializer<ComplexPayload>> stateSer) {
		return source
			.map(createArtificialKeyedStateMapper(appendToEventPayload("-" + name), stateFunc, stateSer))
			.name(name)
			.uid(name)
			.returns(Event.class)
			.keyBy(Event::getKey);
	}

	private static MapFunction<Event, Event> appendToEventPayload(String str) {
		return (MapFunction<Event, Event>) e ->
			new Event(e.getKey(), e.getEventTime(), e.getSequenceNumber(), e.getPayload() + str);
	}
}
