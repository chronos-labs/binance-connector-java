package examples.websocket;

import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebsocketClientImpl;
import examples.PrivateConfig;
import org.json.JSONObject;

public final class UserStream {
    private UserStream() {
    }

    public static void main(String[] args) {
        WebsocketClientImpl wsClient = new WebsocketClientImpl(DefaultUrls.WS_URL);
//        SpotClientImpl spotClient = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET, DefaultUrls.WS_URL);
        SpotClientImpl spotClient = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        JSONObject obj = new JSONObject(spotClient.createUserData().createListenKey());
        String listenKey = obj.getString("listenKey");
        System.out.println("listenKey:" + listenKey);
        wsClient.listenUserStream(listenKey, ((event) -> {
            System.out.println(event);
            wsClient.closeAllConnections();
        }));
    }
}
