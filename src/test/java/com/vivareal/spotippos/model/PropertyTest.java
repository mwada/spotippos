package com.vivareal.spotippos.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PropertyTest {

	private JacksonTester<Property> json;

	@Before
	public void before() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void testSerializeProperty() throws IOException {
		Property property = createProperty();
		assertThat(json.write(property)).isEqualToJson("/property.json");
	}

	@Test
	public void testDeserializeProperty() throws IOException {
		Property property = createProperty();
		assertThat(json.read("/property.json").getObject(), new ReflectionEquals(property));
	}

	private Property createProperty() {
		return new Property().withId(1L).withTitle("Imóvel código 1, com 3 quartos e 2 banheiros.").withPrice(643000)
				.withDescription(
						"Laboris quis quis elit commodo eiusmod qui exercitation. In laborum fugiat quis minim occaecat id.")
				.withX(1257).withY(928).withBeds(3).withBaths(2).withSquareMeters(61)
				.withProvinces(new HashSet<>(Arrays.asList("Jaby")));
	}
	
}