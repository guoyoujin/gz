package com.guoyoujin.gz.gz.communication;

import com.google.gson.Gson;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;


public class GetBeautyInterfaceParser {
	public static BeautyMainVo parserData(String str){
		if(str!=null&&!"".equals(str)){
			Gson gson = new Gson();
			BeautyMainVo vo = gson.fromJson(str, BeautyMainVo.class);
			return vo;
		}
		return null;
	}
}
