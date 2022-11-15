package examples.userdata;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CreateIsolatedMarginListenKey {
    private CreateIsolatedMarginListenKey() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CreateIsolatedMarginListenKey.class);
    public static void main(String[] args) {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");

        String result = client.createUserData().createIsolatedMarginListenKey(parameters);
        logger.info(result);
    }
}
