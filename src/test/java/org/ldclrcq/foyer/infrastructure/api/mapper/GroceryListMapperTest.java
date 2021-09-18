package org.ldclrcq.foyer.infrastructure.api.mapper;

import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListCreateDTO;
import org.ldclrcq.foyer.infrastructure.api.dto.GroceryListDTO;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GroceryListMapperTest {

    GroceryListMapper mapper = new GroceryListMapperImpl();

    @ParameterizedTest
    @AutoSource
    void given_create_dto_should_map_to_domain(GroceryListCreateDTO createDTO) {
        // ACT
        final GroceryList groceryList = mapper.toDomain(createDTO);

        // ASSERT
        assertThat(groceryList)
                .hasNoNullFieldsOrPropertiesExcept("id", "externalId", "ownerId")
                .extracting(GroceryList::getName)
                .isEqualTo(createDTO.name());
    }

    @ParameterizedTest
    @AutoSource
    void given_dto_should_map_to_domain(GroceryList groceryList) {
        // ACT
        final GroceryListDTO dto = mapper.toDTO(groceryList);

        // ASSERT
        assertThat(dto)
                .hasNoNullFieldsOrProperties()
                .extracting(GroceryListDTO::id, GroceryListDTO::name)
                .containsExactly(dto.id(), dto.name());
    }
}