package com.sbt.covid.tracker.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonFilter("covidfilter")
public class CovidData {

	private String state;
	private String country;
	private int totalCases;
	private List<CovidData> covidData;
	private int totalCurrentDay;
	private int totalPreviousDay;

	private int totalConfirmed;
	private int totalDeaths;
	private int totalReovered;
	private int totalActive;

	private int totalPreviousDayConfirmed;
	private int totalPreviousDayDeaths;
	private int totalPreviousDayRecovered;

	private int totalCurrentDayConfirmed;
	private int totalCurrentDayDeaths;
	private int totalCurrentDayRecovered;

	public CovidData() {

	}

	public CovidData(String country, int totalCases) {
		this.country = country;
		this.totalCases = totalCases;
	}

	public CovidData(String state, String country, int totalCases) {
		this.state = state;
		this.country = country;
		this.totalCases = totalCases;
	}

	public CovidData(String country, int totalCases, int totalPreviousDay) {
		this.country = country;
		this.totalCases = totalCases;
		this.totalPreviousDay = totalPreviousDay;
	}

	public CovidData(String state, String country, int totalCases, int totalPreviousDay) {
		this.state = state;
		this.country = country;
		this.totalCases = totalCases;
		this.totalPreviousDay = totalPreviousDay;
	}

	public CovidData(String country, int totalCases, int totalCurrentDay, int totalPreviousDay) {
		this.country = country;
		this.totalCases = totalCases;
		this.totalCurrentDay = totalCurrentDay;
		this.totalPreviousDay = totalPreviousDay;
	}

	public CovidData(String country, String state, int totalConfirmed, int totalDeaths, int totalReovered,
			int totalActive) {
		this.country = country;
		this.state = state;
		this.totalConfirmed = totalConfirmed;
		this.totalDeaths = totalDeaths;
		this.totalReovered = totalReovered;
		this.totalActive = totalActive;
	}

	public CovidData(String country, int totalConfirmed, int totalPreviousDayConfirmed, int totalDeaths,
			int totalPreviousDayDeaths, int totalReovered, int totalPreviousDayRecovered) {
		this.country = country;
		this.totalConfirmed = totalConfirmed;
		this.totalPreviousDayConfirmed = totalPreviousDayConfirmed;
		this.totalDeaths = totalDeaths;
		this.totalPreviousDayDeaths = totalPreviousDayDeaths;
		this.totalReovered = totalReovered;
		this.totalPreviousDayRecovered = totalPreviousDayRecovered;
	}

	public CovidData(String country, int totalConfirmed, int totalPreviousDayConfirmed, int totalCurrentDayConfirmed,
			int totalDeaths, int totalPreviousDayDeaths, int totalCurrentDayDeaths, int totalReovered,
			int totalPreviousDayRecovered, int totalCurrentDayRecovered) {
		this.country = country;
		this.totalConfirmed = totalConfirmed;
		this.totalPreviousDayConfirmed = totalPreviousDayConfirmed;
		this.totalCurrentDayConfirmed = totalCurrentDayConfirmed;
		this.totalDeaths = totalDeaths;
		this.totalPreviousDayDeaths = totalPreviousDayDeaths;
		this.totalCurrentDayDeaths = totalCurrentDayDeaths;
		this.totalReovered = totalReovered;
		this.totalPreviousDayRecovered = totalPreviousDayRecovered;
		this.totalCurrentDayRecovered = totalCurrentDayRecovered;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getTotalPreviousDay() {
		return totalPreviousDay;
	}

	public void setTotalPreviousDay(int totalPreviousDay) {
		this.totalPreviousDay = totalPreviousDay;
	}

	public List<CovidData> getCovidData() {
		return covidData;
	}

	public void setCovidData(List<CovidData> covidData) {
		this.covidData = covidData;
	}

	public int getTotalCurrentDay() {
		return totalCurrentDay;
	}

	public void setTotalCurrentDay(int totalCurrentDay) {
		this.totalCurrentDay = totalCurrentDay;
	}

	public int getTotalConfirmed() {
		return totalConfirmed;
	}

	public void setTotalConfirmed(int totalConfirmed) {
		this.totalConfirmed = totalConfirmed;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalReovered() {
		return totalReovered;
	}

	public void setTotalReovered(int totalReovered) {
		this.totalReovered = totalReovered;
	}

	public int getTotalActive() {
		return totalActive;
	}

	public void setTotalActive(int totalActive) {
		this.totalActive = totalActive;
	}

	public int getTotalPreviousDayConfirmed() {
		return totalPreviousDayConfirmed;
	}

	public void setTotalPreviousDayConfirmed(int totalPreviousDayConfirmed) {
		this.totalPreviousDayConfirmed = totalPreviousDayConfirmed;
	}

	public int getTotalPreviousDayDeaths() {
		return totalPreviousDayDeaths;
	}

	public void setTotalPreviousDayDeaths(int totalPreviousDayDeaths) {
		this.totalPreviousDayDeaths = totalPreviousDayDeaths;
	}

	public int getTotalPreviousDayRecovered() {
		return totalPreviousDayRecovered;
	}

	public void setTotalPreviousDayRecovered(int totalPreviousDayRecovered) {
		this.totalPreviousDayRecovered = totalPreviousDayRecovered;
	}

	public int getTotalCurrentDayConfirmed() {
		return totalCurrentDayConfirmed;
	}

	public void setTotalCurrentDayConfirmed(int totalCurrentDayConfirmed) {
		this.totalCurrentDayConfirmed = totalCurrentDayConfirmed;
	}

	public int getTotalCurrentDayDeaths() {
		return totalCurrentDayDeaths;
	}

	public void setTotalCurrentDayDeaths(int totalCurrentDayDeaths) {
		this.totalCurrentDayDeaths = totalCurrentDayDeaths;
	}

	public int getTotalCurrentDayRecovered() {
		return totalCurrentDayRecovered;
	}

	public void setTotalCurrentDayRecovered(int totalCurrentDayRecovered) {
		this.totalCurrentDayRecovered = totalCurrentDayRecovered;
	}

}
