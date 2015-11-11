package ar.pablitar.pathfinding

import com.uqbar.vainilla.GameScene
import ar.pablitar.pathfinding.components.TileMap
import ar.pablitar.pathfinding.components.Cursor

class PathfindingGameScene extends GameScene {
  val tileMap = new TileMap(16, 12)
  tileMap.addAllTiles(this)
  
  this.addComponent(Cursor)
}