package com.bian.statement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Base exception.
 */
public class BalanceSheetException extends WebApplicationException {

    public BalanceSheetException(Status status, ErrorDto errorDto) {
        super(Response.status(status).
                entity(errorDto).type(MediaType.APPLICATION_JSON)
                .build());
    }
}


