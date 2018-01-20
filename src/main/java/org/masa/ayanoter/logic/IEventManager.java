package org.masa.ayanoter.logic;

import org.masa.ayanoter.dataAccess.Event;
import org.masa.ayanoter.dataAccess.Post;
import org.masa.ayanoter.dataAccess.Repost;
import org.masa.ayanoter.dataAccess.User;

import java.util.List;


public interface IEventManager {
    List<Event> getEventsOfUsers(List<User> users);
}
