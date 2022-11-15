package examples.margin;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EnableIsolatedAccount {
    private EnableIsolatedAccount() {
    }

    private static final Logger logger = LoggerFactory.getLogger(EnableIsolatedAccount.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);

        parameters.put("symbol", "BNBUSDT");

        String result = client.createMargin().enableIsolatedAccount(parameters);
        logger.info(result);
    }
}
