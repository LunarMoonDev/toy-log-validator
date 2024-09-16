package com.project.toy_log_validator.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import com.project.toy_log_validator.entity.Reports;

@Repository
@Transactional
public interface ReportsRepository extends JpaRepository<Reports, BigInteger> {
    @Query(value = "SELECT * FROM reports WHERE is_processed = '0' AND is_error = '0' ORDER BY gmt_create LIMIT 1", nativeQuery = true)
    Reports findFirstByOrderByGmtCreateDesc();

    Optional<Reports> findByReportId(String reportId);
}
