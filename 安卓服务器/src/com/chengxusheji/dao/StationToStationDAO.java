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
import com.chengxusheji.domain.StationToStation;

@Service @Transactional
public class StationToStationDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddStationToStation(StationToStation stationToStation) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(stationToStation);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationToStation> QueryStationToStationInfo(BusStation startStation,BusStation endStation,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StationToStation stationToStation where 1=1";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and stationToStation.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and stationToStation.endStation.stationId=" + endStation.getStationId();
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List stationToStationList = q.list();
    	return (ArrayList<StationToStation>) stationToStationList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationToStation> QueryStationToStationInfo(BusStation startStation,BusStation endStation) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From StationToStation stationToStation where 1=1";
    	if(null != startStation && startStation.getStationId()!=0) hql += " and stationToStation.startStation.stationId=" + startStation.getStationId();
    	if(null != endStation && endStation.getStationId()!=0) hql += " and stationToStation.endStation.stationId=" + endStation.getStationId();
    	Query q = s.createQuery(hql);
    	List stationToStationList = q.list();
    	return (ArrayList<StationToStation>) stationToStationList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<StationToStation> QueryAllStationToStationInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From StationToStation";
        Query q = s.createQuery(hql);
        List stationToStationList = q.list();
        return (ArrayList<StationToStation>) stationToStationList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(BusStation startStation,BusStation endStation) {
        Session s = factory.getCurrentSession();
        String hql = "From StationToStation stationToStation where 1=1";
        if(null != startStation && startStation.getStationId()!=0) hql += " and stationToStation.startStation.stationId=" + startStation.getStationId();
        if(null != endStation && endStation.getStationId()!=0) hql += " and stationToStation.endStation.stationId=" + endStation.getStationId();
        Query q = s.createQuery(hql);
        List stationToStationList = q.list();
        recordNumber = stationToStationList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public StationToStation GetStationToStationById(int id) {
        Session s = factory.getCurrentSession();
        StationToStation stationToStation = (StationToStation)s.get(StationToStation.class, id);
        return stationToStation;
    }

    /*����StationToStation��Ϣ*/
    public void UpdateStationToStation(StationToStation stationToStation) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(stationToStation);
    }

    /*ɾ��StationToStation��Ϣ*/
    public void DeleteStationToStation (int id) throws Exception {
        Session s = factory.getCurrentSession();
        Object stationToStation = s.load(StationToStation.class, id);
        s.delete(stationToStation);
    }

}
