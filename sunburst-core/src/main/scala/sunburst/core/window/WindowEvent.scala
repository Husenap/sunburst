package sunburst.core.window

import sunburst.core.input.Action

enum WindowEvent:
  case Resize(width: Int, height: Int)
  case ContentScale(scaleX: Float, scaleY: Float)
  case Key(key: Int, scancode: Int, action: Action, mods: Int)
  case KeyPress(key: Int, scancode: Int, mods: Int)
  case KeyRelease(key: Int, scancode: Int, mods: Int)
  case KeyRepeat(key: Int, scancode: Int, mods: Int)
  case Char(codepoint: scala.Char)
  case CursorPos(posX: Double, posY: Double)
  case CursorEnter
  case CursorLeave
  case MouseButton(button: Int, action: Action, mods: Int)
  case MouseButtonPress(button: Int, mods: Int)
  case MouseButtonRelease(button: Int, mods: Int)
  case Scroll(offsetX: Double, offsetY: Double)
  case DroppedFile(path: String)
