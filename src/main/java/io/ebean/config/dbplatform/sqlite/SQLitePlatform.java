package io.ebean.config.dbplatform.sqlite;

import io.ebean.annotation.Platform;
import io.ebean.config.dbplatform.DatabasePlatform;
import io.ebean.config.dbplatform.DbPlatformType;
import io.ebean.config.dbplatform.DbType;
import io.ebean.config.dbplatform.IdType;

import java.sql.Types;

public class SQLitePlatform extends DatabasePlatform {

  public SQLitePlatform() {
    super();
    this.platform = Platform.SQLITE;
    this.dbIdentity.setIdType(IdType.IDENTITY);
    this.dbIdentity.setSupportsGetGeneratedKeys(false);
    this.dbIdentity.setSupportsSequence(false);
    this.dbIdentity.setSelectLastInsertedIdTemplate("select last_insert_rowid()");

    this.booleanDbType = Types.INTEGER;
    this.likeClauseRaw = "like ?";
    this.likeClauseEscaped = "like ?";

    dbTypeMap.put(DbType.BIT, new DbPlatformType("int default 0"));
    dbTypeMap.put(DbType.BOOLEAN, new DbPlatformType("int default 0"));
    dbTypeMap.put(DbType.BIGINT, new DbPlatformType("integer"));
    dbTypeMap.put(DbType.SMALLINT, new DbPlatformType("integer"));
  }

  protected void escapeLikeCharacter(char ch, StringBuilder sb) {
    sb.append(ch);
  }

}
