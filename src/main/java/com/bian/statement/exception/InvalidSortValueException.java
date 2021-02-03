package com.bian.statement.exception;

import org.slf4j.MDC;

import javax.ws.rs.core.Response;

public class InvalidSortValueException extends BalanceSheetException {

    public InvalidSortValueException(String sortField, String resourceName, String customMessage) {
        super(Response.Status.BAD_REQUEST, new ErrorDto(MDC.get("correlation"),
                "INVALID_SORT_VALUE", "{0} is an invalid sort value for {1}. {2}", sortField, resourceName, customMessage));
    }
}
