package com.ringtop.common.dao.inter;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ringtop.common.util.ResultPage;

public interface IBaseDao<T> {
	/***************************************************************************
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable save(T entity);
	
	/***************************************************************************
	 * 保存实体或更新实体
	 * @param entity
	 */
	public void merge(T entity);
	
	/***************************************************************************
	 * 批量添加更新
	 * 
	 * @param list
	 */
	public void saveOrUpdateALL(Collection<T> list);
	

	/***************************************************************************
	 * 删除
	 * @param entity
	 * @return
	 */
	public void delete(T entity);
	
	
	/***************************************************************************
	 * 按主键数组删除实体
	 * 
	 * @param entity
	 * @return
	 */
	public long deleteByPrimaryKeys(Serializable[] pks);
	
	/***************************************************************************
	 * 批量删除实体
	 * 
	 * @param list
	 */
	public void deleteALL(Collection<T> list);

	
	/***************************************************************************
	 * SQL删除
	 * 
	 * @param list
	 */
	public int deleteBySQL(String sql, Object[] values);
	

	/***************************************************************************
	 * HQL删除
	 * 
	 * @param list
	 */
	public int deleteByHQL(String hql, Object[] values);
	

	/***************************************************************************
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public void update(T entity);
	

	/***************************************************************************
	 * 保存或更新
	 * 
	 * @param entity
	 * @return
	 */
	public void saveOrUpdate(T entity);
	

	/***************************************************************************
	 * 按主键查找
	 * @param entity
	 * @return
	 */
	public T getByPk(Serializable i);
	

	/***************************************************************************
	 * 按HQL获得单一实体
	 * 
	 * @param entity
	 * @return
	 */
	public T getByHql(String hql, Object[] values);
	

	/***************************************************************************
	 * 按HQL获得实体集合
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> findByHql(String hql, Object[] values);


	/***************************************************************************
	 * 得到数量
	 * 
	 * @param entity
	 * @return
	 */
	public int getAllEntityCount();
	

	/***************************************************************************
	 * 得到数量
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public int getAllEntityCount(String sql, Object[] values);


	/***************************************************************************
	 * 得到数据库信息
	 */
	public void getMetaData();
	
	
	/***************************************************************************
	 * 判断对象某些属�?�的值在数据库中不存在重�
	 * 在PoJo里不能重复的属�性,型如："name,loginid,password"
	 * @param names
	 */
	public boolean isNotUnique(Class<?> entityClass, Object entity, String names);

	
	/***************************************************************************
	 * 分页查询对应实体
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> ResultPage pagedQuery(T entity);
	
	
	/***************************************************************************
	 * 分页查询函数，使用hql
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ResultPage pagedQuery(String hql, Object[] values);

	
	/***************************************************************************
	 * 执行sql
	 * @param sql
	 */
	public void executeSql(String sql);
	
}
