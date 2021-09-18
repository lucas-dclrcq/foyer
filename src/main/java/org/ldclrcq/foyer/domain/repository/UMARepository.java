package org.ldclrcq.foyer.domain.repository;

import io.smallrye.mutiny.Uni;
import org.ldclrcq.foyer.domain.model.GroceryList;

public interface UMARepository {
    Uni<GroceryList> createProtectedResource(GroceryList groceryList);
    Uni<Void> deleteProtectedResource(GroceryList groceryList);
}
