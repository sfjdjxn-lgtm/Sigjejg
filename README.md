# Plebys

Esqueleto de un cliente de utilidades para Minecraft, construido desde cero
sobre **Fabric** para **1.21.5**, con arquitectura inspirada en Meteor Client
(sistema de módulos + mixins + event bus) pero simplificada para que la
entiendas y extiendas vos mismo, en vez de heredar 5 años de deuda técnica.

## Requisitos

- JDK 21
- Gradle (se descarga solo la primera vez vía wrapper, o usá tu Gradle local ≥ 8.12)
- Conexión a internet la primera vez (para bajar Minecraft, mapeos Yarn y Fabric API)

## Cómo correrlo

```bash
./gradlew genSources   # opcional, para tener el código de Minecraft legible en tu IDE
./gradlew runClient    # levanta un cliente de Minecraft con el mod cargado
```

> Nota: este proyecto no incluye el `gradle-wrapper.jar` binario. Si tu
> máquina no tiene Gradle instalado, corré `gradle wrapper --gradle-version 8.12`
> una vez para generarlo, o abrí el proyecto directo en IntelliJ IDEA con el
> plugin de Gradle (lo detecta solo).

## Cómo probar que anda

Una vez adentro del juego, abrí el chat y escribí:

```
.plebys sprint
.plebys fullbright
```

Vas a ver el módulo aparecer en la lista de la esquina superior derecha
mientras esté activo, e desaparecer al desactivarlo.

## Arquitectura

```
com.plebys.client
├── Plebys.java         # entrypoint, arranca todo
├── NovaCommands.java       # comandos locales .plebys <modulo>
├── events/
│   ├── EventBus.java       # pub/sub central
│   ├── TickEvent.java
│   └── Render2DEvent.java
├── modules/
│   ├── Module.java         # clase base de toda función
│   ├── ModuleManager.java  # registro central — acá agregás módulos nuevos
│   ├── Setting.java        # valores configurables por módulo
│   ├── movement/SprintModule.java
│   └── render/FullbrightModule.java
├── mixin/
│   ├── MinecraftClientMixin.java     # dispara TickEvent
│   └── ClientPlayerEntityMixin.java  # gancho listo para módulos de movimiento
└── gui/
    └── ModuleListHud.java  # lista de módulos activos en pantalla
```

### Por qué esta arquitectura y no copiar Meteor tal cual

- **Suscripción bajo demanda**: un `Module` solo se suscribe al `EventBus`
  mientras está `enabled`. Meteor mantiene todos los módulos siempre
  escuchando y filtra internamente — con pocos módulos no importa, pero
  a medida que sumes decenas te conviene este patrón.
- **EventBus propio y simple** en vez de depender de una librería externa
  de eventos: menos peso, más fácil de debuggear, y lo entendés completo
  vos mismo.
- **Comandos de chat antes que GUI gráfica**: podés probar cada módulo
  nuevo en segundos sin tener que programar primero un ClickGUI entero
  (que es, de lejos, la parte más larga de construir). El HUD y los
  comandos ya te dan feedback visual real.

## Roadmap sugerido (en orden de impacto)

1. **Sistema de Settings visible**: hoy `Setting<T>` existe pero nada lo
   renderiza. Conviene resolver esto antes de la GUI gráfica.
2. **ClickGUI real** (ventana arrastrable con categorías) — recién ahí tiene
   sentido invertir en esto, una vez que tengas 10+ módulos.
3. **Sistema de keybinds** por módulo (hoy `Module.keyBind` existe pero no
   está conectado a nada — hay que engancharlo a `KeyBindingHelper` de
   Fabric API).
4. **Módulos de combate** (Killaura, etc.) y de **render** (ESP, Nametags,
   Tracers) — son los más pedidos, pero requieren entender bien el pipeline
   de renderizado 3D (`WorldRenderEvents` de Fabric API).
5. **Sistema de perfiles/config** guardado en disco (JSON con Gson, que ya
   viene con Minecraft).
6. **Soporte multi-versión**: la forma estándar es tener un branch de
   Gradle por rango de versiones (1.21.x, 1.20.x) o usar una herramienta
   como Stonecutter, que preprocesa el mismo código fuente para varias
   versiones de Minecraft a la vez. Te lo recomiendo una vez que el core
   esté estable en 1.21.5 — hacerlo antes solo te va a duplicar trabajo.

## Aviso importante

Muchos servidores de Minecraft prohíben este tipo de clientes en su
multijugador (fullbright y sprint automático suelen tolerarse, pero
cosas como Killaura o X-Ray casi siempre violan las reglas y pueden
resultar en ban). Vos sos responsable de cómo lo usás.
