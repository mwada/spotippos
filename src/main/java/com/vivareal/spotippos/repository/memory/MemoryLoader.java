package com.vivareal.spotippos.repository.memory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;

public class MemoryLoader {

	private final static String PROVINCE_FILE = "provinces.json";

	private final static String PROPERTY_FILE = "properties.json";
	
	protected Map<String, Province> loadProvinceFile() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(PROVINCE_FILE).getFile());
		return new ObjectMapper().readValue(file, new TypeReference<Map<String, Province>>() {
		});
	}

	protected List<Property> loadPropertyFile() throws JsonParseException, JsonMappingException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(PROPERTY_FILE).getFile());
		Properties properties = new ObjectMapper().readValue(file, new TypeReference<Properties>() {
		});
		return properties.getProperties();
	}
	

	public static class Properties {
		private Integer totalProperties;

		private List<Property> properties;

		public Integer getTotalProperties() {
			return totalProperties;
		}

		public void setTotalProperties(Integer totalProperties) {
			this.totalProperties = totalProperties;
		}

		public List<Property> getProperties() {
			return properties;
		}
	}

}