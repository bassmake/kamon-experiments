package sk.bsmk.experiments.kamon

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import kamon.Kamon
import kamon.context.Context
import kamon.trace.Span
import sk.bsmk.experiments.kamon.CustomerActor.{
  AddPoints,
  GetPointsInfo,
  ReducePoints,
  SendPointsTo
}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object KamonExperimentsApp extends App with LazyLogging {

  val duration = 5.seconds
  implicit val timeout: Timeout = Timeout(duration)

  logger.info("Hello kamon")

  val system = ActorSystem("akka-with-kamon")

  val alice = system.actorOf(CustomerActor.props("Alice"), "Alice-actor")
  val bob = system.actorOf(CustomerActor.props("Bob"), "Bob-actor")

  for (i <- Range(1, 10)) {
    val correlationId = PropagatedContext.UuidGenerator.generate()
    logger.info(s"Start cycle $i - $correlationId")

    val span = Kamon
      .buildSpan("point exchange")
      .start()

    val context = Context
      .create(Span.ContextKey, span)
      .withKey(PropagatedContext.CorrelationIdKey, correlationId)

    val scope = Kamon.storeContext(context)

    scope.close()

    Kamon.withContext(context) {
      Kamon.withContextKey(PropagatedContext.CorrelationIdKey, correlationId) {
        alice ! AddPoints(3)
        alice ! AddPoints(30)
        alice ! ReducePoints(10)
        alice ! SendPointsTo(bob, 3)
      }
    }
    Thread.sleep((((i % 3) + 1) * 100).longValue())
  }

  val alicePointsFuture = (alice ? GetPointsInfo).mapTo[Int]
  val bobPointsFuture = (bob ? GetPointsInfo).mapTo[Int]

  val futures = Future
    .sequence(Seq(alicePointsFuture, bobPointsFuture))

  logger.info(s"Alice has ${Await.result(alicePointsFuture, duration)} points")
  logger.info(s"Bob has ${Await.result(bobPointsFuture, duration)} points")

  futures.onComplete { _ =>
    system.terminate()
    Kamon.stopAllReporters()
  }

}
