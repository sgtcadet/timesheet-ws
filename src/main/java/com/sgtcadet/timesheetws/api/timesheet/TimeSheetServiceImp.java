package com.sgtcadet.timesheetws.api.timesheet;

import java.util.List;

import com.sgtcadet.timesheetws.api.period.Period;

import com.sgtcadet.timesheetws.api.user.User;
import com.sgtcadet.timesheetws.api.user.UserRepository;
import com.sgtcadet.timesheetws.api.util.oauth2.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class TimeSheetServiceImp implements TimeSheetService {
    @Autowired private TimeSheetRepository timeSheetRepository;

    @Autowired private UserRepository userRepository;
    @Override
    public List<TimeSheet> findAll() {
        // TODO Auto-generated method stub
        return timeSheetRepository.findAll();
    }

    @Override
    public TimeSheet save(Claims claims, TimeSheet time) {
        // TODO Auto-generated method stub
        // for (Period period : time.getPeriods()) {
        //     time.addPeriod(period);
        // }
        User user = userRepository.getOne(claims.getUserId());
        time.setUser(user);
        return timeSheetRepository.save(time);
    }
    
}