package com.bian.statement.service;

import com.bian.statement.client.CollectionResource;
import com.bian.statement.client.TransactionDTO;
import com.bian.statement.constants.TransactionType;
import com.bian.statement.entity.Transaction;
import com.bian.statement.mapper.TransactionMapper;
import com.bian.statement.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TransactionRepository repository;

    @Autowired
    TransactionMapper mapper;

    public CollectionResource<TransactionDTO> findTransactions(Map<String, Object> parameters) {

        Pageable pageable = PageRequest.of(0, 50);

        Specification<Transaction> specification = buildSpecification(parameters);
        List<TransactionDTO> transactionList = new LinkedList<>();

        Page<Transaction> page = repository.findAll(specification, pageable);
        int totalStatus = (int) page.getTotalElements();

        page.iterator().forEachRemaining(transaction -> {
            TransactionDTO transactionDTO = mapper.map(transaction, TransactionDTO.class);
            transactionList.add(transactionDTO);
        });

        logger.info("Fetched " +  totalStatus + " Transactions Successfully.");
        return new CollectionResource<>(transactionList, transactionList.size(), totalStatus);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction savedTransaction = repository.save(mapper.map(transactionDTO, Transaction.class));
        logger.info("Saved Transaction Successfully." + savedTransaction.getId());
        return mapper.map(savedTransaction, TransactionDTO.class);
    }

    public boolean deleteAllTransactions() {
        repository.deleteAll();
        logger.info("Deleted All Transactions Successfully.");
        return true;
    }

    private Specification<Transaction> buildSpecification(final Map<String, Object> constraints) {
        return new Specification<Transaction>() {
            public Predicate toPredicate(Root<Transaction> transactionRoot,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                List<Predicate> predicates = new LinkedList<>();

                if (constraints.get("accountNumber") != null) {
                    String accountNumber = (String) constraints.get("accountNumber");
                    predicates.add(builder.equal(transactionRoot.get("accountNumber"), accountNumber));
                }

                if (constraints.get("type") != null) {
                    TransactionType type = (TransactionType) constraints.get("type");
                    predicates.add(builder.equal(transactionRoot.get("type"), type));
                }

                if (constraints.get("startDate") != null && constraints.get("endDate") != null) {
                    predicates.add(builder.between(transactionRoot.<Date>get("transactionTs"), (Date)constraints.get("startDate"), (Date)constraints.get("endDate")));
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
