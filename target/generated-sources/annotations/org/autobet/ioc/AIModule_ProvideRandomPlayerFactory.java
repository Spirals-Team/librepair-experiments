package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.autobet.ai.Player;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvideRandomPlayerFactory implements Factory<Player> {
  private final AIModule module;

  public AIModule_ProvideRandomPlayerFactory(AIModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Player get() {
    return Preconditions.checkNotNull(
        module.provideRandomPlayer(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Player> create(AIModule module) {
    return new AIModule_ProvideRandomPlayerFactory(module);
  }
}
