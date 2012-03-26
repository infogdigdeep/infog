package com.infog.web.dto;

import java.util.List;

import com.infog.common.domain.User;


public class GenericListDto<T> {

	private List<T> records;

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}


}
