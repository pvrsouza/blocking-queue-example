package br.com.pablovinciius.blockingqueueexample.service;

import br.com.pablovinciius.blockingqueueexample.model.dto.TransactionPayload;
import br.com.pablovinciius.blockingqueueexample.queue.RecordHolder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

@Service
@Slf4j
public class ExecutionServiceManager {


    @Value("${executor.thread-numbers}")
    private int numberOfThreads;
    private final CalculationProcessService service;

    private Map<Integer, BlockingQueue<RecordHolder<TransactionPayload>>> requestBalanceMap;

    @Autowired
    @Qualifier(value = "threadPoolExecutorFactoryBean")
    private ThreadPoolExecutorFactoryBean threadPoolFactory;

    private ExecutorService executor;


    public ExecutionServiceManager(CalculationProcessService service) {
        this.service = service;
    }

    private final Function<RecordHolder<TransactionPayload>, Integer> retrieveStripe =
            callback -> (callback.convertAccountToInteger() % this.numberOfThreads);


    public void queue(RecordHolder<TransactionPayload> record) {
        Integer threadId = retrieveStripe.apply(record);

        log.info("Thread request for account {} id: {}", record.getRecord().getAccountNo(), threadId);

        getQueue(threadId).add(record);

    }

    @PostConstruct
    public void init() {
        log.info("Initializing thread service with{ {} threads.", this.numberOfThreads);

        this.requestBalanceMap = buildBalancerMap();

        executor = Executors.newFixedThreadPool(this.numberOfThreads, threadPoolFactory);

        for (int i = 0; i < this.numberOfThreads; i++) {
            BlockingQueue<RecordHolder<TransactionPayload>> queue = getQueue(i);
            submitTask(queue);
        }
    }

    public void submitTask(BlockingQueue<RecordHolder<TransactionPayload>> queue) {
        executor.submit(() -> this.takeAndProcess(queue));
    }

    public BlockingQueue<RecordHolder<TransactionPayload>> getQueue(int i) {
        return requestBalanceMap.get(i);
    }

    private void takeAndProcess(BlockingQueue<RecordHolder<TransactionPayload>> queue) {
        while (true) {
            try {
                log.info("Waiting for record...");
                RecordHolder<TransactionPayload> record = queue.take();

                log.info("Processing record {}", record);
                service.process(record.getRecord());
                log.info("Record processed {}", record);

            } catch (InterruptedException e) {
                Thread.interrupted();
                log.error("Error processing record", e);
            } catch (Exception e) {
                log.error("Error processing record", e);

            }
        }
    }

    private Map<Integer, BlockingQueue<RecordHolder<TransactionPayload>>> buildBalancerMap() {
        Map<Integer, BlockingQueue<RecordHolder<TransactionPayload>>> map = new HashMap<>();
        for (int i = 0; i < this.numberOfThreads; i++) {
            map.put(i, new LinkedBlockingQueue<>());
        }
        return map;
    }

}
