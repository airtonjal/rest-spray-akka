package com.airtonjal.rest.data.persistence

import com.airtonjal.rest.model

/** Stand-alone Slick data model for immediate use */
object Projects extends {
   val profile = slick.driver.PostgresDriver
 } with Projects

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Projects {
  val profile: slick.driver.JdbcProfile
  import profile.api._

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Array(ProjectTable.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  def create = schema.create
  def drop = schema.drop

  /** Table description of table project. Objects of this class serve as prototypes for rows in queries. */
  class Project(_tableTag: Tag) extends Table[model.Project](_tableTag, "project") {
    def * = (pid, uid, name, description, meta) <> (model.Project.tupled, model.Project.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(pid), Rep.Some(uid), Rep.Some(name), Rep.Some(description), meta).shaped.<>({r =>
      import r._; _1.map(_=> model.Project.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>
        throw new Exception("Inserting into ? projection not supported."))

    /** Database column pid SqlType(serial), AutoInc, PrimaryKey */
    val pid: Rep[Option[Int]] = column[Option[Int]]("pid", O.AutoInc, O.PrimaryKey)
    /** Database column uid SqlType(int4) */
    val uid: Rep[Int] = column[Int]("uid")
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column description SqlType(text) */
    val description: Rep[String] = column[String]("description")
    /** Database column meta SqlType(text), Default(None) */
    val meta: Rep[Option[String]] = column[Option[String]]("meta", O.Default(None))
  }

  /** Collection-like TableQuery object for table Project */
  lazy val ProjectTable = new TableQuery(tag => new Project(tag))

 }