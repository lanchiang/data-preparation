package de.hpi.isg.dataprep.tools.apps

import java.util.regex.Pattern

import de.hpi.isg.dataprep.tools.process.DataPreparationTask
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DataTypes, StringType}

/**
  * @author Lan Jiang
  * @since 15/01/2018
  */
object TabularDatasetPreparationApp {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {
    // now set the master only to local
    val sparkConf = new SparkConf().setAppName("TabularDatasetPreparationApp").setMaster("local")
    val sc = new SparkContext(sparkConf)


    val spark = SparkSession.builder()
      .appName("Spark SQL basic example").getOrCreate()
    val df = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .option("nullValue", "null")
      .csv("/Users/Fuga/Documents/HPI/data/testdata/pokemon.csv")
//    df.show(10)

    val dfTask = new DataPreparationTask(df)
    dfTask.getDataFrame().printSchema()

    dfTask.changeColumnDataType("identifier", DataTypes.IntegerType)

    dfTask.getDataFrame().printSchema()
    dfTask.getDataFrame().show(10)

    println("-------------------------------------------------------------------------------------------")

  }
}