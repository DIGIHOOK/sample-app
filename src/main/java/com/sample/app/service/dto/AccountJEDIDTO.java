package com.sample.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AccountJEDI entity.
 */
public class AccountJEDIDTO implements Serializable {

    private Long id;

    private Long accountNum;

    private String accountName;

    private Long accountBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountJEDIDTO accountJEDIDTO = (AccountJEDIDTO) o;
        if (accountJEDIDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountJEDIDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountJEDIDTO{" +
            "id=" + getId() +
            ", accountNum=" + getAccountNum() +
            ", accountName='" + getAccountName() + "'" +
            ", accountBalance=" + getAccountBalance() +
            "}";
    }
}
