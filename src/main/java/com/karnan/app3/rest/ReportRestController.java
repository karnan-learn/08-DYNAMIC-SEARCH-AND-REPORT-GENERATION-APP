package com.karnan.app3.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karnan.app3.reports.ExcelGenerator;
import com.karnan.app3.reports.PdfGenerator;
import com.karnan.app3.request.SearchRequest;
import com.karnan.app3.response.SearchResponse;
import com.karnan.app3.service.ReportService;

@RestController
public class ReportRestController {

	@Autowired
	private ReportService service;

	@GetMapping("/plan-names")
	public List<String> getPlanNames(){
		return service.getPlanNames();
	}
	
	@GetMapping("/plan-statues")
	public List<String> getPlanStatues(){
		return service.getPlanStatues();
	}
	
	@PostMapping("/search")
	public List<SearchResponse> search(@RequestBody SearchRequest request){
		return service.searchPlans(request);
	}
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception {

		// we need to set some properties for downloading the excel file in ui
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Plans.xls";
		response.setHeader(headerKey, headerValue);
		// generate excel
		List<SearchResponse> records = service.searchPlans(null);
		ExcelGenerator excel = new ExcelGenerator();
		excel.generateExcel(records, response);
	}
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse httpResponse) throws Exception {

		// we need to set some properties for downloading the excel file in ui
		httpResponse.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Plans.pdf";
		httpResponse.setHeader(headerKey, headerValue);
		// generate pdf
		List<SearchResponse> records = service.searchPlans(null);
		PdfGenerator pdfGen = new PdfGenerator();
		pdfGen.generatePdf(records, httpResponse);
	}
}
