package com.myexplorer.sqlite;

import com.myexplorer.lib.DatabaseInfo;

import android.content.Context;
import android.database.Cursor;

public class HistoryDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "History"; 
	private String sql;
	
	// ������䣬ÿ�ζ���ʹ��
	private String createSql = "create table " + TABLE_NAME
			+ " (webname varchar(100), website varchar(100))";
	
	public HistoryDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// ��ȡ�������ݿ����Ϣ
	public void read() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "select * from " + TABLE_NAME;
		Cursor cursor = db.find(sql);
		
		while (cursor.moveToNext()) {
//			UserInfo.email = cursor.getString(cursor.getColumnIndex("webname"));
//			UserInfo.userName = cursor.getString(cursor.getColumnIndex("website"));
		}
		
		db.close();
	}
	
	// �����ݷ��뱾�����ݿ�
	public void write(String title, String url) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		// �������ݿ���
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(webname, website) values ";
		sql += "('" + title + "',";
		sql += " '" + url + "')";
		db.insert(sql);
		
		db.close();
	}
	
	// ɾ�����ݿ�
	public void delete() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		sql = createSql;
		db.open(DatabaseInfo.DATABASE_NAME, sql);
		
		sql = "delete from " + TABLE_NAME;
		db.delete(sql);
		
		db.close();
	}
	
}
