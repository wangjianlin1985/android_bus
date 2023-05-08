package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.BusStation;
public class BusStationListHandler extends DefaultHandler {
	private List<BusStation> busStationList = null;
	private BusStation busStation;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (busStation != null) { 
            String valueString = new String(ch, start, length); 
            if ("stationId".equals(tempString)) 
            	busStation.setStationId(new Integer(valueString).intValue());
            else if ("stationName".equals(tempString)) 
            	busStation.setStationName(valueString); 
            else if ("longitude".equals(tempString)) 
            	busStation.setLongitude(new Float(valueString).floatValue());
            else if ("latitude".equals(tempString)) 
            	busStation.setLatitude(new Float(valueString).floatValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("BusStation".equals(localName)&&busStation!=null){
			busStationList.add(busStation);
			busStation = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		busStationList = new ArrayList<BusStation>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("BusStation".equals(localName)) {
            busStation = new BusStation(); 
        }
        tempString = localName; 
	}

	public List<BusStation> getBusStationList() {
		return this.busStationList;
	}
}
