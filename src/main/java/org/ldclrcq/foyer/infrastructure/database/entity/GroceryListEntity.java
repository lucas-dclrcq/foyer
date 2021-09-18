package org.ldclrcq.foyer.infrastructure.database.entity;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryListEntity extends PanacheEntityBase {
    @Id
    private String id;
    @Column(name = "external_id")
    private String externalId;
    private String name;
    @Column(name = "owner_id")
    private String ownerId;
}
