package com.guoyoujin.gz.gz.communication;

import com.google.gson.Gson;
import com.guoyoujin.gz.gz.vo.BusSiteDetailListVo;


public class GetBusSiteDetailInterfaceParser {
	public static BusSiteDetailListVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			BusSiteDetailListVo vo = gson.fromJson(str, BusSiteDetailListVo.class);
			return vo;
		}
		return null;
	}
}
