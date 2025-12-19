package com.seapos.webapi;

import com.seapos.webapi.Utility.EmailBodyBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SeaposWebApiApplicationTests {

//	@Test
//	void contextLoads() {
//	}
@MockBean
private EmailBodyBuilder emailBodyBuilder;

}
