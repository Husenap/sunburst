package sunburst.core.window

enum Action:
  // Order is important here, it matches the order in GLFW
  case Release, Press, Repeat

enum Button:
  // Order is important here, it matches the order in GLFW
  case Left, Right, Middle, MouseButton4, MouseButton5, MouseButton6,
  MouseButton7, MouseButton8

type Mods = Int

enum WindowEvent:
  case Resize(width: Int, height: Int)
  case ContentScale(scaleX: Float, scaleY: Float)
  case Key(key: Int, scancode: Int, action: Action, mods: Mods)
  case KeyPress(key: Int, scancode: Int, mods: Mods)
  case KeyRelease(key: Int, scancode: Int, mods: Mods)
  case KeyRepeat(key: Int, scancode: Int, mods: Mods)
  case Char(codepoint: scala.Char)
  case CursorPos(posX: Double, posY: Double)
  case CursorEnter
  case CursorLeave
  case MouseButton(button: Button, action: Action, mods: Mods)
  case MouseButtonPress(button: Button, mods: Mods)
  case MouseButtonRelease(button: Button, mods: Mods)
  case Scroll(offsetX: Double, offsetY: Double)
  case DroppedFile(path: String)
