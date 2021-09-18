package org.ldclrcq.foyer.infrastructure.api.resource;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.usecase.GroceryListCreator;
import org.ldclrcq.foyer.domain.usecase.GroceryListFetcher;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListCreateDTO;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListDTO;
import org.ldclrcq.foyer.infrastructure.api.mapper.GroceryListMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/lists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GroceryListResource {

    private final GroceryListMapper mapper;
    private final GroceryListFetcher groceryListFetcher;
    private final GroceryListCreator groceryListCreator;

    @Claim(standard = Claims.sub)
    String userId;

    @Inject
    GroceryListResource(GroceryListMapper mapper, GroceryListFetcher groceryListFetcher, GroceryListCreator groceryListCreator) {
        this.mapper = mapper;
        this.groceryListFetcher = groceryListFetcher;
        this.groceryListCreator = groceryListCreator;
    }

    @POST
    public Uni<GroceryListDTO> create(GroceryListCreateDTO dto) {
        final GroceryList groceryList = mapper.toDomain(dto).withOwnerId(userId);
        return groceryListCreator.execute(groceryList).map(mapper::toDTO);
    }

    @GET
    @Path("/{id}")
    public Uni<GroceryListDTO> get(@PathParam("id") String id) {
        return groceryListFetcher.execute(id).map(mapper::toDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<GroceryListDTO> delete(@PathParam("id") String id) {
        return groceryListFetcher.execute(id).map(mapper::toDTO);
    }
}
