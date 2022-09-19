package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.repository.AnimeAPIUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimesAPIUserDetailsService implements UserDetailsService {
    private final AnimeAPIUserRepository animeAPIUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(animeAPIUserRepository.findByUsername(username)).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
