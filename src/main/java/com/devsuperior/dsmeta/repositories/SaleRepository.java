package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleProjection;

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

    // Native Query
    // @Query(nativeQuery = true, value = "SELECT sal.id, sal.date, SUM(sal.amount)
    // AS amount, sel.name AS sellerName " +
    // "FROM tb_sales AS sal " +
    // "INNER JOIN tb_seller AS sel ON sal.seller_id = sel.id " +
    // "WHERE sal.date BETWEEN :minDate AND :maxDate " +
    // "AND UPPER(sel.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
    // "GROUP BY sal.id, sal.date, sel.name ", countQuery = "SELECT COUNT(*) FROM
    // (SELECT sal.date, SUM(sal.amount) AS amount, sel.name AS sellerName "
    // +
    // "FROM tb_sales AS sal " +
    // "INNER JOIN tb_seller AS sel ON sal.seller_id = sel.id " +
    // "WHERE sal.date BETWEEN :minDate AND :maxDate " +
    // "AND UPPER(sel.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) " +
    // "GROUP BY sal.id, sal.date, sel.name) AS countQuery")
    // Page<SaleProjection> searchSale(Pageable pageable, LocalDate minDate,
    // LocalDate maxDate, String sellerName);

}
