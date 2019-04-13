package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the events_log database table.
 * 
 */
@Entity
@Table(name="events_log")
public class EventsLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventsId;

	private int eventType;

    @Temporal( TemporalType.TIMESTAMP)
	private Date timestamp;

	//bi-directional many-to-one association to AlertLog
    @ManyToOne
	@JoinColumn(name="alertId")
	private AlertLog alertLog;

	//bi-directional many-to-one association to SensordataDevice
    @ManyToOne
	@JoinColumn(name="deviceId")
	private SensordataDevice sensordataDevice;
    
    //bi-directional many-to-one association to SensordataDevice
    @ManyToOne
    @JoinColumn(name="roomId")
    private SensordataRoom sensordataRoom;

    public EventsLog() {
    }

	public int getEventsId() {
		return this.eventsId;
	}

	public void setEventsId(int eventsId) {
		this.eventsId = eventsId;
	}

	public int getEventType() {
		return this.eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public AlertLog getAlertLog() {
		return this.alertLog;
	}

	public void setAlertLog(AlertLog alertLog) {
		this.alertLog = alertLog;
	}
	
	public SensordataDevice getSensordataDevice() {
		return this.sensordataDevice;
	}

	public void setSensordataDevice(SensordataDevice sensordataDevice) {
		this.sensordataDevice = sensordataDevice;
	}

  public SensordataRoom getSensordataRoom() {
    return sensordataRoom;
  }

  public void setSensordataRoom(SensordataRoom sensordataRoom) {
    this.sensordataRoom = sensordataRoom;
  }
	
}