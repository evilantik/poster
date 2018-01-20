package org.masa.ayanoter.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//TODO: @Transactional(readOnly=true)
public class MyDetailsService implements UserDetailsService {
    private UserManager userManager;

    @Autowired
    public MyDetailsService(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        org.masa.ayanoter.dataAccess.User user = userManager.getByLogin(login);
        if(user == null){
            throw new UsernameNotFoundException(login);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new User(
                user.getLogin(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
    }
}
