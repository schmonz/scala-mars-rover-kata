import scala.annotation.tailrec

object MarsRover {

  @tailrec
  def receive(current: (Position, Direction), commands: Seq[Command]): (Position, Direction) = {
    commands.length match {
      case 0 => current
      case _ => {
        receive(executeCommand(current, commands.head), commands.tail)
      }
    }
  }

  def executeCommand(current: (Position, Direction), command: Command): (Position, Direction) = {
    command.execute(current)
  }
}

case class Position(x: Int, y: Int)

abstract class Direction() {
  def left(): Direction
  def right(): Direction
  def opposite(): Direction
}

object North extends Direction {
  def left(): Direction = {
    West
  }
  def right(): Direction = {
    East
  }
  override def opposite() = South
}

object West extends Direction {
  def left(): Direction = {
    South
  }
  def right(): Direction = {
    North
  }
  override def opposite() = East
}

object South extends Direction {
  def left(): Direction = {
    East
  }
  def right(): Direction = {
    West
  }
  override def opposite() = North
}

object East extends Direction {
  def left(): Direction = {
    North
  }
  def right(): Direction = {
    South
  }
  override def opposite() = West
}

abstract class Command {
  def opposite(): Command
  def execute(current: (Position, Direction)): (Position, Direction)
}

object Forward extends Command {
  override def opposite() = Backward

  override def execute(current: (Position, Direction)): (Position, Direction) = {
    val (currentPosition, currentDirection) = current
    currentDirection match {
      case East => (Position(currentPosition.x + 1, currentPosition.y), currentDirection)
      case West => (Position(currentPosition.x - 1, currentPosition.y), currentDirection)
      case South => (Position(currentPosition.x, currentPosition.y - 1), currentDirection)
      case North => (Position(currentPosition.x, currentPosition.y + 1), currentDirection)
    }
  }
}
object Backward extends Command {
  override def opposite() = Forward

  override def execute(current: (Position, Direction)): (Position, Direction) = {
    val (currentPosition, currentDirection) = current
    (currentDirection) match {
      case West => (Position(currentPosition.x + 1, currentPosition.y), currentDirection)
      case East => (Position(currentPosition.x - 1, currentPosition.y), currentDirection)
      case North => (Position(currentPosition.x, currentPosition.y - 1), currentDirection)
      case South => (Position(currentPosition.x, currentPosition.y + 1), currentDirection)
    }
  }
}
object Left extends Command {
  override def opposite() = Right

  override def execute(current: (Position, Direction)): (Position, Direction) = (current._1, current._2.left())
}
object Right extends Command {
  override def opposite() = Left

  override def execute(current: (Position, Direction)): (Position, Direction) = (current._1, current._2.right())
}
