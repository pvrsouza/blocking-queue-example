package br.com.pablovinciius.blockingqueueexample.service;

import br.com.pablovinciius.blockingqueueexample.model.dto.TransactionPayload;
import br.com.pablovinciius.blockingqueueexample.model.entity.TransferBalance;
import br.com.pablovinciius.blockingqueueexample.repository.TransferBalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class CalculationProcessServiceImpl implements CalculationProcessService {

    private final TransferBalanceRepository transferBalanceRepository;

    public CalculationProcessServiceImpl(TransferBalanceRepository transferBalanceRepository) {
        this.transferBalanceRepository = transferBalanceRepository;
    }

    public TransferBalance process(TransactionPayload payload) {
        Optional<BigDecimal> currentBalance = this.transferBalanceRepository.getCurrentBalanceByAccountNo(payload.getAccountNo());
        log.info("founded currentBalance: {} for accountNumber {}", currentBalance, payload.getAccountNo());

        BigDecimal newBalance = currentBalance
                .orElseGet(this::getDefaultCurrentBalance)
                .add(payload.getAmmout());

        return this.saveOrUpdateCurrentBalance(payload, newBalance);
    }

    @Override
    public Optional<BigDecimal> findBalanceByAccountNo(String accountNo) {
        return this.transferBalanceRepository.getCurrentBalanceByAccountNo(accountNo);
    }


    public BigDecimal getDefaultCurrentBalance(){
        return BigDecimal.ZERO;
    }

    public TransferBalance saveOrUpdateCurrentBalance(TransactionPayload payload, BigDecimal newBalance) {

        Optional<TransferBalance> byAccountNo = this.transferBalanceRepository.getByAccountNo(payload.getAccountNo());

        TransferBalance transferBalance = null;

        if (byAccountNo.isEmpty()){
            transferBalance = TransferBalance.builder()
                    .currentBalance(payload.getAmmout())
                    .accountNo(payload.getAccountNo())
                    .build();

            transferBalance = this.transferBalanceRepository.save(transferBalance);
            log.info("save: {}", transferBalance);
        }else{
            transferBalance = byAccountNo.get();

            transferBalance.setCurrentBalance(newBalance);

            transferBalance = this.transferBalanceRepository.save(transferBalance);
            log.info("save: {}", transferBalance);
        }

        return transferBalance;

    }




}
