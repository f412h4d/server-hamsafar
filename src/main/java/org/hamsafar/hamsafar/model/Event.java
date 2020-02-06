package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Admin admin;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tag tag;

    @DBRef
    @Indexed
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place destination;

    private String title;
    private String header;
    private String detail;

    private Boolean isAd;
    private Integer adViews;
    private Integer adLimit;

    private Integer capacity;

    private Float rate;
    private Date date;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> views;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> checkedIns;

    @DBRef
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Picture picture;

    @DBRef(lazy = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Feedback> feedbacks;
}
