package com.airtonjal.rest.data

import com.airtonjal.rest.data.persistence.Projects
import com.airtonjal.rest.model.Project
import grizzled.slf4j.Logging

import scala.concurrent.Future

/**
 * Data source access of [[com.airtonjal.rest.model.Project]] entity
 *
 * Important to notice that each method implemented in this context will actually return a [[scala.concurrent.Future]]
 * wrapping the actual return value. This is done so in order to make the system more flexible towards parallel,
 * concurrent and asynchronous computation, specially for blocking sections of code, caused by I/O.
 *
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
trait ProjectRepository extends Logging {

  /**
   * Fetches all application projects
   * @return A set of [[com.airtonjal.rest.model.Project]] objects trapped in a [[scala.concurrent.Future]]
   */
  def fetchAll(): Future[Seq[Project]]

  /**
   * Fetches all projects from a given user
   * @param uid The user identification
   * @return A set of user [[com.airtonjal.rest.model.Project]] objects trapped in a [[scala.concurrent.Future]]
   */
  def fetchAll(uid: Int): Future[Seq[Project]]

  /**
   * Fetches a project
   * @param pid The project id
   * @return A [[com.airtonjal.rest.model.Project]] object trapped in a [[scala.concurrent.Future]]
   */
  def fetch(pid: Int): Future[Project]

  /**
   * Inserts a project into the persistence unit
   * @param p The project
   * @return A [[scala.concurrent.Future]]
   */
  def insert(p: Project): Future[Any]

  /**
   * Inserts a seq of projects into the persistence unit
   * @param projectSeq The seq of projects
   * @return A [[scala.concurrent.Future]]
   */
  def insert(projectSeq: Seq[Project]): Future[Any]

  /**
   * Deletes a project from the persistence unit
   * @param pid The project identification
   * @return A [[scala.concurrent.Future]]
   */
  def delete(pid: Int): Future[Any]

  /**
   * Deletes all projects from the persistence unit
   * @return A [[scala.concurrent.Future]]
   */
  def deleteAll(): Future[Any]

}

class ProjectRepositoryImpl extends ProjectRepository {
  import slick.driver.PostgresDriver.api._

  val db = Postgres.db
  val projects = Projects.ProjectTable

  override def fetchAll(): Future[Seq[Project]] = {
    logger.info("Fetching all projects")
    val allProjects = for(p <- projects) yield p
    db.run(allProjects.result)
  }

  override def fetchAll(uid: Int): Future[Seq[Project]] = {
    logger.info(s"Fetching projects for user with id: $uid")
    db.run(projects.filter(_.uid === uid).result)
  }

  override def fetch(pid: Int): Future[Project] = {
    logger.info(s"Fetching project with id: $pid")
    db.run(projects.filter(_.pid === pid).result.head)
  }

  override def insert(p: Project) = {
    logger.info(s"Inserting project\tuid: ${p.uid}, name: ${p.name}. description: ${p.description}")
    db.run(projects += p)
  }

  override def insert(projectSeq: Seq[Project]) = {
    logger.info(s"Inserting project set of size " + projectSeq.size)
    db.run((projects ++= projectSeq).transactionally)
  }

  override def delete(pid: Int) = {
    logger.info(s"Deleting project with id: $pid")
    db.run(projects.filter(_.pid === pid).delete)
  }

  override def deleteAll() = {
    logger.info(s"Deleting all projects")
    db.run(projects.delete)
  }
}
