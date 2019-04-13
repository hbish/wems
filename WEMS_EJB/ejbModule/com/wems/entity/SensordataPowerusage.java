package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sensordata_powerusage database table.
 * 
 */
@Entity
@Table(name="sensordata_powerusage")
public class SensordataPowerusage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int powerusageuid;

    @Temporal( TemporalType.TIMESTAMP)
	private Date time;

	private double watt;

	//bi-directional many-to-one association to SensordataRoom
    @ManyToOne
	@JoinColumn(name="roomuid")
	private SensordataRoom sensordataRoom;

    public SensordataPowerusage() {
    }

	public int getPowerusageuid() {
		return this.powerusageuid;
	}

	public void setPowerusageuid(int powerusageuid) {
		this.powerusageuid = powerusageuid;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getWatt() {
		return this.watt;
	}

	public void setWatt(double watt) {
		this.watt = watt;
	}

	public SensordataRoom getSensordataRoom() {
		return this.sensordataRoom;
	}

	public void setSensordataRoom(SensordataRoom sensordataRoom) {
		this.sensordataRoom = sensordataRoom;
	}
	
}