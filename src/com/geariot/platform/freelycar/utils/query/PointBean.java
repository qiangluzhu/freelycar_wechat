package com.geariot.platform.freelycar.utils.query;

import java.util.Date;

public class PointBean {
	
	public PointBean(Double point, Date commentDate) {
		super();
		this.point = point;
		this.commentDate = commentDate;
	}
	private Double point;
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	private Date commentDate;
}
