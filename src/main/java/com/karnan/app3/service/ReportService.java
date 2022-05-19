package com.karnan.app3.service;

import java.util.List;

import com.karnan.app3.request.SearchRequest;
import com.karnan.app3.response.SearchResponse;


public interface ReportService {

	public List<String> getPlanNames();
	
	public List<String> getPlanStatues();
	
	public List<SearchResponse> searchPlans(SearchRequest request);
	
//	public void exportExcel(List<SearchResponse> records);
//	
//	public void exportPdf(List<SearchResponse> records);

}
