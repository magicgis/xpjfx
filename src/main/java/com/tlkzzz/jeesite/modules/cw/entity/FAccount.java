/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/tlkzzz/jeesite">JeeSite</a> All rights reserved.
 */
package com.tlkzzz.jeesite.modules.cw.entity;

import org.hibernate.validator.constraints.Length;

import com.tlkzzz.jeesite.common.persistence.DataEntity;

/**
 * 账户管理Entity
 * @author xrc
 * @version 2017-04-05
 */
public class FAccount extends DataEntity<FAccount> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 账户名称
	private String bankName;		// 开户行
	private String bankCode;		// 银行卡号
	private String accountBalance;		// 帐户余额
	private String accountType;		// 账户类型
	private String subject_id;       //科目类别ID
	
	public FAccount() {
		super();
	}

	public FAccount(String id){
		super(id);
	}

	@Length(min=0, max=100, message="账户名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="开户行长度必须介于 0 和 100 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=100, message="银行卡号长度必须介于 0 和 100 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	@Length(min=1, max=1, message="账户类型长度必须介于 1 和 1 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Length(min=0, max=64, message="科目类别ID长度必须介于 0 和 64 之间")
	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
}