package cn.com.fri.JUnitTest.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.fri.basic.controller.BaseController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JUnitBase Biz层测试
 * 
 * @author WYQ
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/applicationContext*.xml" })
public class JUnitBaseBiz extends BaseController {
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
