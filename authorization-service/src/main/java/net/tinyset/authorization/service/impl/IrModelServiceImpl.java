package net.tinyset.authorization.service.impl;

import net.tinyset.authorization.service.IrModelService;
import net.tinyset.authorization.domain.IrModel;
import net.tinyset.authorization.repository.IrModelRepository;
import net.tinyset.authorization.service.dto.IrModelDTO;
import net.tinyset.authorization.service.mapper.IrModelMapper;
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
 * Service Implementation for managing IrModel.
 */
@Service
@Transactional
public class IrModelServiceImpl implements IrModelService{

    private final Logger log = LoggerFactory.getLogger(IrModelServiceImpl.class);
    
    private final IrModelRepository irModelRepository;

    private final IrModelMapper irModelMapper;

    public IrModelServiceImpl(IrModelRepository irModelRepository, IrModelMapper irModelMapper) {
        this.irModelRepository = irModelRepository;
        this.irModelMapper = irModelMapper;
    }

    /**
     * Save a irModel.
     *
     * @param irModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IrModelDTO save(IrModelDTO irModelDTO) {
        log.debug("Request to save IrModel : {}", irModelDTO);
        IrModel irModel = irModelMapper.irModelDTOToIrModel(irModelDTO);
        irModel = irModelRepository.save(irModel);
        IrModelDTO result = irModelMapper.irModelToIrModelDTO(irModel);
        return result;
    }

    /**
     *  Get all the irModels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IrModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IrModels");
        Page<IrModel> result = irModelRepository.findAll(pageable);
        return result.map(irModel -> irModelMapper.irModelToIrModelDTO(irModel));
    }

    /**
     *  Get one irModel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IrModelDTO findOne(Long id) {
        log.debug("Request to get IrModel : {}", id);
        IrModel irModel = irModelRepository.findOne(id);
        IrModelDTO irModelDTO = irModelMapper.irModelToIrModelDTO(irModel);
        return irModelDTO;
    }

    /**
     *  Delete the  irModel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IrModel : {}", id);
        irModelRepository.delete(id);
    }
}
