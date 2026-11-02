Run chmod +x gradlew && ./gradlew build

Welcome to Gradle 9.8.2!

Here are the highlights of this release:
 - Java 27 support
 - Maven mirror settings reuse
 - Linked problem locations in build output

For more details see https://docs.gradle.org/9.8.2/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)
Calculating task graph as no cached configuration is available for tasks: build

> Configure project :
Fabric Loom: 1.19.3

> Task :processIncludeJars
> Task :processClientResources
> Task :processTestResources NO-SOURCE
> Task :validateAccessWidener NO-SOURCE
> Task :sourcesJar
> Task :processResources
> Task :compileJava
> Task :classes

> Task :compileClientJava FAILED
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:12: error: package org.lwjgl.glfw does not exist
import org.lwjgl.glfw.GLFW;
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:30: error: cannot find symbol
    public static int menuBind = GLFW.GLFW_KEY_RIGHT_SHIFT;

                                 ^
[Incubating] Problems report is available at: file:///home/runner/work/Tuftu-Visuals/Tuftu-Visuals/build/reports/problems/problems-report.html
  symbol:   variable GLFW
  location: class ExampleModClient
6 actionable tasks: 6 executed
Configuration cache entry stored.
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:31: error: cannot find symbol
    public static int pearlBind = GLFW.GLFW_KEY_Z;
                                  ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:32: error: cannot find symbol
    public static int visualsBind = GLFW.GLFW_KEY_V;
                                    ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:58: error: cannot find symbol
        long window = GLFW.glfwGetCurrentContext();
                      ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:59: error: cannot find symbol
        if (window == 0L || mc.screen != null) return;
                              ^
  symbol:   variable screen
  location: variable mc of type Minecraft
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:63: error: cannot find symbol
            mc.setScreen(new TuftuMenuScreen());
              ^
  symbol:   method setScreen(TuftuMenuScreen)
  location: variable mc of type Minecraft
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:73: error: cannot find symbol
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fВизуалы меча: " + (swordVisuals ? "§aВКЛ" : "§cВЫКЛ")), false);
                  ^
  symbol:   method setOverlayMessage(MutableComponent,boolean)
  location: variable gui of type Gui
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:77: error: cannot find symbol
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fAutoSprint: " + (autoSprint ? "§aВКЛ" : "§cВЫКЛ")), false);
                  ^
  symbol:   method setOverlayMessage(MutableComponent,boolean)
  location: variable gui of type Gui
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:81: error: cannot find symbol
            mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fFullBright: " + (fullBright ? "§aВКЛ" : "§cВЫКЛ")), false);
                  ^
  symbol:   method setOverlayMessage(MutableComponent,boolean)
  location: variable gui of type Gui
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:89: error: cannot find symbol
            down = GLFW.glfwGetMouseButton(window, -key) == GLFW.GLFW_PRESS;
                   ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:89: error: cannot find symbol
            down = GLFW.glfwGetMouseButton(window, -key) == GLFW.GLFW_PRESS;
                                                            ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:91: error: cannot find symbol
            down = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
                   ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:91: error: cannot find symbol
            down = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
                                                   ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:104: error: method swing in class LivingEntity cannot be applied to given types;
                mc.player.swing(InteractionHand.MAIN_HAND);
                         ^
  required: InteractionHand,SwingAnimation,boolean
  found:    InteractionHand
  reason: actual and formal argument lists differ in length
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:106: error: cannot find symbol
                mc.gui.setOverlayMessage(Component.literal("§d[Tuftu Visuals] §fЭндер-Перл брошен!"), false);
                      ^
  symbol:   method setOverlayMessage(MutableComponent,boolean)
  location: variable gui of type Gui
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:110: error: cannot find symbol
        mc.gui.setOverlayMessage(Component.literal("§c[Tuftu Visuals] Нет Эндер-Перла в хотбаре!"), false);
              ^
  symbol:   method setOverlayMessage(MutableComponent,boolean)
  location: variable gui of type Gui
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:146: error: package GLFW does not exist
            case GLFW.GLFW_KEY_RIGHT_SHIFT: return "§bR-SHIFT";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:147: error: package GLFW does not exist
            case GLFW.GLFW_KEY_LEFT_SHIFT: return "§bL-SHIFT";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:148: error: package GLFW does not exist
            case GLFW.GLFW_KEY_LEFT_CONTROL: return "§bL-CTRL";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:149: error: package GLFW does not exist
            case GLFW.GLFW_KEY_RIGHT_CONTROL: return "§bR-CTRL";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:150: error: package GLFW does not exist
            case GLFW.GLFW_KEY_LEFT_ALT: return "§bL-ALT";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:151: error: package GLFW does not exist
            case GLFW.GLFW_KEY_SPACE: return "§bSPACE";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:152: error: package GLFW does not exist
            case GLFW.GLFW_KEY_TAB: return "§bTAB";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:153: error: package GLFW does not exist
            case GLFW.GLFW_KEY_CAPS_LOCK: return "§bCAPS";
                     ^
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:155: error: cannot find symbol
                String name = GLFW.glfwGetKeyName(key, 0);
                              ^
  symbol:   variable GLFW
  location: class ExampleModClient
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:247: error: cannot find symbol
                    btn -> Minecraft.getInstance().setScreen(null)
                                                  ^
  symbol:   method setScreen(<null>)
  location: class Minecraft
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:259: error: cannot find symbol
            long window = GLFW.glfwGetCurrentContext();
                          ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                                        ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                                                                 ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                                        ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                                                                 ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:273: error: cannot find symbol
                if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                    ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:273: error: cannot find symbol
                if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                                                    ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:284: error: cannot find symbol
                if (GLFW.glfwGetMouseButton(window, m) == GLFW.GLFW_PRESS) {
                    ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
/home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:284: error: cannot find symbol
                if (GLFW.glfwGetMouseButton(window, m) == GLFW.GLFW_PRESS) {
                                                          ^
  symbol:   variable GLFW
  location: class TuftuMenuScreen
38 errors

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':compileClientJava' (registered by plugin class 'org.gradle.api.plugins.JavaBasePlugin').
> Compilation failed; see the compiler output below.
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:12: error: package org.lwjgl.glfw does not exist
  import org.lwjgl.glfw.GLFW;
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:146: error: package GLFW does not exist
              case GLFW.GLFW_KEY_RIGHT_SHIFT: return "§bR-SHIFT";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:147: error: package GLFW does not exist
              case GLFW.GLFW_KEY_LEFT_SHIFT: return "§bL-SHIFT";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:148: error: package GLFW does not exist
              case GLFW.GLFW_KEY_LEFT_CONTROL: return "§bL-CTRL";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:149: error: package GLFW does not exist
              case GLFW.GLFW_KEY_RIGHT_CONTROL: return "§bR-CTRL";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:150: error: package GLFW does not exist
              case GLFW.GLFW_KEY_LEFT_ALT: return "§bL-ALT";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:151: error: package GLFW does not exist
              case GLFW.GLFW_KEY_SPACE: return "§bSPACE";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:152: error: package GLFW does not exist
              case GLFW.GLFW_KEY_TAB: return "§bTAB";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:153: error: package GLFW does not exist
              case GLFW.GLFW_KEY_CAPS_LOCK: return "§bCAPS";
                       ^
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:30: error: cannot find symbol
      public static int menuBind = GLFW.GLFW_KEY_RIGHT_SHIFT;
                                   ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:31: error: cannot find symbol
      public static int pearlBind = GLFW.GLFW_KEY_Z;
                                    ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:32: error: cannot find symbol
      public static int visualsBind = GLFW.GLFW_KEY_V;
                                      ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:58: error: cannot find symbol
          long window = GLFW.glfwGetCurrentContext();
                        ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:59: error: cannot find symbol
          if (window == 0L || mc.screen != null) return;
                                ^
    symbol:   variable screen
    location: variable mc of type Minecraft
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:89: error: cannot find symbol
              down = GLFW.glfwGetMouseButton(window, -key) == GLFW.GLFW_PRESS;
                     ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:89: error: cannot find symbol
              down = GLFW.glfwGetMouseButton(window, -key) == GLFW.GLFW_PRESS;
                                                              ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:91: error: cannot find symbol
              down = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
                     ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:91: error: cannot find symbol
              down = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
                                                     ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:155: error: cannot find symbol
                  String name = GLFW.glfwGetKeyName(key, 0);
                                ^
    symbol:   variable GLFW
    location: class ExampleModClient
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:259: error: cannot find symbol
              long window = GLFW.glfwGetCurrentContext();
                            ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
              if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                                          ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
              if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                  ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:263: error: cannot find symbol
              if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS ||
                                                                   ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                  GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                                          ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                  GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                  ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:264: error: cannot find symbol
                  GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DELETE) == GLFW.GLFW_PRESS) {
                                                                   ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:273: error: cannot find symbol
                  if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                      ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:273: error: cannot find symbol
                  if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                                                      ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:284: error: cannot find symbol
                  if (GLFW.glfwGetMouseButton(window, m) == GLFW.GLFW_PRESS) {
                      ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:284: error: cannot find symbol
                  if (GLFW.glfwGetMouseButton(window, m) == GLFW.GLFW_PRESS) {
                                                            ^
    symbol:   variable GLFW
    location: class TuftuMenuScreen
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:63: error: cannot find symbol
              mc.setScreen(new TuftuMenuScreen());
                ^
    symbol:   method setScreen(TuftuMenuScreen)
    location: variable mc of type Minecraft
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:73: error: cannot find symbol
              mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fВизуалы меча: " + (swordVisuals ? "§aВКЛ" : "§cВЫКЛ")), false);
                    ^
    symbol:   method setOverlayMessage(MutableComponent,boolean)
    location: variable gui of type Gui
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:77: error: cannot find symbol
              mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fAutoSprint: " + (autoSprint ? "§aВКЛ" : "§cВЫКЛ")), false);
                    ^
    symbol:   method setOverlayMessage(MutableComponent,boolean)
    location: variable gui of type Gui
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:81: error: cannot find symbol
              mc.gui.setOverlayMessage(Component.literal("§b[Tuftu Visuals] §fFullBright: " + (fullBright ? "§aВКЛ" : "§cВЫКЛ")), false);
                    ^
    symbol:   method setOverlayMessage(MutableComponent,boolean)
    location: variable gui of type Gui
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:106: error: cannot find symbol
                  mc.gui.setOverlayMessage(Component.literal("§d[Tuftu Visuals] §fЭндер-Перл брошен!"), false);
                        ^
    symbol:   method setOverlayMessage(MutableComponent,boolean)
    location: variable gui of type Gui
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:110: error: cannot find symbol
          mc.gui.setOverlayMessage(Component.literal("§c[Tuftu Visuals] Нет Эндер-Перла в хотбаре!"), false);
                ^
    symbol:   method setOverlayMessage(MutableComponent,boolean)
    location: variable gui of type Gui
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:247: error: cannot find symbol
                      btn -> Minecraft.getInstance().setScreen(null)
                                                    ^
    symbol:   method setScreen(<null>)
    location: class Minecraft
  /home/runner/work/Tuftu-Visuals/Tuftu-Visuals/src/client/java/com/example/client/ExampleModClient.java:104: error: method swing in class LivingEntity cannot be applied to given types;
                  mc.player.swing(InteractionHand.MAIN_HAND);
                           ^
    required: InteractionHand,SwingAnimation,boolean
    found:    InteractionHand
    reason: actual and formal argument lists differ in length
  38 errors

* Try:
> Check your code and dependencies to fix the compilation error(s)
> Run with --scan to get full insights from a Build Scan (powered by Develocity).

BUILD FAILED in 13s
Error: Process completed with exit code 1.
