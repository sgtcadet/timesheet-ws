package com.sgtcadet.timesheetws.api.client;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgtcadet.timesheetws.api.manager.Manager;
import com.sgtcadet.timesheetws.api.timesheet.TimeSheet;

import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to TimeSheet
	@OneToMany(mappedBy="client")
	@JsonIgnore
	private List<TimeSheet> timeSheets;

	//bi-directional many-to-one association to Manager
	@ManyToOne
	@JoinColumn(name="manager_id")
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Manager manager;

	public Client() {
        // default constructor
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TimeSheet> getTimeSheets() {
		return this.timeSheets;
	}

	public void setTimeSheets(List<TimeSheet> timeSheets) {
		this.timeSheets = timeSheets;
	}

	public TimeSheet addTimeSheet(TimeSheet timeSheet) {
		getTimeSheets().add(timeSheet);
		timeSheet.setClient(this);

		return timeSheet;
	}

	public TimeSheet removeTimeSheet(TimeSheet timeSheet) {
		getTimeSheets().remove(timeSheet);
		timeSheet.setClient(null);

		return timeSheet;
	}

	public Manager getManager() {
		return this.manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
}