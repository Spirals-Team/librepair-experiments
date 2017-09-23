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
package com.facebook.presto.operator;

import com.facebook.presto.execution.DriverGroupId;
import com.facebook.presto.spi.type.Type;

import java.util.List;

public interface OperatorFactory
{
    List<Type> getTypes();

    Operator createOperator(DriverContext driverContext);

    /**
     * Declare that createOperator will not be called any more and release
     * any resources associated with this factory.
     * <p>
     * This method will be called only once.
     * Implementation doesn't need to worry about duplicate invocations.
     * <p>
     * It is guaranteed that this will only be invoked after {@link #noMoreOperators(DriverGroupId)}
     * has been invoked for all applicable driver groups.
     */
    void noMoreOperators();

    /**
     * Declare that createOperator will not be called any more for the specified DriverGroupId,
     * and release any resources associated with this factory.
     * <p>
     * This method will be called only once for each DriverGroupId.
     * Implementation doesn't need to worry about duplicate invocations.
     * <p>
     * It is guaranteed that this method will be invoked for all applicable driver groups
     * before {@link #noMoreOperators()} is invoked.
     */
    default void noMoreOperators(DriverGroupId driverGroupId)
    {
        // do nothing
    }

    OperatorFactory duplicate();
}
