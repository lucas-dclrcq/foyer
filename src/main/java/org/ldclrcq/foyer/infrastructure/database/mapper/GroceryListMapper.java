package org.ldclrcq.foyer.infrastructure.database.mapper;

import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.infrastructure.database.entity.GroceryListEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GroceryListMapper {
    GroceryList toDomain(GroceryListEntity entity);

    GroceryListEntity toEntity(GroceryList model);
}
