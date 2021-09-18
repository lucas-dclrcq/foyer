package org.ldclrcq.foyer.infrastructure.api.mapper;

import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListCreateDTO;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GroceryListMapper {
    GroceryListDTO toDTO(GroceryList groceryList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "name", source = "name")
    GroceryList toDomain(GroceryListCreateDTO createDTO);
}
