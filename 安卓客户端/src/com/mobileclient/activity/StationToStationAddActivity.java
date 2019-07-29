package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.StationToStation;
import com.mobileclient.service.StationToStationService;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class StationToStationAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明起始站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	private List<BusStation> busStationList = null;
	/*起始站管理业务逻辑层*/
	private BusStationService busStationService = new BusStationService();
	// 声明终到站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	protected String carmera_path;
	/*要保存的站站查询信息*/
	StationToStation stationToStation = new StationToStation();
	/*站站查询管理业务逻辑层*/
	private StationToStationService stationToStationService = new StationToStationService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.stationtostation_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加站站查询");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的起始站
		try {
			busStationList = busStationService.QueryBusStation(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int busStationCount = busStationList.size();
		startStation_ShowText = new String[busStationCount];
		for(int i=0;i<busStationCount;i++) { 
			startStation_ShowText[i] = busStationList.get(i).getStationName();
		}
		// 将可选内容与ArrayAdapter连接起来
		startStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, startStation_ShowText);
		// 设置下拉列表的风格
		startStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_startStation.setAdapter(startStation_adapter);
		// 添加事件Spinner事件监听
		spinner_startStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				stationToStation.setStartStation(busStationList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_startStation.setVisibility(View.VISIBLE);
		spinner_endStation = (Spinner) findViewById(R.id.Spinner_endStation);
		endStation_ShowText = new String[busStationCount];
		for(int i=0;i<busStationCount;i++) { 
			endStation_ShowText[i] = busStationList.get(i).getStationName();
		}
		// 将可选内容与ArrayAdapter连接起来
		endStation_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, endStation_ShowText);
		// 设置下拉列表的风格
		endStation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_endStation.setAdapter(endStation_adapter);
		// 添加事件Spinner事件监听
		spinner_endStation.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				stationToStation.setEndStation(busStationList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加站站查询按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*调用业务逻辑层上传站站查询信息*/
					StationToStationAddActivity.this.setTitle("正在上传站站查询信息，稍等...");
					String result = stationToStationService.AddStationToStation(stationToStation);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
