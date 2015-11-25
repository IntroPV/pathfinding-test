package ar.pablitar.pathfinding.components

import scala.util.Random
import java.awt.geom.Rectangle2D
import java.awt.geom.Point2D
import scala.annotation.tailrec

case class Room(i: Int, j: Int, width: Int, height: Int) {
  def overlapsWith(room: Room, minDistance: Int) = {
    this.toRectangle2D(minDistance).intersects(room.toRectangle2D())
  }

  def toRectangle2D(border: Int = 0) = {
    new Rectangle2D.Float(i - border, j - border, width + 2 * border, height + 2 * border)
  }

  def contains(i: Int, j: Int) = {
    toRectangle2D().contains(i, j)
  }

  def minDistanceTo(aRoom: Room) = {
    (for (
      aTile <- this.tiles();
      otherTile <- aRoom.tiles()
    ) yield aTile.distanceSq(otherTile)).min
  }

  def tiles(offsetX: Int = 0, offsetY:Int = 0) = {
    
    for (
      i <- (i + offsetX) to (i + width - offsetX);
      j <- (j + offsetY) to (j + height - offsetY)
    ) yield new Point2D.Float(i, j)
  }
  
  def centerTiles = {
    tiles(1,1)
  }

  def tilesCloserTo(destination: Room) = {
    this.centerTiles.zip(destination.centerTiles).minBy(tilesTouple => tilesTouple._1.distanceSq(tilesTouple._2))
  }
}

case class Connection(from: Point2D, to: Point2D) {
  def between(x: Int, a: Int, b: Int) = {
    x >= a.min(b) && x <= a.max(b)
  }
  def contains(i: Int, j: Int) = {
    (i == from.getX.toInt && between(j, from.getY().toInt, to.getY().toInt)) ||
      (j == to.getY.toInt && between(i, from.getX().toInt, to.getX().toInt))
  }
}

class DungeonGenerator(xSize: Int, ySize: Int, randomizer: Random) {
  TimeReporter.startEvent("Generating Rooms")
  val rooms = generateRooms

  TimeReporter.endEvent("Generating Rooms")

  TimeReporter.startEvent("Generating Connections")
  val mainPath = generateConnectionsBetweenRooms
  TimeReporter.endEvent("Generating Connections")

  //  val connections = List()

  def generate = Dungeon(rooms, mainPath)

  def generateRooms = {
    val minRoomSize = 8
    val maxRoomSize = 15
    val minDistance = 1

    var rooms = List[Room]()

    var possibleRooms = {
      TimeReporter.startEvent("Generating Possible Rooms")
      val possibleRooms = for (
        i <- 0 to (xSize - minRoomSize);
        j <- 0 to (ySize - minRoomSize);
        width <- (minRoomSize to maxRoomSize.min(xSize - i));
        height <- (minRoomSize to maxRoomSize.min(ySize - j))
      ) yield Room(i, j, width, height)
      
      TimeReporter.endEvent("Generating Possible Rooms")

      possibleRooms
    }

    def isPlaceable(room: Room) = {
//      TimeReporter.startEvent(s"Evaluating room ${room}")
      val placeable = !rooms.exists { existingRoom => existingRoom.overlapsWith(room, minDistance) }
//      TimeReporter.endEvent(s"Evaluating room ${room}")
      placeable
    }

    def roomCandidates = {
      var candidateRooms = for (room <- possibleRooms if isPlaceable(room)) yield room
      possibleRooms = candidateRooms
      possibleRooms
    }

    val roomsStream = {
      def loop: Stream[Room] = {
        val currentRoomCandidates = roomCandidates
        if (currentRoomCandidates.isEmpty)
          Stream.empty[Room]
        else
          Stream.cons(currentRoomCandidates(randomizer.nextInt(currentRoomCandidates.length)), loop)
      }

      loop
    }

    TimeReporter.startEvent(s"Evaluating rooms")
    for (room <- roomsStream) {
      rooms = rooms :+ room
    }
    TimeReporter.endEvent(s"Evaluating rooms")

    rooms
  }

  def connectionBetween(from: Room, to: Room) = {
    val connectionTiles = from.tilesCloserTo(to)
    Connection(connectionTiles._1, connectionTiles._2)
  }

  def generateConnectionsBetweenRooms = {
    @tailrec
    def plotPath(initial: Room, nodes: List[Room], currentPath: List[Connection]): List[Connection] = {
      nodes.sortBy { _.minDistanceTo(initial) } match {
        case Nil          => currentPath
        case next :: rest => plotPath(next, rest, currentPath :+ connectionBetween(initial, next))
      }
    }

    plotPath(rooms.head, rooms.tail, List())
  }

}

object DungeonGenerator {
  def generate(xSize: Int, ySize: Int, randomizer: Random) = {
    new DungeonGenerator(xSize, ySize, randomizer).generate
  }
}