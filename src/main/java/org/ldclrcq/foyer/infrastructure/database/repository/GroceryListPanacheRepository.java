package org.ldclrcq.foyer.infrastructure.database.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.GroceryListRepository;
import org.ldclrcq.foyer.infrastructure.database.entity.GroceryListEntity;
import org.ldclrcq.foyer.infrastructure.database.mapper.GroceryListMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GroceryListPanacheRepository implements PanacheRepositoryBase<GroceryListEntity, String>, GroceryListRepository {
    private final GroceryListMapper mapper;

    @Inject
    GroceryListPanacheRepository(GroceryListMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Uni<GroceryList> save(GroceryList groceryList) {
        final GroceryListEntity groceryListEntity = mapper.toEntity(groceryList);
        return persist(groceryListEntity).map(mapper::toDomain);
    }

    @Override
    public Uni<GroceryList> getOne(String id) {
        return findById(id).map(mapper::toDomain);
    }

    @Override
    public Uni<Void> delete(GroceryList groceryList) {
        final GroceryListEntity groceryListEntity = mapper.toEntity(groceryList);
        return delete(groceryListEntity);
    }

}
