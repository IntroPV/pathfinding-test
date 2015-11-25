package ar.pablitar.pathfinding

import java.awt.geom.Point2D
package object components {

  implicit class Point2DExtensions(self: Point2D) {
    def proyectTo(anotherPoint: Point2D) = {
      anotherPoint.versor * (self dotProduct anotherPoint.versor)
    }

    def dotProduct(anotherPoint: Point2D) = self.getX * anotherPoint.getX + self.getY * anotherPoint.getY

    def versor: Point2D = {
      self / self.module
    }

    def *(scalar: Double) = {
      copy(self.getX * scalar, self.getY * scalar)
    }
    
    def -(point:Point2D) = {
      copy(self.getX - point.getX, self.getY - point.getY)
    }

    def squaredModule = self.getX * self.getX + self.getY * self.getY

    def module = Math.sqrt(squaredModule)

    def /(scalar: Double) = {
      copy(self.getX / scalar, self.getY / scalar)
    }

    def copy(x: Double = self.getX, y: Double = self.getY) = {
      self.clone().asInstanceOf[Point2D]
    }
  }

}