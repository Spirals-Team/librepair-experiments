package com.github.powerlibraries.primitive.collections.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.property.selection.cache.NoSelectionPropertyResolverCache;

@SuppressWarnings("PMD")
public final class Generator {
	
	private Generator() {}

	public static void main(String[] args) throws IOException {
		File targetDirectory;
		if(args.length==1) {
			targetDirectory = new File(args[0]);
		}
		else {
			 targetDirectory = new File("../primitivepower");
		}
		
		generateCode(
			new File("templates"),
			targetDirectory
		);
	}
	
	public static void generateCode(File in, File target) throws IOException {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
		
		Set<File> filesToDelete = new HashSet<>();
		
		Files
			.walk(target.toPath())
			.filter(t -> t.toString().endsWith(".java") || t.toString().endsWith(".xml"))
			.forEach(t -> filesToDelete.add(t.toFile()));
		
		
		final EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
            .configuration()
            	.render()
            		.withStrictMode(true)
            	.and()
            	.propertyResolver()
            		.withCache(NoSelectionPropertyResolverCache.noSelectionPropertyResolverCache())
            		.propertyResolverStrategies()
            		.and()
            	.and()
	        .build();
		
		
		List<File> fileList = Files
			.walk(in.toPath())
			.map(Path::toFile)
			.filter(File::isFile)
			.collect(Collectors.toList());
		
		for(File f:fileList) {
			String relativeDirectory = in.toPath().relativize(f.getParentFile().toPath()).toString();
			
			for(Type t:Type.values()) {
				JtwigModel model = JtwigModel
					.newModel()
					.with("t", t)
					.with("allTypes", Type.values());
			
				File folder = new File(
					target,
					JtwigTemplate.inlineTemplate(relativeDirectory, configuration).render(model)
				);
				folder.mkdirs();
				
				if(f.getName().endsWith(".twig")) {
					JtwigTemplate nameTemplate = JtwigTemplate.inlineTemplate(f.getName().substring(0,f.getName().length()-5), configuration);
					JtwigTemplate template = JtwigTemplate.fileTemplate(f, configuration);
					
					File res = new File(folder, nameTemplate.render(model));
					filesToDelete.remove(res);
					try (OutputStream out = new FileOutputStream(res)) {
						template.render(model, out);
					} catch(Exception e) {
						throw new RuntimeException("Failed in "+f.getName()+" with k="+t, e);
					}
					System.out.println("Written file "+res.getCanonicalPath());
				}
				else {
					File res = new File(folder, f.getName());
					filesToDelete.remove(res);
					FileUtils.copyFile(f, res);
				}
			}
		}
		
		for(File f:filesToDelete) {
			f.delete();
			File dir = f.getParentFile();
			System.out.println("Deleting "+f);
			while(!dir.equals(target) && dir.listFiles().length == 0) {
				System.out.println("Deleting "+dir);
				dir.delete();
				dir = dir.getParentFile();
			}
		}
	}
}
