package com.senomas.common.persistence;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;

import com.senomas.common.U;

public abstract class AbstractCustomRepository {
	
	protected <T> PageRequestId<T> findWithSpecification(String requestId, EntityManager entityManager, Pageable pageable, Filter<T> filter, Class<T> type) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		long total;
		{
			CriteriaQuery<Long> cqTotal = builder.createQuery(Long.class);
			Root<T> root = cqTotal.from(type);
			if (filter != null) {
				Predicate predicate = filter.toPredicate(root, cqTotal, builder);
				if (predicate != null) cqTotal.where(predicate);
			}
			cqTotal.select(builder.count(root));
			total = entityManager.createQuery(cqTotal).getSingleResult();
		}
		
		CriteriaQuery<T> cq = builder.createQuery(type);
		Root<T> root = cq.from(type);
		if (filter != null) {
			Predicate predicate = filter.toPredicate(root, cq, builder);
			if (predicate != null) cq.where(predicate);
		}
		cq.select(root);
		if (pageable.getSort() != null) {
			List<Order> orders = new LinkedList<Order>();
			for (Iterator<org.springframework.data.domain.Sort.Order> itr = pageable.getSort().iterator(); itr.hasNext(); ) {
				org.springframework.data.domain.Sort.Order order = itr.next();
				String sx[] = order.getProperty().split("\\.");
				Path<Object> p = root.get(sx[0]);
				for (int i=1, il=sx.length; i<il; i++) p = p.get(sx[i]);
				if (order.isAscending()) {
					orders.add(builder.asc(p));
				} else {
					orders.add(builder.desc(p));
				}
			}
			cq.orderBy(orders);
		}
		TypedQuery<T> qry = entityManager.createQuery(cq);
		qry.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		return new PageRequestIdImpl<T>(qry.getResultList(), pageable, total, requestId);
	}
	
	protected <T, T1> PageRequestId<T> findWithSpecification(String requestId, EntityManager entityManager, Pageable pageable, Filter2<T, T1> filter, Class<T> type, Class<T1> type1) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		long total;
		{
			CriteriaQuery<Long> cqTotal = builder.createQuery(Long.class);
			Root<T1> root = cqTotal.from(type1);
			if (filter != null) {
				Predicate predicate = filter.toPredicate(root, cqTotal, builder);
				if (predicate != null) cqTotal.where(predicate);
			}
			cqTotal.select(builder.count(root));
			total = entityManager.createQuery(cqTotal).getSingleResult();
		}
		
		CriteriaQuery<T> cq = builder.createQuery(type);
		Root<T1> root = cq.from(type1);
		if (filter != null) {
			Predicate predicate = filter.toPredicate(root, cq, builder);
			if (predicate != null) cq.where(predicate);
		}
		cq.select(filter.getSelection(root, cq, builder));
		if (pageable.getSort() != null) {
			List<Order> orders = new LinkedList<Order>();
			for (Iterator<org.springframework.data.domain.Sort.Order> itr = pageable.getSort().iterator(); itr.hasNext(); ) {
				org.springframework.data.domain.Sort.Order order = itr.next();
				String sx[] = order.getProperty().split("\\.");
				Path<Object> p = root.get(sx[0]);
				for (int i=1, il=sx.length; i<il; i++) p = p.get(sx[i]);
				if (order.isAscending()) {
					orders.add(builder.asc(p));
				} else {
					orders.add(builder.desc(p));
				}
			}
			cq.orderBy(orders);
		}
		TypedQuery<T> qry = entityManager.createQuery(cq);
		qry.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		return new PageRequestIdImpl<T>(qry.getResultList(), pageable, total, requestId);
	}
	
	protected <T, T1, T2> PageRequestId<T> findWithSpecification(String requestId, EntityManager entityManager, Pageable pageable, Filter3<T, T1, T2> specification, Class<T> type, Class<T1> type1, Class<T2> type2) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		long total;
		{
			CriteriaQuery<Long> cqTotal = builder.createQuery(Long.class);
			Root<T1> root1 = cqTotal.from(type1);
			Root<T2> root2 = cqTotal.from(type2);
			Predicate predicate = specification.toPredicate(root1, root2, cqTotal, builder);
			if (predicate != null) cqTotal.where(predicate);
			cqTotal.select(builder.count(root1));
			total = entityManager.createQuery(cqTotal).getSingleResult();
		}
		
		CriteriaQuery<T> cq = builder.createQuery(type);
		Root<T1> root1 = cq.from(type1);
		Root<T2> root2 = cq.from(type2);
		Predicate predicate = specification.toPredicate(root1, root2, cq, builder);
		if (predicate != null) cq.where(predicate);
		cq.select(specification.getSelection(root1, root2, cq, builder));
		if (pageable.getSort() != null) {
			List<Order> orders = new LinkedList<Order>();
			for (Iterator<org.springframework.data.domain.Sort.Order> itr = pageable.getSort().iterator(); itr.hasNext(); ) {
				org.springframework.data.domain.Sort.Order order = itr.next();
				String sx[] = order.getProperty().split("\\.");
				Path<Object> p;
				if (sx[0].equals("t1")) {
					p = root1.get(sx[1]);
				} else if (sx[0].equals("t2")) {
					p = root2.get(sx[1]);
				} else {
					throw new RuntimeException("Invalid order "+U.dump(order));
				}
				for (int i=2, il=sx.length; i<il; i++) p = p.get(sx[i]);
				if (order.isAscending()) {
					orders.add(builder.asc(p));
				} else {
					orders.add(builder.desc(p));
				}
			}
			cq.orderBy(orders);
		}
		TypedQuery<T> qry = entityManager.createQuery(cq);
		qry.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
		return new PageRequestIdImpl<T>(qry.getResultList(), pageable, total, requestId);
	}

}
