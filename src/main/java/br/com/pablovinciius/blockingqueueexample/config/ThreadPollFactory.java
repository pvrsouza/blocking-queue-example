package br.com.pablovinciius.blockingqueueexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPollFactory {

    @Bean
    public ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean() {
        ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean = new ThreadPoolExecutorFactoryBean();
        threadPoolExecutorFactoryBean.setThreadNamePrefix("account-service-");
//
//        threadPoolExecutorFactoryBean.setCorePoolSize(10);
//        threadPoolExecutorFactoryBean.setMaxPoolSize(10);
//        threadPoolExecutorFactoryBean.setQueueCapacity(10);
        return threadPoolExecutorFactoryBean;
    }


//    @Bean
//    ExecutorService executorService() {
//        return Executors.newFixedThreadPool(NUMBER_OF_THREADS, this.threadPoolExecutorFactoryBean());
//    }
}
