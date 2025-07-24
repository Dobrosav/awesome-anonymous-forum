package com.thereputeo.awesomeanonymousforum.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @Lob
    @Column(name = "type", nullable = false)
    private String type;

    @Lob
    @Column(name = "poster")
    private String poster;

    @Lob
    @Column(name = "link")
    private String link;

    @Lob
    @Column(name = "video")
    private String video;

    @Lob
    @Column(name = "audio")
    private String audio;

    @Lob
    @Column(name="author_name")
    private String authorName;

    public Post() {
    }

    public Post(String text, String type, String poster, String link, String video, String audio, String authorName) {
        this.text = text;
        this.type = type;
        this.poster = poster;
        this.link = link;
        this.video = video;
        this.audio = audio;
        this.authorName = authorName;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", poster='" + poster + '\'' +
                ", link='" + link + '\'' +
                ", video='" + video + '\'' +
                ", audio='" + audio + '\'' +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
