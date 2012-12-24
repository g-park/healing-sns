package kr.ac.seoultech.healing.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class StoreAppManager {


	private Context mContext;
	private File sdFile;
	private String sdDirString;
	private ArrayList<String> mFileList;
	private File LOG_FILE;
	private static final String APP_LOG_DIR = "/msns_app_log";
	//변경한 자료를 담는 곳.
	private ArrayList<Object> mObjects = new ArrayList<Object>();
	
	
	private static StoreAppManager storeAppManager; 
	
	private StoreAppManager(Context pContext) {
		
		mContext = pContext;
		mFileList = new ArrayList<String>();
		sdFile = Environment.getExternalStorageDirectory();
		sdDirString = sdFile.getAbsolutePath();
		LOG_FILE = new File(sdDirString+APP_LOG_DIR);
		
		if(LOG_FILE.exists()){
			
		}
		else if(Environment.getExternalStorageState().equals("mounted")){
			LOG_FILE.mkdir();
		}
		
		
		
	}
	
	/**인자로 로그에 기록할 것을 넣는 것.*/
	public void writeLog(String log){
		
		/**로그 되는 것을 구현.*/
		try{
			BufferedWriter file = new BufferedWriter(new FileWriter(sdDirString+APP_LOG_DIR+"/app_log.txt", true));// 출처:[JAVA] 파일 이어쓰기
			PrintWriter printWriter = new PrintWriter(file, true);
//		    file.write(log, 0, log.length());
//	        file.newLine();
			printWriter.println(log);
//	        Toast.makeText(mContext, "Save Success", Toast.LENGTH_SHORT).show();
			} catch(IOException e){
				Toast.makeText(mContext, "Save failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		
	}
	
	static public StoreAppManager getInstance(Context pContext){
		
		if(storeAppManager != null)
		{
			return storeAppManager;	
		}
		else
		{
			storeAppManager = new StoreAppManager(pContext);
			
			return storeAppManager;
		}
		
		
		
	}
}
