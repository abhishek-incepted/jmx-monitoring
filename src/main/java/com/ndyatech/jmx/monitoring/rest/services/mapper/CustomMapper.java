package com.ndyatech.jmx.monitoring.rest.services.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by eclectic on 26-06-2016.
 */
public class CustomMapper extends ObjectMapper {

	public CustomMapper() {
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
