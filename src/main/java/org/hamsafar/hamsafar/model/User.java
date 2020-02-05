package org.hamsafar.hamsafar.model;


import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User extends AuditModel {
    @Indexed(unique = true)
    private String phoneNumber;

    private String name;

    @DBRef
    private Picture picture;

    @DBRef
    private City city;

    private Integer level;

    @DBRef(lazy = true)
    private HashSet<Place> visited;
}
