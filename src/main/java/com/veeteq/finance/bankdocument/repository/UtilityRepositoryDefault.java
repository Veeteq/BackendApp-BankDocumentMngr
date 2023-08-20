package com.veeteq.finance.bankdocument.repository;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile(value = {"default", "dev"})
public class UtilityRepositoryDefault implements UtilityRepository {
    private final int bound = 20000;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UtilityRepositoryDefault(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long getBankStatementId() {
        Random rnd = new Random();
        long nextLong = 0;
        long count = 0;

        TypedQuery<Long> query = entityManager.createQuery("SELECT Count(b) FROM BankStatement b WHERE b.id = :id", Long.class);
        do {
            nextLong = rnd.nextInt(bound);
            count = query.setParameter("id", nextLong).getSingleResult();
        } while(count > 0);

        return nextLong;
    }

    @Override
    public Long[] getBankStatementDetailId(int limit) {
        Long[] resultSet = new Long[limit];

        int i = 0;
        while (i < limit) {
            resultSet[i] = generateRandomIdx();
            i++;
        }
        return resultSet;
    }

    private Long generateRandomIdx() {
        Random rnd = new Random();
        long nextLong = 0;
        long count = 0;

        TypedQuery<Long> query = entityManager.createQuery("SELECT Count(bd) FROM BankStatementDetail bd WHERE bd.detailId = :detailId", Long.class);
        do {
            nextLong = rnd.nextInt(bound);
            count = query.setParameter("detailId", nextLong).getSingleResult();
        } while(count > 0);

        return nextLong;
    }
}
