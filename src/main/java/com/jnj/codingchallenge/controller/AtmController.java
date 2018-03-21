package com.jnj.codingchallenge.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jnj.codingchallenge.bo.BalanceBo;
import com.jnj.codingchallenge.bo.WithdrawalBo;
import com.jnj.codingchallenge.exception.AtmException;
import com.jnj.codingchallenge.service.AtmService;

@RestController
@RequestMapping("/api")
public class AtmController {
	
	@Autowired
	private AtmService atmService;
	
	@GetMapping(value = "/balance/{accountNumber}/{pin}")
	@ResponseBody
	public BalanceBo getBalance(@PathVariable String accountNumber, @PathVariable String pin) throws AtmException{
		return atmService.getBalance(accountNumber, pin);
	}
	
	@GetMapping(value = "/withdraw/{accountNumber}/{pin}/{amount}")
	@ResponseBody	
	public WithdrawalBo getWithdrawal(@PathVariable String accountNumber, @PathVariable String pin, @PathVariable Double amount) throws AtmException{
		return atmService.withdraw(accountNumber, pin, BigDecimal.valueOf(amount));
	}

}
