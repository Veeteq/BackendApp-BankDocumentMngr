package com.veeteq.finance.bankdocument.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.veeteq.finance.bankdocument.model.BankStatement;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {

    @Query(value = "SELECT bs FROM BankStatement bs",
           countQuery = "SELECT count(bs) FROM BankStatement bs")
    Page<BankStatement> findAllWithAccount(PageRequest pageRequest);

    @Query(value = "SELECT bs FROM BankStatement bs "
            + "               LEFT JOIN FETCH bs.details",
            countQuery = "SELECT count(bs) FROM BankStatement bs")
     Page<BankStatement> findAllWithDetails(PageRequest pageRequest);

    @Query(value = "SELECT bs FROM BankStatement bs "
            + "       LEFT JOIN FETCH bs.details"
            + "      WHERE bs.id = :id")
    Optional<BankStatement> findByIdWithDetails(Long id);

    
}
