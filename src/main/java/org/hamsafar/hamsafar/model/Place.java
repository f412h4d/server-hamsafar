package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
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
public class Place extends AuditModel {
    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Admin admin;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    private String title;
    private String detail;
    private String rules;

    private Double lat;
    private Double lng;

    private Float rate;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HashSet<Feedback> feedbacks;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HashSet<User> views;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HashSet<Picture> pictures;
}
