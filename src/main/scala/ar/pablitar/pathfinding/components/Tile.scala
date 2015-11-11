package ar.pablitar.pathfinding.components

import com.uqbar.vainilla.GameComponent
import ar.pablitar.pathfinding.PathfindingGameScene
import ar.pablitar.pathfinding.Pathfinding
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.MouseButton
import java.awt.Rectangle

class Tile(map: TileMap, val i: Int, val j:Int, val passable:Boolean) extends GameComponent[PathfindingGameScene] {

  this.setX(i * Pathfinding.TILE_SIZE)
  this.setY(j * Pathfinding.TILE_SIZE)
  
  this.setAppearance(new TileAppearance(this))
  
  lazy val shape = new Rectangle(getX().toInt, getY().toInt, Pathfinding.TILE_SIZE,Pathfinding.TILE_SIZE)
  
  override def update(state: DeltaState) = {
    if(passable &&
        state.isMouseButtonPressed(MouseButton.LEFT) &&
        shape.contains(Cursor.center)){
      Pathplanner.newPoint(this)
    }
  }
  
  def squaredDistanceTo(tile: Tile) = {
    val distX = (tile.i - i)
    val distY = (tile.j - j)
    
    distX * distX + distY * distY
  }

  def neighbors = {
    map.neighborsOf(this)
  }

  def isNeighborOf(tile: Tile) = {
    tile.i == i && (tile.j - j).abs == 1 || 
    tile.j == j && (tile.i - i).abs == 1
  }
}