package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.StationToStation;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class StationToStationQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明起始站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	private List<BusStation> busStationList = null; 
	/*站点信息管理业务逻辑层*/
	private BusStationService busStationService = new BusStationService();
	// 声明终到站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	/*查询过滤条件保存到这个对象中*/
	private StationToStation queryConditionStationToStation = new StationToStation();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.stationtostation_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置站站查询查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的站点信息
		try {
			busStationList = busStationService.QueryBusStation(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int busStationCount = busStationList.size();
		startStation_ShowText = new String[busStationCount+1];
		startStation_ShowText[0] = "不限制";
		for(int i=1;i<=busStationCount;i++) { 
			startStation_ShowText[i] = busStationList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置起始站下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStationToStation.setStartStation(busStationList.get(arg2-1).getStationId()); 
				else
					queryConditionStationToStation.setStartStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		endStation_ShowText = new String[busStationCount+1];
		endStation_ShowText[0] = "不限制";
		for(int i=1;i<=busStationCount;i++) { 
			endStation_ShowText[i] = busStationList.get(i-1).getStationName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置终到站下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionStationToStation.setEndStation(busStationList.get(arg2-1).getStationId()); 
				else
					queryConditionStationToStation.setEndStation(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStationToStation", queryConditionStationToStation);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
