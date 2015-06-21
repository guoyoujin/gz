package com.guoyoujin.gz.gz.communication;

import com.google.gson.Gson;
import com.guoyoujin.gz.gz.vo.BusLineDetailListVo;

public class GetBusLineDetailInterfaceParser {
	public static BusLineDetailListVo parserData(String str) {
		if (str != null && !"".equals(str)) {
			Gson gson = new Gson();
			BusLineDetailListVo vo = gson.fromJson(str,
					BusLineDetailListVo.class);
			return vo;
		}
		return null;
	}
}
