package com.CulturaEnLasCalles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

	public SQLHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createDB(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int prevVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Profiles");
		createDB(db);

	}
	
	private void createDB(SQLiteDatabase db){
		db.execSQL("CREATE TABLE Profiles (Category TEXT,Neighborhood TEXT)");
	}

}
