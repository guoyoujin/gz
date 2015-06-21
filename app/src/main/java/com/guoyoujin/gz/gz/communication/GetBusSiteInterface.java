package com.guoyoujin.gz.gz.communication;


import com.guoyoujin.gz.gz.exception.ErrorCodeException;
import com.guoyoujin.gz.gz.global.Globe;
import com.guoyoujin.gz.gz.net.ParcelMap;
import com.guoyoujin.gz.gz.net.UnEncryptionHttpConnect;
import com.guoyoujin.gz.gz.net.UnEncryptionRequestParcelable;
import com.guoyoujin.gz.gz.vo.BusSiteListVo;

public class GetBusSiteInterface {

	public static BusSiteListVo getNetData(String searchWords) throws Exception {

		UnEncryptionRequestParcelable requestParam = new UnEncryptionRequestParcelable(
				Globe.BUS_SITE + searchWords);
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
							BusSiteListVo vo = GetBusSiteInterfaceParser
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

}
