package org.matsim.contrib.hybridsim.proto;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class HybridSimulationGrpc {

  private HybridSimulationGrpc() {}

  public static final String SERVICE_NAME = "hybridsim.HybridSimulation";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> METHOD_SIMULATED_TIME_INERVAL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "simulatedTimeInerval"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean> METHOD_TRANSFER_AGENT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "transferAgent"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories> METHOD_RECEIVE_TRAJECTORIES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "receiveTrajectories"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents> METHOD_RETRIEVE_AGENTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "retrieveAgents"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> METHOD_SHUTDOWN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "shutdown"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario,
      org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> METHOD_INIT_SCENARIO =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "hybridsim.HybridSimulation", "initScenario"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty.getDefaultInstance()));

  public static HybridSimulationStub newStub(io.grpc.Channel channel) {
    return new HybridSimulationStub(channel);
  }

  public static HybridSimulationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HybridSimulationBlockingStub(channel);
  }

  public static HybridSimulationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HybridSimulationFutureStub(channel);
  }

  public static interface HybridSimulation {

    public void simulatedTimeInerval(org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver);

    public void transferAgent(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean> responseObserver);

    public void receiveTrajectories(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories> responseObserver);

    public void retrieveAgents(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents> responseObserver);

    public void shutdown(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver);

    public void initScenario(org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver);
  }

  public static interface HybridSimulationBlockingClient {

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty simulatedTimeInerval(org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request);

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean transferAgent(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request);

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories receiveTrajectories(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents retrieveAgents(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty shutdown(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty initScenario(org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request);
  }

  public static interface HybridSimulationFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> simulatedTimeInerval(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request);

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean> transferAgent(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request);

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories> receiveTrajectories(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents> retrieveAgents(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> shutdown(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request);

    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> initScenario(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request);
  }

  public static class HybridSimulationStub extends io.grpc.stub.AbstractStub<HybridSimulationStub>
      implements HybridSimulation {
    private HybridSimulationStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HybridSimulationStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HybridSimulationStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HybridSimulationStub(channel, callOptions);
    }

    @java.lang.Override
    public void simulatedTimeInerval(org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SIMULATED_TIME_INERVAL, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void transferAgent(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_AGENT, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void receiveTrajectories(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_TRAJECTORIES, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void retrieveAgents(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RETRIEVE_AGENTS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void shutdown(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void initScenario(org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request,
        io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INIT_SCENARIO, getCallOptions()), request, responseObserver);
    }
  }

  public static class HybridSimulationBlockingStub extends io.grpc.stub.AbstractStub<HybridSimulationBlockingStub>
      implements HybridSimulationBlockingClient {
    private HybridSimulationBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HybridSimulationBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HybridSimulationBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HybridSimulationBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty simulatedTimeInerval(org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SIMULATED_TIME_INERVAL, getCallOptions(), request);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean transferAgent(org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TRANSFER_AGENT, getCallOptions(), request);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories receiveTrajectories(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RECEIVE_TRAJECTORIES, getCallOptions(), request);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents retrieveAgents(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RETRIEVE_AGENTS, getCallOptions(), request);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty shutdown(org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SHUTDOWN, getCallOptions(), request);
    }

    @java.lang.Override
    public org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty initScenario(org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INIT_SCENARIO, getCallOptions(), request);
    }
  }

  public static class HybridSimulationFutureStub extends io.grpc.stub.AbstractStub<HybridSimulationFutureStub>
      implements HybridSimulationFutureClient {
    private HybridSimulationFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HybridSimulationFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HybridSimulationFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HybridSimulationFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> simulatedTimeInerval(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SIMULATED_TIME_INERVAL, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean> transferAgent(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TRANSFER_AGENT, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories> receiveTrajectories(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RECEIVE_TRAJECTORIES, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents> retrieveAgents(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RETRIEVE_AGENTS, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> shutdown(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty> initScenario(
        org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INIT_SCENARIO, getCallOptions()), request);
    }
  }

  private static final int METHODID_SIMULATED_TIME_INERVAL = 0;
  private static final int METHODID_TRANSFER_AGENT = 1;
  private static final int METHODID_RECEIVE_TRAJECTORIES = 2;
  private static final int METHODID_RETRIEVE_AGENTS = 3;
  private static final int METHODID_SHUTDOWN = 4;
  private static final int METHODID_INIT_SCENARIO = 5;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HybridSimulation serviceImpl;
    private final int methodId;

    public MethodHandlers(HybridSimulation serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SIMULATED_TIME_INERVAL:
          serviceImpl.simulatedTimeInerval((org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>) responseObserver);
          break;
        case METHODID_TRANSFER_AGENT:
          serviceImpl.transferAgent((org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean>) responseObserver);
          break;
        case METHODID_RECEIVE_TRAJECTORIES:
          serviceImpl.receiveTrajectories((org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories>) responseObserver);
          break;
        case METHODID_RETRIEVE_AGENTS:
          serviceImpl.retrieveAgents((org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents>) responseObserver);
          break;
        case METHODID_SHUTDOWN:
          serviceImpl.shutdown((org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>) responseObserver);
          break;
        case METHODID_INIT_SCENARIO:
          serviceImpl.initScenario((org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario) request,
              (io.grpc.stub.StreamObserver<org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final HybridSimulation serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_SIMULATED_TIME_INERVAL,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.LeftClosedRightOpenTimeInterval,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>(
                serviceImpl, METHODID_SIMULATED_TIME_INERVAL)))
        .addMethod(
          METHOD_TRANSFER_AGENT,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Agent,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Boolean>(
                serviceImpl, METHODID_TRANSFER_AGENT)))
        .addMethod(
          METHOD_RECEIVE_TRAJECTORIES,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Trajectories>(
                serviceImpl, METHODID_RECEIVE_TRAJECTORIES)))
        .addMethod(
          METHOD_RETRIEVE_AGENTS,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Agents>(
                serviceImpl, METHODID_RETRIEVE_AGENTS)))
        .addMethod(
          METHOD_SHUTDOWN,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>(
                serviceImpl, METHODID_SHUTDOWN)))
        .addMethod(
          METHOD_INIT_SCENARIO,
          asyncUnaryCall(
            new MethodHandlers<
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Scenario,
              org.matsim.contrib.hybridsim.proto.HybridSimProto.Empty>(
                serviceImpl, METHODID_INIT_SCENARIO)))
        .build();
  }
}
