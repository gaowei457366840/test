package com.ringtop.common.util;

import java.util.ArrayList;
import java.util.List;


public class PageBean{


	  /**
	   * 空白页面
	   */
	  public static final PageBean EMPTY_PageBean =
	      new PageBean(0, 0);

	  /**
	   * 结果集列表
	   */
	  @SuppressWarnings("unchecked")
	  List objects;
	  /**
	   * 开始记录号，从0开始
	   */
	  public int start;

	  /**
	   * 结束记录号
	   */
	  public int end;

	  /**
	   * 显示在页面开始记录号，从1开始
	   */
	//  public int viewStart;
	  /**
	   * 显示在页面结束记录号
	   */
	//  public int viewEnd;
	  /**
	   * 是否有上一页的开关
	   */
	  public boolean hasPrevious; //ok

	  /**
	   * 上一页的页码
	   */
	  public int previousPageBeanNumber; //ok

	  /**
	   * 是否有下一页的开关
	   */
	  public boolean hasNext; //ok

	  /**
	   * 下一页的页码
	   */
	  public int nextPageBeanNumber; //ok

	  /**
	   * 一共有多少行记录
	   */
	  public int total; //ok

	  /**
	   * 一共有多少页
	   */
	  public int totalPageBean; //ok

	  /**
	   * 当前是第几页
	   */
	  public int currentPageBeanNumber; //ok

	  /**
	   * 每页有多少行
	   */
	  public int PageBeanSize; //ok

	  /**
	   * 构造器,创建页面
	   * @param: l 结果集
	   * @param: s 开始记录号，从0开始
	   */
	  public PageBean(int total, int s) {
//	    objects = new ArrayList(l);
	    this.currentPageBeanNumber = s / PageConfig.PAGE_SIZE + 1;
	    this.PageBeanSize = PageConfig.PAGE_SIZE;
	    //System.out.println(PageBeanSize+":size");
//	    this.total = objects.size();
	    this.total = total;
	    if (total == 0) {
	      this.currentPageBeanNumber = 0;
	    }
	    else {
	      autoCalculate();
	    }
	  }
	  
	  public PageBean(int total, int s,int PageBeanSize) {
		this.PageBeanSize = PageBeanSize;
	    this.currentPageBeanNumber = s / this.PageBeanSize + 1;
	    this.total = total;
	    if (total == 0) {
	      this.currentPageBeanNumber = 0;
	    }
	    else {
	      autoCalculate();
	    }
	  }
	  
	  
	  @SuppressWarnings("unchecked")
	public PageBean(ArrayList l, int s) {
	    objects = new ArrayList(l);
	     this.currentPageBeanNumber = s / PageConfig.PAGE_SIZE + 1;
	     this.PageBeanSize = PageConfig.PAGE_SIZE;
	     //System.out.println(PageBeanSize+":size");
	    this.total = objects.size();
//	     this.total = total;
	     if (total == 0) {
	       this.currentPageBeanNumber = 0;
	     }
	     else {
	       autoCalculate();
	     }
	   }
	  
	  /**
	   * 自动计算，根据当前页、页大小、总行数计算出其它属性的值
	   */
	  private void autoCalculate() {
	    start = (currentPageBeanNumber - 1) * PageBeanSize;
	    end = start + PageBeanSize - 1;
	    if (end >= total) {
	      end = total - 1;
	    }
	    totalPageBean = (total + PageBeanSize - 1) / PageBeanSize;
	    if (currentPageBeanNumber == 1) {
	      hasPrevious = false;
	      previousPageBeanNumber = 1;
	    }
	    else {
	      hasPrevious = true;
	      previousPageBeanNumber = currentPageBeanNumber - 1;
	    }
	    if (currentPageBeanNumber == totalPageBean) {
	      hasNext = false;
	      nextPageBeanNumber = totalPageBean;
	    }
	    else {
	      hasNext = true;
	      nextPageBeanNumber = currentPageBeanNumber + 1;
	    }
	  }

	  /**
	   * 获得结果集
	   * @return: List 结果集
	   */
	  @SuppressWarnings("unchecked")
	public List getList() {
	    return objects;
	  }

	  /**
	   * 获得显示在页面的开始记录号，从1开始
	   * @return: int 显示在页面的开始记录号
	   */
	  public int getStart() {
	    return start;
	  }

	  /**
	   * 获得显示在页面的结束记录号
	   * @return: int 显示在页面的结束记录号
	   */
	  public int getEnd() {
	    return end;
	  }

	  /**
	   * 是否有下一页
	   * @return: boolean 是否有下一页的开关
	   */
	  public boolean hasNextPageBean() {
	    return hasNext;
	  }

	  /**
	   * 是否有上一页
	   * @return: boolean 是否有上一页的开关
	   */
	  public boolean hasPreviousPageBean() {
	    return start > 0;
	  }

	  /**
	   * 获得上一页的页码
	   * @return: int 上一页的页码
	   */
	  public int getPreviousPageBeanNumber() {
	    return previousPageBeanNumber;
	  }

	  /**
	   * 获得下一页的页码
	   * @return: int 下一页的页码
	   */
	  public int getNextPageBeanNumber() {
	    return nextPageBeanNumber;
	  }

	  /**
	   * 获得结果集中记录总行数
	   * @return: int 一共有多少行记录
	   */
	  public int getTotal() {
	    return total;
	  }

	  /**
	   * 获得总页数
	   * @return: int 一共有多少页
	   */
	  public int getTotalPageBean() {
	    return totalPageBean;
	  }

	  /**
	   * 获得当前页码
	   * @return: int 当前页码
	   */
	  public int getCurrentPageBeanNumber() {
	    return currentPageBeanNumber;
	  }

	  /**
	   * 获得每页多少行记录
	   * @return: int 页大小
	   */
	  public int getPageBeanSize() {
	    return PageBeanSize;
	  }

	  /**
	   * 获得下一页在结果集中开始的记录号，从0开始
	   * @return: int 下一页在结果集中开始的记录号
	   */
	  public int getStartOfNextPageBean() {
	    return start + this.total;
	  }

	  /**
	   * 获得上一页在结果集中开始的记录号，从0开始
	   * @return: int 下一页在结果集中开始的记录号
	   */
	  public int getStartOfPreviousPageBean() {
	    return Math.max(start - this.total, 0);
	  }

	  /**
	   * 获得当前页包括的记录行数
	   * @return: int 当前页包括的记录行数
	   */
	  public int getSize() {
	    return this.total;
	  }

	  /**
	   * 判断当前页面是否是空白页面,如果当前页面是空白页面,
	   * 返回true,否则,返回false
	   * @return: boolean 当前页面是否是空白页面
	   */
	  public boolean isEmpty() {
	    return this.total ==0;
	  }

}
