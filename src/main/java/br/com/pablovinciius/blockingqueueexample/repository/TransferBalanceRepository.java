package br.com.pablovinciius.blockingqueueexample.repository;

import br.com.pablovinciius.blockingqueueexample.model.entity.TransferBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TransferBalanceRepository extends JpaRepository<TransferBalance, Long> {

    @Query(nativeQuery = true, value = "SELECT current_balance FROM transfer_balance WHERE account_no = ?1")
    Optional<BigDecimal> getCurrentBalanceByAccountNo(String accountNo);

    @Query(nativeQuery = true, value = "SELECT * FROM transfer_balance WHERE account_no = ?1")
    Optional<TransferBalance> getByAccountNo(String accountNo);
}
