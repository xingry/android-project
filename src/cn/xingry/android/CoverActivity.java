package cn.xingry.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
/**
 * 启动界面
 * @author xingruyi
 *
 */
public class CoverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cover);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				Intent intent = new Intent(CoverActivity.this,MainActivity.class);
				startActivity(intent);
				CoverActivity.this.finish();
				
			}
		}, 2000	);
	}
}
