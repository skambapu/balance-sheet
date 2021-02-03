package com.bian.statement.controller;

import com.bian.statement.exception.InvalidSortValueException;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class BaseResource {

    static final String DEFAULT_OFFSET = "0";
    static final String DEFAULT_LIMIT = "50";

    public static Map<String, Sort.Direction> parseSortParam(String sortParams) {
        if (sortParams == null) {
            return null;
        }

        String[] fieldDirectionPairs = sortParams.split(",");
        Map<String, Sort.Direction> sorts = new HashMap<>();
        for (String pair : fieldDirectionPairs) {
            String[] sort = pair.split(":");
            Sort.Direction direction = null;
            if (sort.length > 1) {
                try {
                    direction = Sort.Direction.fromString(sort[1]);
                } catch (IllegalArgumentException e) {
                    throw new InvalidSortValueException(sort[0], "ServiceStatus", "Invalid Direction - must be either DESC or ASC");
                }
            }
            sorts.put(sort[0], direction);
        }

        return sorts;
    }
}
