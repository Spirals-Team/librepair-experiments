new DataFrame('one')
new DataFrame('one', 'two')
new DataFrame(['one'])
new DataFrame(['row1', 'row2'], ['col1', 'col2']) // native array vs collection
new DataFrame([[1,2,3],[4,5,6]])
new DataFrame(['row1', 'row2'], ['col1', 'col2'], [[1,2], [3,4]])
tmp.add('three')
tmp.add('three', 'four', 'five')
tmp.add('three', [1,2,3])
df.drop('a')
//df.drop(0) // object[] vs integer[]
df.retain('a')
//df.retain(0) // same as drop
df.reindex('a')
df.reindex('a', 'b')
df.reindex(['a', 'b'], true)
//df.reindex(0, true)
//df.reindex([0, 1], true)
//df.reindex(0)
df.rename('a', 'A')
df.rename({ 'a': 'A', 'b': 'B' }) // object vs map
df.append(100, [1,2,3])
df.append('test', [1,2,3])
df.append([1,2,3])
//df.append(df.row(0))
df.append('some_row_name', [1,2,3])
//df.append('another_row_name', df.row(0))
df.reshape(5, 2)
df.reshape(['row1', 'row2', 'row3'], ['col1', 'col2']) // native array vs collection
df.join(tmp)
df.join(tmp, JoinType.OUTER)
df.join(tmp, function (row) { return Math.random() })
df.join(tmp, JoinType.OUTER, function (row) { return Math.random() })
df.joinOn(tmp.add('a'), 'a') // trouble finding elems in index
//df.joinOn(tmp, 0, 1)
//df.joinOn(tmp, JoinType.INNER, 0) // assumes INNER is a numeric index
//df.joinOn(tmp, JoinType.INNER, 0, 1)
//df.joinOn(tmp, 'name')
df.joinOn(tmp, 'a', 'b')
df.joinOn(tmp, JoinType.INNER, 'a')
df.joinOn(tmp, JoinType.INNER, 'a', 'b')
tmp.add('a', ['alpha']).add('e', ['merged']); df.merge(tmp)
tmp.add('a', ['alpha', 'tango']).add('e', ['merged', 'added']); df.merge(tmp, JoinType.OUTER)
df.update(tmp)
df.coalesce(tmp)
df.get('0', 'a')
df.get(0, 0)
//df.slice(final Object rowStart, final Object rowEnd)
//df.slice(final Object rowStart, final Object rowEnd, final Object colStart, final Object colEnd)
df.slice(0, 1)
df.slice(0, 1, 0, 1)
df.set('0', 'a', null)
df.set(0, 0, null)
df.col('A')
df.col(0)
df.row('0')
df.row(0)
df.select(function (row) { return false })
df.head()
df.head(3)
df.tail()
df.tail(3)
df.transform(function (row) { return java.util.Collections.singletonList(row) })
df.convert()
df.convert(java.lang.String)
df.toArray()
df.groupBy('a')
df.groupBy('a', 'b')
df.groupBy(['a', 'b'])
//df.groupBy(0) // treats numeric as index names
//df.groupBy(0, 1)
//df.groupBy([0, 1])
df.groupBy(function (row) { return Math.random() })
df.apply(function (value) { return Math.random() * value })
df.pivot('a', 'b', 'c')
df.pivot('a', 'b', 'c', 'd')
//df.pivot(final List<Object> rows, final List<Object> cols, final List<Object> values)
//df.pivot(final Integer row, final Integer col, final Integer ... values)
//df.pivot(final Integer[] rows, final Integer[] cols, final Integer[] values)
//df.pivot(final KeyFunction<V> rows, final KeyFunction<V> cols, final Map<Integer, Aggregate<V,U>> values)
df.sortBy('a')
df.sortBy('a', 'b')
//df.sortBy(final Integer ... cols)
//df.cast(final Class<T> cls)
df.map('a', 'b')
//df.map(final Integer key, final Integer value)
df.unique('a')
df.unique('a', 'b')
//df.unique(0)
//df.unique(0, 1)
tmp.add([1,2,3,4,5]).diff()
tmp.add([1,2,3,4,5]).diff(3)
tmp.add([1,2,3,4,5]).percentChange()
tmp.add([1,2,3,4,5]).percentChange(3)
tmp.add([1,2,3,4,5]).rollapply(function(x) { return x.contains(null) ? null : x.get(0).doubleValue() + x.get(x.size() - 1).doubleValue() })
tmp.add([1,2,3,4,5]).plot()
tmp.add([1,2,3,4,5]).plot(PlotType.BAR)
//df.writeCsv(final String file)
//df.writeXls(final String file)
df.toString()
df.toString(20)
