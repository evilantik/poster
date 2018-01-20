package org.masa.ayanoter.dataAccess;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findByAuthorInOrderByDateDesc(List<User> authors);
}
