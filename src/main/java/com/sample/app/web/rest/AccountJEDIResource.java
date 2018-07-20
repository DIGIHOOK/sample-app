package com.sample.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sample.app.service.AccountJEDIService;
import com.sample.app.web.rest.errors.BadRequestAlertException;
import com.sample.app.web.rest.util.HeaderUtil;
import com.sample.app.web.rest.util.PaginationUtil;
import com.sample.app.service.dto.AccountJEDIDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AccountJEDI.
 */
@RestController
@RequestMapping("/api")
public class AccountJEDIResource {

    private final Logger log = LoggerFactory.getLogger(AccountJEDIResource.class);

    private static final String ENTITY_NAME = "accountJEDI";

    private final AccountJEDIService accountJEDIService;

    public AccountJEDIResource(AccountJEDIService accountJEDIService) {
        this.accountJEDIService = accountJEDIService;
    }

    /**
     * POST  /account-jedis : Create a new accountJEDI.
     *
     * @param accountJEDIDTO the accountJEDIDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountJEDIDTO, or with status 400 (Bad Request) if the accountJEDI has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-jedis")
    @Timed
    public ResponseEntity<AccountJEDIDTO> createAccountJEDI(@RequestBody AccountJEDIDTO accountJEDIDTO) throws URISyntaxException {
        log.debug("REST request to save AccountJEDI : {}", accountJEDIDTO);
        if (accountJEDIDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountJEDI cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountJEDIDTO result = accountJEDIService.save(accountJEDIDTO);
        return ResponseEntity.created(new URI("/api/account-jedis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-jedis : Updates an existing accountJEDI.
     *
     * @param accountJEDIDTO the accountJEDIDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountJEDIDTO,
     * or with status 400 (Bad Request) if the accountJEDIDTO is not valid,
     * or with status 500 (Internal Server Error) if the accountJEDIDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-jedis")
    @Timed
    public ResponseEntity<AccountJEDIDTO> updateAccountJEDI(@RequestBody AccountJEDIDTO accountJEDIDTO) throws URISyntaxException {
        log.debug("REST request to update AccountJEDI : {}", accountJEDIDTO);
        if (accountJEDIDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountJEDIDTO result = accountJEDIService.save(accountJEDIDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountJEDIDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-jedis : get all the accountJEDIS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of accountJEDIS in body
     */
    @GetMapping("/account-jedis")
    @Timed
    public ResponseEntity<List<AccountJEDIDTO>> getAllAccountJEDIS(Pageable pageable) {
        log.debug("REST request to get a page of AccountJEDIS");
        Page<AccountJEDIDTO> page = accountJEDIService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/account-jedis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /account-jedis/:id : get the "id" accountJEDI.
     *
     * @param id the id of the accountJEDIDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountJEDIDTO, or with status 404 (Not Found)
     */
    @GetMapping("/account-jedis/{id}")
    @Timed
    public ResponseEntity<AccountJEDIDTO> getAccountJEDI(@PathVariable Long id) {
        log.debug("REST request to get AccountJEDI : {}", id);
        Optional<AccountJEDIDTO> accountJEDIDTO = accountJEDIService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountJEDIDTO);
    }

    /**
     * DELETE  /account-jedis/:id : delete the "id" accountJEDI.
     *
     * @param id the id of the accountJEDIDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-jedis/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccountJEDI(@PathVariable Long id) {
        log.debug("REST request to delete AccountJEDI : {}", id);
        accountJEDIService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/account-jedis?query=:query : search for the accountJEDI corresponding
     * to the query.
     *
     * @param query the query of the accountJEDI search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/account-jedis")
    @Timed
    public ResponseEntity<List<AccountJEDIDTO>> searchAccountJEDIS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccountJEDIS for query {}", query);
        Page<AccountJEDIDTO> page = accountJEDIService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/account-jedis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
