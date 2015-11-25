package ar.pablitar.pathfinding

import com.uqbar.vainilla.GameScene
import ar.pablitar.pathfinding.components.TileMap
import ar.pablitar.pathfinding.components.Cursor
import ar.pablitar.pathfinding.components.Regenerator

class PathfindingGameScene extends GameScene {
  var tileMap:TileMap = null
  generateMap()

  this.addComponent(Cursor)
  this.addComponent(new Regenerator())

  def generateMap() = {
    tileMap = new TileMap(80, 60)
    tileMap.addAllTiles(this)
  }
}