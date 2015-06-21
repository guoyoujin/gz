package com.guoyoujin.gz.gz.communication;

import com.guoyoujin.gz.gz.exception.ErrorCodeException;
import com.guoyoujin.gz.gz.global.Globe;
import com.guoyoujin.gz.gz.net.ParcelMap;
import com.guoyoujin.gz.gz.net.UnEncryptionHttpConnect;
import com.guoyoujin.gz.gz.net.UnEncryptionRequestParcelable;
import com.guoyoujin.gz.gz.vo.ChatMessageVO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class GetChatInterface {
	/*
	 * 
	 */
	public static ChatMessageVO getNetData(String info) throws Exception {

		UnEncryptionRequestParcelable requestParam = new UnEncryptionRequestParcelable(
				"http://www.tuling123.com/openapi/api?key=bee17c41dd5523c85bee1fe7027be0ee&info="
						+ EncodeString(info));
		
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
							System.out.println("chat ==" + responseBody);
							ChatMessageVO vo = GetChatInterfaceParser
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
