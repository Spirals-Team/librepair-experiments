package org.sjc.transparencia.data;

import org.sjc.transparencia.Dao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.UUID;

public class DataDao {

    private Sql2o connection;

    public DataDao() {
        this.connection = Dao.getConnection();
    }

    public List<Data> retrieveAllData() {
        try (Connection conn = connection.open()) {
            List<Data> datas = conn.createQuery("select * from data")
                    .executeAndFetch(Data.class);
            return datas;
        }
    }

    public List<Data> retrieveByYear(Integer ano) {
        try (Connection conn = connection.open()) {
            List<Data> datas = conn.createQuery("select * from data where ano=:ano")
                    .addParameter("ano", ano)
                    .executeAndFetch(Data.class);
            return datas;
        }
    }

    public Data retrieveByUuid(UUID dataUuid) {
        try (Connection conn = connection.open()) {
            List<Data> datas = conn.createQuery("select * from data where data_uuid=:data_uuid")
                    .addParameter("data_uuid", dataUuid)
                    .executeAndFetch(Data.class);
            return datas.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public UUID retrieveDataUuid(Data data) {
        try (Connection conn = connection.open()) {
            List<Data> datas = conn.createQuery("select * from data where mes=:mes and ano=:ano")
                    .addParameter("mes", data.getMes())
                    .addParameter("ano", data.getAno())
                    .executeAndFetch(Data.class);
            return datas.get(0).getData_uuid();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public UUID insertData(Data data) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("insert into data");
        queryBuilder.append("(data_uuid, mes, ano) ");
        queryBuilder.append("values");
        queryBuilder.append("(:data_uuid, :mes, :ano)");
        try (Connection conn = connection.beginTransaction()) {
            UUID dataUuid = UUID.randomUUID();
            conn.createQuery(queryBuilder.toString())
                    .addParameter("data_uuid", data.getData_uuid())
                    .addParameter("mes", data.getMes())
                    .addParameter("ano", data.getAno())
                    .executeUpdate();
            conn.commit();
            return dataUuid;
        }
    }

    public Boolean removeData(UUID dataUuid) {
        try (Connection conn = connection.open()) {
            List<Data> datas = conn.createQuery("delete from data where data_uuid=:data_uuid")
                    .addParameter("data_uuid", dataUuid)
                    .executeAndFetch(Data.class);
            return true;
        } catch (Sql2oException e) {
            return false;
        }
    }
}