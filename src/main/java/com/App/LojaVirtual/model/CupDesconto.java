package com.App.LojaVirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cup_desconto")
@SequenceGenerator(name = "seq_cup_desconto", sequenceName = "seq_cup_desconto", allocationSize = 1, initialValue = 1)
public class CupDesconto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cup_desconto")
	private Long id;
	
	@Column(nullable = false)
	private String codDesc;
	
	private BigDecimal valorRealDesc;
	
	private BigDecimal valorPorcenDesc;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataValidadeCupom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodDesc() {
		return codDesc;
	}

	public void setCodDesc(String codDesc) {
		this.codDesc = codDesc;
	}

	public BigDecimal getValorRealDesc() {
		return valorRealDesc;
	}

	public void setValorRealDesc(BigDecimal valorRealDesc) {
		this.valorRealDesc = valorRealDesc;
	}

	public BigDecimal getValorPorcebDesc() {
		return valorPorcenDesc;
	}

	public void setValorPorcebDesc(BigDecimal valorPorcebDesc) {
		this.valorPorcenDesc = valorPorcebDesc;
	}

	public Date getDataValidadeCupom() {
		return dataValidadeCupom;
	}

	public void setDataValidadeCupom(Date dataValidadeCupom) {
		this.dataValidadeCupom = dataValidadeCupom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupDesconto other = (CupDesconto) obj;
		return Objects.equals(id, other.id);
	}
	
}
