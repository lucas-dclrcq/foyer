package org.ldclrcq.foyer.infrastructure.iam.repository;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.resource.ProtectedResource;
import org.keycloak.authorization.client.resource.ProtectionResource;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.ldclrcq.foyer.domain.model.GroceryList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KeycloakUMARepositoryTest {

    ProtectedResource protectedResource;
    ProtectionResource protectionResource;
    AuthzClient authzClient;
    KeycloakUMARepository keycloakUMARepository;

    @BeforeEach
    void setup() {
        protectedResource = mock(ProtectedResource.class);
        protectionResource = mock(ProtectionResource.class);
        authzClient = mock(AuthzClient.class);
        keycloakUMARepository = new KeycloakUMARepository(authzClient);

        when(authzClient.protection()).thenReturn(protectionResource);
        when(protectionResource.resource()).thenReturn(protectedResource);
    }

    @Test
    void given_grocery_list_should_create_protected_resource_and_return_list_with_external_id() {
        // ARRANGE
        final GroceryList groceryList = GroceryList.builder()
                .id("LIST1")
                .name("Some list")
                .ownerId("OWNER1")
                .build();
        when(protectedResource.create(any())).thenAnswer(invocationOnMock -> {
            final ResourceRepresentation argument = invocationOnMock.getArgument(0, ResourceRepresentation.class);
            argument.setId("EXT1");
            return argument;
        });

        // ACT
        final Uni<GroceryList> protectedResource = keycloakUMARepository.createProtectedResource(groceryList);

        // ASSERT
        protectedResource.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted()
                .assertItem(GroceryList.builder()
                        .id("LIST1")
                        .name("Some list")
                        .ownerId("OWNER1")
                        .externalId("EXT1")
                        .build());
    }
}