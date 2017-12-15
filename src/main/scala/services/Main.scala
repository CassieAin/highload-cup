package services

import models.{LocationTable, UserTable, VisitTable}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main {



//  def main(args: Array[String]): Unit = {
//    init()
//    FillWithData.fillDatabase()
//  }

  def init(): Unit ={
    Await.result(db.run(LocationTable.table.schema.create), Duration.Inf)
    Await.result(db.run(UserTable.table.schema.create), Duration.Inf)
    Await.result(db.run(VisitTable.table.schema.create), Duration.Inf)
  }
}
