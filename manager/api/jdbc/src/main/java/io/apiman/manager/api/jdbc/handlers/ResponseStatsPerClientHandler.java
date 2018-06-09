/*
 * Copyright 2016 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.manager.api.jdbc.handlers;

import io.apiman.manager.api.beans.metrics.ResponseStatsDataPoint;
import io.apiman.manager.api.beans.metrics.ResponseStatsPerClientBean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * @author eric.wittmann@gmail.com
 */
public class ResponseStatsPerClientHandler implements ResultSetHandler<ResponseStatsPerClientBean> {
    
    /**
     * Constructor.
     */
    public ResponseStatsPerClientHandler() {
    }

    /**
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    @Override
    public ResponseStatsPerClientBean handle(ResultSet rs) throws SQLException {
        ResponseStatsPerClientBean rval = new ResponseStatsPerClientBean();
        while (rs.next()) {
            String client = rs.getString(1);
            if (client == null) {
                continue;
            }
            String rtype = rs.getString(2);
            long count = rs.getLong(3);
            
            ResponseStatsDataPoint dataPoint = rval.getData().get(client);
            if (dataPoint == null) {
                dataPoint = new ResponseStatsDataPoint();
                rval.getData().put(client, dataPoint);
            }
            
            if (rtype == null) {
                dataPoint.setTotal(dataPoint.getErrors() + dataPoint.getFailures() + count);
            } else if (rtype.equals("failure")) { //$NON-NLS-1$
                dataPoint.setTotal(dataPoint.getTotal() + count);
                dataPoint.setFailures(count);
            } else if (rtype.equals("error")) { //$NON-NLS-1$
                dataPoint.setTotal(dataPoint.getTotal() + count);
                dataPoint.setErrors(count);
            }
        }
        return rval;
    }

}
