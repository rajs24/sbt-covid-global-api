package com.sbt.covid.tracker.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CovidDataConstants {

	@Value("${app.covid.global.confirm.url}")
	private String globalDataConfirmUrl;

	@Value("${app.covid.global.death.url}")
	private String globalDataDeathUrl;

	@Value("${app.covid.global.recover.url}")
	private String globalDataRecoverUrl;

	@Value("${app.google.sheet.credentials.file.path}")
	private String googleSheetCredentialsFilePath;

	@Value("${app.google.sheet.application.name}")
	private String googleSheetApplicationName;

	@Value("${app.google.sheet.id}")
	private String googleSheetId;

	@Value("#{new Integer('${app.google.sheet.tn.index}')}")
	private Integer googleSheetTNIndex;

	@Value("#{new Integer('${app.google.sheet.in.index}')}")
	private Integer googleSheetINIndex;

	@Value("${app.google.sheet.range.value}")
	private String googleSheetRangeValue;

	public String getGlobalDataConfirmUrl() {
		return globalDataConfirmUrl;
	}

	public String getGlobalDataDeathUrl() {
		return globalDataDeathUrl;
	}

	public String getGlobalDataRecoverUrl() {
		return globalDataRecoverUrl;
	}

	public String getGoogleSheetCredentialsFilePath() {
		return googleSheetCredentialsFilePath;
	}

	public String getGoogleSheetApplicationName() {
		return googleSheetApplicationName;
	}

	public String getGoogleSheetId() {
		return googleSheetId;
	}

	public Integer getGoogleSheetTNIndex() {
		return googleSheetTNIndex;
	}

	public Integer getGoogleSheetINIndex() {
		return googleSheetINIndex;
	}

	public String getGoogleSheetRangeValue() {
		return googleSheetRangeValue;
	}

}
