package net.tinyset.authorization.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.tinyset.authorization.service.BusinessUnitService;
import net.tinyset.authorization.web.rest.util.HeaderUtil;
import net.tinyset.authorization.web.rest.util.PaginationUtil;
import net.tinyset.authorization.service.dto.BusinessUnitDTO;
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
 * REST controller for managing BusinessUnit.
 */
@RestController
@RequestMapping("/api")
public class BusinessUnitResource {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitResource.class);

    private static final String ENTITY_NAME = "businessUnit";
        
    private final BusinessUnitService businessUnitService;

    public BusinessUnitResource(BusinessUnitService businessUnitService) {
        this.businessUnitService = businessUnitService;
    }

    /**
     * POST  /business-units : Create a new businessUnit.
     *
     * @param businessUnitDTO the businessUnitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new businessUnitDTO, or with status 400 (Bad Request) if the businessUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/business-units")
    @Timed
    public ResponseEntity<BusinessUnitDTO> createBusinessUnit(@Valid @RequestBody BusinessUnitDTO businessUnitDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessUnit : {}", businessUnitDTO);
        if (businessUnitDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new businessUnit cannot already have an ID")).body(null);
        }
        BusinessUnitDTO result = businessUnitService.save(businessUnitDTO);
        return ResponseEntity.created(new URI("/api/business-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /business-units : Updates an existing businessUnit.
     *
     * @param businessUnitDTO the businessUnitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated businessUnitDTO,
     * or with status 400 (Bad Request) if the businessUnitDTO is not valid,
     * or with status 500 (Internal Server Error) if the businessUnitDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/business-units")
    @Timed
    public ResponseEntity<BusinessUnitDTO> updateBusinessUnit(@Valid @RequestBody BusinessUnitDTO businessUnitDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessUnit : {}", businessUnitDTO);
        if (businessUnitDTO.getId() == null) {
            return createBusinessUnit(businessUnitDTO);
        }
        BusinessUnitDTO result = businessUnitService.save(businessUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, businessUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /business-units : get all the businessUnits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of businessUnits in body
     */
    @GetMapping("/business-units")
    @Timed
    public ResponseEntity<List<BusinessUnitDTO>> getAllBusinessUnits(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BusinessUnits");
        Page<BusinessUnitDTO> page = businessUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/business-units");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /business-units/:id : get the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the businessUnitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-units/{id}")
    @Timed
    public ResponseEntity<BusinessUnitDTO> getBusinessUnit(@PathVariable Long id) {
        log.debug("REST request to get BusinessUnit : {}", id);
        BusinessUnitDTO businessUnitDTO = businessUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(businessUnitDTO));
    }

    /**
     * DELETE  /business-units/:id : delete the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/business-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable Long id) {
        log.debug("REST request to delete BusinessUnit : {}", id);
        businessUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
