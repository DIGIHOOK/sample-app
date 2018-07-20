package com.sample.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AccountJEDI.
 */
@Entity
@Table(name = "account_jedi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "accountjedi")
public class AccountJEDI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_num")
    private Long accountNum;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_balance")
    private Long accountBalance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public AccountJEDI accountNum(Long accountNum) {
        this.accountNum = accountNum;
        return this;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountName() {
        return accountName;
    }

    public AccountJEDI accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public AccountJEDI accountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountJEDI accountJEDI = (AccountJEDI) o;
        if (accountJEDI.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountJEDI.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountJEDI{" +
            "id=" + getId() +
            ", accountNum=" + getAccountNum() +
            ", accountName='" + getAccountName() + "'" +
            ", accountBalance=" + getAccountBalance() +
            "}";
    }
}
