package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the alert_setting database table.
 * 
 */
@Entity
@Table(name="alert_setting")
public class AlertSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int alertId;

	private String alertPriority;

	private String alertType;

	private boolean exactThresholdEnabled;

	private double exactThresholdValue;

	private boolean maxThresholdEnabled;

	private double maxThresholdValue;

	private boolean minThresholdEnabled;

	private double minThresholdValue;

	private int score;

	private boolean scoreEnabled;

    @Temporal( TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to AlertLog
	@OneToMany(mappedBy="alertSetting")
	private Set<AlertLog> alertLogs;

	//bi-directional many-to-one association to SensordataParameter
    @ManyToOne
	@JoinColumn(name="dataParameterId")
	private SensordataParameter sensordataParameter;

	//bi-directional many-to-one association to UserGroup
    @ManyToOne
	@JoinColumn(name="alertUserGroup")
	private UserGroup userGroup;

    public AlertSetting() {
    }

	public int getAlertId() {
		return this.alertId;
	}

	public void setAlertId(int alertId) {
		this.alertId = alertId;
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

	public boolean getExactThresholdEnabled() {
		return this.exactThresholdEnabled;
	}

	public void setExactThresholdEnabled(boolean exactThresholdEnabled) {
		this.exactThresholdEnabled = exactThresholdEnabled;
	}

	public double getExactThresholdValue() {
		return this.exactThresholdValue;
	}

	public void setExactThresholdValue(double exactThresholdValue) {
		this.exactThresholdValue = exactThresholdValue;
	}

	public boolean getMaxThresholdEnabled() {
		return this.maxThresholdEnabled;
	}

	public void setMaxThresholdEnabled(boolean maxThresholdEnabled) {
		this.maxThresholdEnabled = maxThresholdEnabled;
	}

	public double getMaxThresholdValue() {
		return this.maxThresholdValue;
	}

	public void setMaxThresholdValue(double maxThresholdValue) {
		this.maxThresholdValue = maxThresholdValue;
	}

	public boolean getMinThresholdEnabled() {
		return this.minThresholdEnabled;
	}

	public void setMinThresholdEnabled(boolean minThresholdEnabled) {
		this.minThresholdEnabled = minThresholdEnabled;
	}

	public double getMinThresholdValue() {
		return this.minThresholdValue;
	}

	public void setMinThresholdValue(double minThresholdValue) {
		this.minThresholdValue = minThresholdValue;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getScoreEnabled() {
		return this.scoreEnabled;
	}

	public void setScoreEnabled(boolean scoreEnabled) {
		this.scoreEnabled = scoreEnabled;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Set<AlertLog> getAlertLogs() {
		return this.alertLogs;
	}

	public void setAlertLogs(Set<AlertLog> alertLogs) {
		this.alertLogs = alertLogs;
	}
	
	public SensordataParameter getSensordataParameter() {
		return this.sensordataParameter;
	}

	public void setSensordataParameter(SensordataParameter sensordataParameter) {
		this.sensordataParameter = sensordataParameter;
	}
	
	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
}