package sunburst.core.graphics

import sunburst.core.math.*
import sunburst.core.input.InputManager
import sunburst.core.input.Key
import sunburst.core.input.MouseButton

case class CameraOptions(
    speed: Float = 1f,
    rotationSpeed: Float = 1f
)

class Camera(cameraOptions: CameraOptions = CameraOptions()):
  var position = Vec3.Zero
  var rotation = Quaternion.Identity

  def update(dt: Float, inputManager: InputManager): Unit =
    val right   = rotation * Vec3.Right
    val up      = rotation * Vec3.Up
    val forward = rotation * Vec3.Forward

    val velocity        = cameraOptions.speed
    val angularVelocity = cameraOptions.rotationSpeed

    if inputManager.isKeyDown(Key.KeyR) then position += forward * dt * velocity
    if inputManager.isKeyDown(Key.KeyH) then position -= forward * dt * velocity
    if inputManager.isKeyDown(Key.KeyT) then position += right * dt * velocity
    if inputManager.isKeyDown(Key.KeyS) then position -= right * dt * velocity
    if inputManager.isKeyDown(Key.KeyW) then position += up * dt * velocity
    if inputManager.isKeyDown(Key.KeyD) then position -= up * dt * velocity

    if inputManager.isKeyDown(Key.KeyE) then
      rotation *= Quaternion.fromAxisAndAngle(Vec3.Right, dt * angularVelocity)
    if inputManager.isKeyDown(Key.KeyU) then
      rotation *= Quaternion.fromAxisAndAngle(Vec3.Right, -dt * angularVelocity)
    if inputManager.isKeyDown(Key.KeyO) then
      rotation *= Quaternion.fromAxisAndAngle(Vec3.Up, dt * angularVelocity)
    if inputManager.isKeyDown(Key.KeyN) then
      rotation *= Quaternion.fromAxisAndAngle(Vec3.Up, -dt * angularVelocity)
    if inputManager.isKeyDown(Key.KeyF) then
      rotation *= Quaternion.fromAxisAndAngle(
        Vec3.Forward,
        dt * angularVelocity
      )
    if inputManager.isKeyDown(Key.KeyP) then
      rotation *= Quaternion.fromAxisAndAngle(
        Vec3.Forward,
        -dt * angularVelocity
      )

  def viewMatrix: Mat4 =
    (rotation.rotationMatrix * Mat4.translation(position)).inverse
