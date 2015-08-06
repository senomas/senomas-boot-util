package com.senomas.common.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public interface Filter2<T, T1> {

	Predicate toPredicate(Root<T1> root, CriteriaQuery<?> query, CriteriaBuilder builder);
	
	Selection<? extends T> getSelection(Root<T1> root1, CriteriaQuery<?> query, CriteriaBuilder builder);

}
