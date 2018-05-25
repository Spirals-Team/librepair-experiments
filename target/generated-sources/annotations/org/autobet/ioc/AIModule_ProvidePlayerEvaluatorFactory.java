package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.autobet.ai.PlayerEvaluator;
import org.autobet.util.GamesProcessorDriver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvidePlayerEvaluatorFactory implements Factory<PlayerEvaluator> {
  private final AIModule module;

  private final Provider<GamesProcessorDriver> driverProvider;

  public AIModule_ProvidePlayerEvaluatorFactory(
      AIModule module, Provider<GamesProcessorDriver> driverProvider) {
    assert module != null;
    this.module = module;
    assert driverProvider != null;
    this.driverProvider = driverProvider;
  }

  @Override
  public PlayerEvaluator get() {
    return Preconditions.checkNotNull(
        module.providePlayerEvaluator(driverProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<PlayerEvaluator> create(
      AIModule module, Provider<GamesProcessorDriver> driverProvider) {
    return new AIModule_ProvidePlayerEvaluatorFactory(module, driverProvider);
  }
}
