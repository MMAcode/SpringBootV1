package makarov.learning.security;

import makarov.learning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyUserDetails_UserSecurityDetailsService implements UserDetails {

    private final User user;

    public MyUserDetails_UserSecurityDetailsService(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorityList = new ArrayList<>();
        user.getAuthorities().forEach(authorityString ->
            authorityList.add(Authority.valueOf(authorityString)));
        // return (Collection<String>) user.getAuthorities();
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
