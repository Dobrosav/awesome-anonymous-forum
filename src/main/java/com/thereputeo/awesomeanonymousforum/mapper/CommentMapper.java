package com.thereputeo.awesomeanonymousforum.mapper;

import com.thereputeo.awesomeanonymousforum.api.model.request.CommentDto;
import com.thereputeo.awesomeanonymousforum.api.model.response.CommentResponse;
import com.thereputeo.awesomeanonymousforum.database.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "parentComment", ignore = true)
    Comment toEntity(CommentDto commentDto);

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "parentCommentId", source = "parentComment.id")
    CommentResponse toResponse(Comment comment);
}
