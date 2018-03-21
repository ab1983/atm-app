package com.jnj.codingchallenge.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jnj.codingchallenge.model.Account;

@Repository
public interface AccountDao extends CrudRepository<Account, String>{

}
