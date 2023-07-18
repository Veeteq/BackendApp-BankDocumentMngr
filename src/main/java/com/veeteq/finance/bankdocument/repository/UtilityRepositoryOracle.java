package com.veeteq.finance.bankdocument.repository;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.Output;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile(value = "prod")
public class UtilityRepositoryOracle implements UtilityRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UtilityRepositoryOracle(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long getBankStatementId() {
        StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("p_get_bank_statement_id")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.OUT);
        procedureQuery.execute();
        Long bankStatementId = (Long) procedureQuery.getOutputParameterValue(1);
        return bankStatementId;
    }

    @Override
    public Long[] getBankStatementDetailId(int limit) {
        Long[] resultSet = new Long[limit];

        try (Session session = entityManager.unwrap(Session.class)) {
            ProcedureCall call = session.createStoredProcedureCall("p_get_details_id");
            call.registerParameter(1, Long.class, ParameterMode.IN).bindValue(Long.valueOf(limit));
            call.registerParameter(2, Integer.class, ParameterMode.REF_CURSOR);
            Output output = call.getOutputs().getCurrent();

            if (output.isResultSet()) {

                @SuppressWarnings("unchecked")
				List<Object> resultList = ((ResultSetOutput) output).getResultList();

                AtomicInteger idx = new AtomicInteger(0);
                Iterator<Object> iterator = resultList.iterator();
                while (iterator.hasNext()) {
                	resultSet[idx.getAndIncrement()] = ((BigDecimal) iterator.next()).longValue();
                }
            }
        }
        return resultSet;
    }
}
