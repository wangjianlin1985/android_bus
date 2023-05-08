package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.StationToStation;
import com.mobileclient.service.StationToStationService;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;
import com.mobileclient.domain.BusStation;
import com.mobileclient.service.BusStationService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class StationToStationDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_id;
	// 声明起始站控件
	private TextView TV_startStation;
	// 声明终到站控件
	private TextView TV_endStation;
	/* 要保存的站站查询信息 */
	StationToStation stationToStation = new StationToStation(); 
	/* 站站查询管理业务逻辑层 */
	private StationToStationService stationToStationService = new StationToStationService();
	private BusStationService busStationService = new BusStationService();
	private int id;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.stationtostation_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看站站查询详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_id = (TextView) findViewById(R.id.TV_id);
		TV_startStation = (TextView) findViewById(R.id.TV_startStation);
		TV_endStation = (TextView) findViewById(R.id.TV_endStation);
		Bundle extras = this.getIntent().getExtras();
		id = extras.getInt("id");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StationToStationDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    stationToStation = stationToStationService.GetStationToStation(id); 
		this.TV_id.setText(stationToStation.getId() + "");
		BusStation startStation = busStationService.GetBusStation(stationToStation.getStartStation());
		this.TV_startStation.setText(startStation.getStationName());
		BusStation endStation = busStationService.GetBusStation(stationToStation.getEndStation());
		this.TV_endStation.setText(endStation.getStationName());
	} 
}
