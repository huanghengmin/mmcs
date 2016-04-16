package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * StatSysterminalinfo entity. @author MyEclipse Persistence Tools
 */

public class StatSysterminalinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String idsystem;
	private Integer terminalsum;
	private Integer handterminalsum;
	private Integer carterminalsum;
	private Integer notebookterminalsum;
	private Integer othertypeterminalsum;
	private Integer yidonglinksum;
	private Integer dianxinlinksum;
	private Integer liantonglinksum;
	private Integer otherlinksum;
	private String ifprovincesum;
	private String ifinit;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public StatSysterminalinfo() {
	}

	/** minimal constructor */
	public StatSysterminalinfo(String idsystem) {
		this.idsystem = idsystem;
	}

	/** full constructor */
	public StatSysterminalinfo(String idsystem, Integer terminalsum,
			Integer handterminalsum, Integer carterminalsum,
			Integer notebookterminalsum, Integer othertypeterminalsum,
			Integer yidonglinksum, Integer dianxinlinksum,
			Integer liantonglinksum, Integer otherlinksum,
			String ifprovincesum, String ifinit, Timestamp updatetime) {
		this.idsystem = idsystem;
		this.terminalsum = terminalsum;
		this.handterminalsum = handterminalsum;
		this.carterminalsum = carterminalsum;
		this.notebookterminalsum = notebookterminalsum;
		this.othertypeterminalsum = othertypeterminalsum;
		this.yidonglinksum = yidonglinksum;
		this.dianxinlinksum = dianxinlinksum;
		this.liantonglinksum = liantonglinksum;
		this.otherlinksum = otherlinksum;
		this.ifprovincesum = ifprovincesum;
		this.ifinit = ifinit;
		this.updatetime = updatetime;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdsystem() {
		return this.idsystem;
	}

	public void setIdsystem(String idsystem) {
		this.idsystem = idsystem;
	}

	public Integer getTerminalsum() {
		return this.terminalsum;
	}

	public void setTerminalsum(Integer terminalsum) {
		this.terminalsum = terminalsum;
	}

	public Integer getHandterminalsum() {
		return this.handterminalsum;
	}

	public void setHandterminalsum(Integer handterminalsum) {
		this.handterminalsum = handterminalsum;
	}

	public Integer getCarterminalsum() {
		return this.carterminalsum;
	}

	public void setCarterminalsum(Integer carterminalsum) {
		this.carterminalsum = carterminalsum;
	}

	public Integer getNotebookterminalsum() {
		return this.notebookterminalsum;
	}

	public void setNotebookterminalsum(Integer notebookterminalsum) {
		this.notebookterminalsum = notebookterminalsum;
	}

	public Integer getOthertypeterminalsum() {
		return this.othertypeterminalsum;
	}

	public void setOthertypeterminalsum(Integer othertypeterminalsum) {
		this.othertypeterminalsum = othertypeterminalsum;
	}

	public Integer getYidonglinksum() {
		return this.yidonglinksum;
	}

	public void setYidonglinksum(Integer yidonglinksum) {
		this.yidonglinksum = yidonglinksum;
	}

	public Integer getDianxinlinksum() {
		return this.dianxinlinksum;
	}

	public void setDianxinlinksum(Integer dianxinlinksum) {
		this.dianxinlinksum = dianxinlinksum;
	}

	public Integer getLiantonglinksum() {
		return this.liantonglinksum;
	}

	public void setLiantonglinksum(Integer liantonglinksum) {
		this.liantonglinksum = liantonglinksum;
	}

	public Integer getOtherlinksum() {
		return this.otherlinksum;
	}

	public void setOtherlinksum(Integer otherlinksum) {
		this.otherlinksum = otherlinksum;
	}

	public String getIfprovincesum() {
		return this.ifprovincesum;
	}

	public void setIfprovincesum(String ifprovincesum) {
		this.ifprovincesum = ifprovincesum;
	}

	public String getIfinit() {
		return this.ifinit;
	}

	public void setIfinit(String ifinit) {
		this.ifinit = ifinit;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}