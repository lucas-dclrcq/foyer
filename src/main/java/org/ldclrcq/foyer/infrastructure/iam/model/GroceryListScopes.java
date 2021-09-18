package org.ldclrcq.foyer.infrastructure.iam.model;

public class GroceryListScopes {
    private GroceryListScopes() {
        // noop
    }

    public static final String VIEW = "grocery_list:view";
    public static final String DELETE = "grocery_list:delete";
    public static final String MODIFY = "grocery_list:modify";

}
