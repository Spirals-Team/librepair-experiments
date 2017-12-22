/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.util;

import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.LogicalBinaryExpression;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.presto.sql.tree.LogicalBinaryExpression.Type.AND;
import static java.util.Collections.emptyList;

public class SpatialJoinUtils
{
    public static final String ST_CONTAINS = "st_contains";
    public static final String ST_INTERSECTS = "st_intersects";

    private SpatialJoinUtils()
    {
    }

    public static boolean isSupportedSpatialFunction(FunctionCall functionCall)
    {
        String functionName = functionCall.getName().toString();
        return functionName.equalsIgnoreCase(ST_CONTAINS)
                || functionName.equalsIgnoreCase(ST_INTERSECTS);
    }

    public static boolean isSupportedSpatialFilter(Expression filterExpression)
    {
        if (filterExpression instanceof FunctionCall) {
            return isSupportedSpatialFunction((FunctionCall) filterExpression);
        }
        else if (filterExpression instanceof LogicalBinaryExpression) {
            LogicalBinaryExpression binaryExpression = (LogicalBinaryExpression) filterExpression;
            if (binaryExpression.getType() == AND) {
                return isSupportedSpatialFunction((FunctionCall) binaryExpression.getLeft()) || isSupportedSpatialFunction((FunctionCall) binaryExpression.getRight());
            }
        }

        return false;
    }

    public static List<FunctionCall> extractSupportedSpatialFunctions(Expression filterExpression)
    {
        if (filterExpression instanceof FunctionCall) {
            FunctionCall function = (FunctionCall) filterExpression;
            if (isSupportedSpatialFunction(function)) {
                return ImmutableList.of(function);
            }
        }
        else if (filterExpression instanceof LogicalBinaryExpression) {
            LogicalBinaryExpression binaryExpression = (LogicalBinaryExpression) filterExpression;
            if (binaryExpression.getType() == AND) {
                List<FunctionCall> spatialFunctions = new ArrayList<>();
                spatialFunctions.addAll(extractSupportedSpatialFunctions(binaryExpression.getLeft()));
                spatialFunctions.addAll(extractSupportedSpatialFunctions(binaryExpression.getRight()));
                return spatialFunctions;
            }
        }

        return emptyList();
    }
}
