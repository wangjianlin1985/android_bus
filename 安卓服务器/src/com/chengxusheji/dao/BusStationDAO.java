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

@Service @Transactional
public class BusStationDAO {

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
    public void AddBusStation(BusStation busStation) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(busStation);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusStation> QueryBusStationInfo(String stationName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BusStation busStation where 1=1";
    	if(!stationName.equals("")) hql = hql + " and busStation.stationName like '%" + stationName + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List busStationList = q.list();
    	return (ArrayList<BusStation>) busStationList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusStation> QueryBusStationInfo(String stationName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BusStation busStation where 1=1";
    	if(!stationName.equals("")) hql = hql + " and busStation.stationName like '%" + stationName + "%'";
    	Query q = s.createQuery(hql);
    	List busStationList = q.list();
    	return (ArrayList<BusStation>) busStationList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BusStation> QueryAllBusStationInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From BusStation";
        Query q = s.createQuery(hql);
        List busStationList = q.list();
        return (ArrayList<BusStation>) busStationList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String stationName) {
        Session s = factory.getCurrentSession();
        String hql = "From BusStation busStation where 1=1";
        if(!stationName.equals("")) hql = hql + " and busStation.stationName like '%" + stationName + "%'";
        Query q = s.createQuery(hql);
        List busStationList = q.list();
        recordNumber = busStationList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public BusStation GetBusStationByStationId(int stationId) {
        Session s = factory.getCurrentSession();
        BusStation busStation = (BusStation)s.get(BusStation.class, stationId);
        return busStation;
    }

    /*更新BusStation信息*/
    public void UpdateBusStation(BusStation busStation) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(busStation);
    }

    /*删除BusStation信息*/
    public void DeleteBusStation (int stationId) throws Exception {
        Session s = factory.getCurrentSession();
        Object busStation = s.load(BusStation.class, stationId);
        s.delete(busStation);
    }

}
