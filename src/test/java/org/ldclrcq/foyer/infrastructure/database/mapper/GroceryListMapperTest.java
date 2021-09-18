package org.ldclrcq.foyer.infrastructure.database.mapper;

import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.infrastructure.database.entity.GroceryListEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GroceryListMapperTest {

    GroceryListMapper mapper = new GroceryListMapperImpl();

    @Test
    void given_entity_should_map_to_domain() {
        // GIVEN
        final GroceryListEntity someList = GroceryListEntity.builder()
                .id("LIST1")
                .externalId("ext1")
                .ownerId("owner1")
                .name("Some list")
                .build();

        // ARRANGE
        final GroceryList groceryList = mapper.toDomain(someList);

        // ACT
        assertThat(groceryList)
                .hasNoNullFieldsOrProperties()
                .extracting(GroceryList::getId, GroceryList::getName, GroceryList::getExternalId, GroceryList::getOwnerId)
                .containsExactly("LIST1", "Some list", "ext1", "owner1");
    }

    @ParameterizedTest
    @AutoSource
    void given_grocery_list_should_map_to_entity(GroceryList groceryList) {
        // ACT
        final GroceryListEntity groceryListEntity = mapper.toEntity(groceryList);

        // ASSERT
        assertThat(groceryListEntity)
                .hasNoNullFieldsOrProperties()
                .extracting(GroceryListEntity::getId, GroceryListEntity::getName)
                .containsExactly(groceryList.getId(), groceryList.getName());

    }
}