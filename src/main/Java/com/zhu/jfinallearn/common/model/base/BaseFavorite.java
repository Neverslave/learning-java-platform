package com.zhu.jfinallearn.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFavorite<M extends BaseFavorite<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setAccountId(java.lang.Integer accountId) {
		set("accountId", accountId);
	}
	
	public java.lang.Integer getAccountId() {
		return getInt("accountId");
	}

	public void setRefType(java.lang.Integer refType) {
		set("refType", refType);
	}
	
	public java.lang.Integer getRefType() {
		return getInt("refType");
	}

	public void setRefId(java.lang.Integer refId) {
		set("refId", refId);
	}
	
	public java.lang.Integer getRefId() {
		return getInt("refId");
	}

	public void setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
	}
	
	public java.util.Date getCreateAt() {
		return get("createAt");
	}

}
