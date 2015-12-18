package org.ihansen.mybatis.generator.extend;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

public interface DBSupport
{
	/**
	 * 向&lt;mapper&gt;中添加子节点
	 * 
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 上午10:20:11
	 */
	public void sqlDialect(Document document, IntrospectedTable introspectedTable);

	/**
	 * 增加批量插入的xml配置
	 * 
	 * @author 吴帅
	 * @parameter @param document
	 * @parameter @param introspectedTable
	 * @createDate 2015年8月9日 下午6:57:43
	 */
	public void addBatchInsertXml(Document document, IntrospectedTable introspectedTable);

	/**
	 * 条件查询sql适配
	 * @author 吴帅
	 * @return 
	 * @parameter @param element
	 * @parameter @param preFixId
	 * @parameter @param sufFixId
	 * @createDate 2015年9月29日 上午11:59:06
	 */
	public XmlElement adaptSelectByExample(XmlElement element, IntrospectedTable introspectedTable);
	
	/**
	 * 插入sql适配
	 * @author 吴帅
	 * @parameter @param element
	 * @parameter @param introspectedTable
	 * @createDate 2015年9月29日 下午12:00:37
	 */
	public void adaptInsertSelective(XmlElement element, IntrospectedTable introspectedTable);
}
