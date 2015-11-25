package ar.pablitar.pathfinding.components

import java.awt.geom.Point2D

case class Line(from: Point2D, to: Point2D) {
  lazy val direction = (to - from).versor

//  lazy val = direction.getX() / direction.getY()
  
  
}