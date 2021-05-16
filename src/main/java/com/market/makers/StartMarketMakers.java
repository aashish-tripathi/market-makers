package com.market.makers;

import com.market.makers.service.OrderService;
import com.market.makers.state.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

public class StartMarketMakers {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMarketMakers.class);

    public static void main(String[] args) throws IOException, JMSException, InterruptedException {
        String configPath = null;
        if (args.length == 0) {
            LOGGER.warn("Config file not provided, loading file from default directory");
            configPath = "/market-makers.properties";
        } else {
            configPath = args[0];
        }
        final boolean kafkaAsCarrier = true;

        Properties properties = new Properties();
        InputStream inputStream = StartMarketMakers.class.getResourceAsStream(configPath);
        properties.load(inputStream);
        String serverUrl = kafkaAsCarrier ? properties.getProperty("exsim.kafka.bootstrap.servers") : properties.getProperty("exsim.tibcoems.serverurl");
        String orderTopic = properties.getProperty("exsim.tibcoems.ordertopic");
        String[] clients = properties.getProperty("exsim.order.sim.clients").split(",");
        String[] stocks = properties.getProperty("exsim.order.sim.symbols").split(",");
        String exchange = properties.getProperty("exsim.order.sim.exchange");
        String brokerName = properties.getProperty("exsim.order.sim.broker");
        String brokerId = properties.getProperty("exsim.order.sim.brokerId");
        String[] clientDetails = clients[0].split("-");
        int workers = Integer.parseInt(properties.getProperty("exsim.order.sim.workers"));

        // load all securities
        Properties dbproperties = new Properties();
        InputStream dbInputStream = Cache.class.getResourceAsStream("/db.properties");
        dbproperties.load(dbInputStream);

        startMarketMakers(stocks, exchange, brokerName, brokerId, clientDetails, workers, new OrderService(serverUrl, orderTopic, kafkaAsCarrier));
    }

    private static void startMarketMakers(String[] stocks, String exchange, String brokerName, String brokerId, String[] clientDetails, int workers, OrderService orderService) throws JMSException {
        orderService.start(stocks, exchange, brokerName, brokerId, clientDetails[0], clientDetails[1], workers);
        LOGGER.info("Market Makers has been started to create liquidity in market {}", exchange);
        Scanner scanner = new Scanner(System.in);
        LOGGER.warn("Enter to stop ...");
        String run = scanner.nextLine();
        while (run.isEmpty()) {
            LOGGER.warn("Turning down application...");
            orderService.shutDown();
        }
    }

}
