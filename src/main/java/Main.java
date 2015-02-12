import com.cinchcast.telephony.commons.connectors.mq.MqConnectionListener;
import com.cinchcast.telephony.commons.connectors.mq.RabbitConfiguration;
import com.cinchcast.telephony.commons.connectors.mq.RabbitMqProducer;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RabbitConfiguration config = new RabbitConfiguration("cc-msgq-dev.sip.blogtalkradio.com", 5673, "guest", "guest", "mqp");
        config.setConnectionListener(new MqConnectionListener() {
            @Override
            public void onConnect(Map<String, Object> stringObjectMap) {
                System.out.println("connected: " + stringObjectMap.get("information"));
            }
        });
        RabbitMqProducer producer = new RabbitMqProducer(config, "com.cinchcast.telephony.mq.exchange");

        TestMessage message = new TestMessage();

        int x = 1;

        try {
            while (true) {
                message.setMessage("danny" + x);

                if (producer.trySendMessage(message, "test-key")) {
                    System.out.println("message sent: " + message.getMessage());
                    x++;
                } else {
                    System.out.println("could not send message, will retry");
                }

                Thread.sleep(2000);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}