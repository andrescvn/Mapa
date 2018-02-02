# Mapa
Se crea un nuevo proyecto eligiendo la opcion de mapa
una vez dentro en el metodo on create se ponen las siguientes lineas de codigo para comprobar que el mapa esta listo
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
Que buscara un metodo anteriormente creado llamado onMapReady donde se inicializara el mapa
El siguiente metodo importante es el locationListener que recogera la posicion actual y con la cual se calculara la distancia a la que estas de los objetos que tenemos que buscar
el siguiente paso sera añadir la libreria para la lectura del QR con esta linea de codigo dentro del gradle 
 compile 'me.dm7.barcodescanner:zxing:1.9.8'
Una vez creado mediante un fragment se relacionan 2 activitys diferentes la 1 con el mapa y la 2 con el lector del QR el cual reenviara a la activity principal el texto del QR y trabajara con el 
