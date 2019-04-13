package com.wems.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the sensordata_address database table.
 * 
 */
@Entity
@Table(name="sensordata_address")
public class SensordataAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int addressuid;

	private String building;

	private String level;

	private String state;

	private String streetname;

	private String streetnumber;

	private String suburb;

	//bi-directional many-to-one association to UserGroup
    @ManyToOne
	@JoinColumn(name="userGroup")
	private UserGroup userGroupBean;

	//bi-directional many-to-one association to SensordataRoom
	@OneToMany(mappedBy="sensordataAddress")
	private Set<SensordataRoom> sensordataRooms;

    public SensordataAddress() {
    }

	public int getAddressuid() {
		return this.addressuid;
	}

	public void setAddressuid(int addressuid) {
		this.addressuid = addressuid;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetname() {
		return this.streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public String getStreetnumber() {
		return this.streetnumber;
	}

	public void setStreetnumber(String streetnumber) {
		this.streetnumber = streetnumber;
	}

	public String getSuburb() {
		return this.suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public UserGroup getUserGroupBean() {
		return this.userGroupBean;
	}

	public void setUserGroupBean(UserGroup userGroupBean) {
		this.userGroupBean = userGroupBean;
	}
	
	public Set<SensordataRoom> getSensordataRooms() {
		return this.sensordataRooms;
	}

	public void setSensordataRooms(Set<SensordataRoom> sensordataRooms) {
		this.sensordataRooms = sensordataRooms;
	}
	
}