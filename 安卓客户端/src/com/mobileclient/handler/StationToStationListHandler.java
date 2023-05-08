package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.StationToStation;
public class StationToStationListHandler extends DefaultHandler {
	private List<StationToStation> stationToStationList = null;
	private StationToStation stationToStation;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (stationToStation != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	stationToStation.setId(new Integer(valueString).intValue());
            else if ("startStation".equals(tempString)) 
            	stationToStation.setStartStation(new Integer(valueString).intValue());
            else if ("endStation".equals(tempString)) 
            	stationToStation.setEndStation(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("StationToStation".equals(localName)&&stationToStation!=null){
			stationToStationList.add(stationToStation);
			stationToStation = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		stationToStationList = new ArrayList<StationToStation>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("StationToStation".equals(localName)) {
            stationToStation = new StationToStation(); 
        }
        tempString = localName; 
	}

	public List<StationToStation> getStationToStationList() {
		return this.stationToStationList;
	}
}
