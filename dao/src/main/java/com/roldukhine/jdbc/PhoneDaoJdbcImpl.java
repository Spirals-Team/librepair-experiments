package com.roldukhine.jdbc;

import com.roldukhine.api.PhoneDao;
import com.roldukhine.entity.Employee;
import com.roldukhine.entity.Phone;
import com.roldukhine.entity.PhoneType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
public class PhoneDaoJdbcImpl extends AbstractDaoJdbcImpl<Phone> implements PhoneDao {

    private static final String TABLE_NAME = "Phone";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (number, type) VALUES (?, ?)";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET number = ?, type = ? WHERE id = ?";

    private static final String GET_PHONE_LIST_BY_EMPLOYEE =
            "SELECT phone.*  \n" +
                    "  FROM EMPLOYEE_TO_PHONE AS ETP" +
                    "      ,PHONE" +
                    " WHERE PHONE.id = ETP.phone_id" +
                    "   AND ETP.employee_id = ?";

    private static final String INSERT_PHONE_TO_EMPLOYEE = "INSERT INTO employee_to_phone (phone_id, employee_id) " +
            "VALUES (?, ?)";

    @Override
    protected String getTableName() {
        logger.trace("getTableName");
        logger.debug("return TableName: " + TABLE_NAME);
        return TABLE_NAME;
    }

    @Override
    public void insert(final Phone phone) {
        logger.debug("insert phone {}", phone);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            logger.debug("createPreparedStatement connection {}", connection);
            PreparedStatement prepareStatement = connection.prepareStatement(INSERT_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setObject(1, phone.getNumber());
            prepareStatement.setObject(2, phone.getPhoneTypeTag());
            return prepareStatement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        phone.setId(id);
    }

    @Override
    public void update(Phone phone) {
        logger.debug("update phone {}", phone);
        this.jdbcTemplate.update(UPDATE_SQL,
                phone.getNumber(),
                phone.getPhoneTypeTag(),
                phone.getId());
    }

    @Override
    protected Phone createInstanceFromResult(ResultSet resultSet) throws SQLException {
        logger.debug("createInstanceFromResult: resultSet {}", resultSet);
        Phone phone = new Phone();
        phone.setId(resultSet.getLong("id"));
        phone.setNumber(resultSet.getString("number"));
        int rsType = resultSet.getInt("type");
        for (PhoneType type : PhoneType.values()) {
            if (type.getTag() == rsType) {
                phone.setType(type);
            }
        }
        return phone;
    }

    public List<Phone> getPhoneListByEmployee(Employee employee) {
        logger.debug("getPhoneListByEmployee Employee: {}", employee);
        final long employeeId = employee.getId();

        List<Phone> phoneList = this.jdbcTemplate.query(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PHONE_LIST_BY_EMPLOYEE);
            preparedStatement.setObject(1, employeeId);
            return preparedStatement;
        }, (resultSet, index) -> createInstanceFromResult(resultSet));

        return phoneList;
    }

    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        logger.debug("insertPhoneToEmployee phone {}, employee {}", phone, employee);
        this.jdbcTemplate.update(INSERT_PHONE_TO_EMPLOYEE,
                phone.getId(),
                employee.getId());
    }
}
