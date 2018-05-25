package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.autobet.util.GamesProcessorDriver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvideDriverFactory implements Factory<GamesProcessorDriver> {
  private final AIModule module;

  private final Provider<DataSource> dataSourceProvider;

  public AIModule_ProvideDriverFactory(AIModule module, Provider<DataSource> dataSourceProvider) {
    assert module != null;
    this.module = module;
    assert dataSourceProvider != null;
    this.dataSourceProvider = dataSourceProvider;
  }

  @Override
  public GamesProcessorDriver get() {
    return Preconditions.checkNotNull(
        module.provideDriver(dataSourceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GamesProcessorDriver> create(
      AIModule module, Provider<DataSource> dataSourceProvider) {
    return new AIModule_ProvideDriverFactory(module, dataSourceProvider);
  }
}
