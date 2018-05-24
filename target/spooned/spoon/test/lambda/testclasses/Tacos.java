package spoon.test.lambda.testclasses;


public class Tacos {
    public void m(java.lang.String id) {
        queryForObject(("SELECT * FROM persons WHERE id = " + id), ( rs, i) -> rs.getString("FIRST_NAME"));
    }

    public <T> T queryForObject(java.lang.String sql, spoon.test.lambda.testclasses.Tacos.RowMapper<T> rowMapper) {
        return null;
    }

    public interface RowMapper<T> {
        T mapRow(java.sql.ResultSet var1, int var2) throws java.sql.SQLException;
    }
}

