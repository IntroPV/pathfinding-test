package ar.pablitar.pathfinding

import com.uqbar.vainilla.Game
import java.awt.Dimension

class PathfindingGame extends Game {
  def getDisplaySize(): Dimension = {
    new Dimension(1280, 800)
  }

  def getTitle(): String = {
    "Hola Pathfinding"
  }

  def initializeResources(): Unit = {
  }

  def setUpScenes(): Unit = {
    this.setCurrentScene(new PathfindingGameScene())
  }
}