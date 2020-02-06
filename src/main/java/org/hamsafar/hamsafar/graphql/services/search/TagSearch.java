package org.hamsafar.hamsafar.graphql.services.search;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hamsafar.hamsafar.model.Tag;
import org.hamsafar.hamsafar.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@GraphQLApi
@CrossOrigin
@AllArgsConstructor
public class TagSearch {
    private final TagRepository tagRepository;

    @GraphQLQuery
    public List<Tag> getAllTags() {
        return this.tagRepository.findAllByVerifiedTrue();
    }

    @GraphQLQuery
    public Tag getTagByTitle(@GraphQLNonNull String title) {
        Optional<Tag> tagOptional = this.tagRepository.findByTitleAndVerifiedTrue(title);
        if (tagOptional.isEmpty()) {
            log.error("Invalid Tag Title");
            throw new RuntimeException("Invalid Tag Title, Not Found");
        }
        return tagOptional.get();
    }
}
