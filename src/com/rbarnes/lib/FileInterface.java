/*
 * project	Java2Week2
 * 
 * package	com.rbarnes.lib
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 12, 2013
 */
package com.rbarnes.lib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import android.content.Context;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class FileInterface.
 */
public class FileInterface {
	
	
	/**
	 * Store string file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param content the content
	 * @param external the external
	 * @return the boolean
	 */
	@SuppressWarnings("resource")
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos =  new FileOutputStream(file);
			}else{
				fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			fos.write(content.getBytes());
			fos.close();
			
			
		}catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
	/**
	 * Store object file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param content the content
	 * @param external the external
	 * @return the boolean
	 */
	@SuppressWarnings("resource")
	public static Boolean storeObjectFile(Context context, String filename, Object content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			ObjectOutputStream oos;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos =  new FileOutputStream(file);
			}else{
				
				fos =  context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			oos =  new ObjectOutputStream(fos);
			oos.writeObject(content);
			oos.close();
			fos.close();
		}catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
			return true;
		}
	
	/**
	 * Read string file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param external the external
	 * @return the string
	 */
	@SuppressWarnings("resource")
	public static String readStringFile(Context context, String filename, Boolean external){
		String content = "";
		try{
			File file;
			FileInputStream fis;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fis =  new FileInputStream(file);
			}else{
				file = new File(filename);
				fis =  context.openFileInput(filename);
			}
			BufferedInputStream bin = new BufferedInputStream(fis);
			
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while((bytesRead = bin.read(contentBytes)) != -1){
				
				content = new String(contentBytes,0,bytesRead);
				contentBuffer.append(content);
				
			}
			content = contentBuffer.toString();
			fis.close();
			
			
		}catch(FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND " + filename);
			return null;
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		
		return content;
	}
	
	/**
	 * Read object file.
	 *
	 * @param context the context
	 * @param filename the filename
	 * @param external the external
	 * @return the object
	 */
	@SuppressWarnings("resource")
	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try{
			File file;
			FileInputStream fis;
			//Check if external storage flag is set
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fis =  new FileInputStream(file);
			}else{
				file = new File(filename);
				fis =  context.openFileInput(filename);
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			try{
				content = (Object)ois.readObject();
			}catch(ClassNotFoundException e){
				Log.e("READ ERROR","INVALD JAVA OBJECT FILE");
			}
			ois.close();
			fis.close();
			
			
		}catch(FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND " + filename);
			return null;
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		
		return content;
	}
}

