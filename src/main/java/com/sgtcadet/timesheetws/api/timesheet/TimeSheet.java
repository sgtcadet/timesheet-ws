package com.sgtcadet.timesheetws.api.timesheet;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.sgtcadet.timesheetws.api.client.Client;
import com.sgtcadet.timesheetws.api.period.Period;
import com.sgtcadet.timesheetws.api.user.User;

import java.util.Collection;
import java.util.List;


/**
 * The persistent class for the time_sheet database table.
 * 
 */
@Entity
@Table(name="time_sheet")
@NamedQuery(name="TimeSheet.findAll", query="SELECT t FROM TimeSheet t")
public class TimeSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="timespent_on_project")
	private Integer timespentOnProject;

	private String title;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "user_id")
//	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private User user;

	private String status; // A- approved | p -pending

	//bi-directional many-to-one association to Period
	@OneToMany(mappedBy="timeSheet", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Period> periods;

	public TimeSheet() {
        // default constructor
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTimespentOnProject() {
		return this.timespentOnProject;
	}

	public void setTimespentOnProject(Integer timespentOnProject) {
		this.timespentOnProject = timespentOnProject;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Period> getPeriods() {
		return this.periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Period addPeriod(Period period) {
		getPeriods().add(period);
		period.setTimeSheet(this);

		return period;
	}

	public Period removePeriod(Period period) {
		getPeriods().remove(period);
		period.setTimeSheet(null);

		return period;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}