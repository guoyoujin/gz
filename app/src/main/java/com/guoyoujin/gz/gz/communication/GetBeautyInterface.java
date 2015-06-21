package com.guoyoujin.gz.gz.communication;

import com.guoyoujin.gz.gz.exception.ErrorCodeException;
import com.guoyoujin.gz.gz.global.Globe;
import com.guoyoujin.gz.gz.net.ParcelMap;
import com.guoyoujin.gz.gz.net.UnEncryptionHttpConnect;
import com.guoyoujin.gz.gz.net.UnEncryptionRequestParcelable;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class GetBeautyInterface {
	/*
	 * col 总标签
	 * tag 子标签
	 * pn 从那一条数据开始拿
	 * rn 拿多少条
	 */
	public static BeautyMainVo getNetData(String col, String tag, int pn, int rn)
			throws Exception {

		UnEncryptionRequestParcelable requestParam = new UnEncryptionRequestParcelable(
				Globe.BEAUTY_BASE_URL + "col=" + EncodeString(col) + "&tag="
						+ EncodeString(tag) + "&sort=0&tag3=&pn=" + pn + "&rn="
						+ rn + "&p=channel&from=1");
		UnEncryptionHttpConnect dhc = new UnEncryptionHttpConnect(requestParam);

		for (int i = 0; i < 3; i++) {
			try {
				dhc.connect();
				String responseBody = dhc.getResponseBody();

				ParcelMap respHeaders = dhc.getHeaders();
				if (respHeaders != null) {
					if (Globe.RESPONSE_HEADER_RESULT_ERROR.equals(respHeaders
							.get(Globe.RESPONSE_HEADER_RESULT))) {
						String errorCode = respHeaders
								.get(Globe.RESPONSE_HEADER_ERROR_CODE);
						if ("404-1".equals(errorCode)) {
							// 缺少必要参数
							continue;
						} else {
							// 无结果
						}
					} else {
						if (responseBody != null) {
							BeautyMainVo vo = GetBeautyInterfaceParser
									.parserData(responseBody);
							return vo;
						}
					}
				}
			} catch (ErrorCodeException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				dhc = null;
			}
		}
		return null;
	}

	public static String EncodeString(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}
}
