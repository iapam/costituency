package com.apam.constituencies.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class PollingAgent implements UserDetails {
    @Id
    private String pollingNumber;
    private String email;
    private String name;
    private String number;
    private String password;
    @ManyToOne
    @JoinColumn
    private Director director;
    private adminRole adminrole;

    public PollingAgent(String pollingNumber, String password, adminRole adminrole) {
        this.pollingNumber = pollingNumber;
        this.password = password;
        this.adminrole = adminrole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(adminrole.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return pollingNumber;
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
