package cn.xingry.android.updateManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.json.R;

/**
 * 更新管理器
 * 
 * @author xingruyi
 * 
 */
public class UpdateManager {
	private static final String TAG = UpdateManager.class.getSimpleName();

	// 提示信息
	private static final String UPDATE_MSG = "软件有更新，快下载吧.";
	// 远程下载地址
	private static final String DOWNLOAD_URL = "http://softfile.3g.qq.com:8080/msoft/179/24659/43549/qq_hd_mini_1.4.apk";
	// 本地存放路径
	private static final String SAVE_PATH = "/sdcard/jsondemo/";
	// 本地存放文件名
	private static final String SAVE_FILE_NAME = SAVE_PATH	+ "UpdateRelease.apk";

	// 下载状态
	private static final int DOWNLOAD_UPDATE = 1;// 下载中
	private static final int DOWNLOAD_OVER = 2;// 下载完成

	private Context context; // 上下文对象

	private Dialog noticeDialog; // 检查更新对话框
	private Dialog downloadDialog ; //下载更新对话框 
	
	private ProgressBar progressBar; // 下载进度条
	private TextView progressDes ; //下载进度描述

	private int progress; // 下载进度
	private Thread downloadThread; // 下载线程

	private boolean interceptFlag = false; // 中断标志

	//handler处理器，用于接收子线程消息，更新视图组件。 
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_UPDATE:
				progressBar.setProgress(progress);
				progressDes.setText("下载中..." + progress+"%");
				break;
			case DOWNLOAD_OVER:
				installAPK();
				break;

			};
		};

	};
	
	public UpdateManager(Context ctx ) {
		this.context = ctx;
	}

	
	/**
	 * 供外部调用的方法
	 */
	public void checkUpdate() {
		showNoticeDialog();
	}
	
	/**
	 * 显示更新对话框 
	 */
	private void showNoticeDialog() {
		AlertDialog.Builder builder =new Builder(context);
		builder.setTitle("软件版本更新");
		builder.setMessage(UPDATE_MSG);
		builder.setPositiveButton("下载"	, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说"	, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		this.noticeDialog = builder.create();
		noticeDialog.show();
	}
	
	/**
	 * 显示下载对话框 
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder =new Builder(context);
		builder.setTitle("软件版本更新");
		
		final LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.progress, null);
		this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
		this.progressDes = (TextView)view.findViewById(R.id.progress_des);
		
		builder.setView(view);
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;				
			}
		});
		
		downloadDialog = builder.create();
		downloadDialog.show();
		
		downloadAPK();
		
	}
	
	/**
	 * 创建线程，用于下载远程APK
	 */
	private Runnable downloadRunable = new Runnable() {
		public void run() {
			try {
				URL url = new URL(DOWNLOAD_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				Log.d(TAG,"file length : " + length);
				InputStream in = conn.getInputStream();
				
				File file =new File(SAVE_PATH);
				if(!file.exists()) {
					file.mkdir();
				}
				
				File apkFile = new File(SAVE_FILE_NAME);
				FileOutputStream out = new FileOutputStream(apkFile);
				
				byte[] buf = new byte[4096];
				int count = 0 ; //记录下载总字节数
				
				do {
					int readNum = in.read(buf);
					count += readNum;
					//计算下载百分比
					progress = (int)(((float)count/length)*100);
					Log.d(TAG,"progress : "  +progress);
					
					//以下代码为，判断当前下载状态，通过handler发送消息至主线程,由主线程更新视图组件。
					//这是因为视图组件不是线程安全的，因此不能在多个线程下进行更新，必须由主线程更新。因此子线程必须发消息到主线程。
					if(readNum<=0) {
						//下载完成
						handler.sendEmptyMessage(DOWNLOAD_OVER);
						break;						
					} else {
						handler.sendEmptyMessage(DOWNLOAD_UPDATE);
					}
					out.write(buf,0,readNum);
					
				}while(!interceptFlag);
				
				out.close();
				in.close();
				
			}catch(Exception ex) {
				ex.printStackTrace();
				Log.e(TAG,"Download ERROR." + ex.getMessage());
			}
		}
	};
	
	/**
	 * 下载远程 APK文件
	 */
	private void downloadAPK() {
		new Thread(downloadRunable).start();
	}
	
	
	
	/**
	 * 安装APK
	 */
	protected void installAPK() {
		File file = new File(SAVE_FILE_NAME);
		if(!file.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://"+file.toString()), "application/vnd.android.package-archive");
		this.context.startActivity(intent);
		
	}

}
