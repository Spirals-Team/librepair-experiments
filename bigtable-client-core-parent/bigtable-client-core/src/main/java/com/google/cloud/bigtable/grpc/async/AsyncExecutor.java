/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
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
package com.google.cloud.bigtable.grpc.async;

import java.io.IOException;
import java.util.List;

import com.google.bigtable.v2.CheckAndMutateRowRequest;
import com.google.bigtable.v2.CheckAndMutateRowResponse;
import com.google.bigtable.v2.MutateRowRequest;
import com.google.bigtable.v2.MutateRowResponse;
import com.google.bigtable.v2.MutateRowsRequest;
import com.google.bigtable.v2.MutateRowsResponse;
import com.google.bigtable.v2.ReadModifyWriteRowRequest;
import com.google.bigtable.v2.ReadModifyWriteRowResponse;
import com.google.bigtable.v2.ReadRowsRequest;
import com.google.bigtable.v2.Row;
import com.google.cloud.bigtable.config.Logger;
import com.google.cloud.bigtable.grpc.BigtableDataClient;
import com.google.cloud.bigtable.grpc.scanner.FlatRow;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.MessageLite;

/**
 * This class provides management of asynchronous Bigtable RPCs. It ensures that there aren't too
 * many concurrent, in flight asynchronous RPCs and also makes sure that the memory used by the
 * requests doesn't exceed a threshold.
 *
 * @author sduskis
 * @version $Id: $Id
 */
public class AsyncExecutor {

  /** Constant <code>LOG</code> */
  protected static final Logger LOG = new Logger(AsyncExecutor.class);

  protected interface AsyncCall<RequestT, ResponseT> {
    ListenableFuture<ResponseT> call(BigtableDataClient client, RequestT request);
  }

  /**
   * Calls {@link BigtableDataClient#mutateRowAsync(MutateRowRequest)}.
   */
  protected static AsyncCall<MutateRowRequest, MutateRowResponse> MUTATE_ROW_ASYNC =
      new AsyncCall<MutateRowRequest, MutateRowResponse>() {
        @Override
        public ListenableFuture<MutateRowResponse> call(BigtableDataClient client, MutateRowRequest request) {
          return client.mutateRowAsync(request);
        }
      };

  /**
   * Calls {@link BigtableDataClient#mutateRowsAsync(MutateRowsRequest)}.
   */
  protected static AsyncCall<MutateRowsRequest, List<MutateRowsResponse>> MUTATE_ROWS_ASYNC =
      new AsyncCall<MutateRowsRequest, List<MutateRowsResponse>>() {
        @Override
        public ListenableFuture<List<MutateRowsResponse>> call(BigtableDataClient client,
            MutateRowsRequest request) {
          return client.mutateRowsAsync(request);
        }
      };

  /**
   * Calls {@link BigtableDataClient#readModifyWriteRowAsync(ReadModifyWriteRowRequest)}.
   */
  protected static AsyncCall<ReadModifyWriteRowRequest, ReadModifyWriteRowResponse> READ_MODIFY_WRITE_ASYNC =
      new AsyncCall<ReadModifyWriteRowRequest, ReadModifyWriteRowResponse>() {
        @Override
        public ListenableFuture<ReadModifyWriteRowResponse> call(BigtableDataClient client,
            ReadModifyWriteRowRequest request) {
          return client.readModifyWriteRowAsync(request);
        }
      };

  /**
   * Calls {@link BigtableDataClient#checkAndMutateRowAsync(CheckAndMutateRowRequest)}.
   */
  protected static AsyncCall<CheckAndMutateRowRequest, CheckAndMutateRowResponse> CHECK_AND_MUTATE_ASYNC =
      new AsyncCall<CheckAndMutateRowRequest, CheckAndMutateRowResponse>() {
        @Override
        public ListenableFuture<CheckAndMutateRowResponse> call(BigtableDataClient client,
            CheckAndMutateRowRequest request) {
          return client.checkAndMutateRowAsync(request);
        }
      };

  /**
   * Calls {@link BigtableDataClient#readRowsAsync(ReadRowsRequest)}.
   */
  protected static AsyncCall<ReadRowsRequest, List<Row>> READ_ROWS_ASYNC =
      new AsyncCall<ReadRowsRequest, List<Row>>() {
        @Override
        public ListenableFuture<List<Row>> call(BigtableDataClient client, ReadRowsRequest request) {
          return client.readRowsAsync(request);
        }
      };


  /**
   * Calls {@link BigtableDataClient#readRowsAsync(ReadRowsRequest)}.
   */
  protected static AsyncCall<ReadRowsRequest, List<FlatRow>> READ_FLAT_ROWS_ASYNC =
      new AsyncCall<ReadRowsRequest, List<FlatRow>>() {
        @Override
        public ListenableFuture<List<FlatRow>> call(BigtableDataClient client, ReadRowsRequest request) {
          return client.readFlatRowsAsync(request);
        }
      };

  private final BigtableDataClient client;
  private final OperationAccountant operationsAccountant;

  /**
   * <p>
   * Constructor for AsyncExecutor.
   * </p>
   * @param client a {@link com.google.cloud.bigtable.grpc.BigtableDataClient} object for executing
   *          RPCs.
   */
  public AsyncExecutor(BigtableDataClient client) {
    this(client, new OperationAccountant());
  }

  /**
   * <p>
   * Constructor for AsyncExecutor.
   * </p>
   * @param client a {@link com.google.cloud.bigtable.grpc.BigtableDataClient} object for executing
   *          RPCs.
   * @param operationAccountant a {@link com.google.cloud.bigtable.grpc.async.OperationAccountant}
   *          object for tracking the RPCs initiated by this instance.
   */
  @VisibleForTesting
  AsyncExecutor(BigtableDataClient client, OperationAccountant operationAccountant) {
    this.client = client;
    this.operationsAccountant = operationAccountant;
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#mutateRowAsync(MutateRowRequest)} on the
   * {@link com.google.bigtable.v2.MutateRowRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.MutateRowRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<MutateRowResponse> mutateRowAsync(MutateRowRequest request)
      throws InterruptedException {
    return call(MUTATE_ROW_ASYNC, request);
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#mutateRowsAsync(MutateRowsRequest)} on the
   * {@link com.google.bigtable.v2.MutateRowsRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.MutateRowRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<List<MutateRowsResponse>> mutateRowsAsync(MutateRowsRequest request)
      throws InterruptedException {
    return call(MUTATE_ROWS_ASYNC, request);
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#checkAndMutateRowAsync(CheckAndMutateRowRequest)} on the
   * {@link com.google.bigtable.v2.CheckAndMutateRowRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.CheckAndMutateRowRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<CheckAndMutateRowResponse> checkAndMutateRowAsync(
      CheckAndMutateRowRequest request) throws InterruptedException {
    return call(CHECK_AND_MUTATE_ASYNC, request);
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#readModifyWriteRow(ReadModifyWriteRowRequest)} on the
   * {@link com.google.bigtable.v2.ReadModifyWriteRowRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.ReadModifyWriteRowRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<ReadModifyWriteRowResponse>
      readModifyWriteRowAsync(ReadModifyWriteRowRequest request) throws InterruptedException {
    return call(READ_MODIFY_WRITE_ASYNC, request);
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#readRowsAsync(ReadRowsRequest)} on the
   * {@link com.google.bigtable.v2.ReadRowsRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.ReadRowsRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<List<Row>> readRowsAsync(ReadRowsRequest request)
      throws InterruptedException {
    return call(READ_ROWS_ASYNC, request);
  }

  /**
   * Performs a {@link com.google.cloud.bigtable.grpc.BigtableDataClient#readRowsAsync(ReadRowsRequest)} on the
   * {@link com.google.bigtable.v2.ReadRowsRequest}. This method may block if
   * {@link OperationAccountant#registerOperation(ListenableFuture)} blocks.
   *
   * @param request The {@link com.google.bigtable.v2.ReadRowsRequest} to send.
   * @return a {@link com.google.common.util.concurrent.ListenableFuture} which can be listened to for completion events.
   * @throws java.lang.InterruptedException if any.
   */
  public ListenableFuture<List<FlatRow>> readFlatRowsAsync(ReadRowsRequest request)
      throws InterruptedException {
    return call(READ_FLAT_ROWS_ASYNC, request);
  }

  private <RequestT extends MessageLite, ResponseT> ListenableFuture<ResponseT> call(
      AsyncCall<RequestT, ResponseT> rpc, RequestT request) {
    // Wait until both the memory and rpc count maximum requirements are achieved before getting a
    // unique id used to track this request.
    ListenableFuture<ResponseT> future;
    try {
      future = rpc.call(client, request);
    } catch (Throwable e) {
      future = Futures.immediateFailedFuture(e);
    }
    operationsAccountant.registerOperation(future);
    return future;
  }

  /**
   * Waits until all operations managed by the
   * {@link com.google.cloud.bigtable.grpc.async.OperationAccountant} complete. See
   * {@link com.google.cloud.bigtable.grpc.async.OperationAccountant#awaitCompletion()} for more
   * information.
   * @throws java.io.IOException if something goes wrong.
   */
  public void flush() throws IOException {
    LOG.trace("Flushing");
    try {
      operationsAccountant.awaitCompletion();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Batch operations were interrupted.");
    }
    LOG.trace("Done flushing");
  }

  /**
   * <p>
   * hasInflightRequests.
   * </p>
   * @return a boolean.
   */
  public boolean hasInflightRequests() {
    return operationsAccountant.hasInflightOperations();
  }

  /**
   * <p>
   * Getter for the field <code>client</code>.
   * </p>
   * @return a {@link com.google.cloud.bigtable.grpc.BigtableDataClient} object.
   */
  public BigtableDataClient getClient() {
    return client;
  }

  /**
   * <p>
   * Getter for the field <code>operationsAccountant</code>.
   * </p>
   * @return a {@link com.google.cloud.bigtable.grpc.async.OperationAccountant} object.
   */
  public OperationAccountant getOperationAccountant() {
    return operationsAccountant;
  }
}
