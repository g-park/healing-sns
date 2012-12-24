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
	//������ �ڷḦ ��� ��.
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
	
	/**���ڷ� �α׿� ����� ���� �ִ� ��.*/
	public void writeLog(String log){
		
		/**�α� �Ǵ� ���� ����.*/
		try{
			BufferedWriter file = new BufferedWriter(new FileWriter(sdDirString+APP_LOG_DIR+"/app_log.txt", true));// ��ó:[JAVA] ���� �̾��
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
