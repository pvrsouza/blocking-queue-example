package br.com.pablovinciius.blockingqueueexample.controller;

import br.com.pablovinciius.blockingqueueexample.model.dto.TransactionPayload;
import br.com.pablovinciius.blockingqueueexample.queue.RecordHolder;
import br.com.pablovinciius.blockingqueueexample.service.CalculationProcessService;
import br.com.pablovinciius.blockingqueueexample.service.ExecutionServiceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {


    private final CalculationProcessService calculationProcessService;

    private final ExecutionServiceManager executionServiceManager;


    @GetMapping("/test/balance/{accountNo}")
    public BigDecimal test(@PathVariable String accountNo) {
        return this.calculationProcessService.findBalanceByAccountNo(accountNo).orElseThrow(() -> new RuntimeException("Account not found"));
    }


    @GetMapping("/test/{qtd_transactions}")
    public void test(@PathVariable int qtd_transactions) {

        final String accountNo1 = "BPXZ000001";
        final String accountNo2 = "BPSQ000001";
        final String accountNo3 = "BPPS000001";
        final String accountNo4 = "BPHK000001";
        final String accountNo5 = "BPTX000001";
        final String accountNo6 = "BPFX000001";
        final String accountNo7 = "BPJR000001";
        final String accountNo8 = "BPSF000001";
        final String accountNo9 = "BPFA000001";
        final String accountNo10 = "BPGF000001";
        final String accountNo11 = "BPGJ000001";

//        int quantity = 500;

        createAccountRecordTransactions(accountNo1, qtd_transactions);

        createAccountRecordTransactions(accountNo2, qtd_transactions);
        createAccountRecordTransactions(accountNo3, qtd_transactions);
//        createAccountRecordTransactions(accountNo4, qtd_transactions);
//        createAccountRecordTransactions(accountNo5, qtd_transactions);
//        createAccountRecordTransactions(accountNo6, qtd_transactions);
//        createAccountRecordTransactions(accountNo7, qtd_transactions);
//        createAccountRecordTransactions(accountNo8, qtd_transactions);
//        createAccountRecordTransactions(accountNo9, qtd_transactions);
//        createAccountRecordTransactions(accountNo10, qtd_transactions);
//        createAccountRecordTransactions(accountNo11, qtd_transactions);

    }

    private void createAccountRecordTransactions(String accountNo, int quantity) {

        for (int i = 0; i < quantity; i++) {

            TransactionPayload transaction = TransactionPayload
                    .builder()
                    .accountNo(accountNo)
                    .ammout(BigDecimal.TEN)
                    .build();

            RecordHolder<TransactionPayload> recordHolder = RecordHolder.of("test", "1", transaction);

            this.executionServiceManager.queue(recordHolder);
        }

    }
}
