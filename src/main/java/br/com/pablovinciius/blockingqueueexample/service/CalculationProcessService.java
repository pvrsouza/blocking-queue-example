package br.com.pablovinciius.blockingqueueexample.service;

import br.com.pablovinciius.blockingqueueexample.model.dto.TransactionPayload;
import br.com.pablovinciius.blockingqueueexample.model.entity.TransferBalance;
import br.com.pablovinciius.blockingqueueexample.queue.RecordHolder;

import java.math.BigDecimal;
import java.util.Optional;

public interface CalculationProcessService {
    TransferBalance process(TransactionPayload payload);

    Optional<BigDecimal> findBalanceByAccountNo(String accountNo);
}
