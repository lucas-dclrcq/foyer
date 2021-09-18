package org.ldclrcq.foyer.domain.usecase;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;
import org.ldclrcq.foyer.domain.repository.UMARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class GroceryListCreator {

    private final GroceryListRepository groceryListRepository;
    private final UMARepository umaRepository;

    @Inject
    GroceryListCreator(GroceryListRepository groceryListRepository, UMARepository umaRepository) {
        this.groceryListRepository = groceryListRepository;
        this.umaRepository = umaRepository;
    }

    public Uni<GroceryList> execute(GroceryList groceryList) {
        final String groceryListId = UUID.randomUUID().toString();
        return umaRepository.createProtectedResource(groceryList.withId(groceryListId))
                .flatMap(groceryListRepository::save)
                .invoke(gl -> log.info("Successfully saved list : id={} ownerId={} externalId={}",
                        gl.getId(), gl.getOwnerId(), gl.getExternalId()));
    }
}
