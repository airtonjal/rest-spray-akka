/**
 * Model entities
 * @author <a href="mailto:airtonjal@gmail.com">Airton Libório</a>
 */
package com.airtonjal.rest.model

case class Project(pid: Option[Int] = None, uid: Int, name: String, description: String, meta: Option[String] = None)

