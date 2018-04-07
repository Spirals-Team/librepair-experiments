package ru.maksimov.andrey.golos4j.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import ru.maksimov.andrey.golos4j.api.method.GetContent;
import ru.maksimov.andrey.golos4j.dto.api.GetContentDto;
import ru.maksimov.andrey.golos4j.exception.SystemException;
import ru.maksimov.andrey.golos4j.socket.ServiceWebSocket;

/**
 * Класс для тестов метода
 * {@link ru.maksimov.andrey.golos4j.api.method.GetContent} и dto
 * {@link ru.maksimov.andrey.golos4j.dto.api.GetContentDto}
 * 
 * @author <a href="mailto:onixbed@gmail.com">amaksimov</a>
 */
public class GetContentTest {

	private static String WSS_URL_NODE = "wss://ws.golos.io";

	@Before
	public void initialize() throws InterruptedException {
		Thread.sleep(2000);
	}

	private String ACCOUNT = "onixred";
	private String PERMLINK = "ne-mogu-perevesti-v-silu-golosa-v-chem-problema";

	@Test
	public void testId() throws SystemException {
		int id = 2;
		GetContent getContent = new GetContent(id, ACCOUNT, PERMLINK);
		GetContentDto getContentDto = ServiceWebSocket.executePost(getContent, GetContentDto.class, WSS_URL_NODE);
		assertEquals(getContentDto.getId(), id);
	}

	@Test
	public void testNotNull() throws SystemException {
		int id = 2;
		GetContent getContent = new GetContent(id, ACCOUNT, PERMLINK);
		GetContentDto getContentDto = ServiceWebSocket.executePost(getContent, GetContentDto.class, WSS_URL_NODE);
		assertNotNull(getContentDto);
	}

	@Test
	public void testNotNullResults() throws SystemException {
		int id = 2;
		GetContent getContent = new GetContent(id, ACCOUNT, PERMLINK);
		GetContentDto getContentDto = ServiceWebSocket.executePost(getContent, GetContentDto.class, WSS_URL_NODE);
		assertNotNull(getContentDto.getResults());
	}
}
