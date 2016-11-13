package demo;


import demo.domain.User;
import demo.domain.UserExample;
import demo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloTest {

	SqlSession sqlSession;

	UserMapper userMapper;
	
	@Before
	public void before() throws FileNotFoundException {
		// Mapper的配置文件
		String resource = HelloTest.class.getResource("/mybatis-config.xml").getFile();
		// 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = new FileInputStream(resource);
		// 构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		sqlSession = sessionFactory.openSession();
		userMapper = sqlSession.getMapper(UserMapper.class);
	}

	/**
	 * insert Test
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:18
	 */
	@Test
	public void insertTest() throws Exception {
		User user = new User.Builder()
				.userName("insert_testzzzzzz")
				.creatTime(new Date())
				.updateTime(new Date())
				.build();
		userMapper.insertSelective(user);
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
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			User user = new User.Builder()
					.userName("insertBatch_test"+i)
					.isDeleted((byte) 0)
					.creatTime(new Date())
					.updateTime(new Date())
					.build();
			userList.add(user);
		}
		userMapper.insertBatch(userList);
		sqlSession.commit();
	}

	/**
	 * select Test
	 * @throws Exception
     */
	@Test
	public void selectTest() throws Exception {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserNameLike("%0%");
		List<User> userList = userMapper.selectByExample(userExample);
		//TODO verify
		System.out.println(userList);
	}






}
