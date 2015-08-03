package com.senomas.common.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;

public class PageRequestIdImpl<T> extends org.springframework.data.domain.PageImpl<T> implements PageRequestId<T> {
	private static final long serialVersionUID = 1L;

	final String requestId;
	
	public PageRequestIdImpl(List<T> content, Pageable pageable, long total, String requestId) {
		super(content, pageable, total);
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

}
