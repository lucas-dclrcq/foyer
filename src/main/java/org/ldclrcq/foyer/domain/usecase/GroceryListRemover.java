package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;
import org.ldclrcq.foyer.domain.repository.UMARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class GroceryListRemover {
    private final GroceryListRepository groceryListRepository;
    private final UMARepository umaRepository;

    @Inject
    GroceryListRemover(GroceryListRepository groceryListRepository, UMARepository umaRepository) {
        this.groceryListRepository = groceryListRepository;
        this.umaRepository = umaRepository;
    }

    public Uni<Void> execute(String id) {
        return groceryListRepository.getOne(id)
                .onItem().ifNull().failWith(new NotFoundException("List with id " + id + "does not exist"))
                .onItem().ifNotNull().transformToUni(groceryList -> groceryListRepository.delete(groceryList)
                        .flatMap(unused -> umaRepository.deleteProtectedResource(groceryList)));
    }
}
