package com.sample.app.service;

import com.sample.app.service.dto.AccountJEDIDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AccountJEDI.
 */
public interface AccountJEDIService {

    /**
     * Save a accountJEDI.
     *
     * @param accountJEDIDTO the entity to save
     * @return the persisted entity
     */
    AccountJEDIDTO save(AccountJEDIDTO accountJEDIDTO);

    /**
     * Get all the accountJEDIS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AccountJEDIDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accountJEDI.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AccountJEDIDTO> findOne(Long id);

    /**
     * Delete the "id" accountJEDI.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the accountJEDI corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AccountJEDIDTO> search(String query, Pageable pageable);
}
