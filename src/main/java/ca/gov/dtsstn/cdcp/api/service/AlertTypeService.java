package ca.gov.dtsstn.cdcp.api.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ca.gov.dtsstn.cdcp.api.data.repository.AlertTypeRepository;
import ca.gov.dtsstn.cdcp.api.service.domain.AlertType;
import ca.gov.dtsstn.cdcp.api.service.domain.mapper.AlertTypeMapper;

@Service
@CacheConfig(cacheNames = { "alert-types" })
public class AlertTypeService {

	private final AlertTypeMapper alertTypeMapper;

	private final AlertTypeRepository alertTypeRepository;

	public AlertTypeService(AlertTypeMapper alertTypeMapper, AlertTypeRepository alertTypeRepository) {
		Assert.notNull(alertTypeMapper, "alertTypeMapper is required; it must not be null");
		Assert.notNull(alertTypeRepository, "alertTypeRepository is required; it must not be null");
		this.alertTypeMapper = alertTypeMapper;
		this.alertTypeRepository = alertTypeRepository;
	}

	@Cacheable(key = "{ 'id', #id }", sync = true)
	public Optional<AlertType> readById(String id) {
		Assert.hasText(id, "id is required; it must not be null or blank");
		return alertTypeRepository.findById(id).map(alertTypeMapper::fromEntity);
	}

	@Cacheable(key = "{ 'code', #code }", sync = true)
	public Optional<AlertType> readByCode(String code) {
		Assert.hasText(code, "code is required; it must not be null or blank");
		return alertTypeRepository.findByCode(code).map(alertTypeMapper::fromEntity);
	}

}
