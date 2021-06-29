package com.linkconverter.demo.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;

@UtilityClass
public final class HashUtils {
	public String getHash(String url) {
		return DigestUtils.sha256Hex(url);
	}
}
