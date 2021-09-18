package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GroceryListFetcherTest {

    GroceryListRepository groceryListRepository;
    GroceryListFetcher groceryListFetcher;

    @BeforeEach
    void setup() {
        groceryListRepository = mock(GroceryListRepository.class);
        groceryListFetcher = new GroceryListFetcher(groceryListRepository);
    }

    @ParameterizedTest
    @AutoSource
    void given_id_is_not_null_should_fetch_grocery_list(GroceryList groceryList) {
        // ARRANGE
        String id = "id";
        when(groceryListRepository.getOne(id)).thenReturn(Uni.createFrom().item(groceryList));

        // ACT
        final Uni<GroceryList> execute = groceryListFetcher.execute(id);

        // ASSERT
        assertThat(execute.await().indefinitely())
                .isEqualTo(groceryList);
        verify(groceryListRepository).getOne(id);
    }

    @Test
    void given_is_is_null_should_fail() {
        // ARRANGE
        String  id = null;

        // ACT
        final Uni<GroceryList> execute = groceryListFetcher.execute(id);

        // ASSERT
        final UniAssertSubscriber<GroceryList> assertSubscriber = execute.subscribe().withSubscriber(UniAssertSubscriber.create());
        assertSubscriber.assertFailedWith(IllegalArgumentException.class);
    }
}