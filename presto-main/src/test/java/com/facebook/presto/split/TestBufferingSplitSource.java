/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.split;

import com.facebook.presto.execution.DriverGroupId;
import com.google.common.util.concurrent.ListenableFuture;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.facebook.presto.split.MockSplitSource.Action.FAIL;
import static com.facebook.presto.split.MockSplitSource.Action.FINISH;
import static io.airlift.testing.Assertions.assertContains;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class TestBufferingSplitSource
{
    @DataProvider
    public static Object[][] nextBatchCalls()
    {
        return new Object[][]{{NextBatchCall.SINGLE_ARGUMENT}, {NextBatchCall.WITH_UNGROUPED_DRIVER_GROUP}};
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testSlowSource(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1).increaseAvailableSplits(25).atSplitCompletion(FINISH);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            assertNextBatch(source, 20, 10, false, nextBatchCall);
            assertNextBatch(source, 6, 6, false, nextBatchCall);
            assertNextBatch(source, 20, 9, true, nextBatchCall);
            assertTrue(source.isFinished());
            assertEquals(mockSource.getNextBatchInvocationCount(), 25);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testFastSource(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(11).increaseAvailableSplits(22).atSplitCompletion(FINISH);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            assertNextBatch(source, 200, 11, false, nextBatchCall);
            assertNextBatch(source, 200, 11, true, nextBatchCall);
            assertTrue(source.isFinished());
            assertEquals(mockSource.getNextBatchInvocationCount(), 2);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testEmptySource(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1).atSplitCompletion(FINISH);
        try (SplitSource source = new BufferingSplitSource(mockSource, 100)) {
            assertNextBatch(source, 200, 0, true, nextBatchCall);
            assertTrue(source.isFinished());
            assertEquals(mockSource.getNextBatchInvocationCount(), 1);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testBlocked(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            // Source has 0 out of 10 needed.
            ListenableFuture<?> nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            assertFalse(nextBatchFuture.isDone());
            mockSource.increaseAvailableSplits(9);
            assertFalse(nextBatchFuture.isDone());
            mockSource.increaseAvailableSplits(1);
            assertNextBatchResult(nextBatchFuture, 10, false, nextBatchCall);

            // Source is completed after getNextBatch invocation.
            nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            assertFalse(nextBatchFuture.isDone());
            mockSource.atSplitCompletion(FINISH);
            assertNextBatchResult(nextBatchFuture, 0, true, nextBatchCall);
            assertTrue(source.isFinished());
        }

        mockSource = new MockSplitSource().setBatchSize(1);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            // Source has 1 out of 10 needed.
            mockSource.increaseAvailableSplits(1);
            ListenableFuture<?> nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            assertFalse(nextBatchFuture.isDone());
            mockSource.increaseAvailableSplits(9);
            assertNextBatchResult(nextBatchFuture, 10, false, nextBatchCall);

            // Source is completed with 5 last splits after getNextBatch invocation.
            nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            mockSource.increaseAvailableSplits(5);
            assertFalse(nextBatchFuture.isDone());
            mockSource.atSplitCompletion(FINISH);
            assertNextBatchResult(nextBatchFuture, 5, true, nextBatchCall);
            assertTrue(source.isFinished());
        }

        mockSource = new MockSplitSource().setBatchSize(1);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            // Source has 9 out of 10 needed.
            mockSource.increaseAvailableSplits(9);
            ListenableFuture<?> nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            assertFalse(nextBatchFuture.isDone());
            mockSource.increaseAvailableSplits(1);
            assertNextBatchResult(nextBatchFuture, 10, false, nextBatchCall);

            // Source failed after getNextBatch invocation.
            nextBatchFuture = invokeNextBatch(source, 10, nextBatchCall);
            mockSource.increaseAvailableSplits(5);
            assertFalse(nextBatchFuture.isDone());
            mockSource.atSplitCompletion(FAIL);
            assertNextBatchFailure(nextBatchFuture, nextBatchCall);
            assertFalse(source.isFinished());
        }

        // Fast source: source produce 8 before, and 8 after invocation. BufferedSource should return all 16 at once.
        mockSource = new MockSplitSource().setBatchSize(8);
        try (SplitSource source = new BufferingSplitSource(mockSource, 10)) {
            mockSource.increaseAvailableSplits(8);
            ListenableFuture<?> nextBatchFuture = invokeNextBatch(source, 20, nextBatchCall);
            assertFalse(nextBatchFuture.isDone());
            mockSource.increaseAvailableSplits(8);
            assertNextBatchResult(nextBatchFuture, 16, false, nextBatchCall);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testFinishedSetWithoutIndicationFromSplitBatch(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1).increaseAvailableSplits(1);
        try (SplitSource source = new BufferingSplitSource(mockSource, 100)) {
            assertNextBatch(source, 1, 1, false, nextBatchCall);
            assertFalse(source.isFinished());
            // Most of the time, mockSource.isFinished() returns the same value as
            // the SplitBatch.noMoreSplits field of the preceding mockSource.getNextBatch() call.
            // However, this is always the case.
            // In this case, the preceding getNextBatch() indicates the noMoreSplits is false,
            // but the next isFinished call will return true.
            mockSource.atSplitCompletion(FINISH);
            assertNextBatch(source, 1, 0, true, nextBatchCall);
            assertTrue(source.isFinished());
            assertEquals(mockSource.getNextBatchInvocationCount(), 2);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testFailImmediate(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1).atSplitCompletion(FAIL);
        try (SplitSource source = new BufferingSplitSource(mockSource, 100)) {
            assertNextBatchFailure(invokeNextBatch(source, 200, nextBatchCall), nextBatchCall);
            assertEquals(mockSource.getNextBatchInvocationCount(), 1);
        }
    }

    @Test(dataProvider = "nextBatchCalls")
    public void testFail(NextBatchCall nextBatchCall)
            throws Exception
    {
        MockSplitSource mockSource = new MockSplitSource().setBatchSize(1).increaseAvailableSplits(1).atSplitCompletion(FAIL);
        try (SplitSource source = new BufferingSplitSource(mockSource, 100)) {
            assertNextBatchFailure(invokeNextBatch(source, 2, nextBatchCall), nextBatchCall);
            assertEquals(mockSource.getNextBatchInvocationCount(), 2);
        }
    }

    public void testDriverGroups()
    {
//        try (SplitSource source = new BufferingSplitSource(mockSource, 100)) {
//            assertNextBatchFailure(source, 2, nextBatchCall);
//            assertEquals(mockSource.getNextBatchInvocationCount(), 2);
//        }
    }

    private ListenableFuture<?> invokeNextBatch(SplitSource source, int maxSize, NextBatchCall nextBatchCall)
    {
        ListenableFuture<?> result;
        switch (nextBatchCall) {
            case SINGLE_ARGUMENT:
                result = source.getNextBatch(maxSize);
                break;
            case WITH_UNGROUPED_DRIVER_GROUP:
                result = source.getNextBatch(DriverGroupId.notGrouped(), maxSize);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return result;
    }

    private void assertNextBatchResult(ListenableFuture<?> nextBatchFuture, int expectedSize, boolean expectedNoMoreSplits, NextBatchCall nextBatchCall)
            throws Exception
    {
        assertTrue(nextBatchFuture.isDone());
        switch (nextBatchCall) {
            case SINGLE_ARGUMENT: {
                List<?> actual = (List<?>) nextBatchFuture.get();
                assertEquals(actual.size(), expectedSize);
                break;
            }
            case WITH_UNGROUPED_DRIVER_GROUP: {
                SplitSource.SplitBatch actual = (SplitSource.SplitBatch) nextBatchFuture.get();
                assertEquals(actual.getSplits().size(), expectedSize);
                assertEquals(actual.isNoMoreSplits(), expectedNoMoreSplits);
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void assertNextBatch(SplitSource source, int maxSize, int expectedSize, boolean expectedNoMoreSplits, NextBatchCall nextBatchCall)
            throws Exception
    {
        ListenableFuture<?> nextBatchFuture = invokeNextBatch(source, maxSize, nextBatchCall);
        assertNextBatchResult(nextBatchFuture, expectedSize, expectedNoMoreSplits, nextBatchCall);
    }

    private void assertNextBatchFailure(ListenableFuture<?> nextBatchFuture, NextBatchCall nextBatchCall)
    {
        assertTrue(nextBatchFuture.isDone());
        try {
            nextBatchFuture.get();
            fail();
        }
        catch (Exception e) {
            assertContains(e.getMessage(), "Mock failure");
        }
    }

    private enum NextBatchCall {
        SINGLE_ARGUMENT,
        WITH_UNGROUPED_DRIVER_GROUP,
    }
}
