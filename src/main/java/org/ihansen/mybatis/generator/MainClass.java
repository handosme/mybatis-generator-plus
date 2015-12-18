package org.ihansen.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MainClass {

	public static final String CFG_FILE_PATH = MainClass.class.getResource("/MybatisGeneratorCfg.xml").getFile();

	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(CFG_FILE_PATH);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		}
		catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(new ProgressCallback() {
				@Override
				public void startTask(String taskName) {
					System.out.println("startTask(String "+taskName+")");
				}
				@Override
				public void saveStarted(int totalTasks) {
					System.out.println("saveStarted(int "+totalTasks+")");
				}
				@Override
				public void introspectionStarted(int totalTasks) {
					System.out.println("introspectionStarted(int "+totalTasks+")");
				}
				@Override
				public void generationStarted(int totalTasks) {
					System.out.println("generationStarted(int "+totalTasks+")");
				}
				@Override
				public void done() {
					System.out.println("done()");
				}
				@Override
				public void checkCancel() throws InterruptedException {
					System.out.println("checkCancel()");
				}
			});
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
