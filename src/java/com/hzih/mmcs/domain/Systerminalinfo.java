package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Systerminalinfo entity. @author MyEclipse Persistence Tools
 */

public class Systerminalinfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String idsystem;
	private Long idterminal;
	private String terminalName;
	private String terminaltype;
	private String termianlOutlink;
	private String termianlos;
	private String termianlband;
	private String cardtype;
	private String cardname;
	private String cardversion;
	private String username;
	private String userid;
	private String policenumber;
	private String userdepart;
	private String userzone;
	private Timestamp regtime;
	private String ifcancel;

	// Constructors

	/** default constructor */
	public Systerminalinfo() {
	}

	/** full constructor */
	public Systerminalinfo(String idsystem, Long idterminal,
			String terminalName, String terminaltype, String termianlOutlink,
			String termianlos, String termianlband, String cardtype,
			String cardname, String cardversion, String username,
			String userid, String policenumber, String userdepart,
			String userzone, Timestamp regtime, String ifcancel) {
		this.idsystem = idsystem;
		this.idterminal = idterminal;
		this.terminalName = terminalName;
		this.terminaltype = terminaltype;
		this.termianlOutlink = termianlOutlink;
		this.termianlos = termianlos;
		this.termianlband = termianlband;
		this.cardtype = cardtype;
		this.cardname = cardname;
		this.cardversion = cardversion;
		this.username = username;
		this.userid = userid;
		this.policenumber = policenumber;
		this.userdepart = userdepart;
		this.userzone = userzone;
		this.regtime = regtime;
		this.ifcancel = ifcancel;
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

	public Long getIdterminal() {
		return this.idterminal;
	}

	public void setIdterminal(Long idterminal) {
		this.idterminal = idterminal;
	}

	public String getTerminalName() {
		return this.terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTerminaltype() {
		return this.terminaltype;
	}

	public void setTerminaltype(String terminaltype) {
		this.terminaltype = terminaltype;
	}

	public String getTermianlOutlink() {
		return this.termianlOutlink;
	}

	public void setTermianlOutlink(String termianlOutlink) {
		this.termianlOutlink = termianlOutlink;
	}

	public String getTermianlos() {
		return this.termianlos;
	}

	public void setTermianlos(String termianlos) {
		this.termianlos = termianlos;
	}

	public String getTermianlband() {
		return this.termianlband;
	}

	public void setTermianlband(String termianlband) {
		this.termianlband = termianlband;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardname() {
		return this.cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public String getCardversion() {
		return this.cardversion;
	}

	public void setCardversion(String cardversion) {
		this.cardversion = cardversion;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPolicenumber() {
		return this.policenumber;
	}

	public void setPolicenumber(String policenumber) {
		this.policenumber = policenumber;
	}

	public String getUserdepart() {
		return this.userdepart;
	}

	public void setUserdepart(String userdepart) {
		this.userdepart = userdepart;
	}

	public String getUserzone() {
		return this.userzone;
	}

	public void setUserzone(String userzone) {
		this.userzone = userzone;
	}

	public Timestamp getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Timestamp regtime) {
		this.regtime = regtime;
	}

	public String getIfcancel() {
		return this.ifcancel;
	}

	public void setIfcancel(String ifcancel) {
		this.ifcancel = ifcancel;
	}

}