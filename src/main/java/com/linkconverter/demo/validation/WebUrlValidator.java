package com.linkconverter.demo.validation;

import com.linkconverter.demo.util.RegexUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class WebUrlValidator implements ConstraintValidator<WebUrlConstraint, String> {

	@Override
	public void initialize(WebUrlConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(value)
				&& (RegexUtils.WEB_PRODUCT_PATTERN.matcher(value).find()
				|| RegexUtils.WEB_SEARCH_PATTERN.matcher(value).find()
				|| RegexUtils.WEB_HOMEPAGE_PATTERN.matcher(value).find());
	}
}
