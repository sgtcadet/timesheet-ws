package com.sgtcadet.timesheetws.api.timesheet;

import com.sgtcadet.timesheetws.api.util.oauth2.Claims;

import java.util.List;

public interface TimeSheetService {
    List<TimeSheet> findAll();
    
    TimeSheet save(Claims claims, TimeSheet time);
}