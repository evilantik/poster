package org.masa.ayanoter.logic;

import org.masa.ayanoter.dataAccess.User;

import java.util.List;

public interface IUserManager {
    List<User> getUsersByIds(List<Integer> ids);

    User get(int id);

    User getByLogin(String login);
}
