package org.ldclrcq.foyer.domain.repository;

import io.smallrye.mutiny.Uni;
import org.ldclrcq.foyer.domain.model.GroceryList;

public interface GroceryListRepository {
    Uni<GroceryList> save(GroceryList groceryList);

    Uni<GroceryList> getOne(String id);

    Uni<Void> delete(GroceryList groceryList);
}
