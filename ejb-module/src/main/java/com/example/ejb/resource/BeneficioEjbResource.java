package com.example.ejb.resource;

import com.example.ejb.dto.TransferDTO;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Stateless
@Path("/beneficios")
public class BeneficioEjbResource {

    @EJB
    private BeneficioEjbService service;

    public BeneficioEjbResource() {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Beneficio beneficio) {
        service.save(beneficio);
        return Response.status(Response.Status.CREATED).build();
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
    public Beneficio find(@PathParam("id") Long id) {
        return service.find(id);
    } 
}
