package org.hibernate.search.elasticsearch.schema.impl;

import java.util.Locale;
import java.io.Serializable;
import java.lang.Object;
import java.lang.String;

/**
 * Warning this class consists of generated code.
 */
public class ElasticsearchValidationMessages_$bundle implements ElasticsearchValidationMessages, Serializable {
    private static final long serialVersionUID = 1L;
    protected ElasticsearchValidationMessages_$bundle() {}
    public static final ElasticsearchValidationMessages_$bundle INSTANCE = new ElasticsearchValidationMessages_$bundle();
    protected Object readResolve() {
        return INSTANCE;
    }
    private static final Locale LOCALE = Locale.ROOT;
    protected Locale getLoggingLocale() {
        return LOCALE;
    }
    private static final String indexContext = "index '%1$s'";
    protected String indexContext$str() {
        return indexContext;
    }
    @Override
    public final String indexContext(final String name) {
        return String.format(getLoggingLocale(), indexContext$str(), name);
    }
    private static final String mappingContext = "mapping '%1$s'";
    protected String mappingContext$str() {
        return mappingContext;
    }
    @Override
    public final String mappingContext(final String name) {
        return String.format(getLoggingLocale(), mappingContext$str(), name);
    }
    private static final String mappingPropertyContext = "property '%1$s'";
    protected String mappingPropertyContext$str() {
        return mappingPropertyContext;
    }
    @Override
    public final String mappingPropertyContext(final String path) {
        return String.format(getLoggingLocale(), mappingPropertyContext$str(), path);
    }
    private static final String mappingPropertyFieldContext = "field '%1$s'";
    protected String mappingPropertyFieldContext$str() {
        return mappingPropertyFieldContext;
    }
    @Override
    public final String mappingPropertyFieldContext(final String name) {
        return String.format(getLoggingLocale(), mappingPropertyFieldContext$str(), name);
    }
    private static final String analyzerContext = "analyzer '%1$s'";
    protected String analyzerContext$str() {
        return analyzerContext;
    }
    @Override
    public final String analyzerContext(final String name) {
        return String.format(getLoggingLocale(), analyzerContext$str(), name);
    }
    private static final String normalizerContext = "normalizer '%1$s'";
    protected String normalizerContext$str() {
        return normalizerContext;
    }
    @Override
    public final String normalizerContext(final String name) {
        return String.format(getLoggingLocale(), normalizerContext$str(), name);
    }
    private static final String charFilterContext = "char filter '%1$s'";
    protected String charFilterContext$str() {
        return charFilterContext;
    }
    @Override
    public final String charFilterContext(final String name) {
        return String.format(getLoggingLocale(), charFilterContext$str(), name);
    }
    private static final String tokenizerContext = "tokenizer '%1$s'";
    protected String tokenizerContext$str() {
        return tokenizerContext;
    }
    @Override
    public final String tokenizerContext(final String name) {
        return String.format(getLoggingLocale(), tokenizerContext$str(), name);
    }
    private static final String tokenFilterContext = "token filter '%1$s'";
    protected String tokenFilterContext$str() {
        return tokenFilterContext;
    }
    @Override
    public final String tokenFilterContext(final String name) {
        return String.format(getLoggingLocale(), tokenFilterContext$str(), name);
    }
    private static final String mappingMissing = "Missing type mapping";
    protected String mappingMissing$str() {
        return mappingMissing;
    }
    @Override
    public final String mappingMissing() {
        return String.format(getLoggingLocale(), mappingMissing$str());
    }
    private static final String propertyMissing = "Missing property mapping";
    protected String propertyMissing$str() {
        return propertyMissing;
    }
    @Override
    public final String propertyMissing() {
        return String.format(getLoggingLocale(), propertyMissing$str());
    }
    private static final String propertyFieldMissing = "Missing field mapping";
    protected String propertyFieldMissing$str() {
        return propertyFieldMissing;
    }
    @Override
    public final String propertyFieldMissing() {
        return String.format(getLoggingLocale(), propertyFieldMissing$str());
    }
    private static final String invalidAttributeValue = "Invalid value for attribute '%1$s'. Expected '%2$s', actual is '%3$s'";
    protected String invalidAttributeValue$str() {
        return invalidAttributeValue;
    }
    @Override
    public final String invalidAttributeValue(final String string, final Object expectedValue, final Object actualValue) {
        return String.format(getLoggingLocale(), invalidAttributeValue$str(), string, expectedValue, actualValue);
    }
    private static final String invalidOutputFormat = "The output format (the first format in the '%1$s' attribute) is invalid. Expected '%2$s', actual is '%3$s'";
    protected String invalidOutputFormat$str() {
        return invalidOutputFormat;
    }
    @Override
    public final String invalidOutputFormat(final String string, final String expectedValue, final String actualValue) {
        return String.format(getLoggingLocale(), invalidOutputFormat$str(), string, expectedValue, actualValue);
    }
    private static final String invalidInputFormat = "Invalid formats for attribute '%1$s'. Every required formats must be in the list, though it's not required to provide them in the same order, and the list must not contain unexpected formats. Expected '%2$s', actual is '%3$s', missing elements are '%4$s', unexpected elements are '%5$s'.";
    protected String invalidInputFormat$str() {
        return invalidInputFormat;
    }
    @Override
    public final String invalidInputFormat(final String string, final java.util.List<String> expectedValue, final java.util.List<String> actualValue, final java.util.List<String> missingFormats, final java.util.List<String> unexpectedFormats) {
        return String.format(getLoggingLocale(), invalidInputFormat$str(), string, expectedValue, actualValue, missingFormats, unexpectedFormats);
    }
    private static final String analyzerMissing = "Missing analyzer definition";
    protected String analyzerMissing$str() {
        return analyzerMissing;
    }
    @Override
    public final String analyzerMissing() {
        return String.format(getLoggingLocale(), analyzerMissing$str());
    }
    private static final String normalizerMissing = "Missing normalizer definition";
    protected String normalizerMissing$str() {
        return normalizerMissing;
    }
    @Override
    public final String normalizerMissing() {
        return String.format(getLoggingLocale(), normalizerMissing$str());
    }
    private static final String invalidAnalyzerCharFilters = "Invalid char filters. Expected '%1$s', actual is '%2$s'";
    protected String invalidAnalyzerCharFilters$str() {
        return invalidAnalyzerCharFilters;
    }
    @Override
    public final String invalidAnalyzerCharFilters(final Object expected, final Object actual) {
        return String.format(getLoggingLocale(), invalidAnalyzerCharFilters$str(), expected, actual);
    }
    private static final String invalidAnalyzerTokenizer = "Invalid tokenizer. Expected '%1$s', actual is '%2$s'";
    protected String invalidAnalyzerTokenizer$str() {
        return invalidAnalyzerTokenizer;
    }
    @Override
    public final String invalidAnalyzerTokenizer(final Object expected, final Object actual) {
        return String.format(getLoggingLocale(), invalidAnalyzerTokenizer$str(), expected, actual);
    }
    private static final String invalidAnalyzerTokenFilters = "Invalid token filters. Expected '%1$s', actual is '%2$s'";
    protected String invalidAnalyzerTokenFilters$str() {
        return invalidAnalyzerTokenFilters;
    }
    @Override
    public final String invalidAnalyzerTokenFilters(final Object expected, final Object actual) {
        return String.format(getLoggingLocale(), invalidAnalyzerTokenFilters$str(), expected, actual);
    }
    private static final String charFilterMissing = "Missing char filter definition";
    protected String charFilterMissing$str() {
        return charFilterMissing;
    }
    @Override
    public final String charFilterMissing() {
        return String.format(getLoggingLocale(), charFilterMissing$str());
    }
    private static final String tokenizerMissing = "Missing tokenizer definition";
    protected String tokenizerMissing$str() {
        return tokenizerMissing;
    }
    @Override
    public final String tokenizerMissing() {
        return String.format(getLoggingLocale(), tokenizerMissing$str());
    }
    private static final String tokenFilterMissing = "Missing token filter definition";
    protected String tokenFilterMissing$str() {
        return tokenFilterMissing;
    }
    @Override
    public final String tokenFilterMissing() {
        return String.format(getLoggingLocale(), tokenFilterMissing$str());
    }
    private static final String invalidAnalysisDefinitionType = "Invalid type. Expected '%1$s', actual is '%2$s'";
    protected String invalidAnalysisDefinitionType$str() {
        return invalidAnalysisDefinitionType;
    }
    @Override
    public final String invalidAnalysisDefinitionType(final String expected, final String actual) {
        return String.format(getLoggingLocale(), invalidAnalysisDefinitionType$str(), expected, actual);
    }
    private static final String invalidAnalysisDefinitionParameter = "Invalid value for parameter '%1$s'. Expected '%2$s', actual is '%3$s'";
    protected String invalidAnalysisDefinitionParameter$str() {
        return invalidAnalysisDefinitionParameter;
    }
    @Override
    public final String invalidAnalysisDefinitionParameter(final String name, final Object expected, final Object actual) {
        return String.format(getLoggingLocale(), invalidAnalysisDefinitionParameter$str(), name, expected, actual);
    }
}
