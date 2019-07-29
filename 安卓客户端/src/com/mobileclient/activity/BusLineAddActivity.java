package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.BusLine;
import com.mobileclient.service.BusLineService;
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
public class BusLineAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明线路名称输入框
	private EditText ET_name;
	// 声明起点站下拉框
	private Spinner spinner_startStation;
	private ArrayAdapter<String> startStation_adapter;
	private static  String[] startStation_ShowText  = null;
	private List<BusStation> busStationList = null;
	/*起点站管理业务逻辑层*/
	private BusStationService busStationService = new BusStationService();
	// 声明终到站下拉框
	private Spinner spinner_endStation;
	private ArrayAdapter<String> endStation_adapter;
	private static  String[] endStation_ShowText  = null;
	// 声明首班车时间输入框
	private EditText ET_startTime;
	// 声明末班车时间输入框
	private EditText ET_endTime;
	// 声明所属公司输入框
	private EditText ET_company;
	// 声明途径站点输入框
	private EditText ET_tjzd;
	// 声明地图线路坐标输入框
	private EditText ET_polylinePoints;
	protected String carmera_path;
	/*要保存的公交线路信息*/
	BusLine busLine = new BusLine();
	/*公交线路管理业务逻辑层*/
	private BusLineService busLineService = new BusLineService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.busline_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加公交线路");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_name = (EditText) findViewById(R.id.ET_name);
		spinner_startStation = (Spinner) findViewById(R.id.Spinner_startStation);
		// 获取所有的起点站
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
				busLine.setStartStation(busStationList.get(arg2).getStationId()); 
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
				busLine.setEndStation(busStationList.get(arg2).getStationId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_endStation.setVisibility(View.VISIBLE);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_company = (EditText) findViewById(R.id.ET_company);
		ET_tjzd = (EditText) findViewById(R.id.ET_tjzd);
		ET_polylinePoints = (EditText) findViewById(R.id.ET_polylinePoints);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加公交线路按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取线路名称*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "线路名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					busLine.setName(ET_name.getText().toString());
					/*验证获取首班车时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "首班车时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					busLine.setStartTime(ET_startTime.getText().toString());
					/*验证获取末班车时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "末班车时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					busLine.setEndTime(ET_endTime.getText().toString());
					/*验证获取所属公司*/ 
					if(ET_company.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "所属公司输入不能为空!", Toast.LENGTH_LONG).show();
						ET_company.setFocusable(true);
						ET_company.requestFocus();
						return;	
					}
					busLine.setCompany(ET_company.getText().toString());
					/*验证获取途径站点*/ 
					if(ET_tjzd.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "途径站点输入不能为空!", Toast.LENGTH_LONG).show();
						ET_tjzd.setFocusable(true);
						ET_tjzd.requestFocus();
						return;	
					}
					busLine.setTjzd(ET_tjzd.getText().toString());
					/*验证获取地图线路坐标*/ 
					if(ET_polylinePoints.getText().toString().equals("")) {
						Toast.makeText(BusLineAddActivity.this, "地图线路坐标输入不能为空!", Toast.LENGTH_LONG).show();
						ET_polylinePoints.setFocusable(true);
						ET_polylinePoints.requestFocus();
						return;	
					}
					busLine.setPolylinePoints(ET_polylinePoints.getText().toString());
					/*调用业务逻辑层上传公交线路信息*/
					BusLineAddActivity.this.setTitle("正在上传公交线路信息，稍等...");
					String result = busLineService.AddBusLine(busLine);
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
