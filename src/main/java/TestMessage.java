import com.cinchcast.telephony.commons.connectors.mq.BaseMessage;
import com.google.gson.Gson;

import java.nio.charset.Charset;

/**
 * Created by danny on 2/10/15.
 */
public class TestMessage extends BaseMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte[] toBytes() {
        Gson gson = getGson();
        String json = gson.toJson(this);

        return json.getBytes(Charset.forName("UTF-8"));
    }
}
