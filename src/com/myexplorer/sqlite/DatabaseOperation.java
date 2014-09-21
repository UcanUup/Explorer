package com.myexplorer.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseOperation {
	
	private Context mContext;
	
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	
	public DatabaseOperation(Context mContext) {
		this.mContext = mContext;
	}
	
	// 打开数据库
	public void open(String dbName) {
		databaseHelper = new DatabaseHelper(mContext, dbName);
	}
	
	// 关闭数据库
	public void close() {
		if (db != null)
			db.close();
		
		if (databaseHelper != null)
			databaseHelper.close();
	}
	
	// 插入数据
	public void insert(String sql) {
		db = databaseHelper.getWritableDatabase();
		db.execSQL(sql);
	}
	
	// 更新数据
	public void update(String sql) {
		db = databaseHelper.getWritableDatabase();
		db.execSQL(sql);
	}
	
	// 删除数据
	public void delete(String sql) {
		db = databaseHelper.getWritableDatabase();
		db.execSQL(sql);
	}
	
	// 查找数据
	public Cursor find(String sql) {
		db = databaseHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		return cursor;
	}
	
}
