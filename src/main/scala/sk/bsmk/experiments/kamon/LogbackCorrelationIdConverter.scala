package sk.bsmk.experiments.kamon

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import kamon.Kamon

class LogbackCorrelationIdConverter extends ClassicConverter {
  override def convert(event: ILoggingEvent): String = {
    Kamon.currentContext().get(PropagatedContext.CorrelationIdKey).toString
  }
}
