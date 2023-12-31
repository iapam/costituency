package com.apam.constituencies.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Director implements UserDetails {
    @Id
    private String constituencyid;
    private String directorid;
    private String constituency_name;
    private String region;
    private String email;
    private String name;
    private String number;
    private String password;
    @Enumerated(EnumType.STRING)
    private  adminRole adminrole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(adminrole.name());
        return Collections.singleton(authority);
    }

    public Director(String constituencyid,String directorid, String password, adminRole adminrole) {
        this.directorid = directorid;
        this.password = password;
        this.adminrole = adminrole;
        this.constituencyid=constituencyid;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {
        return directorid;

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
