package com.example.ejb.controller;

import com.example.ejb.dto.TransferDTO;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("/beneficios")
public class BeneficioEjbController {

    @EJB
    private BeneficioEjbService service;

    public BeneficioEjbController() {
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(@Valid TransferDTO dto) {
        service.transfer(dto.fromId(), dto.toId(), dto.amount());
        return Response.ok("Transferencia efetuada!").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        Beneficio beneficio = service.findById(id);
        return Response.ok(beneficio).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid Beneficio beneficio) {
        service.save(beneficio);
        return Response.ok("Salvo!").build();
    }    
}
