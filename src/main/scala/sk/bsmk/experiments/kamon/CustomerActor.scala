package sk.bsmk.experiments.kamon

import akka.actor.{Actor, ActorRef, Props}
import com.typesafe.scalalogging.LazyLogging
import org.slf4j.MDC
import sk.bsmk.experiments.kamon.CustomerActor.{
  AddPoints,
  GetPointsInfo,
  ReducePoints,
  SendPointsTo
}

object CustomerActor {

  def props(name: String): Props = Props(new CustomerActor(name))

  final case class AddPoints(pointsToAdd: Int)
  final case class ReducePoints(pointsToReduce: Int)
  final case class SendPointsTo(who: ActorRef, pointsToSend: Int)
  final case object GetPointsInfo

}

class CustomerActor(val name: String) extends Actor with LazyLogging {

  private val points: Int = 0

  override def receive: Receive =
    onMessage(points)

  @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
  private def onMessage(points: Int): Receive = {
    case AddPoints(pointsToAdd) =>
      withMdc {
        logger.info(s"adding $pointsToAdd points")
        context.become(onMessage(points + pointsToAdd))
      }
    case ReducePoints(pointsToReduce) =>
      withMdc {
        logger.info(s"reducing $pointsToReduce points")
        context.become(onMessage(points - pointsToReduce))
      }
    case SendPointsTo(toWho, pointsToSend) =>
      withMdc {
        logger.info(s"sending $pointsToSend points to ${toWho.path.name}")
        toWho ! AddPoints(pointsToSend)
        self ! ReducePoints(pointsToSend)
      }
    case GetPointsInfo =>
      withMdc {
        logger.info(s"providing info about $points points")
        sender() ! points
      }
  }

  private def withMdc(handler: => Unit): Unit = {
    MDC.put("Customer", name)
    try {
      handler
    } finally {
      MDC.clear()
    }
  }

}
