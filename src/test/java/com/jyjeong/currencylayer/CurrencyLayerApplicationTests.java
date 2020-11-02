package com.jyjeong.currencylayer;

import com.jyjeong.currencylayer.common.DatabaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class CurrencyLayerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	MockMvc mockMvc;

	@Test
	public void getMain() throws Exception{
		mockMvc.perform(get("/main"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getDate() throws Exception{
		mockMvc.perform(get("/getCurrencyRate?stdCountryCode=KRW"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getReceivedAmount() throws Exception {
		mockMvc.perform(get("/getReceivedAmount?stdCountryCode=KRW&remittance=1000"))
//				.param("stdCountryName","KRW")
//				.param("remittance","1000"))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void setCurrencyLayer(){

		
		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.getAllCurrencyRate();
//		databaseConfig.run();
	}
}
