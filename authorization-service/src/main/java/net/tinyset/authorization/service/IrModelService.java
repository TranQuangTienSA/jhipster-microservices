package net.tinyset.authorization.service;

import net.tinyset.authorization.service.dto.IrModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing IrModel.
 */
public interface IrModelService {

    /**
     * Save a irModel.
     *
     * @param irModelDTO the entity to save
     * @return the persisted entity
     */
    IrModelDTO save(IrModelDTO irModelDTO);

    /**
     *  Get all the irModels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IrModelDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" irModel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IrModelDTO findOne(Long id);

    /**
     *  Delete the "id" irModel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
