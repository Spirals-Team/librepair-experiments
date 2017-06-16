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

package org.apache.flink.test.util;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.AkkaOptions;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.HighAvailabilityOptions;
import org.apache.flink.configuration.JobManagerOptions;
import org.apache.flink.configuration.TaskManagerOptions;
import org.apache.flink.core.testutils.CommonTestUtils;
import org.apache.flink.runtime.messages.TaskManagerMessages;
import org.apache.flink.runtime.minicluster.LocalFlinkMiniCluster;
import org.apache.flink.util.TestLogger;

import akka.actor.ActorRef;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import static org.apache.flink.util.Preconditions.checkArgument;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Utility class containing various methods for testing purposes.
 */
public class TestBaseUtils extends TestLogger {

	private static final Logger LOG = LoggerFactory.getLogger(TestBaseUtils.class);

	protected static final int MINIMUM_HEAP_SIZE_MB = 192;

	protected static final long TASK_MANAGER_MEMORY_SIZE = 80;

	protected static final long DEFAULT_AKKA_ASK_TIMEOUT = 1000;

	protected static final String DEFAULT_AKKA_STARTUP_TIMEOUT = "60 s";

	public static final FiniteDuration DEFAULT_TIMEOUT = new FiniteDuration(DEFAULT_AKKA_ASK_TIMEOUT, TimeUnit.SECONDS);

	// ------------------------------------------------------------------------

	protected static File logDir;

	protected TestBaseUtils(){
		verifyJvmOptions();
	}

	private static void verifyJvmOptions() {
		long heap = Runtime.getRuntime().maxMemory() >> 20;
		Assert.assertTrue("Insufficient java heap space " + heap + "mb - set JVM option: -Xmx" + MINIMUM_HEAP_SIZE_MB
				+ "m", heap > MINIMUM_HEAP_SIZE_MB - 50);
	}

	public static LocalFlinkMiniCluster startCluster(
		int numTaskManagers,
		int taskManagerNumSlots,
		boolean startWebserver,
		boolean startZooKeeper,
		boolean singleActorSystem) throws Exception {

		Configuration config = new Configuration();

		config.setInteger(ConfigConstants.LOCAL_NUMBER_TASK_MANAGER, numTaskManagers);
		config.setInteger(ConfigConstants.TASK_MANAGER_NUM_TASK_SLOTS, taskManagerNumSlots);

		config.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, startWebserver);

		if (startZooKeeper) {
			config.setInteger(ConfigConstants.LOCAL_NUMBER_JOB_MANAGER, 3);
			config.setString(HighAvailabilityOptions.HA_MODE, "zookeeper");
		}

		return startCluster(config, singleActorSystem);
	}

	public static LocalFlinkMiniCluster startCluster(
		Configuration config,
		boolean singleActorSystem) throws Exception {

		logDir = File.createTempFile("TestBaseUtils-logdir", null);
		Assert.assertTrue("Unable to delete temp file", logDir.delete());
		Assert.assertTrue("Unable to create temp directory", logDir.mkdir());
		Path logFile = Files.createFile(new File(logDir, "jobmanager.log").toPath());
		Files.createFile(new File(logDir, "jobmanager.out").toPath());

		config.setLong(TaskManagerOptions.MANAGED_MEMORY_SIZE, TASK_MANAGER_MEMORY_SIZE);
		config.setBoolean(ConfigConstants.FILESYSTEM_DEFAULT_OVERWRITE_KEY, true);

		config.setString(AkkaOptions.ASK_TIMEOUT, DEFAULT_AKKA_ASK_TIMEOUT + "s");
		config.setString(AkkaOptions.STARTUP_TIMEOUT, DEFAULT_AKKA_STARTUP_TIMEOUT);

		config.setInteger(JobManagerOptions.WEB_PORT, 8081);
		config.setString(JobManagerOptions.WEB_LOG_PATH, logFile.toString());

		config.setString(ConfigConstants.TASK_MANAGER_LOG_PATH_KEY, logFile.toString());

		LocalFlinkMiniCluster cluster =  new LocalFlinkMiniCluster(config, singleActorSystem);

		cluster.start();

		return cluster;
	}

	public static void stopCluster(LocalFlinkMiniCluster executor, FiniteDuration timeout) throws Exception {
		if (logDir != null) {
			FileUtils.deleteDirectory(logDir);
		}
		if (executor != null) {
			int numUnreleasedBCVars = 0;
			int numActiveConnections = 0;

			if (executor.running()) {
				List<ActorRef> tms = executor.getTaskManagersAsJava();
				List<Future<Object>> bcVariableManagerResponseFutures = new ArrayList<>();
				List<Future<Object>> numActiveConnectionsResponseFutures = new ArrayList<>();

				for (ActorRef tm : tms) {
					bcVariableManagerResponseFutures.add(Patterns.ask(
						tm,
						TaskManagerMessages.getRequestBroadcastVariablesWithReferences(),
						new Timeout(timeout)));

					numActiveConnectionsResponseFutures.add(Patterns.ask(
						tm,
						TaskManagerMessages.getRequestNumActiveConnections(),
						new Timeout(timeout)));
				}

				Future<Iterable<Object>> bcVariableManagerFutureResponses = Futures.sequence(
						bcVariableManagerResponseFutures, defaultExecutionContext());

				Iterable<Object> responses = Await.result(bcVariableManagerFutureResponses, timeout);

				for (Object response : responses) {
					numUnreleasedBCVars += ((TaskManagerMessages.ResponseBroadcastVariablesWithReferences) response).number();
				}

				Future<Iterable<Object>> numActiveConnectionsFutureResponses = Futures.sequence(
						numActiveConnectionsResponseFutures, defaultExecutionContext());

				responses = Await.result(numActiveConnectionsFutureResponses, timeout);

				for (Object response : responses) {
					numActiveConnections += ((TaskManagerMessages.ResponseNumActiveConnections) response).number();
				}
			}

			executor.stop();
			FileSystem.closeAll();
			System.gc();

			Assert.assertEquals("Not all broadcast variables were released.", 0, numUnreleasedBCVars);
			Assert.assertEquals("Not all TCP connections were released.", 0, numActiveConnections);
		}

	}

	// --------------------------------------------------------------------------------------------
	//  Result Checking
	// --------------------------------------------------------------------------------------------

	public static BufferedReader[] getResultReader(String resultPath) throws IOException {
		return getResultReader(resultPath, new String[]{}, false);
	}

	public static BufferedReader[] getResultReader(
			String resultPath,
			String[] excludePrefixes,
			boolean inOrderOfFiles) throws IOException {

		File[] files = getAllInvolvedFiles(resultPath, excludePrefixes);

		if (inOrderOfFiles) {
			// sort the files after their name (1, 2, 3, 4)...
			// we cannot sort by path, because strings sort by prefix
			Arrays.sort(files, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
					try {
						int f1 = Integer.parseInt(o1.getName());
						int f2 = Integer.parseInt(o2.getName());
						return f1 < f2 ? -1 : (f1 > f2 ? 1 : 0);
					}
					catch (NumberFormatException e) {
						throw new RuntimeException("The file names are no numbers and cannot be ordered: " +
								o1.getName() + "/" + o2.getName());
					}
				}
			});
		}

		BufferedReader[] readers = new BufferedReader[files.length];
		for (int i = 0; i < files.length; i++) {
			readers[i] = new BufferedReader(new FileReader(files[i]));
		}
		return readers;
	}

	public static BufferedInputStream[] getResultInputStream(String resultPath) throws IOException {
		return getResultInputStream(resultPath, new String[]{});
	}

	public static BufferedInputStream[] getResultInputStream(String resultPath, String[]
			excludePrefixes) throws IOException {
		File[] files = getAllInvolvedFiles(resultPath, excludePrefixes);
		BufferedInputStream[] inStreams = new BufferedInputStream[files.length];
		for (int i = 0; i < files.length; i++) {
			inStreams[i] = new BufferedInputStream(new FileInputStream(files[i]));
		}
		return inStreams;
	}

	public static void readAllResultLines(List<String> target, String resultPath) throws IOException {
		readAllResultLines(target, resultPath, new String[]{});
	}

	public static void readAllResultLines(List<String> target, String resultPath, String[] excludePrefixes)
			throws IOException {

		readAllResultLines(target, resultPath, excludePrefixes, false);
	}

	public static void readAllResultLines(
			List<String> target,
			String resultPath,
			String[] excludePrefixes,
			boolean inOrderOfFiles) throws IOException {

		checkArgument(resultPath != null, "resultPath cannot be be null");

		final BufferedReader[] readers = getResultReader(resultPath, excludePrefixes, inOrderOfFiles);
		try {
			for (BufferedReader reader : readers) {
				String s;
				while ((s = reader.readLine()) != null) {
					target.add(s);
				}
			}
		}
		finally {
			for (BufferedReader reader : readers) {
				org.apache.flink.util.IOUtils.closeQuietly(reader);
			}
		}
	}

	public static void compareResultsByLinesInMemory(String expectedResultStr, String resultPath) throws Exception {
		compareResultsByLinesInMemory(expectedResultStr, resultPath, new String[0]);
	}

	public static void compareResultsByLinesInMemory(
			String expectedResultStr,
			String resultPath,
			String[] excludePrefixes) throws Exception {

		ArrayList<String> list = new ArrayList<>();
		readAllResultLines(list, resultPath, excludePrefixes, false);

		String[] result = list.toArray(new String[list.size()]);
		Arrays.sort(result);

		String[] expected = expectedResultStr.isEmpty() ? new String[0] : expectedResultStr.split("\n");
		Arrays.sort(expected);

		if (expected.length != result.length || !Arrays.deepEquals(expected, result)) {
			String msg = String.format(
					"Different elements in arrays: expected %d elements and received %d\n" +
					"files: %s\n expected: %s\n received: %s",
					expected.length, result.length,
					Arrays.toString(getAllInvolvedFiles(resultPath, excludePrefixes)),
					Arrays.toString(expected), Arrays.toString(result));
			fail(msg);
		}
	}

	public static void compareResultsByLinesInMemoryWithStrictOrder(String expectedResultStr,
																	String resultPath) throws Exception {
		compareResultsByLinesInMemoryWithStrictOrder(expectedResultStr, resultPath, new String[]{});
	}

	public static void compareResultsByLinesInMemoryWithStrictOrder(String expectedResultStr,
																	String resultPath, String[] excludePrefixes) throws Exception {
		ArrayList<String> list = new ArrayList<>();
		readAllResultLines(list, resultPath, excludePrefixes, true);

		String[] result = list.toArray(new String[list.size()]);

		String[] expected = expectedResultStr.split("\n");

		Assert.assertEquals("Different number of lines in expected and obtained result.", expected.length, result.length);
		Assert.assertArrayEquals(expected, result);
	}

	public static void checkLinesAgainstRegexp(String resultPath, String regexp){
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher("");

		ArrayList<String> list = new ArrayList<>();
		try {
			readAllResultLines(list, resultPath, new String[]{}, false);
		} catch (IOException e1) {
			Assert.fail("Error reading the result");
		}

		for (String line : list){
			matcher.reset(line);
			if (!matcher.find()){
				String msg = "Line is not well-formed: " + line;
				Assert.fail(msg);
			}
		}
	}

	public static void compareKeyValuePairsWithDelta(String expectedLines, String resultPath,
														String delimiter, double maxDelta) throws Exception {
		compareKeyValuePairsWithDelta(expectedLines, resultPath, new String[]{}, delimiter, maxDelta);
	}

	public static void compareKeyValuePairsWithDelta(String expectedLines, String resultPath,
														String[] excludePrefixes, String delimiter, double maxDelta) throws Exception {
		ArrayList<String> list = new ArrayList<>();
		readAllResultLines(list, resultPath, excludePrefixes, false);

		String[] result = list.toArray(new String[list.size()]);
		String[] expected = expectedLines.isEmpty() ? new String[0] : expectedLines.split("\n");

		Assert.assertEquals("Wrong number of result lines.", expected.length, result.length);

		Arrays.sort(result);
		Arrays.sort(expected);

		for (int i = 0; i < expected.length; i++) {
			String[] expectedFields = expected[i].split(delimiter);
			String[] resultFields = result[i].split(delimiter);

			double expectedPayLoad = Double.parseDouble(expectedFields[1]);
			double resultPayLoad = Double.parseDouble(resultFields[1]);

			Assert.assertTrue("Values differ by more than the permissible delta", Math.abs(expectedPayLoad - resultPayLoad) < maxDelta);
		}
	}

	public static <X> void compareResultCollections(List<X> expected, List<X> actual,
											Comparator<X> comparator) {
		Assert.assertEquals(expected.size(), actual.size());

		Collections.sort(expected, comparator);
		Collections.sort(actual, comparator);

		for (int i = 0; i < expected.size(); i++) {
			Assert.assertEquals(expected.get(i), actual.get(i));
		}
	}

	private static File[] getAllInvolvedFiles(String resultPath, final String[] excludePrefixes) {
		final File result = asFile(resultPath);
		assertTrue("Result file was not written", result.exists());

		if (result.isDirectory()) {
			return result.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					for (String p: excludePrefixes) {
						if (name.startsWith(p)) {
							return false;
						}
					}
					return true;
				}
			});
		} else {
			return new File[] { result };
		}
	}

	protected static File asFile(String path) {
		try {
			URI uri = new URI(path);
			if (uri.getScheme().equals("file")) {
				return new File(uri.getPath());
			} else {
				throw new IllegalArgumentException("This path does not denote a local file.");
			}
		} catch (URISyntaxException | NullPointerException e) {
			throw new IllegalArgumentException("This path does not describe a valid local file URI.");
		}
	}

	// --------------------------------------------------------------------------------------------
	// Comparison methods for tests using collect()
	// --------------------------------------------------------------------------------------------

	public static <T> void compareResultAsTuples(List<T> result, String expected) {
		compareResult(result, expected, true, true);
	}

	public static <T> void compareResultAsText(List<T> result, String expected) {
		compareResult(result, expected,
				false, true);
	}

	public static <T> void compareOrderedResultAsText(List<T> result, String expected) {
		compareResult(result, expected, false, false);
	}

	public static <T> void compareOrderedResultAsText(List<T> result, String expected, boolean asTuples) {
		compareResult(result, expected, asTuples, false);
	}

	private static <T> void compareResult(List<T> result, String expected, boolean asTuples, boolean sort) {
		String[] expectedStrings = expected.split("\n");
		String[] resultStrings = new String[result.size()];

		for (int i = 0; i < resultStrings.length; i++) {
			T val = result.get(i);

			if (asTuples) {
				if (val instanceof Tuple) {
					Tuple t = (Tuple) val;
					Object first = t.getField(0);
					StringBuilder bld = new StringBuilder(first == null ? "null" : first.toString());
					for (int pos = 1; pos < t.getArity(); pos++) {
						Object next = t.getField(pos);
						bld.append(',').append(next == null ? "null" : next.toString());
					}
					resultStrings[i] = bld.toString();
				}
				else {
					throw new IllegalArgumentException(val + " is no tuple");
				}
			}
			else {
				resultStrings[i] = (val == null) ? "null" : val.toString();
			}
		}

		if (sort) {
			Arrays.sort(expectedStrings);
			Arrays.sort(resultStrings);
		}

		// Include content of both arrays to provide more context in case of a test failure
		String msg = String.format(
			"Different elements in arrays: expected %d elements and received %d\n expected: %s\n received: %s",
			expectedStrings.length, resultStrings.length,
			Arrays.toString(expectedStrings), Arrays.toString(resultStrings));

		assertEquals(msg, expectedStrings.length, resultStrings.length);

		for (int i = 0; i < expectedStrings.length; i++) {
			assertEquals(msg, expectedStrings[i], resultStrings[i]);
		}
	}

	// --------------------------------------------------------------------------------------------
	// Comparison methods for tests using sample
	// --------------------------------------------------------------------------------------------

	/**
	 * The expected string contains all expected results separate with line break, check whether all elements in result
	 * are contained in the expected string.
	 * @param result The test result.
	 * @param expected The expected string value combination.
	 * @param <T> The result type.
	 */
	public static <T> void containsResultAsText(List<T> result, String expected) {
		String[] expectedStrings = expected.split("\n");
		List<String> resultStrings = new ArrayList<>();

		for (T val : result) {
			String str = (val == null) ? "null" : val.toString();
			resultStrings.add(str);
		}

		List<String> expectedStringList = Arrays.asList(expectedStrings);

		for (String element : resultStrings) {
			assertTrue(expectedStringList.contains(element));
		}
	}

	// --------------------------------------------------------------------------------------------
	//  Miscellaneous helper methods
	// --------------------------------------------------------------------------------------------

	protected static Collection<Object[]> toParameterList(Configuration ... testConfigs) {
		ArrayList<Object[]> configs = new ArrayList<>();
		for (Configuration testConfig : testConfigs) {
			Object[] c = { testConfig };
			configs.add(c);
		}
		return configs;
	}

	protected static Collection<Object[]> toParameterList(List<Configuration> testConfigs) {
		LinkedList<Object[]> configs = new LinkedList<>();
		for (Configuration testConfig : testConfigs) {
			Object[] c = { testConfig };
			configs.add(c);
		}
		return configs;
	}

	public static void setEnv(Map<String, String> newenv) {
		CommonTestUtils.setEnv(newenv);
	}

	private static ExecutionContext defaultExecutionContext() {
		return ExecutionContext$.MODULE$.global();
	}
	// --------------------------------------------------------------------------------------------
	//  File helper methods
	// --------------------------------------------------------------------------------------------

	protected static void deleteRecursively(File f) throws IOException {
		if (f.isDirectory()) {
			FileUtils.deleteDirectory(f);
		} else if (!f.delete()) {
			System.err.println("Failed to delete file " + f.getAbsolutePath());
		}
	}

	public static String constructTestPath(Class<?> forClass, String folder) {
		// we create test path that depends on class to prevent name clashes when two tests
		// create temp files with the same name
		String path = System.getProperty("java.io.tmpdir");
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path += System.getProperty("file.separator");
		}
		path += (forClass.getName() + "-" + folder);
		return path;
	}

	public static String constructTestURI(Class<?> forClass, String folder) {
		return new File(constructTestPath(forClass, folder)).toURI().toString();
	}

	//---------------------------------------------------------------------------------------------
	// Web utils
	//---------------------------------------------------------------------------------------------

	public static String getFromHTTP(String url) throws Exception {
		URL u = new URL(url);
		LOG.info("Accessing URL " + url + " as URL: " + u);
		HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		connection.setConnectTimeout(100000);
		connection.connect();
		InputStream is;
		if (connection.getResponseCode() >= 400) {
			// error!
			LOG.warn("HTTP Response code when connecting to {} was {}", url, connection.getResponseCode());
			is = connection.getErrorStream();
		} else {
			is = connection.getInputStream();
		}

		return IOUtils.toString(is, connection.getContentEncoding() != null ? connection.getContentEncoding() : "UTF-8");
	}

	/**
	 * Comparator for comparable Tuples.
	 * @param <T> tuple type
	 */
	public static class TupleComparator<T extends Tuple> implements Comparator<T> {

		@Override
		public int compare(T o1, T o2) {
			if (o1 == null || o2 == null) {
				throw new IllegalArgumentException("Cannot compare null tuples");
			}
			else if (o1.getArity() != o2.getArity()) {
				return o1.getArity() - o2.getArity();
			}
			else {
				for (int i = 0; i < o1.getArity(); i++) {
					Object val1 = o1.getField(i);
					Object val2 = o2.getField(i);

					int cmp;
					if (val1 != null && val2 != null) {
						cmp = compareValues(val1, val2);
					}
					else {
						cmp = val1 == null ? (val2 == null ? 0 : -1) : 1;
					}

					if (cmp != 0) {
						return cmp;
					}
				}

				return 0;
			}
		}

		@SuppressWarnings("unchecked")
		private static <X extends Comparable<X>> int compareValues(Object o1, Object o2) {
			if (o1 instanceof Comparable && o2 instanceof Comparable) {
				X c1 = (X) o1;
				X c2 = (X) o2;
				return c1.compareTo(c2);
			}
			else {
				throw new IllegalArgumentException("Cannot compare tuples with non comparable elements");
			}
		}
	}
}
