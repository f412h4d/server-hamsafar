package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Event extends AuditModel {
    private String title;
    private String detail;

    @DBRef
    private Place destination;

    private Float rate;

    private Integer capacity;

    private Date date;

    @DBRef(lazy = true)
    private HashSet<Feedback> feedbacks;
}
