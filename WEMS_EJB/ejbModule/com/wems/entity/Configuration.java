package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the configurations database table.
 * 
 */
@Entity
@Table(name="configurations")
public class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int configurationsId;

	private String currentUsers;

	private int maxUsers;

	private String systemName;

    public Configuration() {
    }

	public int getConfigurationsId() {
		return this.configurationsId;
	}

	public void setConfigurationsId(int configurationsId) {
		this.configurationsId = configurationsId;
	}

	public String getCurrentUsers() {
		return this.currentUsers;
	}

	public void setCurrentUsers(String currentUsers) {
		this.currentUsers = currentUsers;
	}

	public int getMaxUsers() {
		return this.maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}