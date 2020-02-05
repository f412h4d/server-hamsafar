package org.hamsafar.hamsafar.model;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.hamsafar.hamsafar.model.audits.AuditModel;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
@GraphQLType
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Admin extends AuditModel {
    private String name;
    private String username;
    private String password;
    private String token;

    private String companyName;
    private Integer credit;

    @DBRef(lazy = true)
    private List<City> cities;
}
