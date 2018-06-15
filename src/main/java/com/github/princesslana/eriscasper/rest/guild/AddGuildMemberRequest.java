package com.github.princesslana.eriscasper.rest.guild;

import com.github.princesslana.eriscasper.data.Snowflake;
import com.google.common.collect.ImmutableList;
import java.util.Optional;
import org.immutables.value.Value;

/**
 * @see <a
 *     href="https://discordapp.com/developers/docs/resources/guild#add-guild-member-json-params">
 *     https://discordapp.com/developers/docs/resources/guild#add-guild-member-json-params</a>
 */
@Value.Immutable
public interface AddGuildMemberRequest {
  String getAccessToken();

  Optional<String> getNick();

  ImmutableList<Snowflake> getRoles();

  Optional<Boolean> isMute();

  Optional<Boolean> isDeaf();
}
