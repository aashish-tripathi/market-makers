package com.market.makers.service;

import com.ashish.marketdata.avro.Order;
import com.market.makers.senders.OrderSender;
import com.market.makers.util.Throughput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class OrderService {
    private boolean kafka;
    private String topic;
    private String []symbols;
    private String serverUrl;
    private ExecutorService service;
    private List<OrderSender> workerThreads;
    private Throughput throughputWorker;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public OrderService(String serverUrl, final String topic, boolean kafka) {
        this.serverUrl=serverUrl;
        this.topic = topic;
        this.kafka = kafka;
        this.service = Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return  new Thread(r, "Order Sending Thread");
            }
        });
    }

    public void start(final String[] symbols, final String exchange, final String brokerName, final String brokerId, final String clientId, final String clientName, int workers, BlockingQueue<Order> inputQueue) throws JMSException {
        workerThreads = new ArrayList<>();
        for (int i = 0; i < workers; i++) {
            OrderSender senderEMS = new OrderSender(serverUrl, topic, symbols, exchange,
                    brokerName, brokerId, clientId, clientName,
                    kafka,throughputWorker, inputQueue);
            workerThreads.add(senderEMS);
        }
        workerThreads.forEach(t -> service.submit(t));
    }

    public void shutDown(){
        if(workerThreads !=null) {
            workerThreads.forEach(t -> t.setRunning(false));
        }
        service.shutdown();
        LOGGER.info("All threads has been shutdown!");
    }
}
