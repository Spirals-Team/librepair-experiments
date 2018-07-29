package com.longmaple.ttmall.goodservice.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="t_mall_goods")
public class TMallGood implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String description;

	private Integer expirable;

	private String gId;

	private String gName;

	private String iconUrl;

	private BigDecimal inPrice;

	private BigDecimal price;

	private String showPopup;

	private Integer sold;

	private Integer stock;

	private int subcategoryId;

	private Integer supplierId;

	private String unit;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String upForSale;

	private Integer warehouseId;
	
	private String isRecommend;

	public TMallGood() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getExpirable() {
		return this.expirable;
	}

	public void setExpirable(Integer expirable) {
		this.expirable = expirable;
	}

	public String getGId() {
		return this.gId;
	}

	public void setGId(String gId) {
		this.gId = gId;
	}

	public String getGName() {
		return this.gName;
	}

	public void setGName(String gName) {
		this.gName = gName;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public BigDecimal getInPrice() {
		return this.inPrice;
	}

	public void setInPrice(BigDecimal inPrice) {
		this.inPrice = inPrice;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getShowPopup() {
		return this.showPopup;
	}

	public void setShowPopup(String showPopup) {
		this.showPopup = showPopup;
	}

	public Integer getSold() {
		return this.sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public int getSubcategoryId() {
		return this.subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public Integer getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpForSale() {
		return this.upForSale;
	}

	public void setUpForSale(String upForSale) {
		this.upForSale = upForSale;
	}

	public Integer getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

}