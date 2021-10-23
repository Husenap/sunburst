package sunburst.core.input

enum Action:
  // order here is important to match GLFW
  case Release, Press, Repeat

object Mods:
  final val Shift    = 0x01
  final val Control  = 0x02
  final val Alt      = 0x04
  final val Super    = 0x08
  final val CapsLock = 0x10
  final val NumLock  = 0x20

object Key:
  final val KeyUnknown = -1

  /* Printable keys */
  final val KeySpace        = 32
  final val KeyApostrophe   = 39 /* ' */
  final val KeyComma        = 44 /*  */
  final val KeyMinus        = 45 /* - */
  final val KeyPeriod       = 46 /* . */
  final val KeySlash        = 47 /* / */
  final val Key0            = 48
  final val Key1            = 49
  final val Key2            = 50
  final val Key3            = 51
  final val Key4            = 52
  final val Key5            = 53
  final val Key6            = 54
  final val Key7            = 55
  final val Key8            = 56
  final val Key9            = 57
  final val KeySemicolon    = 59 /* ; */
  final val KeyEqual        = 61 /* = */
  final val KeyA            = 65
  final val KeyB            = 66
  final val KeyC            = 67
  final val KeyD            = 68
  final val KeyE            = 69
  final val KeyF            = 70
  final val KeyG            = 71
  final val KeyH            = 72
  final val KeyI            = 73
  final val KeyJ            = 74
  final val KeyK            = 75
  final val KeyL            = 76
  final val KeyM            = 77
  final val KeyN            = 78
  final val KeyO            = 79
  final val KeyP            = 80
  final val KeyQ            = 81
  final val KeyR            = 82
  final val KeyS            = 83
  final val KeyT            = 84
  final val KeyU            = 85
  final val KeyV            = 86
  final val KeyW            = 87
  final val KeyX            = 88
  final val KeyY            = 89
  final val KeyZ            = 90
  final val KeyLeftBracket  = 91 /* [ */
  final val KeyBackslash    = 92 /* \ */
  final val KeyRightBracket = 93 /* ] */
  final val KeyGraveAccent  = 96 /* ` */
  final val KeyWorld1       = 161 /* non-US #1 */
  final val KeyWorld2       = 162 /* non-US #2 */

  /* Function keys */
  final val KeyEscape       = 256
  final val KeyEnter        = 257
  final val KeyTab          = 258
  final val KeyBackspace    = 259
  final val KeyInsert       = 260
  final val KeyDelete       = 261
  final val KeyRight        = 262
  final val KeyLeft         = 263
  final val KeyDown         = 264
  final val KeyUp           = 265
  final val KeyPageUp       = 266
  final val KeyPageDown     = 267
  final val KeyHome         = 268
  final val KeyEnd          = 269
  final val KeyCapsLock     = 280
  final val KeyScrollLock   = 281
  final val KeyNumLock      = 282
  final val KeyPrintScreen  = 283
  final val KeyPause        = 284
  final val KeyF1           = 290
  final val KeyF2           = 291
  final val KeyF3           = 292
  final val KeyF4           = 293
  final val KeyF5           = 294
  final val KeyF6           = 295
  final val KeyF7           = 296
  final val KeyF8           = 297
  final val KeyF9           = 298
  final val KeyF10          = 299
  final val KeyF11          = 300
  final val KeyF12          = 301
  final val KeyF13          = 302
  final val KeyF14          = 303
  final val KeyF15          = 304
  final val KeyF16          = 305
  final val KeyF17          = 306
  final val KeyF18          = 307
  final val KeyF19          = 308
  final val KeyF20          = 309
  final val KeyF21          = 310
  final val KeyF22          = 311
  final val KeyF23          = 312
  final val KeyF24          = 313
  final val KeyF25          = 314
  final val KeyKP0          = 320
  final val KeyKP1          = 321
  final val KeyKP2          = 322
  final val KeyKP3          = 323
  final val KeyKP4          = 324
  final val KeyKP5          = 325
  final val KeyKP6          = 326
  final val KeyKP7          = 327
  final val KeyKP8          = 328
  final val KeyKP9          = 329
  final val KeyKPDecimal    = 330
  final val KeyKPDivide     = 331
  final val KeyKPMultiply   = 332
  final val KeyKPSubtract   = 333
  final val KeyKPAdd        = 334
  final val KeyKPEnter      = 335
  final val KeyKPEqual      = 336
  final val KeyLeftShift    = 340
  final val KeyLeftControl  = 341
  final val KeyLeftAlt      = 342
  final val KeyLeftSuper    = 343
  final val KeyRightShift   = 344
  final val KeyRightControl = 345
  final val KeyRightAlt     = 346
  final val KeyRightSuper   = 347
  final val KeyMenu         = 348
  final val KeyLast         = KeyMenu

object MouseButton:
  final val MouseButton1      = 0
  final val MouseButton2      = 1
  final val MouseButton3      = 2
  final val MouseButton4      = 3
  final val MouseButton5      = 4
  final val MouseButton6      = 5
  final val MouseButton7      = 6
  final val MouseButton8      = 7
  final val MouseButtonLeft   = MouseButton1
  final val MouseButtonRight  = MouseButton2
  final val MouseButtonMiddle = MouseButton3
  final val MouseButtonLast   = MouseButton8
