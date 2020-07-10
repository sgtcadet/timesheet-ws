package com.sgtcadet.timesheetws.api.util.oauth2;

import com.sgtcadet.timesheetws.api.authority.Authority;
import com.sgtcadet.timesheetws.api.user.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    @Autowired
    private ObjectFactory<Claims> claimsObjectFactory;

    public Claims newClaimsObject() {
        return claimsObjectFactory.getObject();
    }

    @Override
    public Claims getClaims(Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        Authentication authentication = oAuth2Authentication.getUserAuthentication();
        Claims claims = newClaimsObject();
        claims.setUsername(((OAuth2Authentication) principal).getName());
        User userDetails = (User) authentication.getPrincipal();
        claims.setUserId(userDetails.getId());

        Set<String> authorities  = new HashSet<>();
        for(Authority auth: userDetails.getAuthorities()){
            authorities.add(auth.getAuthority());
        }

        claims.setAuthorities(authorities);
        return claims;
    }
}
