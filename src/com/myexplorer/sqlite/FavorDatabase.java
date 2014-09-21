package com.myexplorer.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.myexplorer.lib.DatabaseInfo;
import com.myexplorer.lib.Variable;

public class FavorDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "Favor"; 
	private String sql;
	
	public FavorDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// 读取本地数据库的信息
	public void read() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "select * from " + TABLE_NAME;
		Cursor cursor = db.find(sql);
		
		Variable.favorId = new ArrayList<Integer>();
		Variable.favorName = new ArrayList<String>();
		Variable.favorSite = new ArrayList<String>();
		while (cursor.moveToNext()) {
			Variable.favorId.add(0, cursor.getInt(cursor.getColumnIndex("id")));
			Variable.favorName.add(0, cursor.getString(cursor.getColumnIndex("webname")));
			Variable.favorSite.add(0, cursor.getString(cursor.getColumnIndex("website")));
		}
		
		db.close();
	}
	
	// 将数据放入本地数据库
	public void write(String title, String url) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		// 插入数据库中
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(webname, website) values ";
		sql += "('" + title + "',";
		sql += " '" + url + "')";
		db.insert(sql);		
		db.close();
	}
	
	// 删除数据库
	public void delete(int id) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "delete from " + TABLE_NAME 
				+ " where id = " + id + "" ;
		db.delete(sql);
		db.close();
	}
	
}
