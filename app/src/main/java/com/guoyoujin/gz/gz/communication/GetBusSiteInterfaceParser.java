package com.guoyoujin.gz.gz.communication;

import com.google.gson.Gson;
import com.guoyoujin.gz.gz.vo.BusSiteListVo;


public class GetBusSiteInterfaceParser {
	public static BusSiteListVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			BusSiteListVo vo = gson.fromJson(str, BusSiteListVo.class);
			return vo;
		}
		return null;
	}
}
