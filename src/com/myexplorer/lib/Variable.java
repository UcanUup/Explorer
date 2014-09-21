package com.myexplorer.lib;

import java.util.List;

public class Variable {
	// 历史记录的内容
	public static List<String> historyName;
	public static List<String> historySite;
	public static List<Integer> historyId;
	public static boolean[] historyChecks;
	public static String gotoUrl;
	
	// 收藏夹的内容
	public static List<String> favorName;
	public static List<String> favorSite;
	public static List<Integer> favorId;
	public static boolean[] favorChecks;
	public static String gotoUrl2;
	
	// 当前WebView访问的内容
	public static String title;
	public static String site;
}
