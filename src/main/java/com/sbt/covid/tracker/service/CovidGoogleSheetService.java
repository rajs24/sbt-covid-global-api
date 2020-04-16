package com.sbt.covid.tracker.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.sbt.covid.tracker.models.CovidDataConstants;
import com.sbt.covid19.controller.exception.CovidException;

@Service
public class CovidGoogleSheetService {

	@Autowired
	CovidDataConstants dataConstants;

	public static Credential credential;

	private Credential getCredential() throws IOException {
		// System.out.println("googleSheetCredentialFilePath:
		// "+googleSheetCredentialFilePath);
		if (credential == null) {
			InputStream is = CovidGoogleSheetService.class
					.getResourceAsStream(dataConstants.getGoogleSheetCredentialsFilePath());
			credential = GoogleCredential.fromStream(is).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
		}
		return credential;
	}

	private Sheets getSheetsService() throws CovidException {
		try {
			Sheets sheets = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), getCredential())
							.setApplicationName(dataConstants.getGoogleSheetApplicationName()).build();
			return sheets;
		} catch (GeneralSecurityException gse) {
			throw new CovidException("GENERAL_SECURITY_EXCEPTION", gse);
		} catch (IOException ie) {
			throw new CovidException("IO_EXCEPTION - " + ie.getMessage());
		} catch (Exception e) {
			throw new CovidException("EXCEPTION - " + e.getMessage());
		}

	}

	public ValueRange getRange(String sheetId, String range, int index) throws CovidException {
		ValueRange response = null;
		try {
			// response = getSheetsService().spreadsheets().values().get(sheetId,
			// range).execute();
			response = getSheetsService().spreadsheets().values().get(sheetId, getSpreadSheetName(index)).execute();
		} catch (UnknownHostException uhe) {
			throw new CovidException("UNKNOWN_HOST_EXCEPTION", uhe);
		} catch (IOException ie) {
			throw new CovidException("IO_EXCEPTION - " + ie.getMessage());
		} catch (Exception e) {
			throw new CovidException("EXCEPTION - " + e.getMessage());
		}
		return response;

	}

	public String getSpreadSheetName(int index) throws CovidException {
		Spreadsheet response;
		String googleSpreadSheetFileName = null;
		try {
			response = getSheetsService().spreadsheets().get(dataConstants.getGoogleSheetId()).setIncludeGridData(false)
					.execute();
			googleSpreadSheetFileName = response.getSheets().get(index).getProperties().getTitle();
			System.out.println("Sheet File Name: " + googleSpreadSheetFileName);

		} catch (UnknownHostException uhe) {
			throw new CovidException("UNKNOWN_HOST_EXCEPTION", uhe);
		} catch (IOException ie) {
			throw new CovidException("IO_EXCEPTION - " + ie.getMessage());
		}

		return googleSpreadSheetFileName;
	}
}
