package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.BusStationService;
import com.mobileclient.service.BusStationService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class BusLineSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public BusLineSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.busline_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_startStation = (TextView)convertView.findViewById(R.id.tv_startStation);
	  holder.tv_endStation = (TextView)convertView.findViewById(R.id.tv_endStation);
	  holder.tv_startTime = (TextView)convertView.findViewById(R.id.tv_startTime);
	  holder.tv_endTime = (TextView)convertView.findViewById(R.id.tv_endTime);
	  holder.tv_company = (TextView)convertView.findViewById(R.id.tv_company);
	  /*设置各个控件的展示内容*/
	  holder.tv_name.setText("线路名称：" + mData.get(position).get("name").toString());
	  holder.tv_startStation.setText("起点站：" + (new BusStationService()).GetBusStation(Integer.parseInt(mData.get(position).get("startStation").toString())).getStationName());
	  holder.tv_endStation.setText("终到站：" + (new BusStationService()).GetBusStation(Integer.parseInt(mData.get(position).get("endStation").toString())).getStationName());
	  holder.tv_startTime.setText("首班车时间：" + mData.get(position).get("startTime").toString());
	  holder.tv_endTime.setText("末班车时间：" + mData.get(position).get("endTime").toString());
	  holder.tv_company.setText("所属公司：" + mData.get(position).get("company").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_name;
    	TextView tv_startStation;
    	TextView tv_endStation;
    	TextView tv_startTime;
    	TextView tv_endTime;
    	TextView tv_company;
    }
} 
