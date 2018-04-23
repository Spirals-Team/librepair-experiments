package com.epam.brest.course.dao;

import com.epam.brest.course.dto.TruckFullDetailDto;

import com.epam.brest.course.dto.TruckWithAvgDto;
import com.epam.brest.course.model.Order;
import com.epam.brest.course.model.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * dao implementation for truck.
 */
public class TruckDaoImpl implements TruckDao {


    public static final String TRUCK_ID = "truckId";
    public static final String AVG_PER_MONTH = "avgPerMonth";
    public static final String PURCHASED_DATE = "purchasedDate";
    public static final String DESCRIPTIONS = "descriptions";
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * sql query for insert.
     */
    @Value("${truck.add}")
    private String addTruckSql;
    /**
     * sql query for delete.
     */
    @Value("${truck.deleteById}")
    private String deleteTruckByIdSql;
    /**
     * sql query for update.
     */
    @Value("${truck.update}")
    private String updateTruckSql;


    /**
     * basic select all.
     */
    @Value("${truck.selectAll}")
    private String getAllTrucksSql;

    /**
     * This is sql full truck detail.
     */
    @Value("${truck.selectFullTruckDetail}")
    private String fullTruckDetailsql;

    /**
     * This is sql get by truckCode.
     */
    @Value("${truck.selectTruckDetailById}")
    private String getTruckDetailByIdsql;



    /**
     * named param jdbcTemplate.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * @param namedParameterJdbcTemplate1 inject.
     */
    public final void setNamedParameterJdbcTemplate(
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate1) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate1;
    }

    /**
     * @return trucks.
     */
    @Override
    public final List<Truck> getAllTrucks() {
        LOGGER.debug("getAllTrucks()");
        List<Truck> trucks =
                namedParameterJdbcTemplate.getJdbcOperations()
                        .query(getAllTrucksSql,
                                BeanPropertyRowMapper.newInstance(Truck.class));
        return trucks;
    }

    /**
     * @param truck .
     * @return new truck.
     */
    @Override
    public final Truck addTruck(final Truck truck) {
        LOGGER.debug("addTruck({})", truck);

        MapSqlParameterSource
            namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("truckCode",
                    truck.getTruckCode());
            namedParameters.addValue("purchasedDate",
                    truck.getPurchasedDate());
            namedParameters.addValue("descriptions",
                    truck.getDescriptions());
            KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(
                    addTruckSql, namedParameters, generatedKeyHolder);
            truck.setTruckId(generatedKeyHolder.getKey().intValue());


        return truck;
    }

    /**
     * @param id .
     * @return truck.
     */
    @Override
    public final TruckWithAvgDto getTruckById(final Integer id) {
        LOGGER.debug("getTruckById({})", id);
        SqlParameterSource parameterSource =
                new MapSqlParameterSource("t.truckId", id);
        TruckWithAvgDto truckWithAvgDto = namedParameterJdbcTemplate
                .queryForObject(getTruckDetailByIdsql
                         , parameterSource,
                        BeanPropertyRowMapper.newInstance(TruckWithAvgDto.class));
        return truckWithAvgDto;
    }


    /**
     * @param id delete by id.
     */
    @Override
    public final void deleteTruckById(final Integer id) {
        LOGGER.debug("deleteTruckById({})", id);

        namedParameterJdbcTemplate
                .getJdbcOperations()
                .update(deleteTruckByIdSql, id);
    }

    /**
     * @param truck for update.
     */
    @Override
    public final void updateTruck(final Truck truck) {
        SqlParameterSource namedParameterSource =
                new MapSqlParameterSource("truckId",
                        truck.getTruckId())
                        .addValue("truckCode", truck.getTruckCode())
                        .addValue("purchasedDate", truck.getPurchasedDate())
                        .addValue("descriptions", truck.getDescriptions());
        namedParameterJdbcTemplate.update(updateTruckSql, namedParameterSource);
    }



    /**
     * @param id .
     * @return . This is an example of get BY id full detail- mapping of one to many.
     */
    @Override
    public TruckFullDetailDto getTruckFullDetailById (final Integer id) {

     SqlParameterSource namedParameterSource =
                new MapSqlParameterSource("t.truckId",
                        id);
       TruckDetailMapper mapper = new TruckDetailMapper();
       namedParameterJdbcTemplate.query(fullTruckDetailsql, namedParameterSource, mapper);

       TruckFullDetailDto truck = mapper.getDetail();
       return truck;

     }

    /**
     * This is mapper maps one to many relationship
     */
    public class TruckDetailMapper implements RowMapper<TruckFullDetailDto> {
       private TruckFullDetailDto detail;

     public TruckFullDetailDto mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        if (detail == null) {

            this.detail = new TruckFullDetailDto();

            detail.setTruckId(rs.getInt("truckId"));
            detail.setTruckCode(rs.getString("truckCode"));
          //  detail.setAvgPerMonth(rs.getDouble("avgPerMonth"));
            detail.setDescriptions(rs.getString("descriptions"));
            detail.setPurchasedDate(rs.getDate("purchasedDate"));
        }

         Order order = new Order ();
         order.setOrderId(rs.getInt("orderId"));
         order.setPetrolQty(rs.getDouble("petrolQty"));
         order.setOrderDate(rs.getDate("orderDate"));
         order.setTruckId(rs.getInt("truckId"));

         this.detail.getOrderList().add(order);
        return null;
    }

     private TruckFullDetailDto getDetail() {
        return detail;
     }

  }

}




