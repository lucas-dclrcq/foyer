package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;
import org.ldclrcq.foyer.domain.repository.UMARepository;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GroceryListCreatorTest {

    GroceryListRepository groceryListRepository;
    UMARepository umaRepository;
    GroceryListCreator groceryListCreator;

    @BeforeEach
    void setup() {
        groceryListRepository = mock(GroceryListRepository.class);
        umaRepository = mock(UMARepository.class);
        groceryListCreator = new GroceryListCreator(groceryListRepository, umaRepository);
    }

    @Test
    void given_grocery_list_should_save_to_db_and_create_protected_resource() {
        // ARRANGE
        final String ownerId = UUID.randomUUID().toString();
        final GroceryList someList = GroceryList.builder()
                .name("Some list")
                .ownerId(ownerId)
                .build();
        when(groceryListRepository.save(any())).thenAnswer(invocationOnMock -> {
            final GroceryList argument = invocationOnMock.getArgument(0, GroceryList.class);
            return Uni.createFrom().item(argument);
        });
        when(umaRepository.createProtectedResource(any())).thenAnswer(invocationOnMock -> {
            final GroceryList argument = invocationOnMock.getArgument(0, GroceryList.class);
            return Uni.createFrom().item(argument.withExternalId("RESOURCE1"));
        });

        // ACT
        final Uni<GroceryList> result = groceryListCreator.execute(someList);

        // ASSERT
        assertThat(result.await().indefinitely())
                .extracting(
                        GroceryList::getExternalId,
                        GroceryList::getName,
                        GroceryList::getOwnerId
                ).containsExactly(
                        "RESOURCE1",
                        "Some list",
                        ownerId
                );
        assertThat(result.await().indefinitely().getId())
                .matches(id -> {
                    UUID.fromString(id);
                    return true;
                });

    }
}