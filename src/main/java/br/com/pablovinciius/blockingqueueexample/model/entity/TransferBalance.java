package br.com.pablovinciius.blockingqueueexample.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "transfer_balance")
@Table(name = "transfer_balance")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"id"})
@Data
@Builder
public class TransferBalance {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String accountNo;

    private BigDecimal currentBalance;

}

