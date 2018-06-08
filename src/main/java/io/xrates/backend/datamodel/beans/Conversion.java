package io.xrates.backend.datamodel.beans;

import java.util.Date;

import io.xrates.backend.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Check;

@Entity
@Table(name = "conversion")
@Check(constraints = "conversion_rate >= 0")
public class Conversion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "conversion_id")
	private long conversionId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "conversion_timestamp", nullable = false)
	private Date conversionTimestamp;
	
	@Column(name = "conversion_rate", precision = 6)
	private double conversionRate;
	
	@Column(name = "status")
	private String status = Constants.UNPROCESSED;
	
	@Column(name = "percent_change")
	private double percentChange = 0.0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false)
	private Service service;
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(double percentChange) {
		this.percentChange = percentChange;
	}

	public long getConversionId() {
		return conversionId;
	}

	public Date getConversionTimestamp() {
		return conversionTimestamp;
	}

	public void setConversionTimestamp(Date conversionTimestamp) {
		this.conversionTimestamp = conversionTimestamp;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}
	
}
