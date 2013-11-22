//package com.stylist.dbmanager;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import com.stylist.webservices.Constant;
//
//
//
//import android.content.Context;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DbHelper extends SQLiteOpenHelper {
//
//	private final Context myContext;
//	private static String DB_NAME = "stylist.sqlite";
//	private SQLiteDatabase myDataBase; 
//	public DbHelper(Context context) 
//	{
//		
//		super(context, DB_NAME, null, Constant.DATABASE_VERSION);
//		this.myContext = context;
//		boolean dbexist = checkDb();
//        if (dbexist) {
//            //System.out.println("Database exists");
//        	openDataBase(); 
//        } else {
//            System.out.println("Database doesn't exist");
//            try {
//				createDataBase();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        clsose();
//		
//	}
//	/*@Override
//	public void onOpen(SQLiteDatabase db) {
//		// TODO Auto-generated method stub
//		super.onOpen(db);
//		
//		if(!db.isReadOnly())
//			db.execSQL("PRAGMA foreign_keys=ON;");
//	}*/
//	
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		//db.execSQL(DBScript.CREATE_TABLE_SETTING); 
//		
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	public void createDataBase() throws IOException{
//
//		boolean dbExist = checkDataBase();
//		if(!dbExist)
//		{
//			//By calling this method and empty database will be created into the default system path
//			//of your application so we are gonna be able to overwrite that database with our database.
//			this.getWritableDatabase();
//
//			try {
//				copyDataBase(); 
//			} catch (IOException e) {
//				throw new Error("Error copying database");
//			}
//		}
//	}
//	
//	private boolean checkDataBase(){
//		SQLiteDatabase checkDB = null;
//		try{
//			String myPath = Constant.DATABASE_NAME;
//			try {
//				checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}catch(SQLiteException e){
//			//database does't exist yet.
//		}
//		if(checkDB != null){
//			checkDB.close();
//		}
//
//		return checkDB != null ? true : false;
//	}
//	
//	private boolean checkDb()
//	{
//		SQLiteDatabase checkDB = null;
//		String myPath = Constant.DATABASE_NAME;
//		File f=new File(myPath);
//		return f.exists();
//	}
//	
//	private void copyDataBase() throws IOException{
//
//		//Open your local db as the input stream
//		InputStream myInput = myContext.getAssets().open(DB_NAME);
//
//		// Path to the just created empty db
//		String outFileName = Constant.DATABASE_NAME;
//
//		//Open the empty db as the output stream
//		OutputStream myOutput = new FileOutputStream(outFileName);
//
//		//transfer bytes from the inputfile to the outputfile
//		byte[] buffer = new byte[1024];
//		int length;
//		while ((length = myInput.read(buffer))>0){
//			myOutput.write(buffer, 0, length);
//		}
//
//		//Close the streams
//		myOutput.flush();
//		myOutput.close();
//		myInput.close();
//
//	}
//	
//	public void openDataBase() throws SQLException{
//		//Open the database
//		String myPath = Constant.DATABASE_NAME;
//		try {
//			myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public synchronized void clsose() {
//		if(myDataBase != null)
//			myDataBase.close();
//		super.close();
//	}
//
//
//}
