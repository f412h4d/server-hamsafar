package org.hamsafar.hamsafar.model.audits;

import io.leangen.graphql.annotations.GraphQLId;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@GraphQLInterface(name = "AuditModel")
public abstract class AuditModel {
    @Id
    @GraphQLId
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated;

    private Boolean verified = true;
}
