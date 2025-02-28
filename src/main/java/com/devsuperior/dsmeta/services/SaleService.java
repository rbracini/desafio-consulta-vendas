package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> searchReportSale(Pageable pageable, String minDate, String maxDate, String sellerName) {
		LocalDate max = parseDateOrDefault(maxDate, LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()));
		LocalDate min = parseDateOrDefault(minDate, max.minusYears(1));
		return repository.searchReportSale(pageable, min, max, sellerName);
	}

	public List<SaleSummaryDTO> searchSummarySale(String minDate, String maxDate) {
		LocalDate max = parseDateOrDefault(maxDate, LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()));
		LocalDate min = parseDateOrDefault(minDate, max.minusYears(1));
		return repository.searchSummarySale(min, max);
	}

	private LocalDate parseDateOrDefault(String date, LocalDate defaultValue) {
		return (date != null && !date.isEmpty()) ? LocalDate.parse(date) : defaultValue;
	}
}
