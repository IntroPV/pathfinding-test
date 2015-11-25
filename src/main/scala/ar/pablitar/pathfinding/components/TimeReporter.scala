package ar.pablitar.pathfinding.components

object TimeReporter {
  var events = Map.empty[String, Long]
  val startTime = System.currentTimeMillis()

  println(s"Started logging time. Start Time: ${startTime}")

  def startEvent(event: String) {
    val currentTime = System.currentTimeMillis()
    events = events + ((event, currentTime))
    println(s"[${elapsed(currentTime)}] Started ${event} at ${currentTime}")
  }

  def endEvent(event: String) {
    val currentTime = System.currentTimeMillis()
    events.get(event) match {
      case Some(eventStartTime) =>
        println(s"[${elapsed(currentTime)}] Finished ${event} at ${currentTime}. Elapsed: ${currentTime - eventStartTime}")
      case _ =>
        println(s"[${elapsed(currentTime)}] Finished ${event} at ${currentTime}. Unknown start time")
    }
  }

  def elapsed(currentTime: Long = System.currentTimeMillis()) = {
    currentTime - startTime
  }

}