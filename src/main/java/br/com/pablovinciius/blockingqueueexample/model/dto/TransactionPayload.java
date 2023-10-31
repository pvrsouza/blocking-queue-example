package br.com.pablovinciius.blockingqueueexample.model.dto;

import br.com.pablovinciius.blockingqueueexample.queue.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionPayload implements Transaction {

    private String accountNo;

    private BigDecimal ammout;

    @Override
    public String getAccount() {
        return this.accountNo;
    }
}
