package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag extends AuditModel {
    @Indexed(unique = true)
    private String title;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Place> places;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Event> events;
}
