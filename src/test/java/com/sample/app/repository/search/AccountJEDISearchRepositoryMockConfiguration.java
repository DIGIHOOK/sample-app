package com.sample.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AccountJEDISearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AccountJEDISearchRepositoryMockConfiguration {

    @MockBean
    private AccountJEDISearchRepository mockAccountJEDISearchRepository;

}
