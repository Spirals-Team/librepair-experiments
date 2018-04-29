// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.diskstorage.lucene;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Shape;

import org.janusgraph.core.Cardinality;
import org.janusgraph.core.schema.Mapping;
import org.janusgraph.graphdb.internal.Order;
import org.janusgraph.core.attribute.*;
import org.janusgraph.diskstorage.*;
import org.janusgraph.diskstorage.configuration.Configuration;
import org.janusgraph.diskstorage.indexing.*;
import org.janusgraph.graphdb.configuration.GraphDatabaseConfiguration;
import org.janusgraph.graphdb.database.serialize.AttributeUtil;
import org.janusgraph.graphdb.query.JanusGraphPredicate;
import org.janusgraph.graphdb.query.condition.*;
import org.janusgraph.graphdb.types.ParameterType;
import org.janusgraph.util.system.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CachingTokenFilter;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeQueryBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.PrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.QuadPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.spatial.vector.PointVectorStrategy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */

public class LuceneIndex implements IndexProvider {
    private static final Logger log = LoggerFactory.getLogger(LuceneIndex.class);

    private static final String DOCID = "_____elementid";
    private static final String GEOID = "_____geo";

    private static final IndexFeatures LUCENE_FEATURES = new IndexFeatures.Builder()
        .supportedStringMappings(Mapping.TEXT, Mapping.STRING)
        .supportsCardinality(Cardinality.SINGLE)
        .supportsCustomAnalyzer()
        .supportsNanoseconds()
        .supportsGeoContains()
        .build();

    /**
     * Default tree levels used when creating the prefix tree.
     */
    public static final int DEFAULT_GEO_MAX_LEVELS = 20;

    /**
     * Default measure of shape precision used when creating the prefix tree.
     */
    public static final double DEFAULT_GEO_DIST_ERROR_PCT = 0.025;

    private static final Map<Geo, SpatialOperation> SPATIAL_PREDICATES = spatialPredicates();

    private final Map<String, IndexWriter> writers = new HashMap<>(4);
    private final ReentrantLock writerLock = new ReentrantLock();

    private final Map<String, SpatialStrategy> spatial = new ConcurrentHashMap<>(12);
    private final SpatialContext ctx = Geoshape.getSpatialContext();

    private final String basePath;

    /**
     * lazy cache for the delegating analyzers used for writting or querrying for each store
     */
    private final Map<String, LuceneCustomAnalyzer> delegatingAnalyzers = new HashMap<>();

    public LuceneIndex(Configuration config) {
        final String dir = config.get(GraphDatabaseConfiguration.INDEX_DIRECTORY);
        final File directory = new File(dir);
        if ((!directory.exists() && !directory.mkdirs()) || !directory.isDirectory() || !directory.canWrite()) {
            throw new IllegalArgumentException("Cannot access or write to directory: " + dir);
        }
        basePath = directory.getAbsolutePath();
        log.debug("Configured Lucene to use base directory [{}]", basePath);
    }

    private Directory getStoreDirectory(String store) throws BackendException {
        Preconditions.checkArgument(StringUtils.isAlphanumeric(store), "Invalid store name: %s", store);
        final String dir = basePath + File.separator + store;
        try {
            final File path = new File(dir);
            if ((!path.exists() && !path.mkdirs()) || !path.isDirectory() || !path.canWrite()) {
                throw new PermanentBackendException("Cannot access or write to directory: " + dir);
            }
            log.debug("Opening store directory [{}]", path);
            return FSDirectory.open(path.toPath());
        } catch (final IOException e) {
            throw new PermanentBackendException("Could not open directory: " + dir, e);
        }
    }

    private IndexWriter getWriter(String store, KeyInformation.IndexRetriever informations) throws BackendException {
        Preconditions.checkArgument(writerLock.isHeldByCurrentThread());
        IndexWriter writer = writers.get(store);
        if (writer == null) {
            final LuceneCustomAnalyzer analyzer = delegatingAnalyzerFor(store, informations);
            final IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            try {
                writer = new IndexWriter(getStoreDirectory(store), iwc);
                writers.put(store, writer);
            } catch (final IOException e) {
                throw new PermanentBackendException("Could not create writer", e);
            }
        }
        return writer;
    }

    private SpatialStrategy getSpatialStrategy(String key, KeyInformation ki) {
        SpatialStrategy strategy = spatial.get(key);
        final Mapping mapping = Mapping.getMapping(ki);
        final int maxLevels = ParameterType.INDEX_GEO_MAX_LEVELS.findParameter(ki.getParameters(),
            DEFAULT_GEO_MAX_LEVELS);
        final double distErrorPct = ParameterType.INDEX_GEO_DIST_ERROR_PCT.findParameter(ki.getParameters(),
            DEFAULT_GEO_DIST_ERROR_PCT);
        if (strategy == null) {
            synchronized (spatial) {
                if (!spatial.containsKey(key)) {
//                    SpatialPrefixTree grid = new GeohashPrefixTree(ctx, GEO_MAX_LEVELS);
//                    strategy = new RecursivePrefixTreeStrategy(grid, key);
                    if (mapping == Mapping.DEFAULT) {
                        strategy = PointVectorStrategy.newInstance(ctx, key);
                    } else {
                        final SpatialPrefixTree grid = new QuadPrefixTree(ctx, maxLevels);
                        strategy = new RecursivePrefixTreeStrategy(grid, key);
                        ((PrefixTreeStrategy) strategy).setDistErrPct(distErrorPct);
                    }
                    spatial.put(key, strategy);
                } else return spatial.get(key);
            }
        }
        return strategy;
    }

    private static Map<Geo, SpatialOperation> spatialPredicates() {
        return Collections.unmodifiableMap(Stream.of(
            new SimpleEntry<>(Geo.WITHIN, SpatialOperation.IsWithin),
            new SimpleEntry<>(Geo.CONTAINS, SpatialOperation.Contains),
            new SimpleEntry<>(Geo.INTERSECT, SpatialOperation.Intersects),
            new SimpleEntry<>(Geo.DISJOINT, SpatialOperation.IsDisjointTo))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    @Override
    public void register(String store, String key, KeyInformation information, BaseTransaction tx) throws BackendException {
        final Class<?> dataType = information.getDataType();
        final Mapping map = Mapping.getMapping(information);
        Preconditions.checkArgument(map == Mapping.DEFAULT || AttributeUtil.isString(dataType) ||
                (map == Mapping.PREFIX_TREE && AttributeUtil.isGeo(dataType)),
            "Specified illegal mapping [%s] for data type [%s]", map, dataType);
    }

    @Override
    public void mutate(Map<String, Map<String, IndexMutation>> mutations, KeyInformation.IndexRetriever information, BaseTransaction tx) throws BackendException {
        final Transaction ltx = (Transaction) tx;
        writerLock.lock();
        try {
            for (final Map.Entry<String, Map<String, IndexMutation>> stores : mutations.entrySet()) {
                mutateStores(stores, information);
            }
            ltx.postCommit();
        } catch (final IOException e) {
            throw new TemporaryBackendException("Could not update Lucene index", e);
        } finally {
            writerLock.unlock();
        }
    }

    private void mutateStores(Map.Entry<String, Map<String, IndexMutation>> stores, KeyInformation.IndexRetriever information) throws IOException, BackendException {
        IndexReader reader = null;
        try {
            final String storeName = stores.getKey();
            final IndexWriter writer = getWriter(storeName, information);
            reader = DirectoryReader.open(writer, true, true);
            final IndexSearcher searcher = new IndexSearcher(reader);
            for (final Map.Entry<String, IndexMutation> entry : stores.getValue().entrySet()) {
                final String documentId = entry.getKey();
                final IndexMutation mutation = entry.getValue();

                if (mutation.isDeleted()) {
                    if (log.isTraceEnabled())
                        log.trace("Deleted entire document [{}]", documentId);

                    writer.deleteDocuments(new Term(DOCID, documentId));
                    continue;
                }

                final Pair<Document, Map<String, Shape>> docAndGeo = retrieveOrCreate(documentId, searcher);
                final Document doc = docAndGeo.getKey();
                final Map<String, Shape> geoFields = docAndGeo.getValue();

                Preconditions.checkNotNull(doc);
                for (final IndexEntry del : mutation.getDeletions()) {
                    Preconditions.checkArgument(!del.hasMetaData(), "Lucene index does not support indexing meta data: %s", del);
                    final String key = del.field;
                    if (doc.getField(key) != null) {
                        if (log.isTraceEnabled())
                            log.trace("Removing field [{}] on document [{}]", key, documentId);

                        doc.removeFields(key);
                        geoFields.remove(key);
                    }
                }

                addToDocument(storeName, documentId, doc, mutation.getAdditions(), geoFields, information);

                //write the old document to the index with the modifications
                writer.updateDocument(new Term(DOCID, documentId), doc);
            }
            writer.commit();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @Override
    public void restore(Map<String, Map<String, List<IndexEntry>>> documents, KeyInformation.IndexRetriever information, BaseTransaction tx) throws BackendException {
        writerLock.lock();
        try {
            for (final Map.Entry<String, Map<String, List<IndexEntry>>> stores : documents.entrySet()) {
                final String store = stores.getKey();
                final IndexWriter writer = getWriter(store, information);
                final IndexReader reader = DirectoryReader.open(writer, true, true);
                final IndexSearcher searcher = new IndexSearcher(reader);

                for (final Map.Entry<String, List<IndexEntry>> entry : stores.getValue().entrySet()) {
                    final String docID = entry.getKey();
                    final List<IndexEntry> content = entry.getValue();

                    if (content == null || content.isEmpty()) {
                        if (log.isTraceEnabled())
                            log.trace("Deleting document [{}]", docID);

                        writer.deleteDocuments(new Term(DOCID, docID));
                        continue;
                    }

                    final Pair<Document, Map<String, Shape>> docAndGeo = retrieveOrCreate(docID, searcher);
                    addToDocument(store, docID, docAndGeo.getKey(), content, docAndGeo.getValue(), information);

                    //write the old document to the index with the modifications
                    writer.updateDocument(new Term(DOCID, docID), docAndGeo.getKey());
                }
                writer.commit();
            }
            tx.commit();
        } catch (final IOException e) {
            throw new TemporaryBackendException("Could not update Lucene index", e);
        } finally {
            writerLock.unlock();
        }
    }

    private Pair<Document, Map<String, Shape>> retrieveOrCreate(String docID, IndexSearcher searcher) throws IOException {
        final Document doc;
        final TopDocs hits = searcher.search(new TermQuery(new Term(DOCID, docID)), 10);
        final Map<String, Shape> geoFields = Maps.newHashMap();

        if (hits.scoreDocs.length > 1)
            throw new IllegalArgumentException("More than one document found for document id: " + docID);

        if (hits.scoreDocs.length == 0) {
            if (log.isTraceEnabled())
                log.trace("Creating new document for [{}]", docID);

            doc = new Document();
            doc.add(new StringField(DOCID, docID, Field.Store.YES));
        } else {
            if (log.isTraceEnabled())
                log.trace("Updating existing document for [{}]", docID);

            final int docId = hits.scoreDocs[0].doc;
            //retrieve the old document
            doc = searcher.doc(docId);
            for (final IndexableField field : doc.getFields()) {
                if (field.stringValue().startsWith(GEOID)) {
                    try {
                        geoFields.put(field.name(), Geoshape.fromWkt(field.stringValue().substring(GEOID.length())).getShape());
                    } catch (final java.text.ParseException e) {
                        throw new IllegalArgumentException("Geoshape was not parsable");
                    }
                }
            }
        }

        return new ImmutablePair<>(doc, geoFields);
    }

    private void addToDocument(String store,
                               String docID,
                               Document doc,
                               List<IndexEntry> content,
                               Map<String, Shape> geoFields,
                               KeyInformation.IndexRetriever information) {
        Preconditions.checkNotNull(doc);
        for (final IndexEntry e : content) {
            Preconditions.checkArgument(!e.hasMetaData(), "Lucene index does not support indexing meta data: %s", e);
            if (log.isTraceEnabled())
                log.trace("Adding field [{}] on document [{}]", e.field, docID);

            if (doc.getField(e.field) != null)
                doc.removeFields(e.field);

            if (e.value instanceof Number) {
                final Field field;
                final Field sortField;
                if (AttributeUtil.isWholeNumber((Number) e.value)) {
                    field = new LongPoint(e.field, ((Number) e.value).longValue());
                    sortField = new NumericDocValuesField(e.field, ((Number) e.value).longValue());
                } else { //double or float
                    field = new DoublePoint(e.field, ((Number) e.value).doubleValue());
                    sortField = new DoubleDocValuesField(e.field, ((Number) e.value).doubleValue());
                }
                doc.add(field);
                doc.add(sortField);
            } else if (AttributeUtil.isString(e.value)) {
                final String str = (String) e.value;
                final Mapping mapping = Mapping.getMapping(store, e.field, information);
                final Field field;
                switch (mapping) {
                    case DEFAULT:
                    case TEXT:
                        // lowering the case for case insensitive text search
                        field = new TextField(e.field, str.toLowerCase(), Field.Store.YES);
                        break;
                    case STRING:
                        // if this field uses a custom analyzer, it must be stored as a TextField
                        // (or the analyzer, even if it is a KeywordAnalyzer won't be used)
                        field = new TextField(e.field, str, Field.Store.YES);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal mapping specified: " + mapping);
                }
                doc.add(field);
            } else if (e.value instanceof Geoshape) {
                final Shape shape = ((Geoshape) e.value).getShape();
                geoFields.put(e.field, shape);
                doc.add(new StoredField(e.field, GEOID + e.value.toString()));
            } else if (e.value instanceof Date) {
                doc.add(new LongPoint(e.field, (((Date) e.value).getTime())));
            } else if (e.value instanceof Instant) {
                doc.add(new LongPoint(e.field, (((Instant) e.value).toEpochMilli())));
            } else if (e.value instanceof Boolean) {
                doc.add(new IntPoint(e.field, ((Boolean) e.value) ? 1 : 0));
            } else if (e.value instanceof UUID) {
                //Solr stores UUIDs as strings, we we do the same.
                final Field field = new StringField(e.field, e.value.toString(), Field.Store.YES);
                doc.add(field);
            } else {
                throw new IllegalArgumentException("Unsupported type: " + e.value);
            }
        }

        for (final Map.Entry<String, Shape> geo : geoFields.entrySet()) {
            if (log.isTraceEnabled())
                log.trace("Updating geo-indexes for key {}", geo.getKey());

            final KeyInformation ki = information.get(store, geo.getKey());
            final SpatialStrategy spatialStrategy = getSpatialStrategy(geo.getKey(), ki);
            for (final IndexableField f : spatialStrategy.createIndexableFields(geo.getValue())) {
                if (doc.getField(f.name()) != null) {
                    doc.removeFields(f.name());
                }
                doc.add(f);
                if (spatialStrategy instanceof PointVectorStrategy) {
                    doc.add(new DoubleDocValuesField(f.name(), f.numericValue() == null ? null : f.numericValue().doubleValue()));
                }
            }
        }
    }

    private static Sort getSortOrder(IndexQuery query) {
        final Sort sort = new Sort();
        final List<IndexQuery.OrderEntry> orders = query.getOrder();
        if (!orders.isEmpty()) {
            final SortField[] fields = new SortField[orders.size()];
            for (int i = 0; i < orders.size(); i++) {
                final IndexQuery.OrderEntry order = orders.get(i);
                SortField.Type sortType = null;
                final Class dataType = order.getDatatype();
                if (AttributeUtil.isString(dataType)) sortType = SortField.Type.STRING;
                else if (AttributeUtil.isWholeNumber(dataType)) sortType = SortField.Type.LONG;
                else if (AttributeUtil.isDecimal(dataType)) sortType = SortField.Type.DOUBLE;
                else
                    Preconditions.checkArgument(false, "Unsupported order specified on field [%s] with datatype [%s]", order.getKey(), dataType);

                fields[i] = new SortField(order.getKey(), sortType, order.getOrder() == Order.DESC);
            }
            sort.setSort(fields);
        }
        return sort;
    }

    @Override
    public Stream<String> query(IndexQuery query, KeyInformation.IndexRetriever information, BaseTransaction tx) throws BackendException {
        //Construct query
        final String store = query.getStore();
        final LuceneCustomAnalyzer delegatingAnalyzer = delegatingAnalyzerFor(store, information);
        final SearchParams searchParams = convertQuery(query.getCondition(), information.get(store), delegatingAnalyzer);

        try {
            final IndexSearcher searcher = ((Transaction) tx).getSearcher(query.getStore());
            if (searcher == null) {
                return Collections.unmodifiableList(new ArrayList<String>()).stream(); //Index does not yet exist
            }
            Query q = searchParams.getQuery();
            if (null == q)
                q = new MatchAllDocsQuery();

            final long time = System.currentTimeMillis();
            final TopDocs docs = searcher.search(q, query.hasLimit() ? query.getLimit() : Integer.MAX_VALUE - 1, getSortOrder(query));
            log.debug("Executed query [{}] in {} ms", q, System.currentTimeMillis() - time);
            final List<String> result = new ArrayList<>(docs.scoreDocs.length);
            for (int i = 0; i < docs.scoreDocs.length; i++) {
                final IndexableField field = searcher.doc(docs.scoreDocs[i].doc).getField(DOCID);
                result.add(field == null ? null : field.stringValue());
            }
            return result.stream();
        } catch (final IOException e) {
            throw new TemporaryBackendException("Could not execute Lucene query", e);
        }
    }

    private static Query numericQuery(String key, Cmp relation, Number value) {
        switch (relation) {
            case EQUAL:
                return AttributeUtil.isWholeNumber(value) ?
                    LongPoint.newRangeQuery(key, value.longValue(), value.longValue()) :
                    DoublePoint.newRangeQuery(key, value.doubleValue(), value.doubleValue());
            case NOT_EQUAL:
                final BooleanQuery.Builder q = new BooleanQuery.Builder();
                if (AttributeUtil.isWholeNumber(value)) {
                    q.add(LongPoint.newRangeQuery(key, Long.MIN_VALUE, Math.addExact(value.longValue(), -1)), BooleanClause.Occur.SHOULD);
                    q.add(LongPoint.newRangeQuery(key, Math.addExact(value.longValue(), 1), Long.MAX_VALUE), BooleanClause.Occur.SHOULD);
                } else {
                    q.add(DoublePoint.newRangeQuery(key, Double.MIN_VALUE, DoublePoint.nextDown(value.doubleValue())), BooleanClause.Occur.SHOULD);
                    q.add(DoublePoint.newRangeQuery(key, DoublePoint.nextUp(value.doubleValue()), Double.MAX_VALUE), BooleanClause.Occur.SHOULD);
                }
                return q.build();
            case LESS_THAN:
                return (AttributeUtil.isWholeNumber(value)) ?
                    LongPoint.newRangeQuery(key, Long.MIN_VALUE, Math.addExact(value.longValue(), -1)) :
                    DoublePoint.newRangeQuery(key, Double.MIN_VALUE, DoublePoint.nextDown(value.doubleValue()));
            case LESS_THAN_EQUAL:
                return (AttributeUtil.isWholeNumber(value)) ?
                    LongPoint.newRangeQuery(key, Long.MIN_VALUE, value.longValue()) :
                    DoublePoint.newRangeQuery(key, Double.MIN_VALUE, value.doubleValue());
            case GREATER_THAN:
                return (AttributeUtil.isWholeNumber(value)) ?
                    LongPoint.newRangeQuery(key, Math.addExact(value.longValue(), 1), Long.MAX_VALUE) :
                    DoublePoint.newRangeQuery(key, DoublePoint.nextUp(value.doubleValue()), Double.MAX_VALUE);
            case GREATER_THAN_EQUAL:
                return (AttributeUtil.isWholeNumber(value)) ?
                    LongPoint.newRangeQuery(key, value.longValue(), Long.MAX_VALUE) :
                    DoublePoint.newRangeQuery(key, value.doubleValue(), Double.MAX_VALUE);
            default:
                throw new IllegalArgumentException("Unexpected relation: " + relation);
        }
    }

    // adapted from SolrIndex
    private List<String> customTokenize(Analyzer analyzer, String fieldName, String value) {
        final List<String> terms = new ArrayList<>();
        try (CachingTokenFilter stream = new CachingTokenFilter(analyzer.tokenStream(fieldName, value))) {
            final TermToBytesRefAttribute termAtt = stream.getAttribute(TermToBytesRefAttribute.class);
            stream.reset();
            while (stream.incrementToken()) {
                terms.add(termAtt.getBytesRef().utf8ToString());
            }
            return terms;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private void tokenize(SearchParams params, final Mapping mapping, final LuceneCustomAnalyzer delegatingAnalyzer, String value, String key, JanusGraphPredicate janusgraphPredicate) {
        final Analyzer analyzer = delegatingAnalyzer.getWrappedAnalyzer(key);
        final List<String>    terms = customTokenize(analyzer, key, value);
        if (terms.isEmpty()) {
            // This might happen with very short terms
            if (janusgraphPredicate == Text.CONTAINS_PREFIX ) {
                final Term term;
                if (mapping == Mapping.STRING) {
                    term = new Term(key, value);
                } else {
                    term = new Term(key, value.toLowerCase());
                }
                params.addQuery(new PrefixQuery(term), BooleanClause.Occur.MUST);
            }
        } else if (terms.size() == 1) {
            if (janusgraphPredicate == Cmp.EQUAL || janusgraphPredicate == Text.CONTAINS) {
                params.addQuery(new TermQuery(new Term(key, value)));
            } else if (janusgraphPredicate == Cmp.NOT_EQUAL) {
                final BooleanQuery.Builder q = new BooleanQuery.Builder();
                q.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
                q.add(new TermQuery(new Term(key, value)), BooleanClause.Occur.MUST_NOT);
                params.addQuery(q.build(), BooleanClause.Occur.MUST);
            } else if (janusgraphPredicate == Text.CONTAINS_PREFIX) {
                final Term term;
                if (mapping == Mapping.STRING) {
                    term = new Term(key, terms.get(0));
                } else {
                    term = new Term(key, terms.get(0).toLowerCase());
                }
                params.addQuery(new PrefixQuery(term), BooleanClause.Occur.MUST);
            } else throw new IllegalArgumentException("LuceneIndex does not support this predicate with 1 token : " + janusgraphPredicate);
        } else {
            // at the moment, this is only walked for EQUAL and Text.CONTAINS (String and Text mappings)
            final BooleanQuery.Builder q = new BooleanQuery.Builder();
            for (final String term : terms) {
                q.add(new TermQuery(new Term(key, term)), BooleanClause.Occur.MUST);
            }
            params.addQuery(q.build());
        }
    }

    private LuceneCustomAnalyzer delegatingAnalyzerFor(String store, KeyInformation.IndexRetriever information2) {
        if (!delegatingAnalyzers.containsKey(store)) {
            delegatingAnalyzers.put(store, new LuceneCustomAnalyzer(store, information2, Analyzer.PER_FIELD_REUSE_STRATEGY));
        }
        return delegatingAnalyzers.get(store);
    }

    private SearchParams convertQuery(Condition<?> condition, final KeyInformation.StoreRetriever information, final LuceneCustomAnalyzer delegatingAnalyzer) {
        final SearchParams params = new SearchParams();
        if (condition instanceof PredicateCondition) {
            final PredicateCondition<String, ?> atom = (PredicateCondition) condition;
            Object value = atom.getValue();
            final String key = atom.getKey();
            final JanusGraphPredicate janusgraphPredicate = atom.getPredicate();
            if (value instanceof Number) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Cmp, "Relation not supported on numeric types: " + janusgraphPredicate);
                params.addQuery(numericQuery(key, (Cmp) janusgraphPredicate, (Number) value));
            } else if (value instanceof String) {
                if (janusgraphPredicate == Cmp.LESS_THAN) {
                    params.addQuery(TermRangeQuery.newStringRange(key, null, value.toString(), false, false));
                } else if (janusgraphPredicate == Cmp.LESS_THAN_EQUAL) {
                    params.addQuery(TermRangeQuery.newStringRange(key, null, value.toString(), false, true));
                } else if (janusgraphPredicate == Cmp.GREATER_THAN) {
                    params.addQuery(TermRangeQuery.newStringRange(key, value.toString(), null, false, false));
                } else if (janusgraphPredicate == Cmp.GREATER_THAN_EQUAL) {
                    params.addQuery(TermRangeQuery.newStringRange(key, value.toString(), null, true, false));
                } else {
                        final Mapping map = Mapping.getMapping(information.get(key));
                        if ((map == Mapping.DEFAULT || map == Mapping.TEXT) && !Text.HAS_CONTAINS.contains(janusgraphPredicate))
                            throw new IllegalArgumentException("Text mapped string values only support CONTAINS queries and not: " + janusgraphPredicate);
                        if (map == Mapping.STRING && Text.HAS_CONTAINS.contains(janusgraphPredicate))
                            throw new IllegalArgumentException("String mapped string values do not support CONTAINS queries: " + janusgraphPredicate);
                        if (janusgraphPredicate == Text.CONTAINS) {
                            tokenize(params, map, delegatingAnalyzer, ((String) value).toLowerCase(), key, janusgraphPredicate);
                        } else if (janusgraphPredicate == Text.CONTAINS_PREFIX) {
                            tokenize(params, map, delegatingAnalyzer, (String) value, key, janusgraphPredicate);
                        } else if (janusgraphPredicate == Text.PREFIX) {
                            params.addQuery(new PrefixQuery(new Term(key, (String) value)));
                        } else if (janusgraphPredicate == Text.REGEX) {
                            final RegexpQuery rq = new RegexpQuery(new Term(key, (String) value));
                            params.addQuery(rq);
                        } else if (janusgraphPredicate == Text.CONTAINS_REGEX) {
                            // This is terrible -- there is probably a better way
                            // putting this to lowercase because Text search is supposed to be case insensitive
                            final RegexpQuery rq = new RegexpQuery(new Term(key, ".*" + (((String) value).toLowerCase()) + ".*"));
                            params.addQuery(rq);
                        } else if (janusgraphPredicate == Cmp.EQUAL) {
                            tokenize(params, map, delegatingAnalyzer, (String) value, key, janusgraphPredicate);
                        } else if (janusgraphPredicate == Cmp.NOT_EQUAL) {
                            final BooleanQuery.Builder q = new BooleanQuery.Builder();
                            q.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
                            q.add(new TermQuery(new Term(key, (String) value)), BooleanClause.Occur.MUST_NOT);
                            params.addQuery(q.build());
                        } else if (janusgraphPredicate == Text.FUZZY) {
                            params.addQuery(new FuzzyQuery(new Term(key, (String) value)));
                        } else if (janusgraphPredicate == Text.CONTAINS_FUZZY) {
                            value = ((String) value).toLowerCase();
                            final Builder b = new BooleanQuery.Builder();
                            for (final String term : Text.tokenize((String) value)) {
                                b.add(new FuzzyQuery(new Term(key, term)), BooleanClause.Occur.MUST);
                            }
                            params.addQuery(b.build());
                        } else
                            throw new IllegalArgumentException("Relation is not supported for string value: " + janusgraphPredicate);
                }
            } else if (value instanceof Geoshape) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Geo, "Relation not supported on geo types: " + janusgraphPredicate);
                final Shape shape = ((Geoshape) value).getShape();
                final SpatialOperation spatialOp = SPATIAL_PREDICATES.get(janusgraphPredicate);
                final SpatialArgs args = new SpatialArgs(spatialOp, shape);
                params.addQuery(getSpatialStrategy(key, information.get(key)).makeQuery(args));
            } else if (value instanceof Date) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Cmp, "Relation not supported on date types: " + janusgraphPredicate);
                params.addQuery(numericQuery(key, (Cmp) janusgraphPredicate, ((Date) value).getTime()));
            } else if (value instanceof Instant) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Cmp, "Relation not supported on instant types: " + janusgraphPredicate);
                params.addQuery(numericQuery(key, (Cmp) janusgraphPredicate, ((Instant) value).toEpochMilli()));
            } else if (value instanceof Boolean) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Cmp, "Relation not supported on boolean types: " + janusgraphPredicate);
                final int intValue;
                switch ((Cmp) janusgraphPredicate) {
                    case EQUAL:
                        intValue = ((Boolean) value) ? 1 : 0;
                        params.addQuery(IntPoint.newRangeQuery(key, intValue, intValue));
                        break;
                    case NOT_EQUAL:
                        intValue = ((Boolean) value) ? 0 : 1;
                        params.addQuery(IntPoint.newRangeQuery(key, intValue, intValue));
                        break;
                    default:
                        throw new IllegalArgumentException("Boolean types only support EQUAL or NOT_EQUAL");
                }

            } else if (value instanceof UUID) {
                Preconditions.checkArgument(janusgraphPredicate instanceof Cmp, "Relation not supported on UUID types: " + janusgraphPredicate);
                if (janusgraphPredicate == Cmp.EQUAL) {
                    params.addQuery(new TermQuery(new Term(key, value.toString())));
                } else if (janusgraphPredicate == Cmp.NOT_EQUAL) {
                    final BooleanQuery.Builder q = new BooleanQuery.Builder();
                    q.add(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
                    q.add(new TermQuery(new Term(key, value.toString())), BooleanClause.Occur.MUST_NOT);
                    params.addQuery(q.build());
                } else {
                    throw new IllegalArgumentException("Relation is not supported for UUID type: " + janusgraphPredicate);
                }

            } else {
                throw new IllegalArgumentException("Unsupported type: " + value);
            }
        } else if (condition instanceof Not) {
            final SearchParams childParams = convertQuery(((Not) condition).getChild(), information, delegatingAnalyzer);
            params.addQuery(new MatchAllDocsQuery(), BooleanClause.Occur.MUST);
            params.addParams(childParams, BooleanClause.Occur.MUST_NOT);
        } else if (condition instanceof And) {
            for (final Condition c : condition.getChildren()) {
                final SearchParams childParams = convertQuery(c, information, delegatingAnalyzer);
                params.addParams(childParams, BooleanClause.Occur.MUST);
            }
        } else if (condition instanceof Or) {
            for (final Condition c : condition.getChildren()) {
                final SearchParams childParams = convertQuery(c, information, delegatingAnalyzer);
                params.addParams(childParams, BooleanClause.Occur.SHOULD);
            }
        } else throw new IllegalArgumentException("Invalid condition: " + condition);
        return params;
    }

    @Override
    public Stream<RawQuery.Result<String>> query(RawQuery query, KeyInformation.IndexRetriever information, BaseTransaction tx) throws BackendException {
        final Query q;
        try {
            final Analyzer analyzer = delegatingAnalyzerFor(query.getStore(), information);//writers.get(query.getStore()).getAnalyzer();
            q = new QueryParser("_all", analyzer).parse(query.getQuery());
            // Lucene query parser does not take additional parameters so any parameters on the RawQuery are ignored. 
        } catch (final ParseException e) {
            throw new PermanentBackendException("Could not parse raw query: " + query.getQuery(), e);
        }

        try {
            final IndexSearcher searcher = ((Transaction) tx).getSearcher(query.getStore());
            if (searcher == null) {
                return Collections.unmodifiableList(new ArrayList<RawQuery.Result<String>>()).stream(); //Index does not yet exist
            }
            final long time = System.currentTimeMillis();
            //TODO: can we make offset more efficient in Lucene?
            final int offset = query.getOffset();
            int adjustedLimit = query.hasLimit() ? query.getLimit() : Integer.MAX_VALUE - 1;
            if (adjustedLimit < Integer.MAX_VALUE - 1 - offset) adjustedLimit += offset;
            else adjustedLimit = Integer.MAX_VALUE - 1;
            final TopDocs docs = searcher.search(q, adjustedLimit);
            log.debug("Executed query [{}] in {} ms", q, System.currentTimeMillis() - time);
            final List<RawQuery.Result<String>> result = new ArrayList<>(docs.scoreDocs.length);
            for (int i = offset; i < docs.scoreDocs.length; i++) {
                final IndexableField field = searcher.doc(docs.scoreDocs[i].doc).getField(DOCID);
                result.add(new RawQuery.Result<>(field == null ? null : field.stringValue(), docs.scoreDocs[i].score));
            }
            return result.stream();
        } catch (final IOException e) {
            throw new TemporaryBackendException("Could not execute Lucene query", e);
        }
    }

    @Override
    public Long totals(RawQuery query, KeyInformation.IndexRetriever information, BaseTransaction tx) throws BackendException {
        final Query q;
        try {
            final Analyzer analyzer = delegatingAnalyzerFor(query.getStore(), information);//writers.get(query.getStore()).getAnalyzer();
            q = new QueryParser("_all", analyzer).parse(query.getQuery());
        } catch (final ParseException e) {
            throw new PermanentBackendException("Could not parse raw query: "+query.getQuery(),e);
        }

        try {
            final IndexSearcher searcher = ((Transaction) tx).getSearcher(query.getStore());
            if (searcher == null) return 0L; //Index does not yet exist

            final long time = System.currentTimeMillis();
            // Lucene doesn't like limits of 0.  Also, it doesn't efficiently build a total list.
            query.setLimit(1);
            // We ignore offset and limit for totals
            final TopDocs docs = searcher.search(q, 1);
            log.debug("Executed query [{}] in {} ms", q, System.currentTimeMillis() - time);
            return docs.totalHits;
        } catch (final IOException e) {
            throw new TemporaryBackendException("Could not execute Lucene query", e);
        }
    }

    @Override
    public BaseTransactionConfigurable beginTransaction(BaseTransactionConfig config) throws BackendException {
        return new Transaction(config);
    }

    @Override
    public boolean supports(KeyInformation information, JanusGraphPredicate janusgraphPredicate) {
        if (information.getCardinality() != Cardinality.SINGLE) return false;
        final Class<?> dataType = information.getDataType();
        final Mapping mapping = Mapping.getMapping(information);
        if (mapping != Mapping.DEFAULT && !AttributeUtil.isString(dataType) &&
            !(mapping == Mapping.PREFIX_TREE && AttributeUtil.isGeo(dataType))) return false;

        if (Number.class.isAssignableFrom(dataType)) {
            return janusgraphPredicate instanceof Cmp;
        } else if (dataType == Geoshape.class) {
            return janusgraphPredicate == Geo.INTERSECT || janusgraphPredicate == Geo.WITHIN || janusgraphPredicate == Geo.CONTAINS;
        } else if (AttributeUtil.isString(dataType)) {
            switch (mapping) {
                case DEFAULT:
                case TEXT:
                    return janusgraphPredicate == Text.CONTAINS || janusgraphPredicate == Text.CONTAINS_PREFIX || janusgraphPredicate == Text.CONTAINS_FUZZY; // || janusgraphPredicate == Text.CONTAINS_REGEX;
                case STRING:
                    return janusgraphPredicate instanceof Cmp || janusgraphPredicate == Text.PREFIX || janusgraphPredicate == Text.REGEX || janusgraphPredicate == Text.FUZZY;
            }
        } else if (dataType == Date.class || dataType == Instant.class) {
            return janusgraphPredicate instanceof Cmp;
        } else if (dataType == Boolean.class) {
            return janusgraphPredicate == Cmp.EQUAL || janusgraphPredicate == Cmp.NOT_EQUAL;
        } else if (dataType == UUID.class) {
            return janusgraphPredicate == Cmp.EQUAL || janusgraphPredicate == Cmp.NOT_EQUAL;
        }
        return false;
    }

    @Override
    public boolean supports(KeyInformation information) {
        if (information.getCardinality() != Cardinality.SINGLE) return false;
        final Class<?> dataType = information.getDataType();
        final Mapping mapping = Mapping.getMapping(information);
        if (Number.class.isAssignableFrom(dataType) || dataType == Date.class || dataType == Instant.class || dataType == Boolean.class || dataType == UUID.class) {
            return mapping == Mapping.DEFAULT;
        } else if (AttributeUtil.isString(dataType)) {
            return mapping == Mapping.DEFAULT || mapping == Mapping.STRING || mapping == Mapping.TEXT;
        } else if (AttributeUtil.isGeo(dataType)) {
            return mapping == Mapping.DEFAULT || mapping == Mapping.PREFIX_TREE;
        }
        return false;
    }

    @Override
    public String mapKey2Field(String key, KeyInformation information) {
        IndexProvider.checkKeyValidity(key);
        return key.replace(' ', REPLACEMENT_CHAR);
    }

    @Override
    public IndexFeatures getFeatures() {
        return LUCENE_FEATURES;
    }

    @Override
    public void close() throws BackendException {
        try {
            for (IndexWriter w : writers.values()) w.close();
        } catch (IOException e) {
            throw new PermanentBackendException("Could not close writers", e);
        }
    }

    @Override
    public void clearStorage() throws BackendException {
        try {
            FileUtils.deleteDirectory(new File(basePath));
        } catch (final IOException e) {
            throw new PermanentBackendException("Could not delete lucene directory: " + basePath, e);
        }
    }

    @Override
    public boolean exists() throws BackendException {
        if (Files.exists(Paths.get(basePath))) {
            try (final DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(basePath))) {
                return dirStream.iterator().hasNext();
            } catch (final IOException e) {
                throw new PermanentBackendException("Could not read lucene directory: " + basePath, e);
            }
        } else {
            return false;
        }
    }

    private class Transaction implements BaseTransactionConfigurable {

        private final BaseTransactionConfig config;
        private final Set<String> updatedStores = Sets.newHashSet();
        private final Map<String, IndexSearcher> searchers = new HashMap<>(4);

        private Transaction(BaseTransactionConfig config) {
            this.config = config;
        }

        private synchronized IndexSearcher getSearcher(String store) throws BackendException {
            IndexSearcher searcher = searchers.get(store);
            if (searcher == null) {
                final IndexReader reader;
                try {
                    reader = DirectoryReader.open(getStoreDirectory(store));
                    searcher = new IndexSearcher(reader);
                } catch (final IndexNotFoundException e) {
                    searcher = null;
                } catch (final IOException e) {
                    throw new PermanentBackendException("Could not open index reader on store: " + store, e);
                }
                searchers.put(store, searcher);
            }
            return searcher;
        }

        public void postCommit() throws BackendException {
            close();
            searchers.clear();
        }


        @Override
        public void commit() throws BackendException {
            close();
        }

        @Override
        public void rollback() throws BackendException {
            close();
        }

        private void close() throws BackendException {
            try {
                for (final IndexSearcher searcher : searchers.values()) {
                    if (searcher != null) searcher.getIndexReader().close();
                }
            } catch (final IOException e) {
                throw new PermanentBackendException("Could not close searcher", e);
            }
        }

        @Override
        public BaseTransactionConfig getConfiguration() {
            return config;
        }
    }

    /**
     * Encapsulates a Lucene Query that express a JanusGraph {@link org.janusgraph.graphdb.query.Query} using Lucene's
     * abstractions. This object's state is mutable.
     */
    private static class SearchParams {
        private final BooleanQuery.Builder qb = new BooleanQuery.Builder();

        private void addQuery(Query newQuery) {
            addQuery(newQuery, BooleanClause.Occur.MUST);
        }

        private void addQuery(Query newQuery, BooleanClause.Occur occur) {
            qb.add(newQuery, occur);
        }

        private void addParams(SearchParams other, BooleanClause.Occur occur) {
            final Query otherQuery = other.getQuery();
            if (null != otherQuery)
                addQuery(otherQuery, occur);
        }

        private Query getQuery() {
            final BooleanQuery q = qb.build();
            if (0 == q.clauses().size()) {
                return null;
            }
            return q;
        }
    }
}
