package org.ldclrcq.foyer.infrastructure.database.repository;

import io.smallrye.mutiny.Uni;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;

import javax.inject.Inject;

class GroceryListPanacheRepositoryTest {
    @Inject
    GroceryListRepository groceryListRepository;

    @ParameterizedTest
    @AutoSource
    void name(GroceryList groceryList) {
        // ACT

        // ASSERT
    }
}