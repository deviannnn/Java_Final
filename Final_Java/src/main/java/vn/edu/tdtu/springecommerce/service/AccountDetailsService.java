package vn.edu.tdtu.springecommerce.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springecommerce.model.Account;
import vn.edu.tdtu.springecommerce.repository.AccountRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service

public class AccountDetailsService implements UserDetailsService {
        private AccountRepository repository;

        public AccountDetailsService(AccountRepository userRepository) {
            this.repository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Account account = repository.findByUsername(email);
//            Collection<String> roles = Collectors.toList(new SimpleGrantedAuthority(account.getRole()));
            if (account != null) {
                return new org.springframework.security.core.userdetails.User(account.getUsername(),
                        account.getPassword(), Arrays.asList(new SimpleGrantedAuthority(account.getRole())));

            }else{
                throw new UsernameNotFoundException("Invalid username or password.");
            }
        }
//        private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
//            Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
//                    .map(role -> new SimpleGrantedAuthority(role.getName()))
//                    .collect(Collectors.toList());
//            return mapRoles;
//        }
}
