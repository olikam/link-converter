package com.linkconverter.demo.model;

import com.linkconverter.demo.validation.DeepLinkConstraint;
import lombok.Data;

@Data
public class WebUrlRequest {

	@DeepLinkConstraint
	private String deepLink;
}
