package org.masa.ayanoter.logic;

import org.masa.ayanoter.dataAccess.Post;
import org.masa.ayanoter.dataAccess.Repost;
import org.masa.ayanoter.dataAccess.User;

public interface IRepostManager {
    boolean canBeReposted(User user, Post post);

    long getRepostsCount(User user);

    Repost create(User author, int postId);
}
