package sk.bsmk.experiments.kamon

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging

object KamonExperimentsApp extends App with LazyLogging {

  logger.info("Hello kamon")

  val system = ActorSystem("akka-with-kamon")




}
