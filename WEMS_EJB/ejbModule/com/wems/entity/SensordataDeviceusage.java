package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sensordata_deviceusage database table.
 * 
 */
@Entity
@Table(name="sensordata_deviceusage")
public class SensordataDeviceusage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int deviceusageuid;

	private boolean powerstatus;

    @Temporal( TemporalType.TIMESTAMP)
	private Date time;

	//bi-directional many-to-one association to SensordataDevice
    @ManyToOne
	@JoinColumn(name="deviceuid")
	private SensordataDevice sensordataDevice;

    public SensordataDeviceusage() {
    }

	public int getDeviceusageuid() {
		return this.deviceusageuid;
	}

	public void setDeviceusageuid(int deviceusageuid) {
		this.deviceusageuid = deviceusageuid;
	}

	public boolean getPowerstatus() {
		return this.powerstatus;
	}

	public void setPowerstatus(boolean powerstatus) {
		this.powerstatus = powerstatus;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public SensordataDevice getSensordataDevice() {
		return this.sensordataDevice;
	}

	public void setSensordataDevice(SensordataDevice sensordataDevice) {
		this.sensordataDevice = sensordataDevice;
	}
	
}