package com.prabhat.springit.config;

import com.prabhat.springit.domain.User;
import com.prabhat.springit.security.SecurityConfiguration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImp implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null)
            return Optional.of("master@gmail.com");
        return Optional.of(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
    }
}
