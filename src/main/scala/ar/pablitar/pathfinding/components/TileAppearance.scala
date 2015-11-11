package ar.pablitar.pathfinding.components

import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.SimpleAppearance
import java.awt.Graphics2D
import com.uqbar.vainilla.GameComponent
import java.awt.Color
import ar.pablitar.pathfinding.Pathfinding

class TileAppearance(tile:Tile) extends Appearance {
  
  val tileSize = Pathfinding.TILE_SIZE
  
  def getHeight(): Double = {
    tileSize
  }

  def getWidth(): Double = {
    tileSize
  }

  def render(component: GameComponent[_], graphics: Graphics2D): Unit = {
    val x = component.getX().toInt
    val y = component.getY().toInt
    graphics.setColor(Color.BLACK)
    graphics.drawRect(x, y, tileSize, tileSize)
    graphics.setColor(getDrawColor())
    graphics.fillRect(x + 1, y + 1, tileSize - 1, tileSize - 1)
  }

  def update(x$1: Double): Unit = {
    
  }

  def copy[T](): T = {
    new TileAppearance(tile).asInstanceOf[T]
  }

  def getDrawColor() = {
    if(tile.passable)
      if(Pathplanner.isSelected(tile)) Color.YELLOW else Color.GREEN
    else
      Color.RED
  }
}