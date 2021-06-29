package com.linkconverter.demo.model;

import com.linkconverter.demo.validation.WebUrlConstraint;
import lombok.Data;

@Data
public class DeepLinkRequest {

	@WebUrlConstraint
	private String webUrl;
}
