package com.thereputeo.awesomeanonymousforum.api.model.request;

import jakarta.validation.constraints.NotBlank;

public class CommentDto {
    @NotBlank
    private String content;
    @NotBlank
    private String authorName;
    private String imageUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "content='" + content + '\'' +
                ", authorName='" + authorName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
