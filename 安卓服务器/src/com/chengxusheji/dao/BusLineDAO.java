package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.domain.BusStation;
import com.chengxusheji.domain.BusLine;

@Service @Transactional
public class BusLineDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddBusLine(BusLine busLine) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(busLine);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusLine> QueryBusLineInfo(String name,BusStation startStation,BusStation endStation,String company,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BusLine busLine where 1=1";
    	if(!name.equals("")) hql = hql + " and busLine.name like '%" + name + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and busLine.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and busLine.endStation.stationId=" + endStation.getStationId();
    	if(!company.equals("")) hql = hql + " and busLine.company like '%" + company + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List busLineList = q.list();
    	return (ArrayList<BusLine>) busLineList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusLine> QueryBusLineInfo(String name,BusStation startStation,BusStation endStation,String company) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BusLine busLine where 1=1";
    	if(!name.equals("")) hql = hql + " and busLine.name like '%" + name + "%'";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and busLine.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and busLine.endStation.stationId=" + endStation.getStationId();
    	if(!company.equals("")) hql = hql + " and busLine.company like '%" + company + "%'";
    	Query q = s.createQuery(hql);
    	List busLineList = q.list();
    	return (ArrayList<BusLine>) busLineList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusLine> QueryAllBusLineInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From BusLine";
        Query q = s.createQuery(hql);
        List busLineList = q.list();
        return (ArrayList<BusLine>) busLineList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String name,BusStation startStation,BusStation endStation,String company) {
        Session s = factory.getCurrentSession();
        String hql = "From BusLine busLine where 1=1";
        if(!name.equals("")) hql = hql + " and busLine.name like '%" + name + "%'";
        if(null != startStation && startStation.getStationId()!=0) hql += " and busLine.startStation.stationId=" + startStation.getStationId();
        if(null != endStation && endStation.getStationId()!=0) hql += " and busLine.endStation.stationId=" + endStation.getStationId();
        if(!company.equals("")) hql = hql + " and busLine.company like '%" + company + "%'";
        Query q = s.createQuery(hql);
        List busLineList = q.list();
        recordNumber = busLineList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public BusLine GetBusLineByLineId(int lineId) {
        Session s = factory.getCurrentSession();
        BusLine busLine = (BusLine)s.get(BusLine.class, lineId);
        return busLine;
    }

    /*更新BusLine信息*/
    public void UpdateBusLine(BusLine busLine) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(busLine);
    }

    /*删除BusLine信息*/
    public void DeleteBusLine (int lineId) throws Exception {
        Session s = factory.getCurrentSession();
        Object busLine = s.load(BusLine.class, lineId);
        s.delete(busLine);
    }

}
