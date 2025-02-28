package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(sal.id, sal.date, SUM(sal.amount), sal.seller.name) "
            +
            "FROM Sale AS sal JOIN sal.seller " +
            "WHERE sal.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(sal.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
            "GROUP BY sal.id, sal.date, sal.seller.name", countQuery = "SELECT COUNT(sal) FROM Sale AS sal " +
                    "WHERE sal.date BETWEEN :minDate AND :maxDate " +
                    "AND UPPER(sal.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) ")
    Page<SaleReportDTO> searchReportSale(Pageable pageable, LocalDate minDate, LocalDate maxDate, String sellerName);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(SUM(sal.amount), sal.seller.name) " +
            "FROM Sale AS sal " +
            "WHERE sal.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY sal.seller.name")
    List<SaleSummaryDTO> searchSummarySale(LocalDate minDate, LocalDate maxDate);

}
