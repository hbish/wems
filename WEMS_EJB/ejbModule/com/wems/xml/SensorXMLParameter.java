package com.wems.xml;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


/**
 * The persistent class for a serialised xml device data
 */
public class SensorXMLParameter{

	private String name;
	
	private String value;
	
	public SensorXMLParameter(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nParameter - " + getName());
		sb.append(":  ");
		sb.append(getValue());
		return sb.toString();
	}
}