import ar.pablitar.pathfinding.components.Room
import scala.util.Random

import ar.pablitar.pathfinding.components.DungeonGenerator

object test {
  
  val seed = 1                                    //> seed  : Int = 1
  val randomizer = new Random(seed)               //> randomizer  : scala.util.Random = scala.util.Random@34ce8af7
  
  val dungeon = DungeonGenerator.generate(16, 12, randomizer)
                                                  //> dungeon  : ar.pablitar.pathfinding.components.Dungeon = Dungeon(List(Room(10
                                                  //| ,9,3,3), Room(9,5,3,3), Room(4,5,4,5), Room(7,0,3,4), Room(13,2,3,4), Room(0
                                                  //| ,2,3,4), Room(0,7,3,3)))
}