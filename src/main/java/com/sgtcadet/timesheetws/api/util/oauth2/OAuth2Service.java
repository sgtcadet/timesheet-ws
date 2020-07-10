package com.sgtcadet.timesheetws.api.util.oauth2;

import java.security.Principal;

public interface OAuth2Service {
    Claims getClaims(Principal principal);
}
