package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class BusStationAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明站点名称输入框
	private EditText ET_stationName;
	// 声明经度输入框
	private EditText ET_longitude;
	// 声明纬度输入框
	private EditText ET_latitude;
	protected String carmera_path;
	/*要保存的站点信息信息*/
	BusStation busStation = new BusStation();
	/*站点信息管理业务逻辑层*/
	private BusStationService busStationService = new BusStationService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.busstation_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加站点信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_longitude = (EditText) findViewById(R.id.ET_longitude);
		ET_latitude = (EditText) findViewById(R.id.ET_latitude);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加站点信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取站点名称*/ 
					if(ET_stationName.getText().toString().equals("")) {
						Toast.makeText(BusStationAddActivity.this, "站点名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stationName.setFocusable(true);
						ET_stationName.requestFocus();
						return;	
					}
					busStation.setStationName(ET_stationName.getText().toString());
					/*验证获取经度*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(BusStationAddActivity.this, "经度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					busStation.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*验证获取纬度*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(BusStationAddActivity.this, "纬度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					busStation.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*调用业务逻辑层上传站点信息信息*/
					BusStationAddActivity.this.setTitle("正在上传站点信息信息，稍等...");
					String result = busStationService.AddBusStation(busStation);
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
