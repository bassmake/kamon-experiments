package sk.bsmk.experiments.kamon

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import sk.bsmk.experiments.kamon.CustomerActor.{
  AddPoints,
  GetPointsInfo,
  ReducePoints,
  SendPointsTo
}
import akka.pattern.ask
import akka.util.Timeout
import kamon.Kamon
import kamon.prometheus.PrometheusReporter
import kamon.zipkin.ZipkinReporter

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object KamonExperimentsApp extends App with LazyLogging {

  val duration = 5.seconds
  implicit val timeout: Timeout = Timeout(duration)

  logger.info("Hello kamon")

  Kamon.addReporter(new PrometheusReporter())
  Kamon.addReporter(new ZipkinReporter())

  val system = ActorSystem("akka-with-kamon")

  val alice = system.actorOf(CustomerActor.props("Alice"), "Alice-actor")
  val bob = system.actorOf(CustomerActor.props("Bob"), "Bob-actor")

  for (i <- Range(1, 100)) {
    logger.info(s"Start cycle $i")
    alice ! AddPoints(3)
    alice ! AddPoints(30)
    alice ! ReducePoints(10)
    alice ! SendPointsTo(bob, 3)
    Thread.sleep((((i % 3) + 1) * 100).longValue())
  }

  val alicePointsFuture = (alice ? GetPointsInfo).mapTo[Int]
  val bobPointsFuture = (bob ? GetPointsInfo).mapTo[Int]

  Future
    .sequence(Seq(alicePointsFuture, bobPointsFuture))
    .onComplete(_ => system.terminate())

  logger.info(s"Alice has ${Await.result(alicePointsFuture, duration)} points")
  logger.info(s"Bob has ${Await.result(bobPointsFuture, duration)} points")

}
