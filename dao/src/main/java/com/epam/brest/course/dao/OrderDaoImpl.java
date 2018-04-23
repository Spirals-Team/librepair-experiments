package com.epam.brest.course.dao;

import com.epam.brest.course.dto.OrderWithTruckCodeDto;
import com.epam.brest.course.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * dao implementation for order.
 */
public class OrderDaoImpl implements OrderDao {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * start date.
     */
    private static final String START = "start";
    /**
     * end date.
     */
    private static final String END = "end";
    /**
     * sql query for insert.
     */
    @Value("${order.add}")
    private String addOrderSql;
    /**
     * sql query for delete.
     */
    @Value("${order.deleteById}")
    private String deleteOrderByIdSql;
    /**
     * sql query for update.
     */
    @Value("${order.update}")
    private String updateOrderSql;
    /**
     * sql query for view list of orders.
     */
    @Value("${order.selectAllWithTruckCode}")
    private String getAllOrdersWithTruckCodeSql;
    /**
     * sql query for select where @id.
     */
    @Value("${order.selectById}")
    private String getOrderByIdSql;
    /**
     * sql query for select all.
     */
    @Value("${order.selectAll}")
    private String getAllOrdersSql;

    /**
     * named param jdbcTemplate.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @param namedParameterJdbcTemplate1 inject.
     */
    public final void setNamedParameterJdbcTemplate(final
                                                    NamedParameterJdbcTemplate namedParameterJdbcTemplate1) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate1;
    }

    /**
     * @return ordersDto list.
     */
    @Override
    public final Collection<OrderWithTruckCodeDto> getAllOrdersWithTruckCode() {

        LOGGER.debug("getAllOrdersWithTruckCode()");
        Collection<OrderWithTruckCodeDto> ordersDto =

                namedParameterJdbcTemplate
                        .getJdbcOperations()
                        .query(getAllOrdersWithTruckCodeSql,
                                new OrderWithTruckCodeDtoRowMapper());
        return ordersDto;
    }

    /**
     * @return all orders.
     */
    @Override
    public final Collection<Order> getAllOrders(Date start, Date end) {
        LOGGER.debug("getAllOrders({})", start, end);

        MapSqlParameterSource parameterSource =
                new MapSqlParameterSource();

        parameterSource.addValue(START, start);
        parameterSource.addValue(END, end);

        Collection<Order> orders = namedParameterJdbcTemplate
                .query(getAllOrdersSql, parameterSource,
                        BeanPropertyRowMapper.newInstance(Order.class));

        return orders;
    }


    /**
     * @param orderId getByID.
     * @return one order.
     */
    @Override
    public final Order getOrderById(final Integer orderId) {
        LOGGER.debug("getOrderById({})", orderId);
        SqlParameterSource parameterSource =
                new MapSqlParameterSource("orderId", orderId);

        Order order = namedParameterJdbcTemplate
                .queryForObject(
                        getOrderByIdSql, parameterSource,
                        BeanPropertyRowMapper.newInstance(Order.class));
        return order;
    }

    /**
     * @param order insert.
     * @return new order.
     */
    @Override
    public final Order addOrder(final Order order) {
        LOGGER.debug("addOrder({})", order);
        MapSqlParameterSource
                namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("petrolQty",
                order.getPetrolQty());

        namedParameters.addValue("orderDate",
                order.getOrderDate());

        namedParameters.addValue("truckId",
                order.getTruckId());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate
                .update(addOrderSql,
                        namedParameters, generatedKeyHolder);
        order.setOrderId(generatedKeyHolder.getKey().intValue());
        return order;
    }

    /**
     * @param orderId delete by id.
     */
    @Override
    public final void deleteOrderById(final Integer orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        namedParameterJdbcTemplate.getJdbcOperations()
                .update(deleteOrderByIdSql, orderId);
    }


    /**
     * @param order for update.
     *              BeanPropertySqlParameterSource obtains parameter values
     *              from bean properties of a given JavaBean object. The names of the bean
     *              properties have to match the parameter names.
     */
    @Override
    public final void updateOrder(final Order order) {
        LOGGER.debug("updateOrder({})", order);

        SqlParameterSource namedParameter =
                new BeanPropertySqlParameterSource(order);
        namedParameterJdbcTemplate.update(updateOrderSql, namedParameter);
    }

    /**
     * RowMapper.
     */
    private final class OrderWithTruckCodeDtoRowMapper implements
            RowMapper<OrderWithTruckCodeDto> {

        @Override
        public OrderWithTruckCodeDto mapRow(
                final ResultSet resultSet, final int i)
                throws SQLException {

            OrderWithTruckCodeDto orderWithTruckCodeDto =
                    new OrderWithTruckCodeDto();

            orderWithTruckCodeDto
                    .setOrderId(resultSet.getInt("orderId"));

            orderWithTruckCodeDto
                    .setOrderDate(resultSet.getDate("orderDate"));

            orderWithTruckCodeDto
                    .setPetrolQty(resultSet.getDouble("petrolQty"));

            orderWithTruckCodeDto
                    .setTruckCode(resultSet.getString("truckCode"));

            return orderWithTruckCodeDto;
        }
    }
}
