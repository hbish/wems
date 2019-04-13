package com.wems.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.io.StringReader;

import com.wems.xml.*;
import com.wems.entity.*;

public class XMLSerialiser{

	private String tempVal;
	private boolean room, roomName, device, parameter,connected, powerStatus, id, roomWatt, pName;
	private boolean stNum, stName, suburb, state, building, level, temp;
	private boolean pValue, type, brand, model, serial, macaddress;
	SensorXMLData parsedData;
	SensordataAddress address;
	
	//testStrings
	String testReturn;
	
    /**
     * Default constructor. 
     */
    public XMLSerialiser() {
    	testReturn = "";
    	room = true;
    	roomName = false;
    	stNum = false;
    	stName = false;
    	suburb = false;
    	state = false;
    	building = false;
    	level = false;
		device = false;
		temp = true;
		parameter = false;
		id = false;
		connected = false;
		powerStatus = false;
		roomWatt = false;
		pName = false;
		pValue = false;
		type = false;
		brand = false;
		model = false;
		serial = false;
		macaddress = false;
    }
    
    public SensorXMLData parseXML(String xml) {

    	
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			MySAXHandler handler = new MySAXHandler(); 
			System.out.print("DEBUG - " + xml.toString());
			sp.parse(new InputSource(new StringReader(xml)), handler);
			
		}
		
		//Parse Exception Handlers
		catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	
		device = false;
		room = true;
		return parsedData;


	}


//Event Handlers For Parsing XML
class MySAXHandler extends DefaultHandler{
	//Executed when start tag is parsed
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
	}

	//Executed when data is parsed
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
		
		setRoomFlags();
		
		if (device){
			setDeviceFlags();
		}
		
		if (parameter){
			if (tempVal.equalsIgnoreCase("NAME")){
				pName = true;
			} else if (tempVal.equalsIgnoreCase("VALUE")){
				pValue = true;
			}
		}
	}	

	//Executed when end tag is parsed
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (room) {
			setRoomData(qName);
		} 
		
		if (device) {
			setDeviceData(qName);
		}
	}
}

	private boolean convertBoolean(String status){
		if (status.equalsIgnoreCase("0")){
			return false;
		}
		return true;
	}
	private void setRoomFlags(){
		if (tempVal.equalsIgnoreCase("DEVICE")){
			device = true;
			room = false;
		} else if (tempVal.equalsIgnoreCase("PARAMETER")){
			parameter = true;
		} else if (tempVal.equalsIgnoreCase("ID")){
			id = true;
			parameter = false;
		} else if (tempVal.equalsIgnoreCase("NAME")){
			roomName = true;
		} else if (tempVal.equalsIgnoreCase("STREENUMBER")){
			stNum = true;
		} else if (tempVal.equalsIgnoreCase("STREETNAME")){
			stName = true;
		} else if (tempVal.equalsIgnoreCase("SUBURB")){
			suburb = true;
		} else if (tempVal.equalsIgnoreCase("STATE")){
			state = true;
		} else if (tempVal.equalsIgnoreCase("BUILDING")){
			building = true;
		} else if (tempVal.equalsIgnoreCase("BLEVEL")){
			level = true;
		} else if (tempVal.equalsIgnoreCase("CONNECTED")){
			connected = true;
		} else if (tempVal.equalsIgnoreCase("POWERSTATUS")){
			powerStatus = true;
		} else if (tempVal.equalsIgnoreCase("WATT")){
			roomWatt = true;
		}
	}
	
	private void setDeviceFlags(){
		if (tempVal.equalsIgnoreCase("TYPE")){
			type = true;
		} else if (tempVal.equalsIgnoreCase("BRAND")){
			brand = true;
		} else if (tempVal.equalsIgnoreCase("MODEL")){
			model = true;
		} else if (tempVal.equalsIgnoreCase("SERIAL")){
			serial = true;
		} else if (tempVal.equalsIgnoreCase("MACADDRESS")){
			macaddress = true;
		} else if (tempVal.equalsIgnoreCase("ID")){
			id = true;
			parameter=false;
		}
	}
	
	private void setRoomData(String qName){
		if (id) {
			if (qName.equalsIgnoreCase("data")){
				//create room/location object
				parsedData = new SensorXMLData(tempVal);
				id = false;
			}
		} else if (roomName){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setName(tempVal);
				roomName = false;
			}
		} else if (stNum){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setStNo(tempVal);
				stNum = false;
			}
		} else if (stName){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setStName(tempVal);
				stName = false;
			}
		} else if (suburb){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setSuburb(tempVal);
				suburb = false;
			}
		} else if (state){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setState(tempVal);
				state = false;
			}
		} else if (building){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setBuilding(tempVal);
				building = false;
			}
		} else if (level){
			if (qName.equalsIgnoreCase("string")){
				parsedData.setLevel(tempVal);
				level = false;
			}
		} else if (connected) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.setConnected(convertBoolean(tempVal));
				connected = false;
			}
		} else if (powerStatus) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.setPowerStatus(convertBoolean(tempVal));
				powerStatus = false;
			}
		} else if (roomWatt) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.setWatt(Double.parseDouble(tempVal));
				roomWatt = false;
			}
		}
	}
	
	private void setDeviceData(String qName){
		if (id) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.addDevice(tempVal);
				id = false;
			}
		} else if (connected) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.addDeviceConnected(convertBoolean(tempVal));
				connected = false;
			}
		} else if (powerStatus) {
			if (qName.equalsIgnoreCase("data")){
				parsedData.addDevicePowerStatus(convertBoolean(tempVal));
				powerStatus = false;
			}
		} else if (type) {
			if (qName.equalsIgnoreCase("string")){
				parsedData.addDeviceType(tempVal);
				type = false;
			}
		}else if (brand) {
			if (qName.equalsIgnoreCase("string")){
				parsedData.addDeviceBrand(tempVal);
				brand = false;
			}
		}else if (model) {
			if (qName.equalsIgnoreCase("string")){
				parsedData.addDeviceModel(tempVal);
				model = false;
			}
		}else if (serial) {
			if (qName.equalsIgnoreCase("string")){
				parsedData.addDeviceSerial(tempVal);
				serial = false;
			}
		}else if (macaddress) {
			if (qName.equalsIgnoreCase("string")){
				parsedData.addDeviceMac(tempVal);
				macaddress = false;
			}
		}else if (parameter){
			if (pName) {
				if (qName.equalsIgnoreCase("string")){
					if(tempVal.length()>0){
						parsedData.addDeviceParameter(tempVal);
						temp = true;
					}else{
						temp=false;;
					}
					pName = false;
				}
			} 
			if (temp)
			{
				if(pValue){
					if (qName.equalsIgnoreCase("string")){
						if(tempVal.length()>0){
							parsedData.addDeviceParameterValue(tempVal);
						}else{
							parsedData.addDeviceParameterValue("0");
						}
						pValue = false;
					}
				}
			}
		}
	}
}
