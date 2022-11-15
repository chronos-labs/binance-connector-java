package examples.savings;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProjectList {
    private ProjectList() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ProjectList.class);
    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("type", "ACTIVITY");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.API_SECRET);
        String result = client.createSavings().projectList(parameters);
        logger.info(result);
    }
}
