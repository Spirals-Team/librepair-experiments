package org.sql2o.samples.javaedge;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.persistence.Column;
import javax.sql.DataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * @author Ivyxjc
 * @since 4/8/2018
 */
public class DruidDatasourceTest {

    private static Sql2o sql2o;

    @BeforeClass
    public static void initial() throws Exception {
        ClassLoader loader = DruidDatasourceTest.class.getClassLoader();
        URL url = loader.getResource("jdbc.properties");
        URLConnection connection = url.openConnection();
        connection.connect();
        Properties properties = new Properties();
        properties.load(connection.getInputStream());

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        sql2o = new Sql2o(dataSource);
    }

    @Test
    public void testFirst() {
        Connection connection = sql2o.open();
        Long l = (Long) connection.createQuery("SELECT COUNT(1) FROM movie_detail").executeScalar();
        System.out.println(l);
    }

    @Test
    public void testQuery() {
        Connection connection = sql2o.open();
        //MovieCountry movieCountry = connection.createQuery("SELECT * FROM movie_country")
        //    .executeAndFetchFirst(MovieCountry.class);
        ////System.out.println(movieDetail.movie_id);
        ////System.out.println(movieDetail.rating_1);
        //System.out.println(movieCountry.movie_country);
        //System.out.println(movieCountry.movie_id);

        IvyData ivyData = connection.createQuery("SELECT * FROM ivyxjc_data")
            .throwOnMappingFailure(false)
            .setCaseSensitive(false)
            .executeAndFetchFirst(IvyData.class);
        System.out.println(ivyData.getCreatedAt());
        //System.out.println(LocalDateTime.now().atZone(ZoneId.of("America/St_Johns")));
        //System.out.println(ZoneId.systemDefault());
        //System.out.println(ZonedDateTime.now());
    }
}

class MovieDetail {
    int movie_id;
    String movie_name;
    double rating_1;
}

class MovieCountry {
    int id;
    double movie_id;
    String movie_country;
}

class IvyData {
    //private String guid;
    //@Column(name = "source_id")
    //private String sourceId;
    //private String value;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //private Date updated_at;
    //
    //@Column(name = "created_by")
    //private String createdBy;
    //
    //@Column(name = "updated_by")
    //private String updatedBy;
    //

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}