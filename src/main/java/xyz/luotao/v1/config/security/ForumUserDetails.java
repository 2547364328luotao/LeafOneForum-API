package xyz.luotao.v1.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ForumUserDetails implements UserDetails {

    private final xyz.luotao.v1.entity.User user;
    /**
     * 用户权限列表，用于存储用户的角色和权限。
     */
    private final List<GrantedAuthority> authorities;

    // 构造函数注入
    public ForumUserDetails(xyz.luotao.v1.entity.User user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * 返回用户拥有的权限列表。
     * @return 用户权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 返回用户的密码哈希。
     * @return 密码哈希字符串
     */
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    /**
     * 返回用户的用户名（此处使用邮箱作为用户名）。
     * @return 用户名字符串
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * 检查用户账户是否未过期。
     * @return true 如果账户未过期，否则 false
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * 检查用户账户是否未锁定。
     * @return true 如果账户未锁定，否则 false
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * 检查用户凭证（密码）是否未过期。
     * @return true 如果凭证未过期，否则 false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * 检查用户账户是否启用。
     * @return true 如果账户启用，否则 false
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
