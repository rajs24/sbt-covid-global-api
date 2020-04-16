package com.sbt.covid.tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.collect.Iterables;
import com.sbt.covid.tracker.models.CovidData;
import com.sbt.covid.tracker.models.CovidDataConstants;
import com.sbt.covid19.controller.exception.CovidException;

@Service
public class CovidDataService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CovidDataConstants dataConstants;

	@Autowired
	CovidGoogleSheetService googleSheetService;

	private int totalConfirmed;
	private int totalDeaths;
	private int totalReovered;
	private int totalActive;

	private List<CovidData> listOfData = new ArrayList<CovidData>();
	private List<CovidData> listOfConfirmedCasesData = new ArrayList<CovidData>();
	private List<CovidData> listOfDeathsData = new ArrayList<CovidData>();
	private List<CovidData> listOfRecoveredData = new ArrayList<CovidData>();
	private List<CovidData> listOfConfirmedCasesByCountry = new ArrayList<CovidData>();
	private List<CovidData> listOfDeathCasesByCountry = new ArrayList<CovidData>();
	private List<CovidData> listOfRecoveredCasesByCountry = new ArrayList<CovidData>();
	private List<CovidData> listOfAllStateData = new ArrayList<CovidData>();

	@PostConstruct
	@Scheduled(cron = "1 * * * * *")
	public void fecthGlobalCovidTotalConfiredCasesData() throws IOException {

		System.out.println("getGlobalDataConfirmUrl--->" + dataConstants.getGlobalDataConfirmUrl());
		System.out.println("getGlobalDataDeathUrl--->" + dataConstants.getGlobalDataDeathUrl());
		System.out.println("getGlobalDataRecoverUrl--->" + dataConstants.getGlobalDataRecoverUrl());

		System.out.println("getGoogleSheetRangeValue--->" + dataConstants.getGoogleSheetRangeValue());
		System.out.println("getGoogleSheetCredentialsFilePath--->" + dataConstants.getGoogleSheetCredentialsFilePath());

		System.out.println("getGoogleSheetApplicationName--->" + dataConstants.getGoogleSheetApplicationName());
		System.out.println("getGoogleSheetId--->" + dataConstants.getGoogleSheetId());
		System.out.println("getGoogleSheetINIndex--->" + dataConstants.getGoogleSheetINIndex());
		System.out.println("getGoogleSheetTNIndex--->" + dataConstants.getGoogleSheetTNIndex());

		System.out.println("all prop values: " + dataConstants.toString());

		listOfConfirmedCasesData = fetchCovid19GlobalData(dataConstants.getGlobalDataConfirmUrl());
		listOfConfirmedCasesByCountry = fetchCovid19DataByCountry(dataConstants.getGlobalDataConfirmUrl());

	}

	@PostConstruct
	@Scheduled(cron = "1 * * * * *")
	public void fecthGlobalCovidTotalDeathCasesData() throws IOException {

		listOfDeathsData = fetchCovid19GlobalData(dataConstants.getGlobalDataDeathUrl());
		listOfDeathCasesByCountry = fetchCovid19DataByCountry(dataConstants.getGlobalDataDeathUrl());
	}

	@PostConstruct
	@Scheduled(cron = "1 * * * * *")
	public void fecthGlobalCovidTotalRecoveredCasesData() throws IOException {

		listOfRecoveredData = fetchCovid19GlobalData(dataConstants.getGlobalDataRecoverUrl());
		listOfRecoveredCasesByCountry = fetchCovid19DataByCountry(dataConstants.getGlobalDataRecoverUrl());
	}

	@PostConstruct
	@Scheduled(cron = "1 * * * * *")
	public void fecthCovidData() throws CovidException {

		listOfAllStateData = fetchCovid19AllStateData(dataConstants.getGoogleSheetId(),
				dataConstants.getGoogleSheetRangeValue(), dataConstants.getGoogleSheetINIndex());
		totalConfirmed = getTotalConfirmedCases(listOfAllStateData);
		totalDeaths = getTotalDeathCases(listOfAllStateData);
		totalReovered = getTotalRecoveredCases(listOfAllStateData);
		totalActive = getTotalActiveCases(listOfAllStateData);
	}

	public CovidData getCovidDataByCountryName(String countryName) throws IOException {

		List<CovidData> covidDataList1 = new ArrayList<CovidData>();
		List<CovidData> covidDataList2 = new ArrayList<CovidData>();
		List<CovidData> covidDataList3 = new ArrayList<CovidData>();

		covidDataList1 = fetchCovid19ByCountryNameData(countryName, dataConstants.getGlobalDataConfirmUrl());
		covidDataList2 = fetchCovid19ByCountryNameData(countryName, dataConstants.getGlobalDataDeathUrl());
		covidDataList3 = fetchCovid19ByCountryNameData(countryName, dataConstants.getGlobalDataRecoverUrl());

		if ((covidDataList1 != null && !covidDataList1.isEmpty())
				&& (covidDataList2 != null && !covidDataList2.isEmpty())
				&& (covidDataList3 != null && !covidDataList3.isEmpty())) {
			return new CovidData(covidDataList1.get(0).getCountry(), covidDataList1.get(0).getTotalCases(),
					covidDataList1.get(0).getTotalPreviousDay(), covidDataList2.get(0).getTotalCases(),
					covidDataList2.get(0).getTotalPreviousDay(), covidDataList3.get(0).getTotalCases(),
					covidDataList3.get(0).getTotalPreviousDay());
		} else {
			return new CovidData("Not found", 0);
		}

	}

	public List<CovidData> getAllCasesCovidDataByAllCountry() throws IOException {

		List<CovidData> covidResultDataList = new ArrayList<CovidData>();
		List<CovidData> covidDataList1 = new ArrayList<CovidData>();
		List<CovidData> covidDataList2 = new ArrayList<CovidData>();
		List<CovidData> covidDataList3 = new ArrayList<CovidData>();

		covidDataList1 = getGroupByCountryCovidData(
				fetchCovid19AllCasesByAllCountryData(dataConstants.getGlobalDataConfirmUrl()));
		covidDataList2 = getGroupByCountryCovidData(
				fetchCovid19AllCasesByAllCountryData(dataConstants.getGlobalDataDeathUrl()));
		covidDataList3 = getGroupByCountryCovidData(
				fetchCovid19AllCasesByAllCountryData(dataConstants.getGlobalDataRecoverUrl()));

		if ((covidDataList1 != null && !covidDataList1.isEmpty())
				&& (covidDataList2 != null && !covidDataList2.isEmpty())
				&& (covidDataList3 != null && !covidDataList3.isEmpty())) {
			for (int i = 0, j = 0, k = 0; i < covidDataList1.size() && j < covidDataList2.size()
					&& k < covidDataList3.size(); ++i, ++j, ++k) {
				CovidData covidData1 = covidDataList1.get(i);
				CovidData covidData2 = covidDataList2.get(j);
				CovidData covidData3 = covidDataList3.get(k);

				if ((covidData1.getCountry().equalsIgnoreCase(covidData2.getCountry()))
						&& ((covidData1.getCountry().equalsIgnoreCase(covidData3.getCountry())))) {

//					covidResultDataList.add(new CovidData(covidData1.getCountry(), covidData1.getState(),
//							covidData1.getTotalCases(), covidData1.getTotalPreviousDay(), covidData2.getTotalCases(),
//							covidData2.getTotalPreviousDay(), covidData3.getTotalCases(),
//							covidData3.getTotalPreviousDay()));

//					System.out.println("Final: Country: " + covidData1.getCountry() + ", Total: " + covidData1.getTotalCases()
//							+ ", Current: " + covidData1.getTotalCurrentDay() + ", Previous: "
//							+ covidData1.getTotalPreviousDay());
					covidResultDataList.add(new CovidData(covidData1.getCountry(), covidData1.getTotalCases(),
							covidData1.getTotalPreviousDay(), covidData1.getTotalCurrentDay(),
							covidData2.getTotalCases(), covidData2.getTotalPreviousDay(),
							covidData2.getTotalCurrentDay(), covidData3.getTotalCases(),
							covidData3.getTotalPreviousDay(), covidData3.getTotalCurrentDay()));
				}
			}
		}

		covidResultDataList.sort(Comparator.comparingInt(CovidData::getTotalCases).reversed());
		return covidResultDataList;
	}

	public List<CovidData> getGroupByCountryCovidData(List<CovidData> casesByCountry) {
		List<CovidData> totalCasesByCountry = casesByCountry.stream()
				.collect(Collectors.groupingBy(data -> data.getCountry())).entrySet().stream()
				.map(e -> e.getValue().stream()
						.reduce((cvd1, cvd2) -> new CovidData(cvd1.getCountry(),
								cvd1.getTotalCases() + cvd2.getTotalCases(),
								cvd1.getTotalPreviousDay() + cvd2.getTotalPreviousDay())))
				.map(f -> f.get()).collect(Collectors.toList());
		totalCasesByCountry.sort(Comparator.comparing(CovidData::getCountry));
		return totalCasesByCountry;
	}

	private List<CovidData> fetchCovid19AllCasesByAllCountryData(String dataUrl) throws IOException {

		List<CovidData> listOfcovidCountryData = new ArrayList<CovidData>();
		String readData = restTemplate.getForObject(dataUrl, String.class);
		StringReader csvResReader = new StringReader(readData);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvResReader);
		int totalCases = 0;
		int currDayCases = 0;
		int prevDayCases = 0;
		int currDaycheck = 0;
		int prevDaycheck = 0;

		for (CSVRecord record : records) {

			if (record.get(record.size() - 1) != null && record.get(record.size() - 1).length() > 0) {
				totalCases = Integer.parseInt(record.get(record.size() - 1));
			} else {
				totalCases = 0;
			}

			if (record.get(record.size() - 2) != null && record.get(record.size() - 2).length() > 0) {
				currDayCases = Integer.parseInt(record.get(record.size() - 2));
			} else {
				currDayCases = 0;
			}

			if (record.get(record.size() - 3) != null && record.get(record.size() - 3).length() > 0) {
				prevDayCases = Integer.parseInt(record.get(record.size() - 3));
			} else {
				prevDayCases = 0;
			}

			if (totalCases < currDayCases) {
				currDaycheck = 0;
			} else {
				currDaycheck = totalCases - currDayCases;
			}

			if (currDayCases < prevDayCases) {
				prevDaycheck = 0;
			} else {
				prevDaycheck = currDayCases - prevDayCases;
			}

//			System.out.println("Country: " + record.get(1) + ", Total: " + totalCases + ", Current: " + currDaycheck
//					+ ", Previous: " + prevDaycheck);
			// listOfcovidCountryData.add(new CovidData(record.get(0), record.get(1),
			// currDaycheck, prevDaycheck));
			listOfcovidCountryData.add(new CovidData(record.get(1), totalCases, currDaycheck, prevDaycheck));
		}

		// listOfcovidCountryData.sort(Comparator.comparingInt(CovidData::getTotalConfirmed).reversed());

		return this.listOfData = listOfcovidCountryData;
	}

	private List<CovidData> fetchCovid19ByCountryNameData(String countryName, String dataUrl) throws IOException {

		List<CovidData> listOfNewData = new ArrayList<CovidData>();
		String readData = restTemplate.getForObject(dataUrl, String.class);
		StringReader csvResReader = new StringReader(readData);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvResReader);
		int currDayCases = 0;
		int prevDayCases = 0;
		int prevDaycheck = 0;

		for (CSVRecord record : records) {

			if (record.get(1).equalsIgnoreCase(countryName)) {

				if (record.get(record.size() - 1) != null && record.get(record.size() - 1).length() > 0) {
					currDayCases = Integer.parseInt(record.get(record.size() - 1));
				} else {
					currDayCases = 0;
				}

				if (record.get(record.size() - 2) != null && record.get(record.size() - 2).length() > 0) {
					prevDayCases = Integer.parseInt(record.get(record.size() - 2));
				} else {
					prevDayCases = 0;
				}

				if (currDayCases < prevDayCases) {
					prevDaycheck = 0;
				} else {
					prevDaycheck = currDayCases - prevDayCases;
				}

				listOfNewData.add(new CovidData(record.get(0), record.get(1), currDayCases, prevDaycheck));
			}
		}

		return this.listOfData = listOfNewData;
	}

	private List<CovidData> fetchCovid19GlobalData(String dataUrl) throws IOException {

		List<CovidData> listOfNewData = new ArrayList<CovidData>();
		String readData = restTemplate.getForObject(dataUrl, String.class);
		StringReader csvResReader = new StringReader(readData);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvResReader);
		int currDayCases = 0;
		int prevDayCases = 0;
		int prevDaycheck = 0;

		for (CSVRecord record : records) {

			if (record.get(record.size() - 1) != null && record.get(record.size() - 1).length() > 0) {
				currDayCases = Integer.parseInt(record.get(record.size() - 1));
			} else {
				currDayCases = 0;
			}

			if (record.get(record.size() - 2) != null && record.get(record.size() - 2).length() > 0) {
				prevDayCases = Integer.parseInt(record.get(record.size() - 2));
			} else {
				prevDayCases = 0;
			}

			if (currDayCases < prevDayCases) {
				prevDaycheck = 0;
			} else {
				prevDaycheck = currDayCases - prevDayCases;
			}

			listOfNewData.add(new CovidData(record.get(0), record.get(1), currDayCases, prevDaycheck));
		}
		listOfNewData.sort(Comparator.comparingInt(CovidData::getTotalCases).reversed());
		return this.listOfData = listOfNewData;
	}

	private List<CovidData> fetchCovid19DataByCountry(String dataUrl) throws IOException {

		List<CovidData> totalCasesByCountry = this.listOfData.stream()
				.collect(Collectors.groupingBy(data -> data.getCountry())).entrySet().stream()
				.map(e -> e.getValue().stream().reduce(
						(cvd1, cvd2) -> new CovidData(cvd1.getCountry(), cvd1.getTotalCases() + cvd2.getTotalCases())))
				.map(f -> f.get()).collect(Collectors.toList());
		totalCasesByCountry.sort(Comparator.comparingInt(CovidData::getTotalCases).reversed());
		return this.listOfData = totalCasesByCountry;
	}

	private List<CovidData> fetchCovid19AllStateData(String spreadsheetId, String rangeValue, int index)
			throws CovidException {
		List<CovidData> listOfNewData = new ArrayList<CovidData>();
		List<CovidData> result = new ArrayList<CovidData>();

		int totalConfirmed = 0;
		int totalDeaths = 0;
		int totalReovered = 0;
		int totalActive = 0;

		ValueRange response = googleSheetService.getRange(spreadsheetId, rangeValue, index);
		int numRows = response.getValues() != null ? response.getValues().size() : 0;
		System.out.println("No of rows retrieved." + numRows);

		List<List<Object>> values = response.getValues();
		if (values == null || values.isEmpty()) {
			System.out.println("No data found.");
		} else {
			for (List record : Iterables.skip(values, 1)) {

				if (record.get(2) != null && record.get(2).toString().length() > 0) {
					totalConfirmed = Integer.parseInt(record.get(2).toString());
				} else {
					System.out.println("State[" + record.get(2).toString() + "] confirmed has empty value.  ");
					totalConfirmed = 0;
				}

				if (record.get(3) != null && record.get(3).toString().length() > 0) {
					totalReovered = Integer.parseInt(record.get(3).toString());
				} else {
					System.out.println("State[" + record.get(3).toString() + "] recovered has empty value.  ");
					totalReovered = 0;
				}

				if (record.get(4) != null && record.get(4).toString().length() > 0) {
					totalDeaths = Integer.parseInt(record.get(4).toString());
				} else {
					System.out.println("State[" + record.get(4).toString() + "] deaths has empty value.  ");
					totalDeaths = 0;
				}

				if (record.get(5) != null && record.get(5).toString().length() > 0) {
					totalActive = Integer.parseInt(record.get(5).toString());
				} else {
					System.out.println("State[" + record.get(5).toString() + "] active has empty value.  ");
					totalActive = 0;
				}

				listOfNewData.add(new CovidData(record.get(0).toString(), record.get(1).toString(), totalConfirmed,
						totalDeaths, totalReovered, totalActive));
			}
		}

		// skip the last row
		result = listOfNewData.stream().limit(listOfNewData.size() - 1).collect(Collectors.toList());

		result.sort(Comparator.comparingInt(CovidData::getTotalConfirmed).reversed());

		return this.listOfData = result;

	}

	private int getTotalConfirmedCases(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalConfirmed()).sum();
	}

	private int getTotalDeathCases(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalDeaths()).sum();
	}

	private int getTotalRecoveredCases(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalReovered()).sum();
	}

	private int getTotalActiveCases(List<CovidData> listOfCovidData) {
		return listOfCovidData.stream().mapToInt(data -> data.getTotalActive()).sum();
	}

	public List<CovidData> getListOfConfirmedCasesData() {
		return listOfConfirmedCasesData;
	}

	public List<CovidData> getListOfDeathsData() {
		return listOfDeathsData;
	}

	public List<CovidData> getListOfRecoveredData() {
		return listOfRecoveredData;
	}

	public List<CovidData> getListOfConfirmedCasesByCountry() {
		return listOfConfirmedCasesByCountry;
	}

	public List<CovidData> getListOfDeathCasesByCountry() {
		return listOfDeathCasesByCountry;
	}

	public List<CovidData> getListOfRecoveredCasesByCountry() {
		return listOfRecoveredCasesByCountry;
	}

	public List<CovidData> getListOfAllStateData() {
		return listOfAllStateData;
	}

	public int getTotalConfirmed() {
		return totalConfirmed;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public int getTotalReovered() {
		return totalReovered;
	}

	public int getTotalActive() {
		return totalActive;
	}

//	Example patterns:
//
//		"0 0 * * * *" = the top of every hour of every day.
//		"*/10 * * * * *" = every ten seconds.
//		"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//		"0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
//		"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
//		"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//		"0 0 0 25 12 ?" = every Christmas Day at midnight

}
