package com.market.makers;

import com.ashish.marketdata.avro.Order;
import com.market.makers.account.OrderStatus;
import com.market.makers.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
        String runningMode = properties.getProperty("exsim.running.mode");

        OrderService orderService = new OrderService(serverUrl, orderTopic, kafkaAsCarrier);
        if (runningMode != null && runningMode.equalsIgnoreCase("auto")) {
            runAutomationMode(stocks, exchange, brokerName, brokerId, clientDetails, workers, orderService);
        } else {
            runManualMode(stocks, exchange, brokerName, brokerId, clientDetails, workers, orderService);
        }
    }

    private static void runManualMode(String[] stocks, String exchange, String brokerName, String brokerId, String[] clientDetails, int workers, OrderService orderService) throws JMSException, InterruptedException {
        BlockingQueue<Order> orderQueueForManual = new ArrayBlockingQueue<Order>(1024);
        orderService.startSimulatorInManualMode(stocks, exchange, brokerName, brokerId, clientDetails[0], clientDetails[1], workers, true, orderQueueForManual);
        LOGGER.info("Order Simulator has been started in manual mode {}", Calendar.getInstance().getTime());
        String data = null;
        Scanner scanner = new Scanner(System.in);
        boolean stopManual = true;
        LOGGER.warn("Enter 2 times to stop...");
        while (stopManual) {
            Order order = new Order();
            LOGGER.info("Enter symbol ");
            data = scanner.nextLine();
            if (!data.isEmpty()) {
                order.setSymbol(data);
            }
            if (data.isEmpty()) {
                stopManual = false;
                break;
            }
            LOGGER.info("Enter qunantity ");
            data = scanner.nextLine();
            if (!data.isEmpty() && Long.parseLong(data) > 0) {
                order.setQuantity(Long.parseLong(data));
            }
            if (data.isEmpty()) {
                stopManual = false;
                break;
            }
            LOGGER.info("Enter price ");
            data = scanner.nextLine();
            if (!data.isEmpty() && Double.parseDouble(data) > 0) {
                order.setLimitPrice(Double.parseDouble(data));
            }
            if (data.isEmpty()) {
                stopManual = false;
                break;
            }
            LOGGER.info("Enter side (Buy 1, Sell 2 ) ");
            data = scanner.nextLine();
            if (!data.isEmpty() && Integer.parseInt(data) > 0) {
                order.setSide(Integer.parseInt(data));
            }
            order.setExchange("NSE");
            order.setBrokerId("Zero001");
            order.setBrokerName("Zerodha");
            order.setClientId("MARKET-MAKERS-HK");
            order.setClientName("MARKET-MAKERS-HK");
            order.setOrderStatus(OrderStatus.OPEN.name());
            order.setFilledQuantity(0l);
            order.setRemainingQuantity(order.getQuantity());
            order.setOrdertime(Calendar.getInstance().getTimeInMillis());
            order.setOrderId(UUID.randomUUID().toString());
            orderQueueForManual.put(order);
        }
        orderService.shutDown();
    }

    private static void runAutomationMode(String[] stocks, String exchange, String brokerName, String brokerId, String[] clientDetails, int workers, OrderService orderService) throws JMSException {
        orderService.startSimulatorInAutomaticMode(stocks, exchange, brokerName, brokerId, clientDetails[0], clientDetails[1], workers, false, null);
        LOGGER.info("Order Simulator has been started in automatic mode {}", Calendar.getInstance().getTime());
        Scanner scanner = new Scanner(System.in);
        LOGGER.warn("Enter to stop automatic mode...");
        String run = scanner.nextLine();
        while (run.isEmpty()) {
            LOGGER.warn("Turning down application...");
            orderService.shutDown();
        }
    }

}
