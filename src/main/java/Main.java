import com.cinchcast.telephony.commons.connectors.mq.MqConnection;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitConfiguration;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitConnection;
import com.cinchcast.telephony.commons.connectors.mq.rabbit.RabbitSendOptions;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        RabbitConfiguration config = new RabbitConfiguration("cc-msgq-dev.sip.blogtalkradio.com", 5673, "guest", "guest", "mqp");
        MqConnection producer = new RabbitConnection(config);

        int x = 0;
        try {
            while (true) {
                x++;
                TestMessage message = new TestMessage("test " + x);

                if (producer.send(message, new RabbitSendOptions("test-key", "com.cinchcast.telephony.mq.exchange"))) {
                    System.out.println("message sent: " + message.getMessage());
                } else {
                    System.out.println("could not send message:" + message.getMessage());
                }

                Thread.sleep(500);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}