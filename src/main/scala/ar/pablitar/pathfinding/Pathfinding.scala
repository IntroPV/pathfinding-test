package ar.pablitar.pathfinding

import com.uqbar.vainilla.DesktopGameLauncher

object Pathfinding extends App {
  val TILE_SIZE = 20
  new DesktopGameLauncher(new PathfindingGame()).launch()
}