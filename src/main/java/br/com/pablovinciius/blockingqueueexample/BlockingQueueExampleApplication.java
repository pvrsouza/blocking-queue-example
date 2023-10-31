package br.com.pablovinciius.blockingqueueexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlockingQueueExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockingQueueExampleApplication.class, args);
	}

}
