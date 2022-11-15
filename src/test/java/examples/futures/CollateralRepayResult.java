package examples.futures;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CollateralRepayResult {
    private CollateralRepayResult() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CollateralRepayResult.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("quoteId", "test");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        String result = client.createFutures().collateralRepayResult(parameters);
        logger.info(result);
    }
}
