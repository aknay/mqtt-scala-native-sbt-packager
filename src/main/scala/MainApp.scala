import java.util.Calendar

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.{IMqttDeliveryToken, MqttCallback, MqttClient, MqttMessage}

object MainApp {

  val SEND_ME = "SendMe"
  val RECEIVED_IT = "ReceivedIt"

  def main(args: Array[String]) {

    val brokerUrl = "tcp://localhost:1883"

    //Set up persistence for messages
    val persistence = new MemoryPersistence

    //Initializing Mqtt Client specifying brokerUrl, clientID and MqttClientPersistance
    val client = new MqttClient(brokerUrl, MqttClient.generateClientId, persistence)

    //Connect to MqttBroker
    client.connect

    //Subscribe to Mqtt topic
    client.subscribe(SEND_ME)

    //Callback automatically triggers as and when new message arrives on specified topic
    val callback = new MqttCallback {

      override def messageArrived(topic: String, message: MqttMessage): Unit = {
        println("Receiving Data, Topic : %s, Message : %s".format(topic, message))

        topic match {
          case SEND_ME =>
            sendTopic(client, RECEIVED_IT, "I received this: " + message.toString + " at " + Calendar.getInstance().getTime )

          case _ =>
        }
      }

      override def connectionLost(cause: Throwable): Unit = {
        println(cause)
      }

      override def deliveryComplete(token: IMqttDeliveryToken): Unit = {

      }
    }

    //Set up callback for MqttClient
    client.setCallback(callback)

  }

  def sendTopic(client: MqttClient, topic: String, msg: String): Unit = {
    val msgTopic = client.getTopic(topic)
    val messageToPublish = new MqttMessage(msg.getBytes("utf-8"))
    msgTopic.publish(messageToPublish)
    println("Publishing Data, Topic : %s, Message : %s".format(msgTopic.getName, messageToPublish))
  }

}