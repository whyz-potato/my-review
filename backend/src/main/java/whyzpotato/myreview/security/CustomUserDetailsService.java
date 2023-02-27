package whyzpotato.myreview.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.repository.UsersRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users= usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid authentication"));
        return new CustomUserDetails(users);
    }

}
