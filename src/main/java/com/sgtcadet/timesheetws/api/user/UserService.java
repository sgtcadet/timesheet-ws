package com.sgtcadet.timesheetws.api.user;

import com.sgtcadet.timesheetws.api.util.oauth2.Claims;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User save(Claims claims, UserDTO User);
    User saveV2(Claims claims, UserDTO User);
    User saveForAgentMger(Claims claims, UserDTO User) throws NotFoundException;
    User findById(Integer id) throws NotFoundException;
    ResponseEntity<Object> updateDTO(Claims claims, Integer id, UserDTO dto)throws NotFoundException;
    User findByEmailAddress(String emailAddress) throws NotFoundException;
    User findByUsername(Claims claims,String username)throws NotFoundException;
    ResponseEntity<Object> revokeTokenByAccessToken(Claims claims, String token);
    List<User> findAll(Claims claims);
    User findByEmailAddressRsetPassword(String emailAddress) throws NotFoundException;
}
