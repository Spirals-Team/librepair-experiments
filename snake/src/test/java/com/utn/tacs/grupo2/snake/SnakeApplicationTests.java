package com.utn.tacs.grupo2.snake;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SnakeApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    protected MockRestServiceServer mockRestServiceServer;

    @Before
    public void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown() {
        mockRestServiceServer.reset();
    }

    protected String obtenerContenidoArchivo(String rutaArchivo) throws IOException {
        File file = new ClassPathResource(rutaArchivo).getFile();
        return FileCopyUtils.copyToString(new FileReader(file));
    }

    @Test
    public void contextLoads() {
    }

}
