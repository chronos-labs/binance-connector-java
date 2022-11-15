package examples.savings;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FlexibleProductPosition {
    private FlexibleProductPosition() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FlexibleProductPosition.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        String result = client.createSavings().flexibleProductPosition(parameters);
        logger.info(result);
    }
}
