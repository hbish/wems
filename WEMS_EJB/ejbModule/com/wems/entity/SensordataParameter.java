package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the sensordata_parameter database table.
 * 
 */
@Entity
@Table(name="sensordata_parameter")
public class SensordataParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int parameteruid;

	private String name;

    private String value;

	//bi-directional many-to-one association to AlertSetting
	@OneToMany(mappedBy="sensordataParameter")
	private Set<AlertSetting> alertSettings;

	//bi-directional many-to-one association to SensordataDevice
    @ManyToOne
	@JoinColumn(name="deviceuid")
	private SensordataDevice sensordataDevice;

    public SensordataParameter() {
    }

	public int getParameteruid() {
		return this.parameteruid;
	}

	public void setParameteruid(int parameteruid) {
		this.parameteruid = parameteruid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}


    public Double getValueDouble() {
	Double myValue = Double.parseDouble(value);
	return myValue;
    }

	public String getValue() {
		return this.value;
	}

    public String getValueString() {
	return value;
    }
	public void setAlertSettings(Set<AlertSetting> alertSettings) {
		this.alertSettings = alertSettings;
	}
	
	public SensordataDevice getSensordataDevice() {
		return this.sensordataDevice;
	}


    public void setValueDouble(Double value) {
	this.value = value.toString();
    }

	public Set<AlertSetting> getAlertSettings() {
		return this.alertSettings;
	}

    public void setValueString(String value) {
	this.value = value;
    }
	

	public void setSensordataDevice(SensordataDevice sensordataDevice) {
		this.sensordataDevice = sensordataDevice;
	}
	
}