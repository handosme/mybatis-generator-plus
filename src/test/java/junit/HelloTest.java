package junit;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.ihansen.app.domain.BusLog;
import org.ihansen.app.domain.MSBusLog;
import org.ihansen.app.domain.MSBusLogEX;
import org.ihansen.app.domain.SecKey;
import org.ihansen.app.domain.operate.BusLogMapper;
import org.ihansen.app.domain.operate.MSBusLogMapper;
import org.ihansen.app.domain.operate.SecKeyMapper;
import org.ihansen.mybatis.generator.extend.PageHelper;
import org.junit.Before;
import org.junit.Test;

public class HelloTest {

	SqlSession sqlSession;
	
	@Before
	public void before() throws FileNotFoundException {
		// Mapper的配置文件
		String resource = HelloTest.class.getResource("/mybatis-config.xml").getFile();
		// 使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		InputStream is = new FileInputStream(resource);
		// 构建sqlSession的工厂
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		sqlSession = sessionFactory.openSession();

	}

	/**
	 * oracle数据库测试
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:18
	 */
	@Test
	public void t1() throws Exception {
		 SecKeyMapper secKeyMapper = sqlSession.getMapper(SecKeyMapper.class);
		 SecKey secKey = secKeyMapper.selectByPrimaryKey(1l);
		 System.out.println(secKey);
		 
		 SecKey in = new SecKey.Builder().merId("bbu").build();
		 System.out.println(in.getMerId());
		 secKeyMapper.insertSelective(in);
		 sqlSession.commit();
	}
	
	/**
	 * sqlserver数据库测试
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:33
	 */
	@Test
	public void t2() throws Exception {
		MSBusLogMapper msBusLogMapper = sqlSession.getMapper(MSBusLogMapper.class);
		MSBusLogEX msBusLogEX = new MSBusLogEX();
		msBusLogEX.createCriteria().andBusIdEqualTo("10002");
		msBusLogEX.setOrderByClause("id");
		PageHelper pageHelper = new PageHelper(6,5);
		msBusLogEX.setPageHelper(pageHelper);
		List<MSBusLog> msBusLogs = msBusLogMapper.selectByExample(msBusLogEX);
		for (MSBusLog msBusLog : msBusLogs) {
			System.out.println(msBusLog.getContent());
		}
	}
	
	/**
	 * sqlserver数据库测试
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:11:50
	 */
	@Test
	public void t3() throws Exception {
		MSBusLogMapper msBusLogMapper = sqlSession.getMapper(MSBusLogMapper.class);
		
		List<MSBusLog> msBusLogs = new ArrayList<MSBusLog>();
		for(int i=0;i<3;i++){
			MSBusLog msBusLog = new MSBusLog();
			msBusLog.setBusId("30001");
			msBusLog.setOperatorId("000001");
			msBusLog.setContent("批量"+i);
			msBusLog.setDoneDate(new Date());
			msBusLog.setResult("S");
			msBusLog.setState("U");
			if(i%2==0){
				msBusLog.setRemark("ok");
			}
			msBusLogs.add(msBusLog);
		}
		msBusLogMapper.insertBatch(msBusLogs);
		sqlSession.commit();
	}
	
	/**
	 * mysql数据库
	 * @author 吴帅
	 * @parameter @throws Exception
	 * @createDate 2015年12月18日 下午4:12:05
	 */
	@Test
	public void t4() throws Exception {
		BusLogMapper busLogMapper = sqlSession.getMapper(BusLogMapper.class);
		
		List<BusLog> busLogs = new ArrayList<BusLog>();
		for(int i=0;i<3;i++){
			BusLog busLog = new BusLog();
			busLog.setBusId("30001");
			busLog.setOperatorId("000001");
			busLog.setContent("批量"+i);
			busLog.setDoneDate(new Date());
			busLog.setResult("S");
			busLog.setState("U");
			if(i%2==0){
				busLog.setRemark("ok");
			}
			busLogs.add(busLog);
		}
		busLogMapper.insertBatch(busLogs);
		sqlSession.commit();
	}

}
