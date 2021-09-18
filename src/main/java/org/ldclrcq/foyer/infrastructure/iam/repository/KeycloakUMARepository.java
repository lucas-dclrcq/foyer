package org.ldclrcq.foyer.infrastructure.iam.repository;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.PermissionTicketRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.ldclrcq.foyer.domain.model.GroceryList;
import org.ldclrcq.foyer.domain.repository.UMARepository;
import org.ldclrcq.foyer.infrastructure.iam.model.GroceryListScopes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class KeycloakUMARepository implements UMARepository {

    private final AuthzClient authzClient;

    @Inject
    KeycloakUMARepository(AuthzClient authzClient) {
        this.authzClient = authzClient;
    }

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
                "foyer:list"
        );

        groceryListResource.setOwner(groceryList.getOwnerId());
        groceryListResource.setOwnerManagedAccess(true);

        return askKeycloakToCreateProtectedResource(groceryListResource)
                .onItem().transform(ResourceRepresentation::getId)
                .onItem().transform(groceryList::withExternalId);
    }

    @Blocking
    private Uni<ResourceRepresentation> askKeycloakToCreateProtectedResource(ResourceRepresentation groceryListResource) {
        return Uni.createFrom().item(authzClient.protection().resource().create(groceryListResource));
    }

    @Blocking
    @Override
    public Uni<Void> deleteProtectedResource(GroceryList groceryList) {
        return Uni.createFrom().voidItem();
    }

    @Blocking
    public Uni<List<String>> findShares(String userId) {
        final List<String> sharesIds = authzClient.protection()
                .permission()
                .find(null, null, null, userId, true, true, null, null)
                .stream()
                .map(PermissionTicketRepresentation::getId)
                .toList();

        return Uni.createFrom().item(sharesIds);
    }
}
