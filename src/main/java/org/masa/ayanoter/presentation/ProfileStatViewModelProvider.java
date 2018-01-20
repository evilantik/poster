package org.masa.ayanoter.presentation;

import org.masa.ayanoter.dataAccess.User;
import org.masa.ayanoter.logic.IPostManager;
import org.masa.ayanoter.logic.IRepostManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileStatViewModelProvider implements IProfileStatViewModelProvider {
    private IPostManager postManager;
    private IRepostManager repostManager;

    @Autowired
    public ProfileStatViewModelProvider(IPostManager postManager, IRepostManager repostManager) {
        this.postManager = postManager;
        this.repostManager = repostManager;
    }

    @Override
    public ProfileStatViewModel get(User user){
        ProfileStatViewModel result = new ProfileStatViewModel();
        result.likesCount = 0L;
        result.postsCount = postManager.getPostCountForUser(user);
        result.repostsCount = repostManager.getRepostsCount(user);
        result.login = user.getLogin();
        return result;
    }
}
