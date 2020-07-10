package com.sgtcadet.timesheetws.api.period;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sgtcadet.timesheetws.api.timesheet.TimeSheet;



/**
 * The persistent class for the period database table.
 * 
 */
@Entity
@NamedQuery(name="Period.findAll", query="SELECT p FROM Period p")
public class Period implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String comment;

	@Column(name="end_time")
	private Date endTime;

	@Column(name="start_time")
	private Date startTime;

	//bi-directional many-to-one association to TimeSheet
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="time_sheet_id", referencedColumnName="id")
	private TimeSheet timeSheet;

	public Period() {
        // default constructor
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public TimeSheet getTimeSheet() {
		return this.timeSheet;
	}

	public void setTimeSheet(TimeSheet timeSheet) {
		this.timeSheet = timeSheet;
	}

}