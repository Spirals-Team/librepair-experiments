/*
 * OrbisWPS contains a set of libraries to build a Web Processing Service (WPS)
 * compliant with the 2.0 specification.
 *
 * OrbisWPS is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisWPS is distributed under GPL 3 license.
 *
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisWPS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisWPS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisWPS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.orbiswps.service.parser;

import net.opengis.ows._2.CodeType;
import net.opengis.wps._2_0.InputDescriptionType;
import net.opengis.wps._2_0.LiteralDataType;
import net.opengis.wps._2_0.OutputDescriptionType;
import org.orbisgis.orbiswps.groovyapi.attributes.DescriptionTypeAttribute;
import org.orbisgis.orbiswps.groovyapi.attributes.InputAttribute;
import org.orbisgis.orbiswps.groovyapi.attributes.LiteralDataAttribute;
import org.orbisgis.orbiswps.service.utils.ObjectAnnotationConverter;
import org.orbisgis.orbiswps.service.model.DataType;
import org.orbisgis.orbiswps.serviceapi.model.MalformedScriptException;
import org.orbisgis.orbiswps.serviceapi.parser.Parser;

import javax.xml.bind.JAXBElement;
import java.lang.reflect.Field;
import java.net.URI;

/**
 * @author Sylvain PALOMINOS
 **/

public class LiteralDataParser implements Parser {

    @Override
    public InputDescriptionType parseInput(Field f, Object defaultValue, URI processId) throws MalformedScriptException {
        InputDescriptionType input = new InputDescriptionType();
        DataType dataType = getFieldDataType(f);
        if(dataType == null){
            throw new MalformedScriptException(LiteralDataAttribute.class, f.getName(),
                    "The field type is not recognized.");
        }
        LiteralDataType data = ObjectAnnotationConverter.annotationToObject(f.getAnnotation(LiteralDataAttribute.class),
                dataType, defaultValue);
        JAXBElement<LiteralDataType> jaxbElement = new net.opengis.wps._2_0.ObjectFactory().createLiteralData(data);
        input.setDataDescription(jaxbElement);
        //Instantiate the returned input

        ObjectAnnotationConverter.annotationToObject(f.getAnnotation(InputAttribute.class), input);
        ObjectAnnotationConverter.annotationToObject(f.getAnnotation(DescriptionTypeAttribute.class), input,
                processId.toString());

        if(input.getIdentifier() == null){
            CodeType codeType = new CodeType();
            codeType.setValue(processId+":"+f.getName());
            input.setIdentifier(codeType);
        }
        return input;
    }

    @Override
    public OutputDescriptionType parseOutput(Field f, Object defaultValue, URI processId) throws MalformedScriptException {
        OutputDescriptionType output = new OutputDescriptionType();
        DataType dataType = getFieldDataType(f);
        if(dataType == null){
            throw new MalformedScriptException(LiteralDataAttribute.class, f.getName(),
                    "The field type is not recognized.");
        }
        LiteralDataType data = ObjectAnnotationConverter.annotationToObject(f.getAnnotation(LiteralDataAttribute.class),
                dataType, null);
        JAXBElement<LiteralDataType> jaxbElement = new net.opengis.wps._2_0.ObjectFactory().createLiteralData(data);
        output.setDataDescription(jaxbElement);
        //Instantiate the returned input

        ObjectAnnotationConverter.annotationToObject(f.getAnnotation(DescriptionTypeAttribute.class), output,
                processId.toString());

        if(output.getIdentifier() == null){
            CodeType codeType = new CodeType();
            codeType.setValue(processId+":"+f.getName());
            output.setIdentifier(codeType);
        }
        return output;
    }

    private DataType getFieldDataType(Field f){
        DataType dataType = null;
        Class type = f.getType();
        if(type.equals(int.class) || type.equals(Integer.class)){
            dataType = DataType.INTEGER;
        }
        else if(type.equals(float.class) || type.equals(Float.class)){
            dataType = DataType.FLOAT;
        }
        else if(type.equals(long.class) || type.equals(Long.class)){
            dataType = DataType.LONG;
        }
        else if(type.equals(double.class) || type.equals(Double.class)){
            dataType = DataType.DOUBLE;
        }
        else if(type.equals(char.class) || type.equals(Character.class)){
            dataType = DataType.UNSIGNED_BYTE;
        }
        else if(type.equals(short.class) || type.equals(Short.class)){
            dataType = DataType.SHORT;
        }
        else if(type.equals(byte.class) || type.equals(Byte.class)){
            dataType = DataType.BYTE;
        }
        else if(type.equals(boolean.class) || type.equals(Boolean.class)){
            dataType = DataType.BOOLEAN;
        }
        else if(type.equals(String.class)){
            dataType = DataType.STRING;
        }
        return dataType;
    }

    @Override
    public Class getAnnotation() {
        return LiteralDataAttribute.class;
    }

}
