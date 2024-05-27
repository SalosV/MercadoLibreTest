### Visión General de la Arquitectura

![arquitectura](figure/figure1.png)

- Cada capa sigue un [flujo de eventos/datos unidireccional](https://developer.android.com/topic/architecture/ui-layer#udf); la capa de interfaz de usuario (UI) emite eventos de usuario a la capa de datos, y la capa de datos expone los datos como un flujo a otras capas.
- La capa de datos está diseñada para funcionar independientemente de otras capas y debe ser pura, lo que significa que no tiene dependencias en las otras capas.

Con esta arquitectura de acoplamiento flexible, se puede aumentar la reutilización de componentes y la escalabilidad de la aplicación.

### Capa de UI

![arquitectura](figure/figure2.png)

La capa de UI consiste en elementos de interfaz de usuario para configurar pantallas que pueden interactuar con los usuarios y en el [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) que mantiene los estados de la aplicación y restaura los datos cuando hay cambios de configuración.
- Los elementos de UI observan el flujo de datos a través de [DataBinding](https://developer.android.com/topic/libraries/data-binding), que es la parte más esencial de la arquitectura MVVM.
- Con [Bindables](https://github.com/skydoves/bindables), que es un kit de DataBinding para Android que notifica cambios de datos, se puede implementar la vinculación bidireccional y la observación de datos en XML de manera muy limpia.

### Capa de Datos

![arquitectura](figure/figure3.png)

La capa de datos consiste en repositorios, que incluyen lógica de negocio, como consultar datos de la base de datos local y solicitar datos remotos de la red. Se implementa como una fuente de lógica de negocio "offline-first" y sigue el principio de [fuente única de verdad](https://es.wikipedia.org/wiki/Fuente_%C3%BAnica_de_verdad).<br>

**Pokedex** es una aplicación "offline-first", lo que significa que es capaz de realizar todas, o un subconjunto crítico de sus funcionalidades principales sin acceso a Internet. Así, los usuarios no necesitan estar actualizando los recursos de la red todo el tiempo, lo que disminuye el consumo de datos. Para más información, puedes consultar [Construir una aplicación offline-first](https://developer.android.com/topic/architecture/data-layer/offline-first).

## Modularización

![arquitectura](figure/figure4.png)

**Pokedex** adoptó las siguientes estrategias de modularización:

- **Reutilización**: Modularizar adecuadamente el código reutilizable permite oportunidades para compartir código y, al mismo tiempo, limita el acceso al código en otros módulos.
- **Construcción en Paralelo**: Cada módulo puede ejecutarse en paralelo, lo que reduce el tiempo de construcción.
- **Control estricto de visibilidad**: Los módulos restringen la exposición de componentes dedicados y el acceso a otras capas, evitando así que se utilicen fuera del módulo.
- **Enfoque descentralizado**: Cada equipo de desarrollo puede asignar su módulo dedicado y centrarse en sus propios módulos.

Para más información, consulta la [Guía de modularización de aplicaciones Android](https://developer.android.com/topic/modularization).
