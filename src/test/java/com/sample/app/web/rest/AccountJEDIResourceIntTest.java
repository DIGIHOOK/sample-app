package com.sample.app.web.rest;

import com.sample.app.SampleAppApp;

import com.sample.app.domain.AccountJEDI;
import com.sample.app.repository.AccountJEDIRepository;
import com.sample.app.repository.search.AccountJEDISearchRepository;
import com.sample.app.service.AccountJEDIService;
import com.sample.app.service.dto.AccountJEDIDTO;
import com.sample.app.service.mapper.AccountJEDIMapper;
import com.sample.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.sample.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountJEDIResource REST controller.
 *
 * @see AccountJEDIResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleAppApp.class)
public class AccountJEDIResourceIntTest {

    private static final Long DEFAULT_ACCOUNT_NUM = 1L;
    private static final Long UPDATED_ACCOUNT_NUM = 2L;

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_BALANCE = 1L;
    private static final Long UPDATED_ACCOUNT_BALANCE = 2L;

    @Autowired
    private AccountJEDIRepository accountJEDIRepository;


    @Autowired
    private AccountJEDIMapper accountJEDIMapper;
    

    @Autowired
    private AccountJEDIService accountJEDIService;

    /**
     * This repository is mocked in the com.sample.app.repository.search test package.
     *
     * @see com.sample.app.repository.search.AccountJEDISearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountJEDISearchRepository mockAccountJEDISearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccountJEDIMockMvc;

    private AccountJEDI accountJEDI;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountJEDIResource accountJEDIResource = new AccountJEDIResource(accountJEDIService);
        this.restAccountJEDIMockMvc = MockMvcBuilders.standaloneSetup(accountJEDIResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountJEDI createEntity(EntityManager em) {
        AccountJEDI accountJEDI = new AccountJEDI()
            .accountNum(DEFAULT_ACCOUNT_NUM)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountBalance(DEFAULT_ACCOUNT_BALANCE);
        return accountJEDI;
    }

    @Before
    public void initTest() {
        accountJEDI = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountJEDI() throws Exception {
        int databaseSizeBeforeCreate = accountJEDIRepository.findAll().size();

        // Create the AccountJEDI
        AccountJEDIDTO accountJEDIDTO = accountJEDIMapper.toDto(accountJEDI);
        restAccountJEDIMockMvc.perform(post("/api/account-jedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountJEDIDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountJEDI in the database
        List<AccountJEDI> accountJEDIList = accountJEDIRepository.findAll();
        assertThat(accountJEDIList).hasSize(databaseSizeBeforeCreate + 1);
        AccountJEDI testAccountJEDI = accountJEDIList.get(accountJEDIList.size() - 1);
        assertThat(testAccountJEDI.getAccountNum()).isEqualTo(DEFAULT_ACCOUNT_NUM);
        assertThat(testAccountJEDI.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testAccountJEDI.getAccountBalance()).isEqualTo(DEFAULT_ACCOUNT_BALANCE);

        // Validate the AccountJEDI in Elasticsearch
        verify(mockAccountJEDISearchRepository, times(1)).save(testAccountJEDI);
    }

    @Test
    @Transactional
    public void createAccountJEDIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountJEDIRepository.findAll().size();

        // Create the AccountJEDI with an existing ID
        accountJEDI.setId(1L);
        AccountJEDIDTO accountJEDIDTO = accountJEDIMapper.toDto(accountJEDI);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountJEDIMockMvc.perform(post("/api/account-jedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountJEDIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountJEDI in the database
        List<AccountJEDI> accountJEDIList = accountJEDIRepository.findAll();
        assertThat(accountJEDIList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccountJEDI in Elasticsearch
        verify(mockAccountJEDISearchRepository, times(0)).save(accountJEDI);
    }

    @Test
    @Transactional
    public void getAllAccountJEDIS() throws Exception {
        // Initialize the database
        accountJEDIRepository.saveAndFlush(accountJEDI);

        // Get all the accountJEDIList
        restAccountJEDIMockMvc.perform(get("/api/account-jedis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountJEDI.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNum").value(hasItem(DEFAULT_ACCOUNT_NUM.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())));
    }
    

    @Test
    @Transactional
    public void getAccountJEDI() throws Exception {
        // Initialize the database
        accountJEDIRepository.saveAndFlush(accountJEDI);

        // Get the accountJEDI
        restAccountJEDIMockMvc.perform(get("/api/account-jedis/{id}", accountJEDI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountJEDI.getId().intValue()))
            .andExpect(jsonPath("$.accountNum").value(DEFAULT_ACCOUNT_NUM.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountBalance").value(DEFAULT_ACCOUNT_BALANCE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAccountJEDI() throws Exception {
        // Get the accountJEDI
        restAccountJEDIMockMvc.perform(get("/api/account-jedis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountJEDI() throws Exception {
        // Initialize the database
        accountJEDIRepository.saveAndFlush(accountJEDI);

        int databaseSizeBeforeUpdate = accountJEDIRepository.findAll().size();

        // Update the accountJEDI
        AccountJEDI updatedAccountJEDI = accountJEDIRepository.findById(accountJEDI.getId()).get();
        // Disconnect from session so that the updates on updatedAccountJEDI are not directly saved in db
        em.detach(updatedAccountJEDI);
        updatedAccountJEDI
            .accountNum(UPDATED_ACCOUNT_NUM)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountBalance(UPDATED_ACCOUNT_BALANCE);
        AccountJEDIDTO accountJEDIDTO = accountJEDIMapper.toDto(updatedAccountJEDI);

        restAccountJEDIMockMvc.perform(put("/api/account-jedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountJEDIDTO)))
            .andExpect(status().isOk());

        // Validate the AccountJEDI in the database
        List<AccountJEDI> accountJEDIList = accountJEDIRepository.findAll();
        assertThat(accountJEDIList).hasSize(databaseSizeBeforeUpdate);
        AccountJEDI testAccountJEDI = accountJEDIList.get(accountJEDIList.size() - 1);
        assertThat(testAccountJEDI.getAccountNum()).isEqualTo(UPDATED_ACCOUNT_NUM);
        assertThat(testAccountJEDI.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testAccountJEDI.getAccountBalance()).isEqualTo(UPDATED_ACCOUNT_BALANCE);

        // Validate the AccountJEDI in Elasticsearch
        verify(mockAccountJEDISearchRepository, times(1)).save(testAccountJEDI);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountJEDI() throws Exception {
        int databaseSizeBeforeUpdate = accountJEDIRepository.findAll().size();

        // Create the AccountJEDI
        AccountJEDIDTO accountJEDIDTO = accountJEDIMapper.toDto(accountJEDI);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccountJEDIMockMvc.perform(put("/api/account-jedis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountJEDIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountJEDI in the database
        List<AccountJEDI> accountJEDIList = accountJEDIRepository.findAll();
        assertThat(accountJEDIList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccountJEDI in Elasticsearch
        verify(mockAccountJEDISearchRepository, times(0)).save(accountJEDI);
    }

    @Test
    @Transactional
    public void deleteAccountJEDI() throws Exception {
        // Initialize the database
        accountJEDIRepository.saveAndFlush(accountJEDI);

        int databaseSizeBeforeDelete = accountJEDIRepository.findAll().size();

        // Get the accountJEDI
        restAccountJEDIMockMvc.perform(delete("/api/account-jedis/{id}", accountJEDI.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountJEDI> accountJEDIList = accountJEDIRepository.findAll();
        assertThat(accountJEDIList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccountJEDI in Elasticsearch
        verify(mockAccountJEDISearchRepository, times(1)).deleteById(accountJEDI.getId());
    }

    @Test
    @Transactional
    public void searchAccountJEDI() throws Exception {
        // Initialize the database
        accountJEDIRepository.saveAndFlush(accountJEDI);
        when(mockAccountJEDISearchRepository.search(queryStringQuery("id:" + accountJEDI.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accountJEDI), PageRequest.of(0, 1), 1));
        // Search the accountJEDI
        restAccountJEDIMockMvc.perform(get("/api/_search/account-jedis?query=id:" + accountJEDI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountJEDI.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNum").value(hasItem(DEFAULT_ACCOUNT_NUM.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountJEDI.class);
        AccountJEDI accountJEDI1 = new AccountJEDI();
        accountJEDI1.setId(1L);
        AccountJEDI accountJEDI2 = new AccountJEDI();
        accountJEDI2.setId(accountJEDI1.getId());
        assertThat(accountJEDI1).isEqualTo(accountJEDI2);
        accountJEDI2.setId(2L);
        assertThat(accountJEDI1).isNotEqualTo(accountJEDI2);
        accountJEDI1.setId(null);
        assertThat(accountJEDI1).isNotEqualTo(accountJEDI2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountJEDIDTO.class);
        AccountJEDIDTO accountJEDIDTO1 = new AccountJEDIDTO();
        accountJEDIDTO1.setId(1L);
        AccountJEDIDTO accountJEDIDTO2 = new AccountJEDIDTO();
        assertThat(accountJEDIDTO1).isNotEqualTo(accountJEDIDTO2);
        accountJEDIDTO2.setId(accountJEDIDTO1.getId());
        assertThat(accountJEDIDTO1).isEqualTo(accountJEDIDTO2);
        accountJEDIDTO2.setId(2L);
        assertThat(accountJEDIDTO1).isNotEqualTo(accountJEDIDTO2);
        accountJEDIDTO1.setId(null);
        assertThat(accountJEDIDTO1).isNotEqualTo(accountJEDIDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountJEDIMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountJEDIMapper.fromId(null)).isNull();
    }
}
