/*
 * Copyright 2017 Ahmad Bawaneh
 *
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
package org.dominokit.jacksonapt.processor.deserialization;

import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.bean.BeanPropertyDeserializer;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.ObjectMapperProcessor;
import org.dominokit.jacksonapt.processor.Type;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;
import java.util.stream.Collectors;

class DeserializerBuilder {

    private final TypeMirror beanType;
    private final Element field;
    private final TypeMirror fieldType;

    DeserializerBuilder(TypeMirror beanType, Element field) {
        this.beanType = beanType;
        this.field = field;
        this.fieldType = field.asType();
    }

    TypeSpec buildDeserializer() {
        final String paramValue = "value";
        final String paramBean = "bean";

        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder( "" )
                .superclass( ParameterizedTypeName
                        .get( ClassName.get( BeanPropertyDeserializer.class ), TypeName.get( beanType ), Type.wrapperType( fieldType) ) );

            builder.addMethod( buildDeserializerMethod());

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder( "setValue" )
                .addModifiers( Modifier.PUBLIC )
                .addAnnotation( Override.class )
                .addParameter( ClassName.get( beanType ), paramBean );

        AbstractJsonMapperGenerator.AccessorInfo accessorInfo=setterInfo(field);

        methodBuilder.addParameter( Type.wrapperType( fieldType ), paramValue )
                .addParameter( JsonDeserializationContext.class, "ctx" )
                .addStatement( "$L", paramBean + "." + accessorInfo.accessor + (accessorInfo.present ? "(" : "=") + paramValue + (accessorInfo.present ? ")" : "") );

        builder.addMethod( methodBuilder.build() );

        return builder.build();
    }

    private MethodSpec buildDeserializerMethod(){
        return MethodSpec.methodBuilder( "newDeserializer" )
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns( ParameterizedTypeName.get( ClassName.get( JsonDeserializer.class ), ObjectMapperProcessor.DEFAULT_WILDCARD ) )
                .addStatement( "return $L", new FieldDeserializersChainBuilder(beanType).getInstance(field) )
                .build();
    }

    private AbstractJsonMapperGenerator.AccessorInfo setterInfo(Element field) {
        final String upperCaseFirstLetter = upperCaseFirstLetter(field.getSimpleName().toString());
        if (allBeanMethods(beanType).contains("set" + upperCaseFirstLetter)) {
            return new AbstractJsonMapperGenerator.AccessorInfo(true, "set" + upperCaseFirstLetter);
        }
        return new AbstractJsonMapperGenerator.AccessorInfo(false, field.getSimpleName().toString());
    }

    private String upperCaseFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private Set<String> allBeanMethods(TypeMirror beanType) {
        return ((TypeElement) ObjectMapperProcessor.typeUtils.asElement(beanType)).getEnclosedElements().stream()
                .filter(e -> ElementKind.METHOD.equals(e.getKind()) &&
                        !e.getModifiers().contains(Modifier.STATIC) &&
                        e.getModifiers().contains(Modifier.PUBLIC))
                .map(e -> e.getSimpleName().toString()).collect(Collectors.toSet());
    }

}
