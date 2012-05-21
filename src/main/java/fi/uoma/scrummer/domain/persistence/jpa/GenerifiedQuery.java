package fi.uoma.scrummer.domain.persistence.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * Extends the Query interface to provide type safe method alternatives
 * for fetching results. Decorates the JPA provider implementation of Query
 * with {@link GenerifiedQuery#getSingleResultSafe()} and {@link GenerifiedQuery#getResultListSafe()}.
 * 
 * @author bertell
 *
 * @param <E>
 */
public class GenerifiedQuery<E> implements Query {
	
	private Query query;
	
	public GenerifiedQuery(Query query) {
		this.query = query;
	}
	
	@Override
	public int executeUpdate() {
		return query.executeUpdate();
	}

	/**
	 * Use getSingleResultSafe instead
	 */
	@Deprecated
	@Override
	public Object getSingleResult() {
		return query.getSingleResult();
	}

	/**
	 * Type safe version of getSingleResult()
	 * @return
	 */
	public E getSingleResultSafe() {
		@SuppressWarnings("unchecked")
		E result = (E)query.getSingleResult();		
		return result;
	}
	
	@Override
	public GenerifiedQuery<E> setFirstResult(int startPosition) {
		query.setFirstResult(startPosition);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setFlushMode(FlushModeType flushMode) {
		query.setFlushMode(flushMode);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setHint(String hintName, Object value) {
		query.setHint(hintName, value);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setMaxResults(int maxResult) {
		query.setMaxResults(maxResult);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(String name, Object value) {
		query.setParameter(name, value);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(int position, Object value) {
		query.setParameter(position, value);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(String name, Date value, TemporalType temporalType) {
		query.setParameter(name, value, temporalType);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(String name, Calendar value, TemporalType temporalType) {
		query.setParameter(name, value, temporalType);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(int position, Date value, TemporalType temporalType) {
		query.setParameter(position, value, temporalType);
		return this;
	}

	@Override
	public GenerifiedQuery<E> setParameter(int position, Calendar value, TemporalType temporalType) {
		query.setParameter(position, value, temporalType);
		return this;
	}

	/**
	 * Use getResultListSafe instead
	 */
	@Override
	@Deprecated
	public List<?> getResultList() {
		return query.getResultList();
	}
	
	/**
	 * Type safe version of getResultList()
	 */
	public List<E> getResultListSafe() {
		@SuppressWarnings("unchecked")
		List<E> result = (List<E>)query.getResultList();	
		return result;
	}
}