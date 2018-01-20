package org.masa.ayanoter.dataAccess;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findAllByOrderByDateDesc();
    List<Post> findByUserOrderByDateDesc(User user);
    long countByUser(User user);
}
