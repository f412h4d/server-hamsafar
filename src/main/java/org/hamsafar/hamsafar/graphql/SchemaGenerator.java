package org.hamsafar.hamsafar.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import lombok.AllArgsConstructor;
import org.hamsafar.hamsafar.graphql.services.AdminService;
import org.hamsafar.hamsafar.graphql.services.CityService;
import org.hamsafar.hamsafar.graphql.services.PlaceService;
import org.hamsafar.hamsafar.graphql.services.auth.AdminAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchemaGenerator {
    private final AdminService adminService;
    private final AdminAuth adminAuth;

    private final CityService cityService;
    private final PlaceService placeService;

    @Bean
    public GraphQL getGraphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingletons(adminService, adminAuth, cityService, placeService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        return GraphQL.newGraphQL(schema).build();
    }
}
