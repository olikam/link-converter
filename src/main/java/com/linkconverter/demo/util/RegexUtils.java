package com.linkconverter.demo.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public final class RegexUtils {
	private final String WEB_DOMAIN_REGEX = "https://www\\.trendyol\\.com";

	public final Pattern WEB_HOMEPAGE_PATTERN = Pattern
			.compile(WEB_DOMAIN_REGEX + "/Hesabim/(Favoriler|#/Siparislerim)");

	public final Pattern WEB_PRODUCT_PATTERN = Pattern
			.compile(
					WEB_DOMAIN_REGEX
							+ "/[\\w-]+/[\\w-]+-p-(?<contentId>\\d+)((\\?boutiqueId=(?<boutiqueId>\\d+))?([?&]merchantId=(?<merchantId>\\d+))?)?");

	public final Pattern WEB_SEARCH_PATTERN = Pattern
			.compile(
					WEB_DOMAIN_REGEX
							+ "/tum--urunler\\?q=(?<query>.+)");

	private final String DEEPLINK_DOMAIN_REGEX = "ty://\\?Page=";

	public final Pattern DEEPLINK_HOMEPAGE_PATTERN = Pattern.compile(DEEPLINK_DOMAIN_REGEX + "(Favorites|Orders)");

	public final Pattern DEEPLINK_PRODUCT_PATTERN = Pattern
			.compile(DEEPLINK_DOMAIN_REGEX
					+ "Product&ContentId=(?<contentId>\\d+)(&CampaignId=(?<campaignId>\\d+)|&MerchantId=(?<merchantId>\\d+)){0,2}");

	public final Pattern DEEPLINK_SEARCH_PATTERN = Pattern.compile(DEEPLINK_DOMAIN_REGEX + "Search&Query=(?<query>.+)");

}
