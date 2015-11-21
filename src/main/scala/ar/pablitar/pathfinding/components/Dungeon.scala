package ar.pablitar.pathfinding.components

case class Dungeon(rooms: List[Room], connections: List[Connection]) {
  def isPassable(i: Int, j: Int) = {
    rooms.exists { room => room.contains(i, j) } || connections.exists { connection => connection.contains(i, j) }
  }
}