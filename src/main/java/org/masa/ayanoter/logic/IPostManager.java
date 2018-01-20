package org.masa.ayanoter.logic;

import org.masa.ayanoter.dataAccess.Post;
import org.masa.ayanoter.dataAccess.User;

public interface IPostManager {
    long getPostCountForUser(User user);

    //TODO: @Transactional?
    Post create(User author, String text);
}
