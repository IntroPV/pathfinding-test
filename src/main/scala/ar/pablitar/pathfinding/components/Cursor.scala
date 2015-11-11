package ar.pablitar.pathfinding.components

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.appearances.Circle
import java.awt.Color
import com.uqbar.vainilla.DeltaState
import java.awt.geom.Point2D
import java.awt.Point

object Cursor extends GameComponent[GameScene] {
  val diameter = 20
  lazy val radius = diameter / 2
  this.setAppearance(new Circle(Color.WHITE, diameter))
  
  override def update(state: DeltaState) = {
    val mousePos = state.getCurrentMousePosition
    this.setX(state.getCurrentMousePosition.getX)
    this.setY(state.getCurrentMousePosition.getY)
  }

  def center = {
    new Point(this.getX.toInt + radius, this.getY.toInt + radius)
  }
}