package com.krzywdek19.readingTacker.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public String getLoggedUser() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
