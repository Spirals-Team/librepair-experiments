package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.autobet.ai.TeamRaterStatsCollector;
import org.autobet.util.GamesProcessorDriver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvideTeamRaterStatsCollectorFactory
    implements Factory<TeamRaterStatsCollector> {
  private final AIModule module;

  private final Provider<GamesProcessorDriver> driverProvider;

  public AIModule_ProvideTeamRaterStatsCollectorFactory(
      AIModule module, Provider<GamesProcessorDriver> driverProvider) {
    assert module != null;
    this.module = module;
    assert driverProvider != null;
    this.driverProvider = driverProvider;
  }

  @Override
  public TeamRaterStatsCollector get() {
    return Preconditions.checkNotNull(
        module.provideTeamRaterStatsCollector(driverProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<TeamRaterStatsCollector> create(
      AIModule module, Provider<GamesProcessorDriver> driverProvider) {
    return new AIModule_ProvideTeamRaterStatsCollectorFactory(module, driverProvider);
  }
}
