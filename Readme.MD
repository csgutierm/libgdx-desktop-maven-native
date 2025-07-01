# intro
This is a simplified test of a native libgdx app
Desktop version using maven

# native compilation
mvn -Pnative native:compile

# using -ob
Total time:  36.419 s

# without -ob
Aprox. 58s


# remover warnings
--enable-native-access=ALL-UNNAMED


# no iniciar consola

src\main\resources\META-INF\native-image\com.csgm\game

Crear archivo 

native-image.properties

contenido
Args = -H:NativeLinkerOption=/SUBSYSTEM:WINDOWS -H:NativeLinkerOption=/ENTRY:mainCRTStartup
