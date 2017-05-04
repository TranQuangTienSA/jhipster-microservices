package net.tinyset.authorization.service.impl;

import net.tinyset.authorization.service.BusinessUnitService;
import net.tinyset.authorization.domain.BusinessUnit;
import net.tinyset.authorization.repository.BusinessUnitRepository;
import net.tinyset.authorization.service.dto.BusinessUnitDTO;
import net.tinyset.authorization.service.mapper.BusinessUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BusinessUnit.
 */
@Service
@Transactional
public class BusinessUnitServiceImpl implements BusinessUnitService{

    private final Logger log = LoggerFactory.getLogger(BusinessUnitServiceImpl.class);
    
    private final BusinessUnitRepository businessUnitRepository;

    private final BusinessUnitMapper businessUnitMapper;

    public BusinessUnitServiceImpl(BusinessUnitRepository businessUnitRepository, BusinessUnitMapper businessUnitMapper) {
        this.businessUnitRepository = businessUnitRepository;
        this.businessUnitMapper = businessUnitMapper;
    }

    /**
     * Save a businessUnit.
     *
     * @param businessUnitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BusinessUnitDTO save(BusinessUnitDTO businessUnitDTO) {
        log.debug("Request to save BusinessUnit : {}", businessUnitDTO);
        BusinessUnit businessUnit = businessUnitMapper.businessUnitDTOToBusinessUnit(businessUnitDTO);
        businessUnit = businessUnitRepository.save(businessUnit);
        BusinessUnitDTO result = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);
        return result;
    }

    /**
     *  Get all the businessUnits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessUnits");
        Page<BusinessUnit> result = businessUnitRepository.findAll(pageable);
        return result.map(businessUnit -> businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit));
    }

    /**
     *  Get one businessUnit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BusinessUnitDTO findOne(Long id) {
        log.debug("Request to get BusinessUnit : {}", id);
        BusinessUnit businessUnit = businessUnitRepository.findOne(id);
        BusinessUnitDTO businessUnitDTO = businessUnitMapper.businessUnitToBusinessUnitDTO(businessUnit);
        return businessUnitDTO;
    }

    /**
     *  Delete the  businessUnit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessUnit : {}", id);
        businessUnitRepository.delete(id);
    }
}
