package examples.websocket;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebsocketClientImpl;
import com.binance.connector.client.utils.WebSocketCallback;
import examples.PrivateConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public final class BookTicker {
    private BookTicker() {
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        WebsocketClientImpl client = new WebsocketClientImpl();
        List<String> symbols = new ArrayList<>(Arrays.asList("APTUSDT", "APTBUSD"));

        BookTicker bookTicker = new BookTicker();
        bookTicker.countdown(symbols);

        for (String symbol : symbols) {
            bookTicker.captureBookTickerData(client, "bookticker", symbol);
            bookTicker.captureTradeData(client, "trade", symbol);
        }
        bookTicker.snapshotOrderBook(symbols); // capture after the streams have started
    }

    public void countdown(List<String> symbols) throws InterruptedException {
        long time = System.currentTimeMillis();

        // long sleep
        while (time < 1666141199000L) { // 10:59:59 EST = Epoch 1664377199000L
            System.out.println(time);
            Thread.sleep(500);
            time = System.currentTimeMillis();
        }

        System.out.println("Entering countdown");

        // short sleep
        while (time < 1666141200000L || !isListed(symbols)) { // 11:00:00 EST = Epoch 1664377200000L
            Thread.sleep(1);
            time = System.currentTimeMillis();
        }

        System.out.println("Starting data capture");
    }

    public boolean isListed(List<String> symbols) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbols", symbols);

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        try {
            client.createMarket().tickerSymbol(parameters);
        } catch (BinanceClientException e) {
            if (e.getHttpStatusCode() == 400)
                System.out.println(e.getErrMsg());
            return false;
        }
        parameters.clear();

        return true;
    }

    public void snapshotOrderBook(List<String> symbols) throws IOException {
        String filePrefix = "orderBookSnapshot";
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);


        for (String symbol : symbols) {
            String filename = String.format("%s-%s.txt", symbol, filePrefix);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

            System.out.println(String.format("Snapshotting order book for %s", symbol));
            parameters.put("symbol", symbol);
            String result = client.createMarket().depth(parameters);
            writer.append(result);
            writer.close();
        }
    }

    public void captureBookTickerData(WebsocketClientImpl client,
                                      String filePrefix,
                                      String symbol) throws IOException {
        MsgProcessor msgProcessor = new MsgProcessor(symbol, filePrefix);
        client.bookTicker(symbol, msgProcessor);
    }

    public void captureTradeData(WebsocketClientImpl client,
                                 String filePrefix,
                                 String symbol) throws IOException {
        MsgProcessor msgProcessor = new MsgProcessor(symbol, filePrefix);
        client.bookTicker(symbol, msgProcessor);
    }

    class MsgProcessor implements WebSocketCallback {
        int fileNumSuffix = 0;
        String symbol;
        String filePrefix;
        String filename;
        Path path;
        BufferedWriter writer;

        public MsgProcessor(String symbol, String filePrefix) throws IOException {
            this.symbol = symbol;
            this.filePrefix = filePrefix;
            this.filename = String.format("%s-%s-output-%d.txt", symbol, filePrefix, fileNumSuffix);
            this.path = Paths.get(filename);
            this.writer = new BufferedWriter(new FileWriter(filename, true));
        }

        @Override
        public void onReceive(String data) {
            try {
                writer.append(data + ", localTimestamp=" + System.currentTimeMillis() + "\n");

                long bytes = Files.size(path);
                if (bytes > 20000000) { // 20mb
                    writer.close();
                    fileNumSuffix++;
                    filename = String.format("%s-%s-output-%d.txt", symbol, filePrefix, fileNumSuffix);
                    writer = new BufferedWriter(new FileWriter(filename, true));
                    path = Paths.get(filename);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            client.closeAllConnections();
        }
    }
}
