package com.linkconverter.demo.util;

import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public final class EncoderUtils {

	/**
	 * This method decodes the given value first and then encode the decoded value in order to be sure the given value is not already encoded.
	 *
	 * @param value The value that will be encoded. (If it's not encoded already.)
	 * @return Returns encoded value.
	 */
	public String encode(String value) {
		try {
			String charset = StandardCharsets.UTF_8.toString();
			String decode = URLDecoder.decode(value, charset);
			return URLEncoder.encode(decode, charset);
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
}
