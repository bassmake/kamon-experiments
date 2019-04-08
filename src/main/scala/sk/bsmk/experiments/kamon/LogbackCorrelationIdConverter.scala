package sk.bsmk.experiments.kamon

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import kamon.Kamon

import java.util.UUID

class LogbackCorrelationIdConverter extends ClassicConverter {
  override def convert(event: ILoggingEvent): String = {
    val correlationId: UUID =
      Kamon.currentContext().get(PropagatedContext.CorrelationIdKey)
    correlationId.toString
  }
}
