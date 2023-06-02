package com.veeteq.finance.bankdocument.repository;

import com.veeteq.finance.bankdocument.model.BankStatementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankStatementDetailRepository extends JpaRepository<BankStatementDetail, Long> {

  @Modifying
  @Query("UPDATE BankStatementDetail bsd " +
          "  SET bsd.counterpartyId = :counterpartyId " +
          "WHERE bsd.id = :id")
  int setCounterpartyId(@Param("counterpartyId") Long counterpartyId, @Param("id") Long id);
}
