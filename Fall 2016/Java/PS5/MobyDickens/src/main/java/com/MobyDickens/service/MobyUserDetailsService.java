package com.MobyDickens.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Created by Brad on 11/21/2016.
 */
/*@Repository
public class MobyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found " + username);
        }
        return new SecurityUser(user);
    }
}
*/