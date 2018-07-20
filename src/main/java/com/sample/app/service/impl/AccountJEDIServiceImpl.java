package com.sample.app.service.impl;

import com.sample.app.service.AccountJEDIService;
import com.sample.app.domain.AccountJEDI;
import com.sample.app.repository.AccountJEDIRepository;
import com.sample.app.repository.search.AccountJEDISearchRepository;
import com.sample.app.service.dto.AccountJEDIDTO;
import com.sample.app.service.mapper.AccountJEDIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AccountJEDI.
 */
@Service
@Transactional
public class AccountJEDIServiceImpl implements AccountJEDIService {

    private final Logger log = LoggerFactory.getLogger(AccountJEDIServiceImpl.class);

    private final AccountJEDIRepository accountJEDIRepository;

    private final AccountJEDIMapper accountJEDIMapper;

    private final AccountJEDISearchRepository accountJEDISearchRepository;

    public AccountJEDIServiceImpl(AccountJEDIRepository accountJEDIRepository, AccountJEDIMapper accountJEDIMapper, AccountJEDISearchRepository accountJEDISearchRepository) {
        this.accountJEDIRepository = accountJEDIRepository;
        this.accountJEDIMapper = accountJEDIMapper;
        this.accountJEDISearchRepository = accountJEDISearchRepository;
    }

    /**
     * Save a accountJEDI.
     *
     * @param accountJEDIDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AccountJEDIDTO save(AccountJEDIDTO accountJEDIDTO) {
        log.debug("Request to save AccountJEDI : {}", accountJEDIDTO);
        AccountJEDI accountJEDI = accountJEDIMapper.toEntity(accountJEDIDTO);
        accountJEDI = accountJEDIRepository.save(accountJEDI);
        AccountJEDIDTO result = accountJEDIMapper.toDto(accountJEDI);
        accountJEDISearchRepository.save(accountJEDI);
        return result;
    }

    /**
     * Get all the accountJEDIS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountJEDIDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountJEDIS");
        return accountJEDIRepository.findAll(pageable)
            .map(accountJEDIMapper::toDto);
    }


    /**
     * Get one accountJEDI by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountJEDIDTO> findOne(Long id) {
        log.debug("Request to get AccountJEDI : {}", id);
        return accountJEDIRepository.findById(id)
            .map(accountJEDIMapper::toDto);
    }

    /**
     * Delete the accountJEDI by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountJEDI : {}", id);
        accountJEDIRepository.deleteById(id);
        accountJEDISearchRepository.deleteById(id);
    }

    /**
     * Search for the accountJEDI corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccountJEDIDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccountJEDIS for query {}", query);
        return accountJEDISearchRepository.search(queryStringQuery(query), pageable)
            .map(accountJEDIMapper::toDto);
    }
}
