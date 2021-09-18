package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GroceryListFetcher {

    private final GroceryListRepository groceryListRepository;

    @Inject
    GroceryListFetcher(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    public Uni<GroceryList> execute(String id) {
        if (id == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Id should not be null"));
        }

        return groceryListRepository.getOne(id);
    }
}
