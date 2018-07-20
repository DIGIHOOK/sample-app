package com.sample.app.service.mapper;

import com.sample.app.domain.*;
import com.sample.app.service.dto.AccountJEDIDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AccountJEDI and its DTO AccountJEDIDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountJEDIMapper extends EntityMapper<AccountJEDIDTO, AccountJEDI> {



    default AccountJEDI fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountJEDI accountJEDI = new AccountJEDI();
        accountJEDI.setId(id);
        return accountJEDI;
    }
}
