package com.linkconverter.demo.validation;

import com.linkconverter.demo.util.RegexUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class DeepLinkValidator implements ConstraintValidator<DeepLinkConstraint, String> {

	@Override
	public void initialize(DeepLinkConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(value)
				&& (RegexUtils.DEEPLINK_PRODUCT_PATTERN.matcher(value).find()
				|| RegexUtils.DEEPLINK_SEARCH_PATTERN.matcher(value).find()
				|| RegexUtils.DEEPLINK_HOMEPAGE_PATTERN.matcher(value).find());
	}
}
