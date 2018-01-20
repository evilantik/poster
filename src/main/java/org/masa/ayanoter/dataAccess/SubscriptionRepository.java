package org.masa.ayanoter.dataAccess;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    List<Subscription> findByFromUser(int fromUser);
    Subscription findByFromUserAndToUser(int fromUser, int toUser);
    long deleteByFromUserAndToUser(int fromUser, int toUser);
}
