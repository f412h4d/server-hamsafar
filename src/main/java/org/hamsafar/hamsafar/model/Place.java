package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Place extends AuditModel {
    private String title;
    private String detail;
    private String rules;

    private Double lat;
    private Double lng;

    @DBRef
    private City city;

    @DBRef(lazy = true)
    private HashSet<Feedback> feedbacks;

    @DBRef(lazy = true)
    private List<User> views;

    @DBRef(lazy = true)
    private List<Picture> pictures;
}
