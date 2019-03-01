package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.BillingMaster;
import com.legalfriend.entities.Institution;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;

@Repository
public interface BillingMasterRepository extends CrudRepository<BillingMaster, Long> {

	BillingMaster findById(Long id);

	@Query(value = "SELECT * FROM billing_master WHERE fk_recourse_id = ?1", nativeQuery = true)
	List<BillingMaster> findByBillingRecourseId(Long recourseId);

	@Query(value = "SELECT * FROM billing_master WHERE fk_user_id = ?1", nativeQuery = true)
	List<BillingMaster> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM billing_master WHERE fk_user_id is null", nativeQuery = true)
	List<BillingMaster> findAll();

	List<BillingMaster> findByStageAndRecourse(Stage stage, Recourse recourse);

	List<BillingMaster> findByInstitutionAndRecourseAndStage(Institution institution, Recourse recourse, Stage stage);

	List<BillingMaster> findByInstitutionAndStageAndRecourse(Institution institution, Stage stage, Recourse recourse);

}
