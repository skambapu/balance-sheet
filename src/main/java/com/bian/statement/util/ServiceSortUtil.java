package com.bian.statement.util;

import com.bian.statement.exception.InvalidSortValueException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceSortUtil {

    public static Sort buildSortParams(Map<String, Sort.Direction> sortParams, Map<String, String> availableSortFields) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String field : sortParams.keySet()) {
            if (availableSortFields.containsKey(field)) {
                orders.add(new Sort.Order(sortParams.get(field), availableSortFields.get(field)));
            } else {
                throw new InvalidSortValueException(field, "Latest Balance", "Available sort fields include: " + availableSortFields.keySet().toString());
            }
        }

        return Sort.by(orders);
    }
}
