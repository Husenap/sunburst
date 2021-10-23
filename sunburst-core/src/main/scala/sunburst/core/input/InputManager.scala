package sunburst.core.input

import sunburst.core.window.Window
import sunburst.core.window.WindowEvent

class InputManager private:
  private val keys         = Array.fill(Key.KeyLast + 1)(false)
  private val mouseButtons = Array.fill(MouseButton.MouseButtonLast + 1)(false)
  private var mods         = 0

  def isKeyDown(key: Int): Boolean       = keys(key)
  def isButtonDown(button: Int): Boolean = mouseButtons(button)
  def isModDown(mod: Int): Boolean       = (mods & mod) != 0

object InputManager:
  def apply(window: Window): InputManager =
    val inputManager = new InputManager()

    window.onEvent {
      case WindowEvent.Key(key, _, action, mods)         =>
        inputManager.mods = mods
        inputManager.keys(key) = action != Action.Release
      case WindowEvent.MouseButton(button, action, mods) =>
        inputManager.mods = mods
        inputManager.mouseButtons(button) = action != Action.Release
      case _                                             =>
    }

    inputManager
