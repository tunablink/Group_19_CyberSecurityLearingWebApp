package com.example.blog_web.services;

import com.example.blog_web.models.MyUserDetails;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service hỗ trợ xác thực người dùng dựa trên JPA.
 * <p>
 * Spring Security sẽ gọi Service này trong tiến trình đăng nhập để load thông tin UserDetails
 * từ cơ sở dữ liệu qua UserRepository.
 * </p>
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
