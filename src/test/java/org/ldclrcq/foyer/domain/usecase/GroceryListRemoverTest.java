package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;
import org.ldclrcq.foyer.domain.repository.UMARepository;

import javax.ws.rs.NotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GroceryListRemoverTest {

    GroceryListRepository groceryListRepository;
    UMARepository umaRepository;
    GroceryListRemover groceryListRemover;

    @BeforeEach
    void setup() {
        groceryListRepository = mock(GroceryListRepository.class);
        umaRepository = mock(UMARepository.class);
        groceryListRemover = new GroceryListRemover(groceryListRepository, umaRepository);
    }

    @Test
    void given_existing_list_should_delete_in_db_and_protected_resource() {
        // ARRANGE
        final GroceryList groceryList = GroceryList.builder()
                .id("LIST1")
                .name("Some list")
                .externalId("EXT1")
                .ownerId("OWNER1")
                .build();
        when(groceryListRepository.getOne(anyString())).thenReturn(Uni.createFrom().item(groceryList));
        when(umaRepository.deleteProtectedResource(any())).thenReturn(Uni.createFrom().voidItem());
        when(groceryListRepository.delete(any())).thenReturn(Uni.createFrom().voidItem());

        // ACT
        final Uni<Void> result = groceryListRemover.execute("LIST1");

        // ASSERT
        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
        verify(groceryListRepository).getOne("LIST1");
        verify(groceryListRepository).delete(groceryList);
        verify(umaRepository).deleteProtectedResource(groceryList);

    }

    @Test
    void given_list_does_not_exist_should_fail_and_do_nothing() {
        // ARRANGE
        when(groceryListRepository.getOne(anyString())).thenReturn(Uni.createFrom().nullItem());

        // ACT
        final Uni<Void> result = groceryListRemover.execute("LIST1");

        // ASSERT
        result.subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(NotFoundException.class);
        verify(groceryListRepository).getOne(eq("LIST1"));
        verify(groceryListRepository, never()).delete(any());
        verify(umaRepository, never()).deleteProtectedResource(any());
    }
}