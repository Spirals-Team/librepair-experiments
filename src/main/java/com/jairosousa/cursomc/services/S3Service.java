package com.jairosousa.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jairosousa.cursomc.services.exceptios.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Value("${s3.bucket}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3cliente;

	public URI uploadFile(MultipartFile multipartFile) {

		try {
			String fileName = multipartFile.getOriginalFilename();// Extrai nome arquivo enviado
			InputStream is = multipartFile.getInputStream(); // Objeto Basico de leitura dos arquivos de origem
		
			String contentType = multipartFile.getContentType(); // Extrai o tipo de arquivo enviado.

			return uploadFile(is, fileName, contentType);
			
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		} 
		

	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);

			LOG.info("Iniciando upload");
			s3cliente.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload Finalizado");

			return s3cliente.getUrl(bucketName, fileName).toURI();
		
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
			
		}

	}

}
