package com.thereputeo.awesomeanonymousforum.api.model.request;

import com.fasterxml.jackson.annotation.JsonValue;
import com.thereputeo.awesomeanonymousforum.model.PostType;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class PostDto implements Serializable {
    private PostType postType;
    @NotBlank
    private String content;
    private String externalLink;
    @NotBlank
    private String authorName;
    @JsonValue(value = false)
    private Boolean includeKeanuWhoa;

    public PostDto() {
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public Boolean getIncludeKeanuWhoa() {
        return includeKeanuWhoa;
    }

    public void setIncludeKeanuWhoa(Boolean includeKeanuWhoa) {
        this.includeKeanuWhoa = includeKeanuWhoa;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "postType=" + postType +
                ", content='" + content + '\'' +
                ", externalLink='" + externalLink + '\'' +
                ", authorName='" + authorName + '\'' +
                ", includeKeanuWhoa=" + includeKeanuWhoa +
                '}';
    }
}
