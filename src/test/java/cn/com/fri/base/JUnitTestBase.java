package cn.com.fri.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.com.fri.basic.controller.BaseController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author WYQ
 * 
 * @date 2016-2-18 上午10:49:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public abstract class JUnitTestBase extends BaseController {

	private ObjectMapper mapper;

	@Before
	public void init() {
		mapper = new ObjectMapper();
	}

	protected void printJson(Object o) {
		try {
			String json = mapper.writeValueAsString(o);
			log.info(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
