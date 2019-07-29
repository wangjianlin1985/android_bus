package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class BusStationEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_stationId;
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

	private int stationId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.busstation_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑站点信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_stationId = (TextView) findViewById(R.id.TV_stationId);
		ET_stationName = (EditText) findViewById(R.id.ET_stationName);
		ET_longitude = (EditText) findViewById(R.id.ET_longitude);
		ET_latitude = (EditText) findViewById(R.id.ET_latitude);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		stationId = extras.getInt("stationId");
		/*单击修改站点信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取站点名称*/ 
					if(ET_stationName.getText().toString().equals("")) {
						Toast.makeText(BusStationEditActivity.this, "站点名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_stationName.setFocusable(true);
						ET_stationName.requestFocus();
						return;	
					}
					busStation.setStationName(ET_stationName.getText().toString());
					/*验证获取经度*/ 
					if(ET_longitude.getText().toString().equals("")) {
						Toast.makeText(BusStationEditActivity.this, "经度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_longitude.setFocusable(true);
						ET_longitude.requestFocus();
						return;	
					}
					busStation.setLongitude(Float.parseFloat(ET_longitude.getText().toString()));
					/*验证获取纬度*/ 
					if(ET_latitude.getText().toString().equals("")) {
						Toast.makeText(BusStationEditActivity.this, "纬度输入不能为空!", Toast.LENGTH_LONG).show();
						ET_latitude.setFocusable(true);
						ET_latitude.requestFocus();
						return;	
					}
					busStation.setLatitude(Float.parseFloat(ET_latitude.getText().toString()));
					/*调用业务逻辑层上传站点信息信息*/
					BusStationEditActivity.this.setTitle("正在更新站点信息信息，稍等...");
					String result = busStationService.UpdateBusStation(busStation);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    busStation = busStationService.GetBusStation(stationId);
		this.TV_stationId.setText(stationId+"");
		this.ET_stationName.setText(busStation.getStationName());
		this.ET_longitude.setText(busStation.getLongitude() + "");
		this.ET_latitude.setText(busStation.getLatitude() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
