package org.ballerinalang.cassandra.generated.providers;

import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.natives.NativeElementRepository;
import org.ballerinalang.natives.NativeElementRepository.NativeActionDef;
import org.ballerinalang.natives.NativeElementRepository.NativeFunctionDef;
import org.ballerinalang.spi.NativeElementProvider;

@JavaSPIService ("org.ballerinalang.spi.NativeElementProvider")
public class StandardNativeElementProvider implements NativeElementProvider {

	@Override
	public void populateNatives(NativeElementRepository repo) {
		repo.registerNativeFunction(new NativeFunctionDef("wso2", "cassandra:0.0.0", "close", new TypeKind[] { TypeKind.RECORD }, new TypeKind[] {  }, "org.ballerinalang.cassandra.actions.Close"));
		repo.registerNativeFunction(new NativeFunctionDef("wso2", "cassandra:0.0.0", "CallerActions.update", new TypeKind[] { TypeKind.STRING, TypeKind.ARRAY }, new TypeKind[] {  }, "org.ballerinalang.cassandra.actions.Update"));
		repo.registerNativeFunction(new NativeFunctionDef("wso2", "cassandra:0.0.0", "CallerActions.select", new TypeKind[] { TypeKind.STRING, TypeKind.ARRAY }, new TypeKind[] { TypeKind.TABLE }, "org.ballerinalang.cassandra.actions.Select"));
		repo.registerNativeFunction(new NativeFunctionDef("wso2", "cassandra:0.0.0", "createClient", new TypeKind[] { TypeKind.RECORD }, new TypeKind[] {  }, "org.ballerinalang.cassandra.endpoint.CreateCassandraClient"));
	}

}
