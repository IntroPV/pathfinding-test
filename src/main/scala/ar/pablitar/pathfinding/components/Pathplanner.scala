package ar.pablitar.pathfinding.components

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.SortedSet
import scala.math.Ordering
object Pathplanner {
  var origin = Option.empty[Tile]
  var destination = Option.empty[Tile]

  var path = List.empty[Tile]

  def newPoint(tile: Tile):Unit = {
    (origin, destination) match {
      case (None, _) => origin = Some(tile)
      case (Some(_), None) => {
        destination = Some(tile)
        planPath()
      }
      case (Some(_), Some(_)) => {
        origin = None
        destination = None
        path = List.empty[Tile]
      }
    }
  }

  def isSelected(tile: Tile) = {
    origin.contains(tile) || destination.contains(tile) || path.contains(tile)
  }
  
  def planPath() = {
    path = aStar[Tile](
        origin.get,
        destination.get,
        _.neighbors.filter(_.passable).toList,
        _.squaredDistanceTo(_),
        _.squaredDistanceTo(_))  
  }
  
  
  def aStar[T](start:T, goal:T, neighbors:T => List[T], heuristic:(T,T)=>Float, costFunc:(T,T)=>Float):List[T] = {
    def calculateCost(path:(Float, List[T])) =
      path._2.tail.foldLeft((0f,path._2.head))((costHead,node) => {
          val (cost, head) = costHead
          (cost + costFunc(node,head), node)
        })._1

    def findBestCandidate(candidates:Set[(Float, List[T])]) =
      candidates.foldLeft(
        (Float.PositiveInfinity, (0f, List[T]())))((best, path) => {
          val guesstimate = path._1 + heuristic(path._2.head, goal)
          if (best._1 <  guesstimate) best else (guesstimate,path)
        })._2

    // Uses mutable set for performance... ?
    var fringe = Set((0f,start::Nil))
    var closed = Set[T]()

    while(!fringe.isEmpty) {
      val (cost, current) = findBestCandidate(fringe)
      fringe = fringe - ((cost, current))
      if(current.head equals goal)
        return current.reverse
      else if (!closed.contains(current.head )) {
        closed = closed + current.head
        neighbors(current.head).foreach(n => {
            val nCost = costFunc(current.head, n) + cost
            fringe = fringe + ((nCost, n::current))
          })
      }
    }
    // Searched all possible paths...
    return Nil
  }
//  def planPath() = {
//    val start = origin.get
//    val goal = origin.get
//    
//    var closed = ArrayBuffer()
//    val currentCostMap = Map[Tile, Double]().withDefaultValue(Double.MaxValue)
//    val estimatedCostMap = Map[Tile, Double]().withDefaultValue(Double.MaxValue)
//    
//    val cameFrom = Map[Tile, Tile]()
//    
//    
//    def heuristicCostEstimate(node:Tile) = {
//      node.squaredDistanceTo(goal)
//    }
//    
//    def visitNode(node: Tile, cost: Double) = {
//      currentCostMap += ((node, cost))
//      estimatedCostMap += ((node, cost + heuristicCostEstimate(start)))
//    }
//    
//    def pathFor(current:
//    
//    visitNode(start, 0)
//    
//    var opened = SortedSet(goal)(Ordering.by { tile => estimatedCostMap(tile) })
//    
//    while(!opened.isEmpty) {
//      val current = opened.head
//      
//      if(current == goal) {
//        return pathFor(current)
//      }
//    }
//    
//  }
}
