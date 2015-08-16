package astar
package maze

/**
 * A problem for the A* agent to traverse.
 *
 * Mazes are generated from text strings using:
 * +- for horizontal walls
 * | for vertical walls
 * spaces for paths
 * I for the entrance
 * O for the exit
 *
 * Author: Phillip Johnson
 * Date: 3/14/15 (mmmm...π)
 */

case class Cell(x: Int, y: Int) {
  override def toString = s"($x, $y)"
}

class Maze(val pattern: String) {
  require(pattern.contains("I") && pattern.contains("O"), "Missing maze entrance or exit")

  private val WALL_STRINGS = Set('+', '-', '|')
  private val PATH_STRINGS = Set(' ', 'I', 'O')
  private val ENTRANCE_STRING = 'I'
  private val EXIT_STRING = 'O'

  //private val stringRows = pattern.split('\n')
  val (width, height) = sizeOfMaze

  lazy val walls: Set[Cell] = {
    searchMaze(WALL_STRINGS)
  }

  lazy val paths: Set[Cell] = {
    searchMaze(PATH_STRINGS)
  }

  lazy val entrance: (Cell) = {
    searchMaze(Set(ENTRANCE_STRING)).head
  }

  lazy val exit: (Cell) = {
    searchMaze(Set(EXIT_STRING)).head
  }

  def sizeOfMaze = {
    val stringRows = pattern.split('\n')
    (stringRows.head.length - 1, stringRows.length)
  }

  private def searchMaze(chars: Set[Char]) = {
    val stringRows = pattern.split('\n')
    (for {
      (rowStr: String, row: Int) <- stringRows.zipWithIndex
      (char: Char, col: Int) <- rowStr.zipWithIndex
      if chars.contains(char)
    } yield Cell(col, row)).toSet
  }

}
