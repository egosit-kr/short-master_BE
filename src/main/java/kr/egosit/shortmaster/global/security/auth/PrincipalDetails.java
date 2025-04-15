package kr.egosit.shortmaster.global.security.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class PrincipalDetails implements UserDetails {
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    public PrincipalDetails(String email, Collection<String> roles) {
        this.email = email;
        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { return null;}

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
