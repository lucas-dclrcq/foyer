package org.ldclrcq.foyer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
@AllArgsConstructor
public class GroceryList {
    private String id;
    private String externalId;
    private String name;
    private String ownerId;
}
