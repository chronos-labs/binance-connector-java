package examples.margin;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class GetBnbBurn {
    private GetBnbBurn() {
    }

    private static final Logger logger = LoggerFactory.getLogger(GetBnbBurn.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        String result = client.createMargin().getBnbBurn(parameters);
        logger.info(result);
    }
}
