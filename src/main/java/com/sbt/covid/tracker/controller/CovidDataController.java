package com.sbt.covid.tracker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sbt.covid.tracker.models.CovidData;
import com.sbt.covid.tracker.service.CovidDataService;

@RestController
@RequestMapping("/api/v1/covid/india")
public class CovidDataController {

	@Autowired
	private CovidDataService dataService;

	@GetMapping("/allstate")
	public MappingJacksonValue getAllStateCovidDatas(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfAllStateData());
		return getValue(covidData, Arrays.asList("covidData", "state", "country", "totalConfirmed", "totalDeaths",
				"totalReovered", "totalActive"));
	}

	@GetMapping("/allstate/totalconfirmed")
	public MappingJacksonValue getAllStateConfirmedCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		covidData.setTotalConfirmed(dataService.getTotalConfirmed());
		return getValue(covidData, "totalConfirmed");
	}

	@GetMapping("/allstate/totaldeaths")
	public MappingJacksonValue getAllStateDeathCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		covidData.setTotalDeaths(dataService.getTotalDeaths());
		return getValue(covidData, "totalDeaths");
	}

	@GetMapping("/allstate/totalrecovered")
	public MappingJacksonValue getAllStateRecoveredCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		covidData.setTotalReovered(dataService.getTotalReovered());
		return getValue(covidData, "totalReovered");
	}

	@GetMapping("/allstate/totalactive")
	public MappingJacksonValue getAllStateActiveCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		covidData.setTotalActive(dataService.getTotalActive());
		return getValue(covidData, "totalActive");
	}

	public MappingJacksonValue getValue(CovidData covidData, String includeField) {
		MappingJacksonValue wrapper = new MappingJacksonValue(covidData);
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("covidfilter",
				SimpleBeanPropertyFilter.filterOutAllExcept(includeField));
		wrapper.setFilters(filterProvider);

		return wrapper;
	}

	private MappingJacksonValue getValue(CovidData covidData, List<String> fields) {
		String[] includeFields = fields.stream().toArray(String[]::new);

		MappingJacksonValue wrapper = new MappingJacksonValue(covidData);
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("covidfilter",
				SimpleBeanPropertyFilter.filterOutAllExcept(includeFields));
		wrapper.setFilters(filterProvider);

		return wrapper;
	}
}
