package org.masa.ayanoter.dataAccess;

import org.springframework.data.repository.CrudRepository;


public interface RepostRepository extends CrudRepository<Repost, Integer> {
    Repost findByPostAndAuthor(Post post, User author);
    long countByPost_User(User user);
}
