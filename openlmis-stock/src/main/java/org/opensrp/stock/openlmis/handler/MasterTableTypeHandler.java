package org.opensrp.stock.openlmis.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterTableTypeHandler implements TypeHandler<BaseMetaData> {

    public static final ObjectMapper mapper = new ObjectMapper().enableDefaultTyping();

    @Override
    public void setParameter(PreparedStatement ps, int i, BaseMetaData parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (parameter != null) {
                String jsonString = mapper.writeValueAsString(parameter);
                PGobject jsonObject = new PGobject();
                jsonObject.setType("jsonb");
                jsonObject.setValue(jsonString);
                ps.setObject(i, jsonObject);
            }
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BaseMetaData getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String jsonString = rs.getString(columnName);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            BaseMetaData result = mapper.readValue(jsonString, BaseMetaData.class);
            return result;
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BaseMetaData getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String jsonString = rs.getString(columnIndex);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            BaseMetaData result = mapper.readValue(jsonString, BaseMetaData.class);
            return result;
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public BaseMetaData getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String jsonString = cs.getString(columnIndex);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            BaseMetaData result = mapper.readValue(jsonString, BaseMetaData.class);
            return result;
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
