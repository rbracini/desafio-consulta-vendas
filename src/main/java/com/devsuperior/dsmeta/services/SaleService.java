package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
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

	public Page<SaleReportDTO> searchSale(Pageable pageable, String minDate, String maxDate, String sellerName) {

		LocalDate maxLocalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate minLocalDate = maxLocalDate.minusYears(1L);

		if (minDate != null && !minDate.isEmpty()) {
			minLocalDate = LocalDate.parse(minDate);
		}

		if (maxDate != null && !maxDate.isEmpty()) {
			maxLocalDate = LocalDate.parse(maxDate);
		}

		// return repository.searchSale(pageable, minLocalDate, maxLocalDate,
		// sellerName).map(SaleReportDTO::new);
		return repository.searchReportSale(pageable, minLocalDate, maxLocalDate, sellerName);
	}

}
