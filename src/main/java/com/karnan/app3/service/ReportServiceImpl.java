package com.karnan.app3.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.karnan.app3.entity.EligibilityDtlsEntity;
import com.karnan.app3.repo.EligDtlsRepository;
import com.karnan.app3.request.SearchRequest;
import com.karnan.app3.response.SearchResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EligDtlsRepository repo;
	
	@Override
	public List<String> getPlanNames() {
		return repo.getUniquePlanNames();
	}

	@Override
	public List<String> getPlanStatues() {
		return repo.getUniquePlanStatues();
	}

	@Override
	public List<SearchResponse> searchPlans(SearchRequest request) {
		
		List<EligibilityDtlsEntity> eligRecords = null;
		
		if(isSearchReqEmpty(request)) { 
			eligRecords= repo.findAll();
		}else {
			String planName = request.getPlanName();
			String planStatus  = request.getPlanStatus();
			LocalDate startDate = request.getStartDate();
			LocalDate endDate = request.getEndDate();
			EligibilityDtlsEntity entity = new EligibilityDtlsEntity();
			if(planName != null && !planName.equals("")) {
				entity.setPlanName(planName);
			}
			if(planStatus != null && !planStatus.equals("")) {
				entity.setPlanStatus(planStatus);			
			}
			if(startDate != null && endDate != null) {
				entity.setStartDate(startDate);
				entity.setEndDate(endDate);
			}
			//it will create where condition query based on the entity object values
			Example<EligibilityDtlsEntity> of = Example.of(entity);
			// fetch the record
			eligRecords= repo.findAll(of);
		}
		//change the return type of results from List<EligibilityDtlsEntity> to List<SearchResponse> 
		List<SearchResponse> response = new ArrayList<>();
		for(EligibilityDtlsEntity eligRecord : eligRecords) {
			SearchResponse sr= new SearchResponse();
			BeanUtils.copyProperties(eligRecord, sr);
			response.add(sr);
		}
		return response;
	}

	private boolean isSearchReqEmpty(SearchRequest request) {
		
		if(request.getPlanName() !=null && !request.getPlanName().equals("")) {
			return false;
		}
		if(request.getPlanStatus() !=null && !request.getPlanStatus().equals("")) {
			return false;
		}
		if(request.getStartDate() !=null && !request.getStartDate().equals("")) {
			return false;
		}
		if(request.getEndDate() !=null && !request.getEndDate().equals("")) {
			return false;
		}
		return true;
	}

}
