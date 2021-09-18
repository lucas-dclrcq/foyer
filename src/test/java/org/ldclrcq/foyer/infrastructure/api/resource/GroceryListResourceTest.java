package org.ldclrcq.foyer.infrastructure.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@QuarkusTest
class GroceryListResourceTest {

    @Test
    @TestSecurity(user = "michel", roles = "user")
    void name() {
        assertThat(true).isTrue();
    }
}