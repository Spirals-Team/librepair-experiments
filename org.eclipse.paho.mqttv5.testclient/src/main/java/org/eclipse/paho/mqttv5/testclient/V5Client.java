package org.eclipse.paho.mqttv5.testclient;


import org.eclipse.paho.mqttv5.client.IMqttDeliveryToken;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttActionListener;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptionsBuilder;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.client.MqttToken;
import org.eclipse.paho.mqttv5.client.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

public class V5Client implements MqttCallback {

	private String broker = "tcp://localhost:1883";
	private String clientId = "test-client-id"; 
	private String topic = "MQTT Examples";
	private String content = "Message from MqttPublishSample";
	private String willContent = "I've Disconnected, sorry!";
	private int qos = 0;
	Object runningLock;
	boolean running = true;
	int x = 0;
	public V5Client() throws InterruptedException {

		try {
			MqttDefaultFilePersistence persistence = new MqttDefaultFilePersistence();
			MqttAsyncClient asyncClient = new MqttAsyncClient(broker, clientId, persistence);

			// Lets build our Connection Options:
			MqttConnectionOptionsBuilder conOptsBuilder = new MqttConnectionOptionsBuilder();
			MqttConnectionOptions conOpts = conOptsBuilder.serverURI(broker).cleanSession(true)
					.sessionExpiryInterval(120L).automaticReconnect(true)
					.topicAliasMaximum(10)
					.will(topic, new MqttMessage(willContent.getBytes(), qos, false, null)).build();
			asyncClient.setCallback(this);


			System.out.println("Connecting to broker: " + broker);

			IMqttToken connectToken = asyncClient.connect(conOpts, null, new MqttActionListener() {

				@Override
				public void onSuccess(IMqttToken asyncActionToken) {
					System.out.println("Connected, RC: " + asyncActionToken.getResponse());
					

					printConnectDetails((MqttToken) asyncActionToken);
					try {
						IMqttToken subToken = asyncClient.subscribe(topic, qos);
						subToken.waitForCompletion();
						printSubscriptionDetails((MqttToken) subToken);
						System.out.println("Sub Return Code: " );
						System.out.println(subToken.getReasonCodes()[0]);
						
						MqttMessage msg = new MqttMessage(content.getBytes());
						msg.setQos(qos);
						IMqttDeliveryToken pubDelToken = asyncClient.publish(topic, msg);
						
					} catch (MqttException e) {
						System.err.println("Exception Occured whilst Subscribing:");
						e.printStackTrace();
					}

				}

				@Override
				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					System.out.println("Failed to Connect: " + exception.getLocalizedMessage());

				}
			});
			
			connectToken.waitForCompletion();
			
			

			while (running) {

				// asyncClient.publish(topic, new MqttMessage(content.getBytes(), 2, false));
				Thread.sleep(1000);
				System.out.println("Sending Message: " + x);
				String message = content + " " + x;
				if(x == 5) {
					message = "FINISH";
					running = false;
				}
				IMqttDeliveryToken deliveryToken = asyncClient.publish(topic, new MqttMessage(message.getBytes(), qos, false, null));
				deliveryToken.waitForCompletion();
				//System.out.println("Delivery Reason Code: " + deliveryToken.getReasonCodes()[0]);
				x++;

			}
			
			// Unsubscribe from the topic
			IMqttToken unsubscribeToken = asyncClient.unsubscribe(topic);
			unsubscribeToken.waitForCompletion();
			System.out.println("Unsubscribed: " + unsubscribeToken.getResponse().getReasonCodes()[0]);
			
			
			asyncClient.disconnect(5000);
			System.out.println("Disconnected");
			asyncClient.close();
			System.exit(0);

		} catch (MqttException e) {
			System.err.println("Exception Occured whilst connecting the client: ");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws MqttException, InterruptedException {
		// Create and start this sample client
		V5Client client = new V5Client();

	}

	public static void printSubscriptionDetails(MqttToken token) {
		System.out.println("Subscription Response: [reasonString=" + token.getMessageProperties().getReasonString() + ", user"
				+ ", userDefinedProperties=" + token.getMessageProperties().getUserProperties());

	}

	public static void printConnectDetails(MqttToken token) {
		System.out.println("Connection Response: [ sessionPresent=" + token.getSessionPresent() + ", properties=" + token.getMessageProperties().toString() + "]");
	}

	

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String incomingMessage = new String(message.getPayload());
		System.out.println("Incoming Message: [" +incomingMessage + "], topic:[" + topic + "]");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Delivery Complete: " + token.getMessageId());

	}

	@Override
	public void disconnected(MqttDisconnectResponse disconnectResponse) {
		System.out.println("Disconnection Complete!");
		
	}

	@Override
	public void mqttErrorOccured(MqttException exception) {
		System.out.println("An exception occured in the MQTT Client: " + exception.getMessage());
		
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		System.out.println("Client successfully connected, reconnect: " + reconnect + ", URI: " + serverURI);
		
	}

	@Override
	public void authPacketArrived(int reasonCode, MqttProperties properties) {
		System.out.println("An auth packet was recieved");
	}
	

}
