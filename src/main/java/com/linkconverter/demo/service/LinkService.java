package com.linkconverter.demo.service;

public interface LinkService {
	String convertToDeepLink(String url);
	String convertToWebUrl(String url);
}
