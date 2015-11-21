package ar.pablitar.pathfinding.components

import com.uqbar.vainilla.GameScene
import scala.util.Random

class TileMap(xSize: Int, ySize: Int) {
  
  val seed = System.currentTimeMillis()
  val randomizer = new Random(seed)
  
  val dungeon = DungeonGenerator.generate(xSize, ySize, randomizer)
  
  val tiles = for(i <- 0 to xSize - 1;
      j <- 0 to ySize -1) yield {
    new Tile(this, i,j, dungeon.isPassable(i, j))
  }
  
  def addAllTiles(scene: GameScene) {
     tiles.foreach {
       scene.addComponent(_)
     }
  }

  def neighborsOf(tile: Tile) = {
    tiles.filter { x => x.isNeighborOf(tile) }
  }


  def removeAllTiles() = {
    tiles.foreach { _.destroy() }
  }

}