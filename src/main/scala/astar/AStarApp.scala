package astar

import astar.maze.{Cell, Mazes}
import astar.search.{Agent, Heuristics}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
object AStarApp {

  private val (grey, blue, yellow) = ("#333333", "#4682b4", "#ffb958")
  private val (zoom, offset, drawRate) = (10 /*px*/ , 5 /*px*/ , 10 /*milliseconds*/ )

  @JSExport
  def doDynContent(): Unit = {
    val canvas = dom.document.getElementById("canvas").asInstanceOf[html.Canvas]

    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val maze = Mazes.maze40

    val agent = new Agent(maze, Heuristics.manhattan)

    val solution: Option[List[Cell]] = agent.search()

    canvas.height = maze.height * zoom; canvas.width = maze.width * zoom

    ctx.clearRect(0, 0, canvas.width, canvas.height)

    ctx.fillStyle = grey
    for (wall <- maze.walls) {
      ctx.fillRect(wall.x * zoom, wall.y * zoom, zoom, zoom)
    }

    if (solution.isDefined) {
      dom.console.log("Nodes expanded:" + agent.searchHistory.size)
      draw(agent.searchHistory)
    }

    def draw(fringe: Seq[List[Cell]]): Unit = {
      if (fringe.nonEmpty) {
        ctx.fillStyle = blue
        for (cell <- fringe.head) {
          ctx.beginPath()
          ctx.arc(cell.x * zoom + offset, cell.y * zoom + offset, (zoom - 2) / 2, 0, 2 * math.Pi)
          ctx.fill()
        }
        dom.setTimeout(() => draw(fringe.tail), drawRate)
      } else {
        val path = solution.get
        ctx.fillStyle = yellow
        for (cell <- path) {
          ctx.beginPath()
          ctx.arc(cell.x * zoom + offset, cell.y * zoom + offset, (zoom - 2) / 2, 0, 2 * math.Pi)
          ctx.fill()
        }
      }
    }
  }
}