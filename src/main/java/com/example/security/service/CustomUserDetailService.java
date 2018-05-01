package com.example.security.service;

import com.example.security.domain.CustomUserDetails;
import com.example.security.domain.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author VIVEK KUMAR SINGH
 * @since (2018-04-30 17:02:06)
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
  @Autowired UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByName(username);

    user.orElseThrow(() -> new UsernameNotFoundException("Username not Found!!!"));
    return user.map(CustomUserDetails::new).get();
  }
}
