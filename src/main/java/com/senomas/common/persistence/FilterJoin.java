package com.senomas.common.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public interface FilterJoin<T, T1, T2> {

	Predicate toPredicate(Root<T1> root1, Root<T2> root2, CriteriaQuery<?> query, CriteriaBuilder builder);
	
	Selection<? extends T> getSelection(Root<T1> root1, Root<T2> root2, CriteriaQuery<?> query, CriteriaBuilder builder);

}
