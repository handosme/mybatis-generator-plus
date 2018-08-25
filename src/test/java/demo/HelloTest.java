package demo;


import demo.domain.oob.OperateLog;
import demo.domain.oob.OperateLogExample;
import demo.domain.ooc.UserVisitLog;
import demo.domain.ooc.UserVisitLogExample;
import demo.mapper.oob.OperateLogMapper;
import demo.mapper.ooc.UserVisitLogMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HelloTest {

	SqlSession sqlSession;

	OperateLogMapper operateLogMapper;

	UserVisitLogMapper userVisitLogMapper;

	@Before
	public void before() throws FileNotFoundException {
		// Mapper的配置文件
		String resource = HelloTest.class.getResource("/mybatis-config.xml").getFile();
		// 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = new FileInputStream(resource);
		// 构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		sqlSession = sessionFactory.openSession();
		operateLogMapper = sqlSession.getMapper(OperateLogMapper.class);
		userVisitLogMapper = sqlSession.getMapper(UserVisitLogMapper.class);
	}

	/**
	 * insert Test
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:18
	 */
	@Test
	public void insertTest() throws Exception {
		OperateLog operateLog = new OperateLog.Builder()
				.action("insert_test")
				.build();
		operateLogMapper.insertSelective(operateLog);
		sqlSession.commit();
	}

	/**
	 * insertBatch Test
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:33
	 */
	@Test
	public void insertBatchTest() throws Exception {
		List<OperateLog> operateLogList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			OperateLog operateLog = new OperateLog.Builder()
					.action("insertBatch_test"+i)
					.build();
			operateLogList.add(operateLog);
		}
		operateLogMapper.insertBatch(operateLogList);
		sqlSession.commit();
	}


	/**
	 * select page Test
	 * @throws Exception
	 */
	@Test
	public void selectPageTest() throws Exception {
		OperateLogExample relationshipsExample = new OperateLogExample();
		relationshipsExample.setPagination(0l,2l);
		List<OperateLog> operateLogList = operateLogMapper.selectByExample(relationshipsExample);
		//TODO verify
		System.out.println(operateLogList);
	}

	@Test
	public void selectByBigOffsetTest(){
		UserVisitLogExample userVisitLogExample = new UserVisitLogExample();
		userVisitLogExample.createCriteria().andIdLessThan(100);
		userVisitLogExample.setPagination(0,10);
		List<UserVisitLog> userVisitLogList = userVisitLogMapper.selectByBigOffset(userVisitLogExample);
		System.out.println(userVisitLogList);
	}



}
