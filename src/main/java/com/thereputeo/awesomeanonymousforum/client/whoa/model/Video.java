package com.thereputeo.awesomeanonymousforum.client.whoa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Video {
    @JsonProperty("1080p")
    private String _1080p;
    @JsonProperty("720p")
    private String _720p;
    @JsonProperty("480p")
    private String _480p;
    @JsonProperty("360p")
    private String _360p;

    public Video() {
    }

    public String get_1080p() {
        return _1080p;
    }

    public void set_1080p(String _1080p) {
        this._1080p = _1080p;
    }

    public String get_720p() {
        return _720p;
    }

    public void set_720p(String _720p) {
        this._720p = _720p;
    }

    public String get_480p() {
        return _480p;
    }

    public void set_480p(String _480p) {
        this._480p = _480p;
    }

    public String get_360p() {
        return _360p;
    }

    public void set_360p(String _360p) {
        this._360p = _360p;
    }
}
