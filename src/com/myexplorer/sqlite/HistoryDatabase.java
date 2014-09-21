package com.myexplorer.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.myexplorer.lib.DatabaseInfo;
import com.myexplorer.lib.Variable;

public class HistoryDatabase {
	
	private Context mContext;
	
	private final String TABLE_NAME = "History"; 
	private String sql;
	
	public HistoryDatabase(Context mContext) {
		this.mContext = mContext;
	}
	
	// ��ȡ�������ݿ����Ϣ
	public void read() {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "select * from " + TABLE_NAME;
		Cursor cursor = db.find(sql);
		
		Variable.historyId = new ArrayList<Integer>();
		Variable.historyName = new ArrayList<String>();
		Variable.historySite = new ArrayList<String>();
		while (cursor.moveToNext()) {
			Variable.historyId.add(0, cursor.getInt(cursor.getColumnIndex("id")));
			Variable.historyName.add(0, cursor.getString(cursor.getColumnIndex("webname")));
			Variable.historySite.add(0, cursor.getString(cursor.getColumnIndex("website")));
		}
		
		db.close();
	}
	
	// �����ݷ��뱾�����ݿ�
	public void write(String title, String url) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		// �������ݿ���
		sql = "insert into " + TABLE_NAME;
		sql += " " + "(webname, website) values ";
		sql += "('" + title + "',";
		sql += " '" + url + "')";
		db.insert(sql);		
		db.close();
	}
	
	// ɾ�����ݿ�
	public void delete(int id) {
		DatabaseOperation db = new DatabaseOperation(mContext);
		db.open(DatabaseInfo.DATABASE_NAME);
		
		sql = "delete from " + TABLE_NAME 
				+ " where id = " + id + "" ;
		db.delete(sql);
		db.close();
	}
	
}
