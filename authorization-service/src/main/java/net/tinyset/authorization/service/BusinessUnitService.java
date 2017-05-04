package net.tinyset.authorization.service;

import net.tinyset.authorization.service.dto.BusinessUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing BusinessUnit.
 */
public interface BusinessUnitService {

    /**
     * Save a businessUnit.
     *
     * @param businessUnitDTO the entity to save
     * @return the persisted entity
     */
    BusinessUnitDTO save(BusinessUnitDTO businessUnitDTO);

    /**
     *  Get all the businessUnits.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BusinessUnitDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" businessUnit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BusinessUnitDTO findOne(Long id);

    /**
     *  Delete the "id" businessUnit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
