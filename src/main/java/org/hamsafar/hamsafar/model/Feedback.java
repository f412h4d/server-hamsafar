package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Feedback extends AuditModel {
    @DBRef
    @Indexed(unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place place;

    private Integer rate;
}
