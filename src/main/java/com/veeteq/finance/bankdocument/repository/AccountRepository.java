package com.veeteq.finance.bankdocument.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veeteq.finance.bankdocument.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
