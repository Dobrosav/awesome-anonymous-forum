package com.thereputeo.awesomeanonymousforum.mapper;

import com.thereputeo.awesomeanonymousforum.api.model.request.PostDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.PostResponse;
import com.thereputeo.awesomeanonymousforum.database.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "text", source = "content")
    @Mapping(target = "link", source = "externalLink")
    @Mapping(target = "type", source = "postType")
    Post toEntity(PostDto postDto);

    PostResponse toResponse(Post post);
}
