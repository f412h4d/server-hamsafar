package org.hamsafar.hamsafar.graphql.services.adjust;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Tag;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class TagAdjust {
    private final TagRepository tagRepository;

    @GraphQLMutation
    public Tag addTag(@GraphQLNonNull String title) {
        return this.tagRepository.save(Tag.builder()
                .title(title)
                .build());
    }
}
