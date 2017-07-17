/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.contentstream.operator.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.contentstream.operator.Operator;

import java.awt.geom.GeneralPath;
import java.io.IOException;

/**
 * B Fill and then stroke the path, using the nonzero winding number rule to determine the region
 * to fill.
 *
 * @author Ben Litchfield
 */
public class FillNonZeroAndStrokePath extends GraphicsOperatorProcessor
{
    @Override
    public void process(Operator operator, List<COSBase> operands) throws IOException
    {
        context.fillAndStrokePath(GeneralPath.WIND_NON_ZERO);
    }

    @Override
    public String getName()
    {
        return "B";
    }
}
