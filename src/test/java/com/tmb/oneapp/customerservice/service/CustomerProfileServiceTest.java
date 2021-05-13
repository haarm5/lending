package com.tmb.oneapp.customerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.service.CustomerProfileService;

//import static org.junit.Assert.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.anyString;
//import static org.mockito.Mockito.when;

import java.util.List;

@RunWith(JUnit4.class)
public class CustomerProfileServiceTest {

	private CustomerProfileService customerProfileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		customerProfileService = new CustomerProfileService();
	}

	@Test
//	@Ignore
	public void testgetCatagoryByName() {
		List<CommonCodeEntry> listCommonCodeEntry = customerProfileService.getCatagoryByName("EMPLOYMENT_STATUS", "MIB",
				"3", "725a9ec5-5de0-4b95-a51f-9774b559b459");
		Assert.notEmpty(listCommonCodeEntry);
	}
	
//	@Test
//	@Ignore
//	public void testConstructModel() {
//		customerProfileService.constructModel();
//	}
	

}
