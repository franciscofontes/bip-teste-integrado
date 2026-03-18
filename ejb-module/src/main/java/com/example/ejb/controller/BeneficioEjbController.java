package com.example.ejb.controller;

import com.example.ejb.dto.BeneficioRequestDTO;
import com.example.ejb.dto.BeneficioResponseDTO;
import com.example.ejb.dto.TransferDTO;
import com.example.ejb.mapper.BeneficioRequestMapper;
import com.example.ejb.mapper.BeneficioResponseMapper;
import com.example.ejb.service.BeneficioEjbService;
import com.example.ejb.utils.MessageUtils;
import com.example.ejb.validation.TransferValidation;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        var beneficiosResponseDTO = service.findAll().stream().map(beneficio -> new BeneficioResponseDTO(beneficio.getId(), beneficio.getNome(), beneficio.getValor(), beneficio.getVersion())).toList();
        return Response.ok(beneficiosResponseDTO).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        var beneficio = service.findById(id);
        var beneficioResponseDTO = new BeneficioResponseMapper().toDTO(beneficio);
        return Response.ok(beneficioResponseDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(BeneficioRequestDTO dto) {
        service.create(new BeneficioRequestMapper().toEntity(dto));
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, BeneficioRequestDTO dto) {
        service.update(id, new BeneficioRequestMapper().toEntity(dto));
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(@Valid TransferDTO dto) {
        service.transfer(dto.fromId(), dto.toId(), dto.amount());
        return Response.ok(MessageUtils.TRANSFER_SUCCESS).build();
    }
}
