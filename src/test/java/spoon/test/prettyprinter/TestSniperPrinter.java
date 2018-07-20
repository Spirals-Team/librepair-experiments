package spoon.test.prettyprinter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import spoon.Launcher;
import spoon.experimental.modelobs.SourceFragmentsTreeCreatingChangeCollector;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.printer.change.SniperJavaPrettyPrinter;
import spoon.test.prettyprinter.testclasses.ToBeChanged;
import spoon.testing.utils.ModelUtils;

public class TestSniperPrinter {

	@Test
	public void testPrintUnchaged() throws Exception {
		checkSniper(ToBeChanged.class.getName(), type -> {
			//do not change the model
		}, (type, printed) -> {
			assertChanged(type, printed);
		});
	}

	@Test
	public void testPrintChanged() throws Exception {
		checkSniper(ToBeChanged.class.getName(), type -> {
			//change the model
			type.getField("string").setSimpleName("modified");
		}, (type, printed) -> {
			assertChanged(type, printed, "\\bstring\\b", "modified");
		});
	}
	
	@Test
	public void testPrintChangedReferenceBuilder() throws Exception {
		checkSniper("spoon.test.prettyprinter.ReferenceBuilder", type -> {
			//find to be removed statement
			CtStatement toBeRemoved = type.filterChildren((CtStatement stmt) -> stmt.getPosition().isValidPosition() && stmt.getPosition().getLine() == 230).first();
			//TODO fix that this toString changes model...
			assertEquals("bounds = false", toBeRemoved.toString());
			//change the model
			toBeRemoved.delete();
		}, (type, printed) -> {
			assertChanged(type, printed, "\\QNO_SUPERINTERFACES) {\n\\E\\s*bounds\\s*=\\s*false;\n", "NO_SUPERINTERFACES) {\n");
		});
	}
	
	@Test
	public void testPrintAfterRemovedParameter() {
		checkSniper(ToBeChanged.class.getName(), type -> {
			//delete first parameter of method `andSomeOtherMethod`
			type.getMethodsByName("andSomeOtherMethod").get(0).getParameters().get(0).delete();;
		}, (type, printed) -> {
			assertChanged(type, printed, "\\s*int\\s*param1,", "");
		});
	}

	private void checkSniper(String testClass, Consumer<CtType<?>> typeChanger, BiConsumer<CtType<?>, String> resultChecker) {
		Launcher launcher = new Launcher();
		launcher.addInputResource(getResourcePath(testClass));
		launcher.getEnvironment().setCommentEnabled(true);
		launcher.getEnvironment().setAutoImports(true);
		launcher.getEnvironment().useTabulations(true);
		launcher.buildModel();
		Factory f = launcher.getFactory();

		final CtClass<?> ctClass = launcher.getFactory().Class().get(testClass);
		
		new SourceFragmentsTreeCreatingChangeCollector().attachTo(f.getEnvironment());
		//change the model
		typeChanger.accept(ctClass);
		
		SniperJavaPrettyPrinter printer = new SniperJavaPrettyPrinter(f.getEnvironment());
		CompilationUnit cu = f.CompilationUnit().getOrCreate(ctClass);
		List<CtType<?>> toBePrinted = new ArrayList<>();
		toBePrinted.add(ctClass);

		printer.calculate(cu, toBePrinted);

		resultChecker.accept(ctClass, printer.getResult());
	}
	
	private static String getResourcePath(String className) {
		String r = "./src/test/java/"+className.replaceAll("\\.", "/")+".java";
		if (new File(r).exists()) {
			return r;
		}
		r = "./src/test/resources/"+className.replaceAll("\\.", "/")+".java";
		if (new File(r).exists()) {
			return r;
		}
		throw new RuntimeException("Resource of class " + className + " doesn't exist");
	}

	private void assertChanged(CtType<?> ctClass, String printedSource, String... replacements) {
		assertEquals(0, replacements.length % 2);
		String originSource = ctClass.getPosition().getCompilationUnit().getOriginalSourceCode();
		//skip imports, which are not handled well yet
		originSource = skipImports(originSource);
		printedSource = skipImports(printedSource);
		
		int nrChanges = replacements.length / 2;
		for (int i = 0; i < nrChanges; i++) {
			String str = replacements[i * 2];
			String replacement = replacements[i * 2 + 1];
			originSource = originSource.replaceAll(str, replacement);
		}
		assertEquals(originSource, printedSource);
	}

	Pattern importRE = Pattern.compile("^(?:import|package)\\s.*;\\s*$", Pattern.MULTILINE);
	
	private String skipImports(String source) {
		Matcher m = importRE.matcher(source);
		int lastImportEnd = 0;
		while(m.find()) {
			lastImportEnd = m.end();
		};
		System.out.println(lastImportEnd);
		return source.substring(lastImportEnd).trim();
	}
}
