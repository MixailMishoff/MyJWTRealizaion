package com.security.TryingToMakeCorrectRealizationOfJWT.jwt;

import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import com.security.TryingToMakeCorrectRealizationOfJWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private String signature;
    private UserRepository userRepository;

    /**
     * Извлекаем пользователя из бд,
     *
     * @param email
     * @return {@link UserDetails} Возвращает объект сведений о пользователе
     * используя полученную информацию о пользователе
     * @throws UsernameNotFoundException
     */
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userRes = userRepository.findByEmail(email);
//        if (userRes.isEmpty())
//            throw new UsernameNotFoundException("Could not findUser with email = " + email);
//        User user = userRes.get();
//        return new org.springframework.security.core.userdetails.User(
//                email,
//                user.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("USER")));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())  // Убедитесь, что это хэшированный пароль
                .authorities("ROLE_USER")
                .build();
    }
}
