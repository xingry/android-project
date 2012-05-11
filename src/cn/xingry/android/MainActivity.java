package cn.xingry.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.xingry.android.basic.ActivityLifecycle;
import cn.xingry.android.json.JSONActivity;
import cn.xingry.android.skin.SkinSettingManager;
import cn.xingry.android.updateManager.UpdateManager;
/**
 * 示例的主界面，此界面提供多个按钮，点击不同按钮跳转至相应界面。
 * @author xingruyi
 *
 */
public class MainActivity extends Activity {

	//主窗体的布局管理器
	private LinearLayout layout = null;
	
	//皮肤管理器
	private SkinSettingManager skinManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.layout = (LinearLayout) findViewById(R.id.mainLayout);		
		
		setTitle("xingry's android示例");
		
		//activity生命周期示例
		initActivityLifecycle();
		
		//json示例
		initJSONDemo();
		
		//版本更新示例
		initUpdateManager();
		
		//皮肤切换
		initSkin();
		
	}
	/**
	 * activity生命周期示例
	 */
	private void initActivityLifecycle() {
		Button btnLifecycle = new Button(this);
		btnLifecycle.setText("Activity生命周期演示");
		btnLifecycle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(view.getContext(),ActivityLifecycle.class);
				startActivity(intent);
			}
		});
		layout.addView(btnLifecycle);
	}
	/**
	 * JSON示例
	 */
	private void initJSONDemo() {
		//创建按钮
		Button btnJson = new Button(this);
		btnJson.setText("获取远程服务器JSON数据");
		btnJson.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(view.getContext(),JSONActivity.class);
				startActivity(intent);
			}
		});
		layout.addView(btnJson);
	}
	
	/**
	 * 更新管理示例
	 */
	private void initUpdateManager() {
		Button btnUpdate = new Button(this);
		btnUpdate.setText("检查更新");
		btnUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				UpdateManager manager = new UpdateManager(view.getContext());
				manager.checkUpdate();
			}
		});
		layout.addView(btnUpdate);
	}
	
	/**
	 * 切换皮肤 
	 */
	private void initSkin() {
		//初始化皮肤 
		skinManager = new SkinSettingManager(this);
		skinManager.initSkin();
		
		//添加按钮实现切换皮肤 
		Button btnSkin = new Button(this);
		btnSkin.setText("切换皮肤");
		this.layout.addView(btnSkin);
		btnSkin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				skinManager.toggleSkins();
			}
		});
	}
	
}
