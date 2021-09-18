package org.ldclrcq.foyer.infrastructure.iam.repository;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.UMARepository;
import org.ldclrcq.foyer.infrastructure.iam.model.GroceryListScopes;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Set;

@RequestScoped
public class KeycloakUMARepository implements UMARepository {

    private final AuthzClient authzClient;

    @Inject
    KeycloakUMARepository(AuthzClient authzClient) {
        this.authzClient = authzClient;
    }

    @Blocking
    @Override
    public Uni<GroceryList> createProtectedResource(GroceryList groceryList) {
        final ResourceRepresentation groceryListResource = new ResourceRepresentation(
                groceryList.getName(),
                Set.of(
                        new ScopeRepresentation(GroceryListScopes.VIEW),
                        new ScopeRepresentation(GroceryListScopes.DELETE),
                        new ScopeRepresentation(GroceryListScopes.MODIFY)
                ),
                "/lists/" + groceryList.getId(),
                "list"
        );

        groceryListResource.setOwner(groceryList.getOwnerId());
        groceryListResource.setOwnerManagedAccess(true);

        return Uni.createFrom().item(authzClient.protection().resource().create(groceryListResource))
                .map(ResourceRepresentation::getId)
                .map(groceryList::withExternalId);
    }

    @Blocking
    @Override
    public Uni<Void> deleteProtectedResource(GroceryList groceryList) {
        return Uni.createFrom().voidItem();
    }
}
