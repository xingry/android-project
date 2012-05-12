package cn.xingry.android.basic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
/**
 * Activity生命周期示例
 * @author xingruyi
 *
 */
public class ActivityLifecycle extends Activity {

	private static final String TAG = ActivityLifecycle.class.getSimpleName();

	private LinearLayout layout = null;
	private EditText txt = null;
	private String content; // 用于缓存文本框中内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "--onCreate--");

		this.layout = new LinearLayout(this);
		this.layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(this.layout);

		init();// 初始化文本框 和 按钮

	}

	private void init() {
		txt = new EditText(this);
		txt.setText("此文本中按home键再次返回后仍然保留");

		Button btn = new Button(this);
		btn.setText("返回");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();// 关闭当前窗体
			}
		});
		layout.addView(btn);
		layout.addView(txt);

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "--onStart--");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "--onStop--");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "--onPause--");
		// 当按HOME键时，在onPause方法里，将文本框中值缓存起来
		this.content = txt.getText().toString();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "--onRestart--");
		txt.setText(content); // 当按HOME键时，然后再次启动应用时，要恢复先前状态
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "--onResume--");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "--onDestroy--");
	}

}
