package com.example.ejb.controller;

import com.example.ejb.dto.BeneficioRequestDTO;
import com.example.ejb.dto.BeneficioResponseDTO;
import com.example.ejb.dto.TransferDTO;
import com.example.ejb.mapper.BeneficioRequestMapper;
import com.example.ejb.mapper.BeneficioResponseDetailsMapper;
import com.example.ejb.mapper.BeneficioResponseMapper;
import com.example.ejb.model.Beneficio;
import com.example.ejb.model.Page;
import com.example.ejb.service.BeneficioEjbService;
import com.example.ejb.utils.MessageUtils;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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
        var beneficiosResponseDTO = service.findAll().stream().map(beneficio -> new BeneficioResponseDTO(beneficio.getId(), beneficio.getNome(), beneficio.getValor(), beneficio.isAtivo())).toList();
        return Response.ok(beneficiosResponseDTO).build();
    }

    @GET
    @Path("/page")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByPage(
            @QueryParam("number") @DefaultValue("0") Integer number,
            @QueryParam("size") @DefaultValue("12") Integer size,
            @QueryParam("orderBy") @DefaultValue("id") String orderBy,
            @QueryParam("direction") @DefaultValue("DESC") String direction) {
        Page<Beneficio> pageBeneficios = service.findByPage(number, size, orderBy, direction);
        List<BeneficioResponseDTO> dtos = pageBeneficios.getContent().stream().map(beneficio -> new BeneficioResponseDTO(beneficio.getId(), beneficio.getNome(), beneficio.getValor(), beneficio.isAtivo())).toList();
        Page<BeneficioResponseDTO> pageDtos = new Page<>(dtos, pageBeneficios.getPageNumber(), pageBeneficios.isFirst(), pageBeneficios.isLast(),
                pageBeneficios.getPageSize(), pageBeneficios.getTotalPages(), pageBeneficios.getTotalElements());
        return Response.ok(pageDtos).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        var beneficio = service.findById(id);
        var beneficioResponseDetailsDTO = new BeneficioResponseDetailsMapper().toDTO(beneficio);
        return Response.ok(beneficioResponseDetailsDTO).build();
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
