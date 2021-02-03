package com.bian.statement.service;

import com.bian.statement.client.BalanceDTO;
import com.bian.statement.client.CollectionResource;
import com.bian.statement.entity.Balance;
import com.bian.statement.entity.Transaction;
import com.bian.statement.mapper.BalanceMapper;
import com.bian.statement.repository.BalanceRepository;
import com.bian.statement.util.ServiceSortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class BalanceStatementService {
    @Autowired
    BalanceRepository repository;

    @Autowired
    BalanceMapper balanceMapper;

    private static final Map<String, String> AVAILABLE_SORT_FIELDS = createSortFieldsMap();

    private static Map<String, String> createSortFieldsMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "id");
        map.put("lastUpdateTs", "lastUpdateTs");
        return map;
    }

    public CollectionResource<BalanceDTO> findBalances(String accountNumber, Map<String, Sort.Direction> sorts) {

        Pageable pageable = PageRequest.of(0, 50, ServiceSortUtil.buildSortParams(sorts, AVAILABLE_SORT_FIELDS));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("accountNumber", accountNumber);

        Specification<Balance> specification = buildSpecification(parameters);
        Page<Transaction> page = repository.findAll(specification, pageable);
        int totalBalances = (int) page.getTotalElements();

        List<BalanceDTO> balanceDTOList = new LinkedList<>();
        page.iterator().forEachRemaining(balance -> {
            balanceDTOList.add(balanceMapper.map(balance, BalanceDTO.class));
        });

        return new CollectionResource<>(balanceDTOList, balanceDTOList.size(), totalBalances);
    }

    private Specification<Balance> buildSpecification(final Map<String, Object> constraints) {
        return new Specification<Balance>() {
            public Predicate toPredicate(Root<Balance> balanceRoot,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                List<Predicate> predicates = new LinkedList<>();

                if (constraints.get("accountNumber") != null) {
                    String vin = (String) constraints.get("accountNumber");
                    predicates.add(builder.equal(balanceRoot.get("accountNumber"), vin));
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}