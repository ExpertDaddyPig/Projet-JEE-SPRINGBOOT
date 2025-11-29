package com.main.repository;

import com.main.model.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Integer> {
    @Query("SELECT p FROM Payslip p WHERE p.employe_id = :id")
    List<Payslip> findByEmployeId(@Param("id") int id);
}