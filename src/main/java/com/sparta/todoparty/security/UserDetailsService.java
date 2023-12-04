package com.sparta.todoparty.security;

import com.sparta.todoparty.user.entity.User;
import com.sparta.todoparty.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));
        return new UserDetailsImpl(user);
    }

}