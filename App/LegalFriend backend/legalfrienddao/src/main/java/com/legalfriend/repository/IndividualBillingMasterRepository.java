package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.IndividualBillingMaster;
import com.legalfriend.entities.Recourse;
import com.legalfriend.entities.Stage;

@Repository
public interface IndividualBillingMasterRepository extends CrudRepository<IndividualBillingMaster, Long> {

	IndividualBillingMaster findById(Long id);

	@Query(value = "SELECT * FROM ind_billing_master WHERE fk_recourse_id = ?1", nativeQuery = true)
	List<IndividualBillingMaster> findByBillingRecourseId(Long recourseId);

	@Query(value = "SELECT * FROM ind_billing_master WHERE fk_user_id = ?1", nativeQuery = true)
	List<IndividualBillingMaster> findByUserId(Long fkUserId);

	@Query(value = "SELECT * FROM ind_billing_master WHERE fk_user_id is null", nativeQuery = true)
	List<IndividualBillingMaster> findAll();

	List<IndividualBillingMaster> findByStageAndRecourse(Stage stage, Recourse recourse);

	List<IndividualBillingMaster> findByStageAndRecourseAndUserId(Stage stage, Recourse recourse, Long userId);

}
