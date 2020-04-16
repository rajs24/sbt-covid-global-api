package com.sbt.covid.tracker.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sbt.covid.tracker.models.CovidData;
import com.sbt.covid.tracker.service.CovidDataService;

@RestController
@RequestMapping("/api/v1/covid/global")
public class CovidGlobalDataController {

	@Autowired
	private CovidDataService dataService;

	@GetMapping("/confirm")
	public MappingJacksonValue getGlobalConfirmedCases(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfConfirmedCasesData());
		return getValue(covidData, Arrays.asList("covidData", "state", "country", "totalCases", "totalPreviousDay"));
	}

	@GetMapping("/confirm/country")
	public MappingJacksonValue getGlobalConfirmedCasesByCountry(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfConfirmedCasesByCountry());
		return getValue(covidData, Arrays.asList("covidData", "country", "totalCases"));
	}

	@GetMapping("/confirm/totalcurrent")
	public MappingJacksonValue getGlobalConfirmedTotalCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		List<CovidData> listOfConfirmedCasesData = dataService.getListOfConfirmedCasesData();
		covidData.setTotalCurrentDay(getTotalReportedCase(listOfConfirmedCasesData));
		return getValue(covidData, "totalCurrentDay");
	}

	@GetMapping("/confirm/totalprevious")
	public MappingJacksonValue getGlobalConfirmedTotalPreviousDayCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		List<CovidData> listOfConfirmedCasesData = dataService.getListOfConfirmedCasesData();
		covidData.setTotalPreviousDay(getTotalReportedNewCase(listOfConfirmedCasesData));
		return getValue(covidData, "totalPreviousDay");
	}

	@GetMapping("/death")
	public MappingJacksonValue getGlobalDeathCases(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfDeathsData());
		return getValue(covidData, Arrays.asList("covidData", "state", "country", "totalCases", "totalPreviousDay"));
	}

	@GetMapping("/death/country")
	public MappingJacksonValue getGlobalDeathCasesByCountry(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfDeathCasesByCountry());
		return getValue(covidData, Arrays.asList("covidData", "country", "totalCases"));
	}

	@GetMapping("/death/totalcurrent")
	public MappingJacksonValue getGlobalDeathTotalCases(Model model) {

		CovidData covidData = new CovidData();
		List<CovidData> listOfDeathCasesData = dataService.getListOfDeathsData();
		covidData.setTotalCurrentDay(getTotalReportedCase(listOfDeathCasesData));
		return getValue(covidData, "totalCurrentDay");
	}

	@GetMapping("/death/totalprevious")
	public MappingJacksonValue getGlobalDeathTotalPreviousDayCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		List<CovidData> listOfConfirmedCasesData = dataService.getListOfDeathsData();
		covidData.setTotalPreviousDay(getTotalReportedNewCase(listOfConfirmedCasesData));
		return getValue(covidData, "totalPreviousDay");
	}

	@GetMapping("/recover")
	public MappingJacksonValue getGlobalRecoveredCases(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfRecoveredData());
		return getValue(covidData, Arrays.asList("covidData", "state", "country", "totalCases", "totalPreviousDay"));
	}

	@GetMapping("/recover/country")
	public MappingJacksonValue getGlobalRecoveredCasesByCountry(Model model) {

		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getListOfRecoveredCasesByCountry());
		return getValue(covidData, Arrays.asList("covidData", "country", "totalCases"));
	}

	@GetMapping("/recover/totalcurrent")
	public MappingJacksonValue getGlobalRecoveredTotalCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		List<CovidData> listOfConfirmedCasesData = dataService.getListOfRecoveredData();
		covidData.setTotalCurrentDay(getTotalReportedCase(listOfConfirmedCasesData));
		return getValue(covidData, "totalCurrentDay");
	}

	@GetMapping("/recover/totalprevious")
	public MappingJacksonValue getGlobalRecoveredTotalPreviousDayCases(Model model) throws JsonProcessingException {

		CovidData covidData = new CovidData();
		List<CovidData> listOfConfirmedCasesData = dataService.getListOfRecoveredCasesByCountry();
		covidData.setTotalPreviousDay(getTotalReportedNewCase(listOfConfirmedCasesData));
		return getValue(covidData, "totalPreviousDay");
	}

	@GetMapping("/all")
	public MappingJacksonValue getAllCasesCovidDataByAllCountry(Model model) throws IOException {
		CovidData covidData = new CovidData();
		covidData.setCovidData(dataService.getAllCasesCovidDataByAllCountry());
		return getValue(covidData,
				Arrays.asList("covidData", "country", "totalConfirmed", "totalPreviousDayConfirmed",
						"totalCurrentDayConfirmed", "totalDeaths", "totalPreviousDayDeaths", "totalCurrentDayDeaths",
						"totalReovered", "totalPreviousDayRecovered", "totalCurrentDayRecovered"));
	}

	@GetMapping("/all/{countryName}")
	public MappingJacksonValue getAllCasesByCountryName(@PathVariable("countryName") String countryName, Model model)
			throws IOException {

		return getValue(dataService.getCovidDataByCountryName(countryName),
				Arrays.asList("covidData", "country", "totalConfirmed", "totalPreviousDayConfirmed", "totalDeaths",
						"totalPreviousDayDeaths", "totalReovered", "totalPreviousDayRecovered"));
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

	private int getTotalReportedCase(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalCases()).sum();
	}

	private int getTotalReportedNewCase(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalPreviousDay()).sum();
	}
}
