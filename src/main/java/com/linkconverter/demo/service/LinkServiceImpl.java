package com.linkconverter.demo.service;

import com.linkconverter.demo.entity.Link;
import com.linkconverter.demo.repository.LinkRepository;
import com.linkconverter.demo.util.EncoderUtils;
import com.linkconverter.demo.util.HashUtils;
import com.linkconverter.demo.util.RegexUtils;
import com.linkconverter.demo.util.UrlUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
	private final Map<String, String> cache = new ConcurrentHashMap<>();
	private static final String DEEP_LINK_TEMPLATE = "ty://?Page=";
	private final LinkRepository linkRepository;

	/**
	 * @param webUrl The web url which will be converted to a deep link.
	 * @return Deep link representation of the given webUrl.
	 */
	@Override
	public String convertToDeepLink(String webUrl) {
		String hash = HashUtils.getHash(webUrl);
		String deepLink = cache.computeIfAbsent(hash, k -> calculateDeepLink(webUrl));
		String webUrlToBePersisted = RegexUtils.WEB_SEARCH_PATTERN.matcher(webUrl).find() ? getWebUrlWithEncodedQueryString(webUrl) : webUrl;
		return insertDB(webUrlToBePersisted, deepLink);
	}

	private String calculateDeepLink(String webUrl) {
		Matcher matcher = RegexUtils.WEB_PRODUCT_PATTERN.matcher(webUrl);
		if (matcher.find()) {
			return calculateProductDeepLink(matcher);
		}
		matcher = RegexUtils.WEB_SEARCH_PATTERN.matcher(webUrl);
		if (matcher.find()) {
			return calculateSearchDeepLink(matcher);
		}
		matcher = RegexUtils.WEB_HOMEPAGE_PATTERN.matcher(webUrl);
		if (matcher.find()) {
			return UrlUtil.DEEP_LINK_HOME;
		}
		//Cannot happen
		throw new AssertionError();
	}

	private String calculateProductDeepLink(Matcher matcher) {
		StringBuilder deepLink = new StringBuilder(DEEP_LINK_TEMPLATE);
		deepLink.append("Product");
		deepLink.append("&ContentId=")
				.append(matcher.group("contentId"));
		String boutiqueId = matcher.group("boutiqueId");
		if (StringUtils.isNotEmpty(boutiqueId)) {
			deepLink.append("&CampaignId=")
					.append(boutiqueId);
		}
		String merchantId = matcher.group("merchantId");
		if (StringUtils.isNotEmpty(merchantId)) {
			deepLink.append("&MerchantId=")
					.append(merchantId);
		}
		return deepLink.toString();
	}

	private String calculateSearchDeepLink(Matcher matcher) {
		return DEEP_LINK_TEMPLATE + "Search&"
				+ "Query="
				+ EncoderUtils.encode(matcher.group("query"));
	}

	private String getWebUrlWithEncodedQueryString(String url) {
		String[] urlParts = url.split("\\?q=");
		String path = urlParts[0].trim();
		String query = urlParts[1].trim();
		return path + "?q=" + EncoderUtils.encode(query);
	}

	/**
	 * @param deepLink The deep link which will be converted to a web url.
	 * @return Web url representation of the given deepLink.
	 */
	@Override
	public String convertToWebUrl(String deepLink) {
		String hash = HashUtils.getHash(deepLink);
		String webUrl = cache.computeIfAbsent(hash, k -> calculateWebUrl(deepLink));
		String persistedDeepLink =
				RegexUtils.DEEPLINK_SEARCH_PATTERN.matcher(deepLink).find() ? getDeepLinkWithEncodedQueryString(deepLink) : deepLink;
		return insertDB(persistedDeepLink, webUrl);
	}

	private String calculateWebUrl(String deepLink) {
		Matcher matcher = RegexUtils.DEEPLINK_PRODUCT_PATTERN.matcher(deepLink);
		if (matcher.find()) {
			return calculateProductWebUrl(matcher);
		}

		matcher = RegexUtils.DEEPLINK_SEARCH_PATTERN.matcher(deepLink);
		if (matcher.find()) {
			return calculateSearchWebUrl(matcher);
		}

		matcher = RegexUtils.DEEPLINK_HOMEPAGE_PATTERN.matcher(deepLink);
		if (matcher.find()) {
			return UrlUtil.WEB_URL_HOME;
		}
		//Cannot happen
		throw new AssertionError();
	}

	private String calculateProductWebUrl(Matcher matcher) {
		StringBuilder webUrl = new StringBuilder(UrlUtil.WEB_URL_HOME);
		webUrl.append("/brand");
		webUrl.append("/name-p-")
				.append(matcher.group("contentId"));
		String campaignId = matcher.group("campaignId");
		if (StringUtils.isNotEmpty(campaignId)) {
			webUrl.append("?boutiqueId=")
					.append(campaignId);
		}
		String merchantId = matcher.group("merchantId");
		if (StringUtils.isNotEmpty(merchantId)) {
			String querySeparator = StringUtils.isNotEmpty(campaignId) ? "&" : "?";
			webUrl.append(querySeparator)
					.append("merchantId=")
					.append(merchantId);
		}
		return webUrl.toString();
	}

	private String calculateSearchWebUrl(Matcher matcher) {
		return UrlUtil.WEB_URL_HOME + "/tum--urunler?q=" + EncoderUtils.encode(matcher.group("query"));
	}

	private String getDeepLinkWithEncodedQueryString(String deepLink) {
		String[] urlParts = deepLink.split("Query=");
		String path = urlParts[0].trim();
		String query = urlParts[1].trim();
		return path + "Query=" + EncoderUtils.encode(query);
	}

	private String insertDB(String request, String response) {
		Link link = new Link(request, response);
		return linkRepository.save(link).getResponse();
	}
}
