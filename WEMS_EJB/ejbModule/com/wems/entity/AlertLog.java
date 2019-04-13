package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the alert_log database table.
 * 
 */
@Entity
@Table(name="alert_log")
public class AlertLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int alertsId;

	private String alertPriority;

	private String alertType;


	private String sensorDatadump;


    @Temporal( TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to AlertSetting
    @ManyToOne
	@JoinColumn(name="alertSettingId")
	private AlertSetting alertSetting;

	//bi-directional many-to-one association to EventsLog
	@OneToMany(mappedBy="alertLog")
	private Set<EventsLog> eventsLogs;

    public AlertLog() {
    }

	public int getAlertsId() {
		return this.alertsId;
	}

	public void setAlertsId(int alertsId) {
		this.alertsId = alertsId;
	}

	public String getAlertPriority() {
		return this.alertPriority;
	}

	public void setAlertPriority(String alertPriority) {
		this.alertPriority = alertPriority;
	}

	public String getAlertType() {
		return this.alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getSensorDatadump() {
		return this.sensorDatadump;
	}

	public void setSensorDatadump(String sensorDatadump) {
		this.sensorDatadump = sensorDatadump;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public AlertSetting getAlertSetting() {
		return this.alertSetting;
	}

	public void setAlertSetting(AlertSetting alertSetting) {
		this.alertSetting = alertSetting;
	}
	
	public Set<EventsLog> getEventsLogs() {
		return this.eventsLogs;
	}

	public void setEventsLogs(Set<EventsLog> eventsLogs) {
		this.eventsLogs = eventsLogs;
	}
}