package cn.xingry.android.json;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.xingry.android.updateManager.UpdateManager;
import cn.xingry.android.util.JSONUtil;

import com.android.json.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JSONActivity extends Activity {
    private static final String API_URL="http://10.0.2.2:8080/android/json.jsp";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //绑定事件
        attachEvent();
    }
    
    private void attachEvent() {
    	final Button btnGetJSON = (Button)findViewById(R.id.btnGetJSON);
    	final TextView txt = (TextView)findViewById(R.id.txtContent);
    	final Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
    	
    	
    	//获取JSON
    	btnGetJSON.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//获取服务端内容
				try {
					// {"students" : [ { "name" : "xx" , "age" : 23 } ] }
					JSONObject json = JSONUtil.getJSON(API_URL);
					//获取students属性对应数组
					JSONArray array = json.getJSONArray("students");
					
					StringBuffer buf = new StringBuffer();
					//遍历获取数组每个元素 
					for(int i=0;i<array.length();i++) {
						JSONObject stu = array.getJSONObject(i);
						//获取name属性值
						String name = (String) stu.get("name");
						Integer age = (Integer)stu.getInt("age");
						buf.append(name + "-" + age);
						buf.append("\n");
					}			
					txt.setText("成功获取 " + array.length() + " 个数据，\n" + buf.toString());
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
    	
    	
    	//检查更新
    	btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				UpdateManager manager = new UpdateManager(view.getContext());
				manager.checkUpdate();
				
			}
		});
    	
    	
    }
}