import com.dslplatform.json.AbstractAnnotationProcessorTest;
import com.dslplatform.json.CompiledJsonProcessor;
import com.dslplatform.json.NonNull;
import org.junit.Test;

import javax.annotation.processing.Processor;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PackageTest extends AbstractAnnotationProcessorTest {

	@NonNull
	protected Collection<Processor> getProcessors() {
		return Collections.<Processor>singletonList(new CompiledJsonProcessor());
	}
	@NonNull
	protected List<String> getDefaultArguments() {
		return Collections.singletonList("-Adsljson.showdsl=true");
	}

	@Test
	public void testPackageValidation() {
		assertCompilationReturned(
				Diagnostic.Kind.ERROR,
				3,
				compileTestCase(NoPackage.class),
				"but class 'NoPackage' is defined without a package name and cannot be accessed");
	}

	@Test
	public void testPackageValidationForNested() {
		assertCompilationReturned(
				Diagnostic.Kind.ERROR,
				4,
				compileTestCase(NestedWithoutPackage.Nested.class),
				"but class 'NestedWithoutPackage.Nested' is defined without a package name and cannot be accessed");
	}
}
