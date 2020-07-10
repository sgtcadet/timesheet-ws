package com.sgtcadet.timesheetws.api.timesheet;

import com.sgtcadet.timesheetws.api.cron.timesheet.TimeSheetCronService;
import com.sgtcadet.timesheetws.api.util.oauth2.Claims;
import com.sgtcadet.timesheetws.api.util.oauth2.OAuth2Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class TimeSheetController {
    
    @Autowired private TimeSheetService timeSheetService;

    @Autowired private OAuth2Service oAuth2Service;

    @Autowired private TimeSheetCronService cronService;


    @GetMapping(path = "/timesheets")
    public ResponseEntity<Object>findAll(){
        cronService.approveJob();
        cronService.executeTask2();
        return new ResponseEntity<>(timeSheetService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/timesheets/cron")
    public ResponseEntity<Object>executeCrons(){
        cronService.approveJob();
        cronService.executeTask2();
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PostMapping(path = "/timesheets")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token |Ex: Bearer {token}",
                    required = true, dataType = "string", paramType = "header") })
    public ResponseEntity<Object> save(Principal principal, @RequestBody TimeSheet time){

        Claims claims = oAuth2Service.getClaims(principal);

        return new ResponseEntity<>(timeSheetService.save(claims,time), HttpStatus.CREATED);
    }
}