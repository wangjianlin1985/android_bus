package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.BusLine;
public class BusLineListHandler extends DefaultHandler {
	private List<BusLine> busLineList = null;
	private BusLine busLine;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (busLine != null) { 
            String valueString = new String(ch, start, length); 
            if ("lineId".equals(tempString)) 
            	busLine.setLineId(new Integer(valueString).intValue());
            else if ("name".equals(tempString)) 
            	busLine.setName(valueString); 
            else if ("startStation".equals(tempString)) 
            	busLine.setStartStation(new Integer(valueString).intValue());
            else if ("endStation".equals(tempString)) 
            	busLine.setEndStation(new Integer(valueString).intValue());
            else if ("startTime".equals(tempString)) 
            	busLine.setStartTime(valueString); 
            else if ("endTime".equals(tempString)) 
            	busLine.setEndTime(valueString); 
            else if ("company".equals(tempString)) 
            	busLine.setCompany(valueString); 
            else if ("tjzd".equals(tempString)) 
            	busLine.setTjzd(valueString); 
            else if ("polylinePoints".equals(tempString)) 
            	busLine.setPolylinePoints(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("BusLine".equals(localName)&&busLine!=null){
			busLineList.add(busLine);
			busLine = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		busLineList = new ArrayList<BusLine>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("BusLine".equals(localName)) {
            busLine = new BusLine(); 
        }
        tempString = localName; 
	}

	public List<BusLine> getBusLineList() {
		return this.busLineList;
	}
}
