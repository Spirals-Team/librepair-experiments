/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.protocol.amqp.proton.handler;

import javax.security.auth.Subject;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.apache.activemq.artemis.protocol.amqp.broker.ProtonProtocolManager;
import org.apache.activemq.artemis.protocol.amqp.proton.ProtonInitializable;
import org.apache.activemq.artemis.protocol.amqp.sasl.ClientSASL;
import org.apache.activemq.artemis.protocol.amqp.sasl.SASLResult;
import org.apache.activemq.artemis.protocol.amqp.sasl.ServerSASL;
import org.apache.activemq.artemis.spi.core.remoting.ReadyListener;
import org.apache.activemq.artemis.utils.actors.Actor;
import org.apache.qpid.proton.Proton;
import org.apache.qpid.proton.amqp.Symbol;
import org.apache.qpid.proton.amqp.transport.AmqpError;
import org.apache.qpid.proton.amqp.transport.ErrorCondition;
import org.apache.qpid.proton.engine.Collector;
import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.EndpointState;
import org.apache.qpid.proton.engine.Event;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Sasl;
import org.apache.qpid.proton.engine.SaslListener;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Transport;
import org.apache.qpid.proton.engine.impl.TransportInternal;
import org.jboss.logging.Logger;

public class ProtonHandler extends ProtonInitializable implements SaslListener {

   private static final Logger log = Logger.getLogger(ProtonHandler.class);

   private static final byte SASL = 0x03;

   private static final byte BARE = 0x00;

   private final Transport transport = Proton.transport();

   private final Connection connection = Proton.connection();

   private final Collector collector = Proton.collector();

   private List<EventHandler> handlers = new ArrayList<>();

   private ServerSASL chosenMechanism;
   private ClientSASL clientSASLMechanism;

   private final ReentrantLock lock = new ReentrantLock();

   private final long creationTime;

   private final boolean isServer;

   private SASLResult saslResult;

   protected volatile boolean dataReceived;

   protected boolean receivedFirstPacket = false;

   private final Executor flushExecutor;

   protected final ReadyListener readyListener;

   boolean inDispatch = false;

   private final Actor<ByteBuf> bufferActor;

   private final Actor<AbstractEvent> eventProcessor;

   final ProtonProtocolManager protonProtocolManager;

   private void executeFlush() {
      flushExecutor.execute(this::flush);
   }

   public ProtonHandler(ProtonProtocolManager protonProtocolManager, Executor flushExecutor, boolean isServer) {
      this.protonProtocolManager = protonProtocolManager;
      this.flushExecutor = flushExecutor;
      this.readyListener = () -> executeFlush();

      this.eventProcessor = new Actor<>(flushExecutor, this::processEvent, this::flush);
      this.bufferActor = new Actor<>(flushExecutor, this::actBuffer);
      this.creationTime = System.currentTimeMillis();
      this.isServer = isServer;

      try {
         ((TransportInternal) transport).setUseReadOnlyOutputBuffer(false);
      } catch (NoSuchMethodError nsme) {
         // using a version at runtime where the optimization isn't available, ignore
         log.trace("Proton output buffer optimisation unavailable");
      }

      transport.bind(connection);
      connection.collect(collector);
   }

   public long tick(boolean firstTick) {
      lock.lock();
      try {
         if (!firstTick) {
            try {
               if (connection.getLocalState() != EndpointState.CLOSED) {
                  long rescheduleAt = transport.tick(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()));
                  if (transport.isClosed()) {
                     throw new IllegalStateException("Channel was inactive for to long");
                  }
                  return rescheduleAt;
               }
            } catch (Exception e) {
               log.warn(e.getMessage(), e);
               transport.close();
               connection.setCondition(new ErrorCondition());
            }
            return 0;
         }
         return transport.tick(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()));
      } finally {
         flushBytes();
         lock.unlock();
      }
   }

   public void execute(Runnable runnable) {
      flushExecutor.execute(runnable);
   }

   public int capacity() {
      lock.lock();
      try {
         return transport.capacity();
      } finally {
         lock.unlock();
      }
   }

   public void lock() {
      lock.lock();
   }

   public void unlock() {
      lock.unlock();
   }

   public boolean tryLock(long time, TimeUnit timeUnit) {
      try {
         return lock.tryLock(time, timeUnit);
      } catch (InterruptedException e) {

         Thread.currentThread().interrupt();
         return false;
      }
   }

   public Transport getTransport() {
      return transport;
   }

   public Connection getConnection() {
      return connection;
   }

   public ProtonHandler addEventHandler(EventHandler handler) {
      handlers.add(handler);
      return this;
   }

   public void createServerSASL(String[] mechanisms) {
      Sasl sasl = transport.sasl();
      sasl.server();
      sasl.setMechanisms(mechanisms);
      sasl.setListener(this);
   }

   public void flushBytes() {

      lock.lock();
      try {
         for (EventHandler handler : handlers) {
            if (!handler.flowControl(readyListener)) {
               return;
            }
         }

         while (true) {
            int pending = transport.pending();

            if (pending <= 0) {
               break;
            }

            // We allocated a Pooled Direct Buffer, that will be sent down the stream
            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.directBuffer(pending);
            ByteBuffer head = transport.head();
            buffer.writeBytes(head);

            for (EventHandler handler : handlers) {
               handler.pushBytes(buffer);
            }

            transport.pop(pending);
         }
      } finally {
         lock.unlock();
      }
   }

   public SASLResult getSASLResult() {
      return saslResult;
   }

   public void inputBuffer(ByteBuf buffer) {
      dataReceived = true;

      protonProtocolManager.pressureIn(buffer.writerIndex());

      bufferActor.act(buffer.retain());
      //actBuffer(buffer.retain());
   }

   private void actBuffer(ByteBuf buffer) {
      int credits = buffer.writerIndex();
      lock.lock();
      try {
         while (buffer.readableBytes() > 0) {
            int capacity = transport.capacity();

            if (!receivedFirstPacket) {
               try {
                  byte auth = buffer.getByte(4);
                  if (auth == SASL || auth == BARE) {
                     if (isServer) {
                        dispatchAuth(auth == SASL);
                     } else if (auth == BARE && clientSASLMechanism == null) {
                        dispatchAuthSuccess();
                     }
                     /*
                     * there is a chance that if SASL Handshake has been carried out that the capacity may change.
                     * */
                     capacity = transport.capacity();
                  }
               } catch (Throwable e) {
                  log.warn(e.getMessage(), e);
               }

               receivedFirstPacket = true;
            }

            if (capacity > 0) {
               ByteBuffer tail = transport.tail();
               int min = Math.min(capacity, buffer.readableBytes());
               tail.limit(min);
               buffer.readBytes(tail);

               flush();
            } else {
               if (capacity == 0) {
                  log.debugf("abandoning: readableBytes=%d", buffer.readableBytes());
               } else {
                  log.debugf("transport closed, discarding: readableBytes=%d, capacity=%d", buffer.readableBytes(), transport.capacity());
               }
               break;
            }
         }
      } finally {
         lock.unlock();
         buffer.release();
         protonProtocolManager.pressureOut(credits);
      }
   }

   public boolean checkDataReceived() {
      boolean res = dataReceived;

      dataReceived = false;

      return res;
   }

   public long getCreationTime() {
      return creationTime;
   }

   public void flush() {
      lock.lock();
      try {
         transport.process();
         dispatch();
      } finally {
         lock.unlock();
      }
   }

   public void close(ErrorCondition errorCondition) {
      lock.lock();
      try {
         if (errorCondition != null) {
            connection.setCondition(errorCondition);
         }
         connection.close();
         flush();
      } finally {
         lock.unlock();
      }
   }

   // server side SASL Listener
   @Override
   public void onSaslInit(Sasl sasl, Transport transport) {
      log.debug("onSaslInit: " + sasl);
      dispatchRemoteMechanismChosen(sasl.getRemoteMechanisms()[0]);

      if (chosenMechanism != null) {

         processPending(sasl);

      } else {
         // no auth available, system error
         saslComplete(sasl, Sasl.SaslOutcome.PN_SASL_SYS);
      }
   }

   private void processPending(Sasl sasl) {
      byte[] dataSASL = new byte[sasl.pending()];

      int received = sasl.recv(dataSASL, 0, dataSASL.length);
      if (log.isTraceEnabled()) {
         log.trace("Working on sasl, length:" + received);
      }

      byte[] response = chosenMechanism.processSASL(received != -1 ? dataSASL : null);
      if (response != null) {
         sasl.send(response, 0, response.length);
      }

      saslResult = chosenMechanism.result();
      if (saslResult != null) {
         if (saslResult.isSuccess()) {
            saslComplete(sasl, Sasl.SaslOutcome.PN_SASL_OK);
         } else {
            saslComplete(sasl, Sasl.SaslOutcome.PN_SASL_AUTH);
         }
      }
   }

   @Override
   public void onSaslResponse(Sasl sasl, Transport transport) {
      log.debug("onSaslResponse: " + sasl);
      processPending(sasl);
   }

   // client SASL Listener
   @Override
   public void onSaslMechanisms(Sasl sasl, Transport transport) {

      dispatchMechanismsOffered(sasl.getRemoteMechanisms());

      if (clientSASLMechanism == null) {
         log.infof("Outbound connection failed - unknown mechanism, offered mechanisms: %s", Arrays.asList(sasl.getRemoteMechanisms()));

         dispatchAuthFailed();
      } else {
         sasl.setMechanisms(clientSASLMechanism.getName());
         byte[] initialResponse = clientSASLMechanism.getInitialResponse();
         if (initialResponse != null) {
            sasl.send(initialResponse, 0, initialResponse.length);
         }
      }
   }

   @Override
   public void onSaslChallenge(Sasl sasl, Transport transport) {
      int challengeSize = sasl.pending();
      byte[] challenge = new byte[challengeSize];
      sasl.recv(challenge, 0, challengeSize);
      byte[] response = clientSASLMechanism.getResponse(challenge);
      sasl.send(response, 0, response.length);
   }

   @Override
   public void onSaslOutcome(Sasl sasl, Transport transport) {
      log.debug("onSaslOutcome: " + sasl);
      switch (sasl.getState()) {
         case PN_SASL_FAIL:
            log.info("Outbound connection failed, authentication failure");

            dispatchAuthFailed();
            break;
         case PN_SASL_PASS:
            log.debug("Outbound connection succeeded");
            if (sasl.pending() != 0) {
               byte[] additionalData = new byte[sasl.pending()];
               sasl.recv(additionalData, 0, additionalData.length);
               clientSASLMechanism.getResponse(additionalData);
            }
            saslResult = new SASLResult() {
               @Override
               public String getUser() {
                  return null;
               }

               @Override
               public Subject getSubject() {
                  return null;
               }

               @Override
               public boolean isSuccess() {
                  return true;
               }
            };

            dispatchAuthSuccess();
            break;

         default:
            break;
      }
   }

   private void saslComplete(Sasl sasl, Sasl.SaslOutcome saslOutcome) {
      log.debug("saslComplete: " + sasl);
      sasl.done(saslOutcome);
      if (chosenMechanism != null) {
         chosenMechanism.done();
         chosenMechanism = null;
      }
   }

   private void dispatchAuthFailed() {
      for (EventHandler h : handlers) {
         h.onAuthFailed(this, getConnection());
      }
   }

   private void dispatchAuthSuccess() {
      for (EventHandler h : handlers) {
         h.onAuthSuccess(this, getConnection());
      }
   }

   private void dispatchMechanismsOffered(final String[] mechs) {
      for (EventHandler h : handlers) {
         h.onSaslMechanismsOffered(this, mechs);
      }
   }

   private void dispatchAuth(boolean sasl) {
      for (EventHandler h : handlers) {
         h.onAuthInit(this, getConnection(), sasl);
      }
   }

   private void dispatchRemoteMechanismChosen(final String mech) {
      for (EventHandler h : handlers) {
         h.onSaslRemoteMechanismChosen(this, mech);
      }
   }

   private void dispatch() {
      Event ev;

      lock.lock();
      try {
         if (inDispatch) {
            // Avoid recursion from events
            return;
         }
         try {
            inDispatch = true;
            while ((ev = collector.peek()) != null) {
               dispatchEvent(ev);
               collector.pop();
            }

         } finally {
            inDispatch = false;
         }
      } finally {
         flushBytes();
         lock.unlock();
      }

   }

   private void dispatchEvent(Event event) {
      switch (event.getType()) {
         case CONNECTION_INIT:
         case CONNECTION_LOCAL_OPEN:
         case CONNECTION_REMOTE_OPEN:
         case CONNECTION_LOCAL_CLOSE:
         case CONNECTION_REMOTE_CLOSE:
         case CONNECTION_FINAL:
            eventProcessor.act(new ConnectionEvent(event.getType(), event.getConnection()));
            break;
         case SESSION_INIT:
         case SESSION_LOCAL_OPEN:
         case SESSION_REMOTE_OPEN:
         case SESSION_LOCAL_CLOSE:
         case SESSION_REMOTE_CLOSE:
         case SESSION_FINAL:
            eventProcessor.act(new SessionEvent(event.getType(), event.getSession()));
            break;
         case LINK_INIT:
         case LINK_LOCAL_OPEN:
         case LINK_REMOTE_OPEN:
         case LINK_LOCAL_CLOSE:
         case LINK_REMOTE_CLOSE:
         case LINK_FLOW:
         case LINK_FINAL:
         case LINK_LOCAL_DETACH:
         case LINK_REMOTE_DETACH:
            eventProcessor.act(new LinkEvent(event.getType(), event.getLink()));
            break;
         case TRANSPORT:
            eventProcessor.act(new TransportEvent(event.getType(), event.getTransport()));
            break;
         case DELIVERY:
            eventProcessor.act(new DeliveryEvent(event.getType(), event.getDelivery()));
            break;
         default:
            break;
      }
   }

   private void processEvent(AbstractEvent event) {

      for (EventHandler h : handlers) {
         if (log.isTraceEnabled()) {
            log.trace("Handling " + event + " towards " + h);
         }
         try {
            event.dispatch(h);
         } catch (Exception e) {
            log.warn(e.getMessage(), e);
            ErrorCondition error = new ErrorCondition();
            error.setCondition(AmqpError.INTERNAL_ERROR);
            error.setDescription("Unrecoverable error: " + (e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage()));
            connection.setCondition(error);
            connection.close();
            break;
         }
      }
   }

   private abstract static class AbstractEvent<TYPE> {

      final Event.Type type;
      final TYPE object;

      AbstractEvent(Event.Type type, TYPE object) {
         this.type = type;
         this.object = object;
      }

      public abstract void dispatch(EventHandler handler) throws Exception;
   }

   private static class ConnectionEvent extends AbstractEvent<Connection> {

      ConnectionEvent(Event.Type type, Connection object) {
         super(type, object);
      }

      @Override
      public void dispatch(EventHandler handler) throws Exception {
         switch (type) {
            case CONNECTION_INIT:
               handler.onInit(object);
               break;
            case CONNECTION_LOCAL_OPEN:
               handler.onLocalOpen(object);
               break;
            case CONNECTION_REMOTE_OPEN:
               handler.onRemoteOpen(object);
               break;
            case CONNECTION_LOCAL_CLOSE:
               handler.onLocalClose(object);
               break;
            case CONNECTION_REMOTE_CLOSE:
               handler.onRemoteClose(object);
               break;
            case CONNECTION_FINAL:
               handler.onFinal(object);
               break;
            default:
               throw new IllegalStateException("unkown event " + type);
         }
      }

   }

   private static class SessionEvent extends AbstractEvent<Session> {

      SessionEvent(Event.Type type, Session object) {
         super(type, object);
      }

      @Override
      public void dispatch(EventHandler handler) throws Exception {
         switch (type) {
            case SESSION_INIT:
               handler.onInit(object);
            case SESSION_LOCAL_OPEN:
               handler.onLocalOpen(object);
               break;
            case SESSION_REMOTE_OPEN:
               handler.onRemoteOpen(object);
               break;
            case SESSION_LOCAL_CLOSE:
               handler.onLocalClose(object);
               break;
            case SESSION_REMOTE_CLOSE:
               handler.onRemoteClose(object);
               break;
            case SESSION_FINAL:
               handler.onFinal(object);
               break;
            default:
               throw new IllegalStateException("unkown event " + type);
         }
      }

   }

   private static class LinkEvent extends AbstractEvent<Link> {

      LinkEvent(Event.Type type, Link object) {
         super(type, object);
      }

      @Override
      public void dispatch(EventHandler handler) throws Exception {
         switch (type) {
            case LINK_INIT:
               handler.onInit(object);
               break;
            case LINK_LOCAL_OPEN:
               handler.onLocalOpen(object);
               break;
            case LINK_REMOTE_OPEN:
               handler.onRemoteOpen(object);
               break;
            case LINK_LOCAL_CLOSE:
               handler.onLocalClose(object);
               break;
            case LINK_REMOTE_CLOSE:
               handler.onRemoteClose(object);
               break;
            case LINK_FLOW:
               handler.onFlow(object);
               break;
            case LINK_FINAL:
               handler.onFinal(object);
               break;
            case LINK_LOCAL_DETACH:
               handler.onLocalDetach(object);
               break;
            case LINK_REMOTE_DETACH:
               handler.onRemoteDetach(object);
               break;

            default:
               throw new IllegalStateException("unkown event " + type);
         }
      }

   }

   private static class TransportEvent extends AbstractEvent<Transport> {

      TransportEvent(Event.Type type, Transport object) {
         super(type, object);
      }

      @Override
      public void dispatch(EventHandler handler) throws Exception {
         switch (type) {
            case TRANSPORT:
               handler.onTransport(object);
               break;

            default:
               throw new IllegalStateException("unkown event " + type);
         }
      }

   }

   private static class DeliveryEvent extends AbstractEvent<Delivery> {

      DeliveryEvent(Event.Type type, Delivery object) {
         super(type, object);
      }

      @Override
      public void dispatch(EventHandler handler) throws Exception {

         switch (type) {

            case DELIVERY:
               handler.onDelivery(object);
               break;

            default:
               throw new IllegalStateException("unkown event " + type);

         }

      }
   }

   public void open(String containerId, Map<Symbol, Object> connectionProperties) {
      this.transport.open();
      this.connection.setContainer(containerId);
      this.connection.setProperties(connectionProperties);
      this.connection.open();
      flush();
   }

   public void setChosenMechanism(ServerSASL chosenMechanism) {
      this.chosenMechanism = chosenMechanism;
   }

   public void setClientMechanism(final ClientSASL saslClientMech) {
      this.clientSASLMechanism = saslClientMech;
   }

   public void createClientSASL() {
      Sasl sasl = transport.sasl();
      sasl.client();
      sasl.setListener(this);
   }
}
