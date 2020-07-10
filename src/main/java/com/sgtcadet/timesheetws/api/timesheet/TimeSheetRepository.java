package com.sgtcadet.timesheetws.api.timesheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet,Integer>{

    @Query("select t from TimeSheet t where t.status = 'P'")
    List<TimeSheet>findAllPending();
}