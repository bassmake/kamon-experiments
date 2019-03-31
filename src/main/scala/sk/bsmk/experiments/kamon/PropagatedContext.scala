package sk.bsmk.experiments.kamon

import java.util.UUID

import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.impl.RandomBasedGenerator
import kamon.context.Key

object PropagatedContext {

  val UuidGenerator: RandomBasedGenerator = Generators.randomBasedGenerator()
  val CorrelationIdKey: Key[UUID] =
    Key.broadcast[UUID]("correlationId", UuidGenerator.generate())

}
