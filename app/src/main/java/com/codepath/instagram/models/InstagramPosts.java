package com.codepath.instagram.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 12/6/15.
 */
public class InstagramPosts implements Serializable {
    public List<InstagramPost> posts;

    public InstagramPosts(List<InstagramPost> posts) {
        this.posts = posts;
    }
}
