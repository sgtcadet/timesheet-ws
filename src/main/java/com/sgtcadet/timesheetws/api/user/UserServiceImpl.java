package com.sgtcadet.timesheetws.api.user;

import com.sgtcadet.timesheetws.api.util.oauth2.Claims;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService{

    @Autowired UserRepository userRepository;

    @Override
    public User save(Claims claims, UserDTO user) {
        return null;
    }

    @Override
    public User saveV2(Claims claims, UserDTO user) {
        return null;
    }

    @Override
    public User saveForAgentMger(Claims claims, UserDTO user) throws NotFoundException {
        return null;
    }

    @Override
    public User findById(Integer id) throws NotFoundException {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<Object> updateDTO(Claims claims, Integer id, UserDTO dto) throws NotFoundException {
        return null;
    }

    @Override
    public User findByEmailAddress(String emailAddress) throws NotFoundException {
        return null;
    }

    @Override
    public User findByUsername(Claims claims, String username) throws NotFoundException {
        Optional<User> officer = userRepository.findByUsername(username);
        if(!officer.isPresent()){
            throw new NotFoundException("Not Found");
        }
        return officer.get();
    }

    @Override
    public ResponseEntity<Object> revokeTokenByAccessToken(Claims claims, String token) {
        return null;
    }

    @Override
    public List<User> findAll(Claims claims) {
        return null;
    }

    @Override
    public User findByEmailAddressRsetPassword(String emailAddress) throws NotFoundException {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user.get();
    }
}
