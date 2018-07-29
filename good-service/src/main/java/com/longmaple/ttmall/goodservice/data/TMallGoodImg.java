package com.longmaple.ttmall.goodservice.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_mall_goods_photos")
public class TMallGoodImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private long gId;

	private String imgUrl;

	public TMallGoodImg() {
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

	public long getGId() {
		return this.gId;
	}

	public void setGId(long gId) {
		this.gId = gId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}