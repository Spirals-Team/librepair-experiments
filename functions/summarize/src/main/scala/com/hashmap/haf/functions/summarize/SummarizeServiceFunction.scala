package com.hashmap.haf.functions.summarize

import com.hashmap.haf.annotations.IgniteFunction
import com.hashmap.haf.datastore.DataframeIgniteCache
import com.hashmap.haf.functions.constants.TaskConfigurationConstants._
import com.hashmap.haf.functions.services.ServiceFunction
import org.apache.ignite.Ignite
import org.apache.ignite.resources.IgniteInstanceResource
import org.apache.ignite.services.ServiceContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Column, DataFrame, Row, SparkSession}

@IgniteFunction(functionClazz = "SummarizeSparkTask", service = "summarizeService",
  configs = Array())
class SparkSummarizeService extends ServiceFunction{

  var appName = ""
  @IgniteInstanceResource
  var ignite: Ignite = _


  override def run(inputKey: String, outputKey: String, functionArguments: Map[String, String], configurations: Map[String, String]): String = {
    println("Executing Spark Summarize.....")
    val spark = SparkSession
      .builder()
      .appName(configurations.getOrElse(SPARK_APP_NAME, throw new IllegalArgumentException(SPARK_APP_NAME + " not provided in configurations")))
      .master(configurations.getOrElse(SPARK_MASTER, throw new IllegalArgumentException(SPARK_MASTER + " not provided in configurations")))
      .getOrCreate()
    val cache = DataframeIgniteCache.create()
    val (schema, igniteRDD) = cache.get(spark.sparkContext, inputKey)

    val rdd1: RDD[Row] = igniteRDD.map(_._2)
    val df = spark.sqlContext.createDataFrame(rdd1, schema)
    df.show()
    df.cache()
    val metadata = MetadataHandler.get(df)
    val newMetaData = CommonUtils.getAllSummarizeOperations.foldLeft(metadata)((meta, op) => op(meta, df)).build()
    val newDs = MetadataHandler.set(df, newMetaData)
    cache.set(spark.sparkContext, newDs, outputKey)
    println("Setting to output cache .......showing only 10 rows of it")
    newDs.show(10)
    spark.close()
    "successful"
  }

  override def cancel(ctx: ServiceContext) = println("Cancelled")

  override def init(ctx: ServiceContext) = {
    appName = ctx.name()
  }

  override def execute(ctx: ServiceContext) = println("Executing")
}


object SummarizeOperations{
  def count = (metadata: MetadataBuilder, df: DataFrame) => {
    metadata.putLong("total_records", df.count())
  }
}

object SummarizeOperationsOnEachColumn{


  def numericalUnaryOperations = (metadata: MetadataBuilder, df: DataFrame) => {
    import org.apache.spark.sql.functions.{col, max, mean, min}

    type AggregateFunctions = Column => Column
    val operations: List[AggregateFunctions] = List(min, max, mean)
    val fields = CommonUtils.filterNumericColumns(df)
    val fieldNames = fields.map(field => field.name)
    val allExprTups = (for(fName <- fieldNames; nAgg <- operations) yield nAgg(col(fName))).toSeq


    val newDf = df.agg(allExprTups.head, allExprTups.tail:_*)

    newDf.first()
      .getValuesMap(newDf.schema.fieldNames)
      .foldLeft(metadata)((meta, tup2) => meta.putString(tup2._1, tup2._2.toString))
  }

  def commonUnaryOperations = (metadata: MetadataBuilder, df: DataFrame) => {
    import org.apache.spark.sql.functions.{approx_count_distinct, col}

    type AggregateFunctions = Column => Column
    val operations: Seq[AggregateFunctions] = List(approx_count_distinct)
    val fields = CommonUtils.filterNumericColumns(df)
    val fieldNames = fields.map(field => field.name)
    val allExprTups = (for(fName <- fieldNames; nAgg <- operations) yield nAgg(col(fName))).toSeq


    val newDf = df.agg(allExprTups.head, allExprTups.tail:_*)

    newDf.first()
      .getValuesMap(newDf.schema.fieldNames)
      .foldLeft(metadata)((meta, tup2) => meta.putString(tup2._1, tup2._2.toString))
  }


}

object CommonUtils {
  type SummaryFunction = (MetadataBuilder, sql.DataFrame) => MetadataBuilder

  def filterNumericColumns(df: DataFrame) = {
    df.schema.fields
      .filter(x => x.dataType == IntegerType || x.dataType == DoubleType || x.dataType == LongType)
  }

  val getAllSummarizeOperations: Seq[SummaryFunction] = {
    import SummarizeOperations._
    List(count)
    //todo fix numeric unary operations - its giving null pointer
    //List(numericalUnaryOperations, count)
  }

}


object MetadataHandler {
  def get(df: DataFrame) = {
    val meta = new sql.types.MetadataBuilder()
    df.schema.filter(field => field.name == "app_metadata").headOption match {
      case Some(field) => meta.withMetadata(field.metadata)
      case _ => meta
    }
  }

  def set(df: DataFrame, newMetadata: Metadata) = {
    val newColumn = df.col(df.schema.fieldNames.apply(0)).as("app_metadata", newMetadata)
    df.withColumn("app_metadata", newColumn)
  }

}