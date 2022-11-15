package examples.subaccount;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FuturesPositionRiskV2 {
    private FuturesPositionRiskV2() {
    }
    private static final int futuresType = 1;

    private static final Logger logger = LoggerFactory.getLogger(FuturesPositionRiskV2.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("email", "");
        parameters.put("futuresType", futuresType);

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        String result = client.createSubAccount().futuresPositionRiskV2(parameters);
        logger.info(result);
    }
}
