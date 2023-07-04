package com.veeteq.finance.bankdocument.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.veeteq.finance.bankdocument.model.BankStatementDetail;

public interface BankStatementDetailRepository extends JpaRepository<BankStatementDetail, Long> {

  @Modifying
  @Query("UPDATE BankStatementDetail bsd " +
          "  SET bsd.counterpartyId = :counterpartyId " +
          "WHERE bsd.id = :id")
  int setCounterpartyId(@Param("counterpartyId") Long counterpartyId, @Param("id") Long id);

  @Query("SELECT bsd" +
          " FROM BankStatementDetail bsd " +
          " JOIN FETCH bsd.bankStatement " +
          "WHERE bsd.operationDate = :operationDate")
  List<BankStatementDetail> findByOperationDate(@Param(value = "operationDate") LocalDate operationDate);
}
