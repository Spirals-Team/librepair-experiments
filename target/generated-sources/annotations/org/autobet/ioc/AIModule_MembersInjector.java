package org.autobet.ioc;

import dagger.MembersInjector;
import java.util.Set;
import javax.annotation.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.autobet.ai.TeamRater;
import org.autobet.ai.TeamRaterStatsCollector;
import org.autobet.util.GamesProcessorDriver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_MembersInjector implements MembersInjector<AIModule> {
  private final Provider<DataSource> dataSourceProvider;

  private final Provider<GamesProcessorDriver> driverProvider;

  private final Provider<TeamRaterStatsCollector> statsCollectorProvider;

  private final Provider<Set<TeamRater>> teamRatersProvider;

  public AIModule_MembersInjector(
      Provider<DataSource> dataSourceProvider,
      Provider<GamesProcessorDriver> driverProvider,
      Provider<TeamRaterStatsCollector> statsCollectorProvider,
      Provider<Set<TeamRater>> teamRatersProvider) {
    assert dataSourceProvider != null;
    this.dataSourceProvider = dataSourceProvider;
    assert driverProvider != null;
    this.driverProvider = driverProvider;
    assert statsCollectorProvider != null;
    this.statsCollectorProvider = statsCollectorProvider;
    assert teamRatersProvider != null;
    this.teamRatersProvider = teamRatersProvider;
  }

  public static MembersInjector<AIModule> create(
      Provider<DataSource> dataSourceProvider,
      Provider<GamesProcessorDriver> driverProvider,
      Provider<TeamRaterStatsCollector> statsCollectorProvider,
      Provider<Set<TeamRater>> teamRatersProvider) {
    return new AIModule_MembersInjector(
        dataSourceProvider, driverProvider, statsCollectorProvider, teamRatersProvider);
  }

  @Override
  public void injectMembers(AIModule instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.provideDriver(dataSourceProvider.get());
    instance.provideTeamRaterStatsCollector(driverProvider.get());
    instance.providePlayerEvaluator(driverProvider.get());
    instance.provideChancesBasedPlayers(statsCollectorProvider.get(), teamRatersProvider.get());
  }
}
