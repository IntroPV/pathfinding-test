package ar.pablitar.pathfinding.components

import com.uqbar.vainilla.GameComponent
import ar.pablitar.pathfinding.PathfindingGameScene
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key

class Regenerator extends GameComponent[PathfindingGameScene] {
  override def update(state:DeltaState) = {
    if(state.isKeyPressed(Key.R)) {
      regenerate()
    }
  }

  def regenerate() = {
    this.getScene.tileMap.removeAllTiles()
    this.getScene.generateMap()
  }
}