package com.example.ejb.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BeneficioExceptionMapper implements ExceptionMapper<BeneficioException> {

    @Override
    public Response toResponse(BeneficioException exception) {
        return Response.status(exception.getStatus())
                .entity(exception.getMessage())
                .build();
    }
}
