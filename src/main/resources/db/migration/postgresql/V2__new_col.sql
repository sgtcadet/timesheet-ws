
--
Alter table time_sheet
    add column status varchar default 'P'
    constraint c_timesheet1 check (status in('A','P'));