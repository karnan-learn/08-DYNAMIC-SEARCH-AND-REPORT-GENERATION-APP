package com.karnan.app3.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.karnan.app3.entity.EligibilityDtlsEntity;
@Repository
public interface EligDtlsRepository extends JpaRepository<EligibilityDtlsEntity, Integer> {

	@Query("select distinct(planName) from EligibilityDtlsEntity")
	public List<String> getUniquePlanNames();
	
	@Query("select distinct(planStatus) from EligibilityDtlsEntity")
	public List<String> getUniquePlanStatues();
}
