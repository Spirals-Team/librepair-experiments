package com.hedvig.productPricing;

import com.hedvig.productPricing.pricing.PricingEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest()
//@EnableTransactionManagement
//@ContextConfiguration(classes = { AxonTestConfiguration.class})
//@Transactional()
public class ProductPricingApplicationTests {

	@MockBean
	private PricingEngine pricingEngine;
	//@Autowired
	//private WebApplicationContext context;

	//@Autowired
	//private CommandGateway commandGateway;

	//@Autowired
	//private TestTool testTool;

	private ModelMapper mapper = new ModelMapper();

	@Before
	public void setUp() {
		//MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		//RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	public void dummy() {
		//Fix these stupid tests.
	}
/*
	@Test
	public void contextLoads() {
		given().
				header("hedvig.token", "1337").
		when().
			get("/insurance").
		then().
			statusCode(200).
			body("status", equalTo("PENDING"));
	}
*/
/*
	@Test
	public void CreateProduct_Returns_NewProduct_Id() {
		BDDMockito.given(pricingEngine.isStartupComplete()).willReturn(true);
		given().
				body(mapper.map(Members.TOLVAN, CalculateQuoteRequest.class)).
				contentType(ContentType.JSON).
		when().
				post("/createProduct/").
		then().
				body("id", org.hamcrest.CoreMatchers.notNullValue());
	}*/

	/*@Test
	public void CreateProduct_WithSafetyIncreaserNONE_ToInsurance() {
		UUID productId = UUID.randomUUID();
		testTool.MemberCreated(Members.TOLVAN);
		when(pricingEngine.isStartupComplete()).thenReturn(true);
		when(pricing)
		given().
				body(mapper.map(Members.TOLVAN.addSafetyIncreaser(SafetyIncreaserType.NONE), CalculateQuoteRequest.class)).
				contentType(ContentType.JSON).
		when().
				post("/createProduct/").
		then().
				body("id", org.hamcrest.CoreMatchers.notNullValue());

		given().
				header("hedvig.token", Members.TOLVAN.getMemberId()).
		when().
				get("/insurance").
		then().
				statusCode(200).
				body("status", equalTo("QUOTE")).
				body("categories.perils.id.flatten()", CoreMatchers.hasItems	("ME.LEGAL", "ME.ASSAULT"));
	}*/

/*

	@Test
	public void ContractSigned_ChangesstatusOfInsuranceTo_INACTIVE() {
		UUID productId = UUID.randomUUID();
		testTool.
				MemberCreated(Members.TOLVAN).
				ProductCreated(Members.TOLVAN.getMemberId(),
						productId,
						FixureData.createTwoPerils(),
						132.0f,
						ProductStates.QUOTE).
				commit();

		given().
				body(new ContractSignedRequest(Members.TOLVAN.getMemberId(), "blabla", "", "")).
				contentType(ContentType.JSON).
		when().
				post("/i/insurance/contractSigned").
		then().
				statusCode(200);

	}


	@Test
	public void AfterContractSigned_StatusIs_INACTIVE() throws InterruptedException {
		String memberId = Members.TOLVAN.getMemberId();
		UUID productId = UUID.randomUUID();
		testTool.
				MemberCreated(Members.TOLVAN).
				ProductCreated(Members.TOLVAN.getMemberId(),
						productId,
						FixureData.createTwoPerils(),
						132.0f,
						ProductStates.QUOTE).
				ContractSigned(Members.TOLVAN.getMemberId()).
				commit();

		Thread.sleep(100l);

		given().
				header("hedvig.token", memberId).
		when().
				get("/insurance").
		then().
				statusCode(200).
				body("status", equalTo("PENDING"));

	}*/

	/*
	@Test
	public void AfterGetQuote_StatusIs_NewPriceIsNotNull() throws InterruptedException {
		String memberId = Members.TOLVAN.getMemberId();
		UUID productId = UUID.randomUUID();
		testTool.
				MemberCreated(Members.TOLVAN).
				ProductCreated(Members.TOLVAN.getMemberId(),
						productId,
						FixureData.createTwoPerils(),
						132.0f,
						ProductStatus.QUOTE).
				ContractSigned(Members.TOLVAN.getMemberId()).
				QuoteCreated(productId, FixureData.createTwoPerils(), 200.0f).
				commit();

		Thread.sleep(100l);

		given().
				header("hedvig.token", memberId).
		when().
				get("/insurance").
		then().
				statusCode(200).
				body("newTotalPrice", equalTo(132f));
	}
    */
	/*@Test
	public void GIVEN_NewPrice_EqualTo_Price_NewPriceIsNull() throws InterruptedException {
		String memberId = Members.TOLVAN.getMemberId();
		UUID productId = UUID.randomUUID();
		List<PerilDTO> productPerils = FixureData.createTwoPerils();
		testTool.
				MemberCreated(Members.TOLVAN).
				ProductCreated(Members.TOLVAN.getMemberId(),
						productId,
						FixureData.createTwoPerils(),
						132.0f,
						ProductStatus.QUOTE).
				ContractSigned(Members.TOLVAN.getMemberId()).
				QuoteCreated(productId, productPerils, 132.0f).
				commit();

		Thread.sleep(100l);

		given().
				header("hedvig.token", memberId).
				when().
				get("/insurance").
				then().
				statusCode(200).
				body("newTotalPrice", nullValue());
	}*/

}
