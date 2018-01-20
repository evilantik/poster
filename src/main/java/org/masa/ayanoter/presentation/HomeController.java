package org.masa.ayanoter.presentation;

import org.masa.ayanoter.dataAccess.*;
import org.masa.ayanoter.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {

    private UserRepository userRepository;
    private IPostManager postManager;
    private ISubscriptionManager subscriptionManager;
    private IUserManager userManager;
    private IRepostManager repostManager;
    private IEventListViewModelProvider eventListViewModelProvider;
    private IProfileStatViewModelProvider profileStatViewModelProvider;

    @Autowired
    public HomeController(UserRepository userRepository,
                          IPostManager postManager,
                          ISubscriptionManager subscriptionManager,
                          IUserManager userManager,
                          IRepostManager repostManager,
                          IEventListViewModelProvider eventListViewModelProvider,
                          IProfileStatViewModelProvider profileStatViewModelProvider){
        this.userRepository = userRepository;
        this.postManager = postManager;
        this.subscriptionManager = subscriptionManager;
        this.userManager = userManager;
        this.repostManager = repostManager;
        this.eventListViewModelProvider = eventListViewModelProvider;
        this.profileStatViewModelProvider = profileStatViewModelProvider;
    }



    @RequestMapping("/home/news")
    public String getNews(Model model, Principal principal) throws Exception {
        User currentUser = userManager.getByLogin(principal.getName());


        List<Subscription> subscriptions = subscriptionManager.getUserSubscriptions(currentUser);

        List<Integer> subscribedToIds = new ArrayList<>();
        for (Subscription subscription : subscriptions){
            subscribedToIds.add(subscription.getToUser());
        }

        List<User> subscribedTo = userManager.getUsersByIds(subscribedToIds);

        List<User> subscriptionsAndSelf = new ArrayList<>();
        subscriptionsAndSelf.addAll(subscribedTo);
        subscriptionsAndSelf.add(currentUser);

        List<EventViewModel> events = eventListViewModelProvider.get(subscriptionsAndSelf, currentUser);

        ProfileStatViewModel profileStatViewModel = profileStatViewModelProvider.get(currentUser);


        model.addAttribute("events", events);
        model.addAttribute("subscriptions", subscribedTo);
        model.addAttribute("profileStat", profileStatViewModel);

        return "home/news";
    }

    @RequestMapping("/home/profile")
    public String getProfile(@RequestParam(required = false) Integer id, Model model, Principal principal){
        User currentUser = userManager.getByLogin(principal.getName());

        User user;

        if(id == null){
            user = currentUser;
        } else {
            user = userManager.get(id);
        }

        List<Subscription> subscriptions = subscriptionManager.getUserSubscriptions(user);

        List<Integer> subscribedToIds = new ArrayList<>();
        for (Subscription subscription : subscriptions){
            subscribedToIds.add(subscription.getToUser());
        }

        List<User> subscribedTo = userManager.getUsersByIds(subscribedToIds);

        List<EventViewModel> events = eventListViewModelProvider.get(user, currentUser);

        ProfileStatViewModel profileStatViewModel = profileStatViewModelProvider.get(user);

        boolean canSubscribe = !subscriptionManager.isSubscribed(currentUser, user);

        boolean isCurrentUserProfile = Objects.equals(user.getId(), currentUser.getId());

        model.addAttribute("displayUser", user);
        model.addAttribute("events", events);
        model.addAttribute("subscriptions", subscribedTo);
        model.addAttribute("profileStat", profileStatViewModel);
        model.addAttribute("canSubscribe", canSubscribe);
        model.addAttribute("isCurrentUserProfile", isCurrentUserProfile);
        return "home/profile";
    }

    @RequestMapping("/home/profile/subscribe")
    public String subscribe(@RequestParam int id, Principal principal){
        User currentUser = userManager.getByLogin(principal.getName());

        subscriptionManager.subscribe(currentUser, id);

        return "redirect:/home/profile?id="+id;
    }

    @Transactional
    @RequestMapping("/home/profile/unsubscribe")
    public String unsubscribe(@RequestParam int id, Principal principal){
        User currentUser = userManager.getByLogin(principal.getName());

        subscriptionManager.unsubscribe(currentUser, id);
        return "redirect:/home/profile?id=" + id;
    }

    @RequestMapping(path="/home/newpost", method = RequestMethod.GET)
    public String getNewPost(){
        return "home/newpost";
    }

    @RequestMapping(path="/home/newpost", method = RequestMethod.POST)
    @Transactional
    public String postNewPost(@RequestParam String postText, Principal principal) throws Exception {

        User currentUser = userManager.getByLogin(principal.getName());

        postManager.create(currentUser, postText);

        return "redirect:/home/news";
    }

    @RequestMapping("home/settings")
    public String getSettings(){
        return "home/settings";
    }

    @RequestMapping("home/repost")
    public String repost(@RequestParam int postId, @RequestParam(required = false) String redirect, Principal principal){
        User currentUser = userRepository.findByLogin("Vasya");

        repostManager.create(currentUser, postId);

        if(redirect == null){
            redirect = "/home/news";
        }

        return "redirect:" + redirect;
    }
}
