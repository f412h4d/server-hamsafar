package org.hamsafar.hamsafar.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import lombok.AllArgsConstructor;
import org.hamsafar.hamsafar.graphql.services.*;
import org.hamsafar.hamsafar.graphql.services.adjust.*;
import org.hamsafar.hamsafar.graphql.services.auth.AdminAuth;
import org.hamsafar.hamsafar.graphql.services.auth.UserAuth;
import org.hamsafar.hamsafar.graphql.services.search.AdminSearch;
import org.hamsafar.hamsafar.graphql.services.search.CitySearch;
import org.hamsafar.hamsafar.graphql.services.search.EventSearch;
import org.hamsafar.hamsafar.graphql.services.search.PlaceSearch;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchemaGenerator {
    private final AdminAdjust adminAdjust;
    private final CityAdjust cityAdjust;
    private final EventAdjust eventAdjust;
    private final FeedbackAdjust feedbackAdjust;
    private final PlaceAdjust placeAdjust;
    private final TagAdjust tagAdjust;
    private final UserAdjust userAdjust;

    private final AdminAuth adminAuth;
    private final UserAuth userAuth;

    private final AdminSearch adminSearch;
    private final CitySearch citySearch;
    private final EventSearch eventSearch;
    private final PlaceSearch placeSearch;

    private final AdminService adminService;
    private final CityService cityService;
    private final EventService eventService;
    private final FeedbackService feedbackService;
    private final PlaceService placeService;
    private final TagService tagService;
    private final UserService userService;

    @Bean
    public GraphQL getGraphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingletons(
                        adminAdjust, cityAdjust, placeAdjust, tagAdjust, userAdjust, eventAdjust, feedbackAdjust,
                        adminAuth, userAuth,
                        adminSearch, citySearch, eventSearch, placeSearch,
                        adminService, cityService, eventService, placeService, tagService, userService, feedbackService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        return GraphQL.newGraphQL(schema).build();
    }
}
