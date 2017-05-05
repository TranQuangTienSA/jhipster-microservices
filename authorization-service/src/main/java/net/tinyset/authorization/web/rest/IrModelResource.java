package net.tinyset.authorization.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.tinyset.authorization.service.IrModelService;
import net.tinyset.authorization.web.rest.util.HeaderUtil;
import net.tinyset.authorization.web.rest.util.PaginationUtil;
import net.tinyset.authorization.service.dto.IrModelDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing IrModel.
 */
@RestController
@RequestMapping("/api")
public class IrModelResource {

    private final Logger log = LoggerFactory.getLogger(IrModelResource.class);

    private static final String ENTITY_NAME = "irModel";
        
    private final IrModelService irModelService;

    public IrModelResource(IrModelService irModelService) {
        this.irModelService = irModelService;
    }

    /**
     * POST  /ir-models : Create a new irModel.
     *
     * @param irModelDTO the irModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new irModelDTO, or with status 400 (Bad Request) if the irModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ir-models")
    @Timed
    public ResponseEntity<IrModelDTO> createIrModel(@Valid @RequestBody IrModelDTO irModelDTO) throws URISyntaxException {
        log.debug("REST request to save IrModel : {}", irModelDTO);
        if (irModelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new irModel cannot already have an ID")).body(null);
        }
        IrModelDTO result = irModelService.save(irModelDTO);
        return ResponseEntity.created(new URI("/api/ir-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ir-models : Updates an existing irModel.
     *
     * @param irModelDTO the irModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated irModelDTO,
     * or with status 400 (Bad Request) if the irModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the irModelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ir-models")
    @Timed
    public ResponseEntity<IrModelDTO> updateIrModel(@Valid @RequestBody IrModelDTO irModelDTO) throws URISyntaxException {
        log.debug("REST request to update IrModel : {}", irModelDTO);
        if (irModelDTO.getId() == null) {
            return createIrModel(irModelDTO);
        }
        IrModelDTO result = irModelService.save(irModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, irModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ir-models : get all the irModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of irModels in body
     */
    @GetMapping("/ir-models")
    @Timed
    public ResponseEntity<List<IrModelDTO>> getAllIrModels(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of IrModels");
        Page<IrModelDTO> page = irModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ir-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ir-models/:id : get the "id" irModel.
     *
     * @param id the id of the irModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the irModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ir-models/{id}")
    @Timed
    public ResponseEntity<IrModelDTO> getIrModel(@PathVariable Long id) {
        log.debug("REST request to get IrModel : {}", id);
        IrModelDTO irModelDTO = irModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(irModelDTO));
    }

    /**
     * DELETE  /ir-models/:id : delete the "id" irModel.
     *
     * @param id the id of the irModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ir-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteIrModel(@PathVariable Long id) {
        log.debug("REST request to delete IrModel : {}", id);
        irModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
