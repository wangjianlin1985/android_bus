package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.BusLine;
import com.mobileclient.service.BusLineService;
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
public class BusLineDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_lineId;
	// 声明线路名称控件
	private TextView TV_name;
	// 声明起点站控件
	private TextView TV_startStation;
	// 声明终到站控件
	private TextView TV_endStation;
	// 声明首班车时间控件
	private TextView TV_startTime;
	// 声明末班车时间控件
	private TextView TV_endTime;
	// 声明所属公司控件
	private TextView TV_company;
	// 声明途径站点控件
	private TextView TV_tjzd;
	// 声明地图线路坐标控件
	private TextView TV_polylinePoints;
	/* 要保存的公交线路信息 */
	BusLine busLine = new BusLine(); 
	/* 公交线路管理业务逻辑层 */
	private BusLineService busLineService = new BusLineService();
	private BusStationService busStationService = new BusStationService();
	private int lineId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.busline_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看公交线路详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_lineId = (TextView) findViewById(R.id.TV_lineId);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_startStation = (TextView) findViewById(R.id.TV_startStation);
		TV_endStation = (TextView) findViewById(R.id.TV_endStation);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_company = (TextView) findViewById(R.id.TV_company);
		TV_tjzd = (TextView) findViewById(R.id.TV_tjzd);
		TV_polylinePoints = (TextView) findViewById(R.id.TV_polylinePoints);
		Bundle extras = this.getIntent().getExtras();
		lineId = extras.getInt("lineId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BusLineDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    busLine = busLineService.GetBusLine(lineId); 
		this.TV_lineId.setText(busLine.getLineId() + "");
		this.TV_name.setText(busLine.getName());
		BusStation startStation = busStationService.GetBusStation(busLine.getStartStation());
		this.TV_startStation.setText(startStation.getStationName());
		BusStation endStation = busStationService.GetBusStation(busLine.getEndStation());
		this.TV_endStation.setText(endStation.getStationName());
		this.TV_startTime.setText(busLine.getStartTime());
		this.TV_endTime.setText(busLine.getEndTime());
		this.TV_company.setText(busLine.getCompany());
		this.TV_tjzd.setText(busLine.getTjzd());
		this.TV_polylinePoints.setText(busLine.getPolylinePoints());
	} 
}
