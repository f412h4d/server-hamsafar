package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Event extends AuditModel {
    private String title;
    private String detail;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place destination;

    private Float rate;

    private Integer capacity;

    private Date date;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Feedback> feedbacks;
}
