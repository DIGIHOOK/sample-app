package com.sample.app.repository.search;

import com.sample.app.domain.AccountJEDI;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AccountJEDI entity.
 */
public interface AccountJEDISearchRepository extends ElasticsearchRepository<AccountJEDI, Long> {
}
