package com.ringtop.common.dao.impl;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.exception.BaseException;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.BeanUtils;
import com.ringtop.common.util.ExceptionToString;
import com.ringtop.common.util.PageUtil;
import com.ringtop.common.util.ResultPage;

/***************************************************************************
 * 公共底层
 * @author Administrator
 *
 * @param <T>
 */
public final class HibernateBaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	//log4j写日志
	private Logger logger = Logger.getLogger(HibernateBaseDaoImpl.class);
	
	private static Map<String, String> mapTableEntity = new HashMap<String, String>();

	private static Map<String, String> mapTablePK = new HashMap<String, String>();

	public HibernateBaseDaoImpl() {
		
	}

	public HibernateBaseDaoImpl(Class<T> clazz, String pkField) {
		this.clazz = clazz;
		this.pkField = pkField;
	}

	private Class<T> clazz;

	private String pkField;
	

	/***************************************************************************
	 * 按主键查找
	 * @param entity
	 * @return
	 */
	public T getByPk(Serializable i) {
		return (T) this.getHibernateTemplate().get(clazz, i);
	}
	

	/***************************************************************************
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable save(T entity) {
		
		try {
			
			logger.info(" sava begin : " + entity.getClass().getName());
			
			Serializable serializable = this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
			
			logger.info(" sava end : " + entity.getClass().getName());
			
			return serializable;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(ExceptionToString.getTrace(e));
			return null;
		}
	}
	
	
	/***************************************************************************
	 * 保存实体或更新实体
	 * @param entity
	 */
	public void merge(T entity) {
		
		try {
			this.getHibernateTemplate().merge(entity);
		} catch (Exception e) {
			logger.info(ExceptionToString.getTrace(e));
			e.printStackTrace();
		}
	}

	
	/***************************************************************************
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public void update(T entity) {
		try {
			this.getHibernateTemplate().merge(entity);
		} catch (Exception e) {
			logger.info(ExceptionToString.getTrace(e));
			e.printStackTrace();
		}
	}

	
	/***************************************************************************
	 * 保存或更新
	 * 
	 * @param entity
	 * @return
	 */
	public void saveOrUpdate(T entity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.info(ExceptionToString.getTrace(e));
			e.printStackTrace();
		}
	}
	
	
	/***************************************************************************
	 * 批量添加更新
	 * 
	 * @param list
	 */
	public void saveOrUpdateALL(Collection<T> list) {
		
		if(Assert.notEmpty(list)) {
			logger.info(clazz.getName()+ ": " + list.toArray().toString());
			
			this.getHibernateTemplate().saveOrUpdateAll(list);
			
			logger.info("批量添加 " + clazz.getName() + " 信息成功");
		}
	}

	
	/***************************************************************************
	 * 按HQL获得单一实体
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getByHql(String hql, Object[] values) {
		
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(true);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (T) query.uniqueResult();
	}

	
	/***************************************************************************
	 * 按HQL获得实体集合
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHql(String hql, Object[] values) {
		
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(true);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		return (List<T>) query.list();
	}
	
	
	/***************************************************************************
	 * 删除
	 * @param entity
	 * @return
	 */
	public void delete(T entity) {
		
		try {
			logger.info("begin 删除 ："  + entity.getClass().getName());
			
			this.getHibernateTemplate().delete(entity);
			
			logger.info(" end 删除 ："  + entity.getClass().getName() + " 成功");
			
		} catch (DataAccessException e) {
			logger.info(ExceptionToString.getTrace(e));
			e.printStackTrace();
		}
	}
	

	/***************************************************************************
	 * 按主键数组删除实体
	 * 
	 * @param entity
	 * @return
	 */
	public long deleteByPrimaryKeys(Serializable[] pks) {
		
		StringBuffer buffer = new StringBuffer(100);
		if (pks != null) {
			for (int i = 0; i < pks.length; i++) {
				buffer.append(pks[i].toString());
				buffer.append(",");
			}
		}
		
		String sql = "delete from " + clazz.getName() + " where " + pkField + " in(" + buffer.substring(0, buffer.length() - 1) + ")";
		
		logger.info(sql);
		
		Query query = this.getSession().createQuery(sql);
		
		return query.executeUpdate();
		
		
	}

	
	/***************************************************************************
	 * 得到数量
	 * 
	 * @param entity
	 * @return
	 */
	public int getAllEntityCount() {
		
		if (mapTablePK.isEmpty() || mapTableEntity.isEmpty()) {
			getMetaData();
		}
		String tableName = mapTableEntity.get(clazz.getName().toString().replaceAll("class ", ""));
		
		String pkName = mapTablePK.get(tableName);
		String sql = "select count(" + pkName + ") from " + tableName;
		logger.info(sql);
		
		Query query = this.getSession().createQuery(sql);
		query.setCacheable(true);
		
		return Integer.parseInt(query.list().get(0).toString());
	}

	
	/***************************************************************************
	 * 得到数量
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public int getAllEntityCount(String sql, Object[] values) {
		
		if (!Assert.hasText(sql)) {
			return -1;
		}
		
		getMetaData();
		String tableName = mapTableEntity.get(clazz.getName().toString());
		
		String pkName = mapTablePK.get(tableName);
		 
		int index = sql.indexOf("order by");
		if (null != sql && index != -1) {
			sql = sql.substring(0, index - 1);
		}
		
		sql = "select count(a." + pkName + ") from (select " + sql + " ) a ";
		logger.info(sql);
		
		Query query = this.getSession().createSQLQuery(sql);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		return Integer.parseInt(query.list().get(0).toString());
	}



	/***************************************************************************
	 * 得到数据库信息
	 */
	@SuppressWarnings("unchecked")
	public void getMetaData() {
		
		if (mapTablePK.isEmpty() || mapTableEntity.isEmpty()) {
			
			SessionFactory factory = this.getHibernateTemplate().getSessionFactory();
			Map metaMap = factory.getAllClassMetadata();
			Set set = metaMap.keySet();
			AbstractEntityPersister classMetadata = null;
			String tableName = null;
			String className = null;
			String pk = null;
			
			for (Object obj : set) {
				classMetadata = (AbstractEntityPersister) metaMap.get(obj);
				tableName = classMetadata.getTableName().toLowerCase();
				className = classMetadata.getEntityMetamodel().getName();
				pk = classMetadata.getKeyColumnNames()[0];
				mapTableEntity.put(className, tableName);
				mapTablePK.put(tableName, pk);
			}
		}
	}


	/***************************************************************************
	 * SQL删除
	 * 
	 * @param list
	 */
	public int deleteBySQL(String sql, Object[] values) {
		
		logger.info(sql);
		
		SQLQuery query = this.getSession().createSQLQuery(sql);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		return query.executeUpdate();
	}

	
	/***************************************************************************
	 * HQL删除
	 * 
	 * @param list
	 */
	public int deleteByHQL(String hql, Object[] values) {
		
		logger.info(hql);
		
		Query query = this.getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		return query.executeUpdate();
	}

	
	/***************************************************************************
	 * 批量删除实体
	 * 
	 * @param list
	 */
	public void deleteALL(Collection<T> list) {
		this.getHibernateTemplate().deleteAll(list);
	}
	
	
	/***************************************************************************
	 * 分页查询对应实体
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> ResultPage pagedQuery(T entity){
		
		Criteria criteria =  getCriteria(entity.getClass());
		criteria.add(Example.create(entity));
		
		return pagedQuery(criteria);
	}
	
	
	/***************************************************************************
	 * 创建Criteria对象 降低方法的级别
	 * @param criterion
	 */
	private Criteria getCriteria(Class<?> entityClass){
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}
	
	
	/***************************************************************************
	 * 私有方法：
	 * 
	 * 分页查询函数，使用Criteria.
	 * @param pageNo 页号
	 */
	@SuppressWarnings("unchecked")
	private ResultPage pagedQuery(Criteria criteria){
		
		//在这里从PageUtil里面来拿pageNo,和pageSize
		int pageNo = PageUtil.getPageNo();
		int pageSize = PageUtil.getPageSize();
		
		org.springframework.util.Assert.isTrue(pageNo >= 0, "pageNo should start from 0");
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出�?,清空两�?�来执行Count操作
		Projection projection = impl.getProjection();
		List orderEntries;
		try {
			orderEntries = (List) BeanUtils.getDeclaredProperty(impl,
					"orderEntries");
			BeanUtils
					.setDeclaredProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		Projection rc = Projections.rowCount();
		criteria.setProjection(rc);
		Object o = criteria.uniqueResult();

		// 执行查询
		int totalCount = ((Integer) o).intValue();
		
		// 将之前的Projection和OrderBy条件重新设回�?
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		try {
			BeanUtils.setDeclaredProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new ResultPage();

		int startIndex = ResultPage.getStartOfPage(pageNo, pageSize);
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new ResultPage(startIndex, totalCount, pageSize, list);
	}
	
	
	/***************************************************************************
	 * 分页查询函数，使用hql
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultPage pagedQuery(String hql, Object[] values){
		
		Assert.hasText(hql);
		this.getHibernateTemplate().setCacheQueries(true);
		
		//在这里从PageUtil里面来拿pageNo,和pageSize
		int pageNo = PageUtil.getPageNo();
		int pageSize = PageUtil.getPageSize();
		
		// 如果每页长度小于0,那么查询全部符合条件的数据放在一个Page对象中返回
		if (pageSize < 0) {
			List countlist = getHibernateTemplate().find(hql, values);
			if (countlist.size() < 1) {
				return new ResultPage();
			} else {
				return new ResultPage(1, countlist.size(), countlist.size(),countlist);
			}
		}

		// Count查询
		String countQueryString = " select count (*) "
				+ removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		if(countlist.isEmpty()){
			return new ResultPage();
		}
		int totalCount = Integer.parseInt("" + countlist.get(0));

		if (totalCount < 1)
			return new ResultPage();

		// 实际查询返回分页对象
		int startIndex = ResultPage.getStartOfPage(pageNo, pageSize);
		Query query = getQuery(hql, values);
		query.setCacheable(true);
		List list = null;
		try {
			list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return new ResultPage(startIndex, totalCount, pageSize, list);
	}
	
	
	/***************************************************************************
	 * 判断对象某些属�?�的值在数据库中不存在重�
	 * 在PoJo里不能重复的属�性,型如："name,loginid,password"
	 * @param names
	 */
	public boolean isNotUnique(Class<?> entityClass, Object entity, String names){
		
		Assert.hasText(names);
		Criteria criteria = getCriteria(entityClass).setProjection(
				Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			// 循环加入
			for (int i = 0; i < nameList.length; i++) {
				criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));
			}

			// 以下代码为了如果是update的情况,排除entity自身
			String idName = getIdName(entityClass);
			// 通过反射取得entity的主键�
			Object id = PropertyUtils.getProperty(entity, idName);
			// 如果id!=null,说明对象已存存在,该操作为update,加入排除自身的判断
			if (id != null) {
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
			}
		} catch (Exception e) {
			logger.info(ExceptionToString.getTrace(e));
			throw new BaseException("判断重复异常");
		}
		
		return ((Integer) criteria.uniqueResult()).intValue() > 0;
	}

	public String getIdName(Class<?> entityClass){
		
		Assert.notNull(entityClass);
		String idName = getSessionFactory().getClassMetadata(entityClass).getIdentifierPropertyName();
		org.springframework.util.Assert.hasText(idName, entityClass.getSimpleName() + "has no id column define");
		
		return idName;
	}
	

	/***************************************************************************
	 * 执行sql
	 * @param sql
	 */
	public void executeSql(String sql) {
		
		if(Assert.hasText(sql)) {
			this.getSession().createSQLQuery(sql).executeUpdate();
		}
	}
	
	
	/***************************************************************************
	 * 私有方法：
	 * 
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * @param hql
	 * @return
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	/***************************************************************************
	 * 私有方法：
	 * 
	 * 去除hql的select 子句，未考虑union的情况用于pagedQuery.
	 * @param hql
	 * @return
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		org.springframework.util.Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	
	/***************************************************************************
	 * 创建Query 对象
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query getQuery(String hql, Object[] values){
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		query.setCacheable(true);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
}
