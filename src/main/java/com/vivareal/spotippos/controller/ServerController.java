package com.vivareal.spotippos.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;
import com.vivareal.spotippos.service.PropertyService;

@RestController
public class ServerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private PropertyService propertyService;
	
	@PostMapping(value = "/properties",
            consumes =  { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Property addProperty(@RequestBody Property property)  {
	    LOGGER.info("Method[addProperty] property[{}]", property);
        return propertyService.addProperty(property.withId(null));
    }

    @GetMapping(value = "/properties/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Property getProperty(@PathVariable("id") Long id)  {
        LOGGER.info("Method[getProperty] id[{}]", id);
        return propertyService.getProperty(id);
    }

    @GetMapping(value = "/properties",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public PropertiesResponse getProperties(Integer ax, Integer ay, Integer bx, Integer by)  {
        LOGGER.info("Method[getProperties] ax[{}] ay[{}] bx[{}] by[{}]", ax, ay, bx, by);
        return new PropertiesResponse(propertyService.getProperties(new Boundaries(ax, ay, bx, by)));
    }
    
    @PostMapping(value = "/loadProperties",
            consumes =  { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void addProperties(@RequestBody PropertiesRequest propertiesRequest)  {
        LOGGER.info("Method[addProperties] propertiesRequest[{}]", propertiesRequest);
    	propertiesRequest.getProperties().forEach(p->propertyService.addProperty(p));
    }

    @PostMapping(value = "/loadProvinces",
            consumes =  { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public void addProvinces(@RequestBody Map<String, Province> provinces)  {
        LOGGER.info("Method[addProvinces] provinces[{}]", provinces);
    	provinces.forEach((name, province)->propertyService.addProvince(province.withName(name)));
    }
    
}