/*Creo la conexion con la DB y relizo operacion CRUDO
 para estudante*/
package DatosEstuduante_Operacion_CRUDO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
// para insertar y actualizar
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.Password;

public class Estudiante extends Persona {
    //URL de la base de datos, nombre de usuario y contraseña para JDBC

    static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    static final String URL_BASEDATOS = "jdbc:mysql://localhost/universidad";
    static final String NOMBREUSUARIO = "admindb";
    static final String CONTRASENIA = "database";

    // creo el constructor de esta clase
    public Estudiante() {
        //envio valores al costructor de la place padre de estudiante 
        super(" ", " ", " ", " ", " ");
    }

    // implemento los metodos abstrac heredados de Persona 
    @Override
    public void ingresarDatosEstudiante() {
        String nombre;
        String apellido;
        String cedula;
        String celular;
        String correo;

        Scanner lector = new Scanner(System.in);
        System.out.println("nombre: ");
        nombre = lector.next();
        System.out.println("apellido: ");
        apellido = lector.next();
        System.out.println("cedula: ");
        cedula = lector.next();
        System.out.println("celular: ");
        celular = lector.next();
        System.out.println("correo: ");
        correo = lector.next();

        //envio los valores a la clase persona a validar, ya que estudiante eredo dodo de ella       
        establecerNombre(nombre);
        establecerApellido(apellido);
        establecerCedula(cedula);
        establecerCelular(celular);
        establecerCorreoElectronico(correo);

        // evito datos en blanco en el ingreso de datos
        if ((obtenerNombre() != " ") && (obtenerApellido() != " ") && (obtenerCedula() != " ")
                && (obtenerCelular() != " ") && (obtenerCorreoElectronico() != " ")) {

            String consulta = "insert into estudiante (nombre,apellido,cedula,celular,correo)values (" + "'" + obtenerNombre() + "'"
                    + "," + "'" + obtenerApellido() + "'" + "," + "'" + obtenerCedula() + "'" + "," + "'" + obtenerCelular() + "'" + "," + "'" + obtenerCorreoElectronico() + "'" + ");";

            Connection conexion = null;//maneja la conexion

            // se crea una variable tipo PreparedStatement para la insercion de datos
            PreparedStatement stmt;

            try {
                // carga la clase CONTROLADOR
                Class.forName(CONTROLADOR);
                // establesco la conexion ala base de datos
                conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

                // consulto la base de datos
                //se usa la variable conexion llamando al metodo prepareStatement 
                //y envia parametro de la sentencia
                stmt = conexion.prepareStatement(consulta);
                stmt.executeUpdate();

                System.out.println("Datos Guardados");
                conexion.close();
                stmt.close();
            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }
        } else {
            System.out.println("Datos Incorrectos Intenta de nuevo");
            ingresarDatosEstudiante();
        }
    }

    @Override
    public void ingresarDatosMateria() {
        String ciclo;
        String materia;
        Scanner lector = new Scanner(System.in);
        System.out.println("Formato de ingresar Ciclo: tercero,cuarto etc");
        ciclo = lector.next();
        lector.nextLine();
        System.out.println("Materia:");
        materia = lector.nextLine();
        //lector.nextLine();

        try {
            if (((ciclo != " ") && (ciclo != null)) && ((materia != " " && (materia != null)))) {

                Connection conexion = null;
                PreparedStatement stmt;
                // carga la clase CONTROLADOR
                Class.forName(CONTROLADOR);
                // establesco la conexion ala base de datos
                conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

                // consulto la base de datos

                String consulta = "INSERT INTO materias(ciclo,nombreMateria)VALUES(" + "'"
                        + ciclo + "'" + "," + "'" + materia + "'" + ");";

                stmt = conexion.prepareStatement(consulta);// envio como parametro la consulta              
                stmt.executeUpdate();
                System.out.println("Datos Guardados");

                // cierro 
                conexion.close();
                stmt.close();
            } else {
                System.out.println("Datos Incorrectos Intenta de nuevo");
                ingresarDatosMateria();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mostrarDatosEstudianteMaterias() {
        System.out.println("\t\tLISTA DE LOS ALUMNOS CON SUS MATERIAS");
        Connection conexion = null;
        Statement instruccion = null;
        ResultSet conjuntodatos = null;

        try {
            String consulta = "SELECT  idrelacionestudiantemateria.IdEstudiante, nombre, apellido, ciclo, nombreMateria FROM estudiante "
                    + "INNER JOIN idrelacionestudiantemateria "
                    + "INNER JOIN materias ON estudiante.idEstudiante = idrelacionestudiantemateria.idEstudiante "
                    + "AND materias.idMaterias = idrelacionestudiantemateria.idMateria";
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

            instruccion = conexion.createStatement();
            conjuntodatos = instruccion.executeQuery(consulta);

            ResultSetMetaData metadatos = conjuntodatos.getMetaData();

            int numColumnas = metadatos.getColumnCount();

            for (int i = 1; i <= numColumnas; i++) {
                System.out.printf("%-8s\t", metadatos.getColumnName(i));
            }
            System.out.println();

            // imprimo los valores de la tabla
            while (conjuntodatos.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    System.out.printf("%-8s\t", conjuntodatos.getObject(i));
                }
                System.out.println();
            }

            //  ESTUDIANTES Y MATERIAS MOSTRADOS SEGUN EL ID INGRESADO
            System.out.println("\n\n\n Desea Buscar algun estudiante en concreto y/n");
            Scanner lector = new Scanner(System.in);
            String rstEstBus = lector.next();
            while (rstEstBus.equalsIgnoreCase("y")) {
                int buskedaIdEs = 0;
                System.out.println("\nIngrese el idEstudiante");
                buskedaIdEs = lector.nextInt();

                consulta = "SELECT  idrelacionestudiantemateria.IdEstudiante, nombre, apellido, ciclo, nombreMateria FROM estudiante "
                        + "INNER JOIN idrelacionestudiantemateria "
                        + "INNER JOIN materias ON estudiante.idEstudiante = idrelacionestudiantemateria.idEstudiante "
                        + "AND materias.idMaterias = idrelacionestudiantemateria.idMateria WHERE "
                        + "estudiante.IdEstudiante = " + buskedaIdEs + " ;";

                instruccion = conexion.createStatement();
                conjuntodatos = instruccion.executeQuery(consulta);

                metadatos = conjuntodatos.getMetaData();

                numColumnas = metadatos.getColumnCount();

                for (int i = 1; i <= numColumnas; i++) {
                    System.out.printf("%-8s\t", metadatos.getColumnName(i));
                }
                System.out.println();

                // imprimo los valores de la tabla
                while (conjuntodatos.next()) {
                    for (int i = 1; i <= numColumnas; i++) {
                        System.out.printf("%-8s\t", conjuntodatos.getObject(i));
                    }
                    System.out.println();
                }
                
                // pregunta pa otra consulta
                System.out.println("\n\n\n Desea Buscar otro estudiante en concreto y/n");
                rstEstBus = lector.next();
            }


            conexion.close();
            instruccion.close();
            conjuntodatos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarDatosEstudiante() {
        Scanner lector = new Scanner(System.in);
        Connection conexion = null;
        Statement instruccion = null;
        ResultSet conjuntodatos = null;

        PreparedStatement stmt = null;

        String consulta = "SELECT nombre, apellido, cedula,celular,correo FROM estudiante;";

        try {
            //cargo el controlador
            Class.forName(CONTROLADOR);
            // establesco la conexion
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            // creo obj statament para consultar
            instruccion = conexion.createStatement();
            // hago la consulata
            conjuntodatos = instruccion.executeQuery(consulta);

            // proceso los resultados 
            ResultSetMetaData metadatos = conjuntodatos.getMetaData();

            // sako los titulos par que escoga el usuario el campo a actualizar 
            int numColumnas = metadatos.getColumnCount();

            System.out.println("Ingrese el numearl del campo deseado a actualizar:");
            // imprimo los titulos
            for (int i = 1; i <= numColumnas; i++) {
                System.out.printf("%d. %s\n", i, metadatos.getColumnName(i));
            }
            instruccion.close();
            conjuntodatos.close();

            //implemento la actualicacion de los datos
            int literalEst, idEstu;
            literalEst = lector.nextInt();
            String nombre, apellido, cedula, celular, correo;
            if ((literalEst > 0) && (literalEst <= numColumnas)) {
                System.out.println("Ingresa el IdEstudiante:");
                idEstu = lector.nextInt();
                switch (literalEst) {
                    case 1:
                        if ((idEstu > 0) && (idEstu <= numColumnas)) {
                            System.out.println("Ingresa el nombre:");
                            nombre = lector.next();
                            establecerNombre(nombre);
                            if ((obtenerNombre() != " ") && (obtenerNombre() != null)) {
                                consulta = "UPDATE estudiante SET nombre = '" + nombre
                                        + "' WHERE IdEstudiante = " + idEstu + ";";
                            }
                        } else {
                            System.out.println("Datos Incorrectos");
                            actualizarDatosEstudiante();
                        }
                        break;
                    case 2:
                        if ((idEstu > 0) && (idEstu <= numColumnas)) {
                            System.out.println("Ingresa el apellido:");
                            apellido = lector.next();
                            establecerApellido(apellido);
                            if ((obtenerApellido() != " ") && (obtenerApellido() != null)) {
                                consulta = "UPDATE estudiante SET apellido = '" + apellido
                                        + "' WHERE IdEstudiante = " + idEstu + ";";
                            }
                        } else {
                            System.out.println("Datos Incorrectos");
                            actualizarDatosEstudiante();
                        }
                        break;
                    case 3:
                        if ((idEstu > 0) && (idEstu <= numColumnas)) {
                            System.out.println("Ingresa la cedula:");
                            cedula = lector.next();
                            establecerCedula(cedula);
                            if ((obtenerCedula() != " ") && (obtenerCedula() != null)) {
                                consulta = "UPDATE estudiante SET cedula = '" + cedula
                                        + "' WHERE IdEstudiante = " + idEstu + ";";
                            }
                        } else {
                            System.out.println("Datos Incorrectos");
                            actualizarDatosEstudiante();
                        }
                        break;
                    case 4:
                        if ((idEstu > 0) && (idEstu <= numColumnas)) {
                            System.out.println("Ingresa el celular:");
                            celular = lector.next();
                            establecerCelular(celular);
                            if ((obtenerCelular() != " ") && (obtenerCelular() != null)) {
                                consulta = "UPDATE estudiante SET celular = '" + celular
                                        + "' WHERE IdEstudiante = " + idEstu + ";";
                            }
                        } else {
                            System.out.println("Datos Incorrectos");
                            actualizarDatosEstudiante();
                        }
                        break;
                    case 5:
                        if ((idEstu > 0) && (idEstu <= numColumnas)) {
                            System.out.println("Ingresa el correo:");
                            correo = lector.next();
                            establecerCorreoElectronico(correo);
                            if ((obtenerCorreoElectronico() != " ") && (obtenerCorreoElectronico() != null)) {
                                consulta = "UPDATE estudiante SET correo = '" + correo
                                        + "' WHERE IdEstudiante = " + idEstu + ";";
                            }
                        } else {
                            System.out.println("Datos Incorrectos");
                            actualizarDatosEstudiante();
                        }
                        break;
                }
            } else {
                System.out.println("Literal fuera de indices");
                actualizarDatosEstudiante();
            }

            stmt = conexion.prepareStatement(consulta);
            stmt.executeUpdate();

            System.out.println("Datos actualizados");

            conexion.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarDatosMateria() {
        Scanner lector = new Scanner(System.in);
        System.out.println("Esconja el campo que desea actualizar:\n 1. ciclo\n 2.nombreMateria");
        int camp = lector.nextInt();
        if ((camp > 0) && (camp <= 2)) {
            Connection conexion = null;
            PreparedStatement stmt;
            String consulta, valorCamp;
            int id;
            if (camp == 1) {
                try {
                    // carga la clase CONTROLADOR
                    Class.forName(CONTROLADOR);

                    // establesco la conexion ala base de datos
                    conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.out.println("Ingrese el Id del campo:");
                id = lector.nextInt();

                System.out.println("Ingrese el nuevo valor del campo ciclo:");
                valorCamp = lector.next();

                consulta = "UPDATE materias SET ciclo =" + "'" + valorCamp + "'"
                        + "WHERE IdMaterias = " + id + ";";
                try {

                    stmt = conexion.prepareStatement(consulta);
                    stmt.executeUpdate();

                    System.out.println("Datos Guardados");

                    // cierro 
                    conexion.close();
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {

                try {
                    // carga la clase CONTROLADOR
                    Class.forName(CONTROLADOR);

                    // establesco la conexion ala base de datos
                    conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

                    System.out.println("Ingrese el Id del campo:");
                    id = lector.nextInt();
                    lector.nextLine();

                    System.out.println("Ingrese el nuevo valor del campo materia:");
                    valorCamp = lector.nextLine();

                    consulta = "UPDATE materias SET nombreMateria = " + "'" + valorCamp + "'"
                            + " WHERE IdMaterias = " + id + ";";

                    // mando la consulta
                    stmt = conexion.prepareStatement(consulta);
                    stmt.executeUpdate();

                    System.out.println("Datos Guardados");

                    // cierro 
                    conexion.close();
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Datos mal ingresados intente de nuevo");
            actualizarDatosMateria();
        }

    }

    @Override
    public void eliminarDatosEstudiante() {
        Connection conexion = null;
        Statement instruccion = null;
        ResultSet conjuntodatos = null;

        Scanner lector = new Scanner(System.in);


        try {
            String consulta = "select count(*) from estudiante;";
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            instruccion = conexion.createStatement();
            conjuntodatos = instruccion.executeQuery(consulta);
            ResultSetMetaData metadatos = conjuntodatos.getMetaData();
            //System.out.println("Total "+ conjuntodatos.getObject("count(*)"));

            // meto dentro de un while ya que si no da error 
            // e imprimo los datos y paso auna variable pa controlar el limite de filas
            int numfilas = 0;
            while (conjuntodatos.next()) {
                for (int i = 1; i <= 1; i++) {
                    System.out.printf("Total de filas: %-8s\t", conjuntodatos.getInt(i));
                    numfilas = (int) conjuntodatos.getInt(i);
                }
                System.out.println();
            }
            // con esat get int para devuelve el numero de filas hecho por la consulta
            int idEstudiante;

            System.out.println("Ingrese un IdMateria que empieze desde 1");
            idEstudiante = lector.nextInt();

            if (idEstudiante > 0) {
                instruccion.close();
                conjuntodatos.close();
                consulta = "delete from estudiante where IdEstudiante= " + idEstudiante + ";";

                PreparedStatement stmt = conexion.prepareStatement(consulta);
                stmt.executeUpdate();
                System.out.println("Datos borrados");

                // para actualizar y eliminar los datos ke ya no existen en la tabla de relacion
                consulta = "delete from idrelacionestudiantemateria where idEstudiante = " + idEstudiante + ";";
                stmt = conexion.prepareStatement(consulta);
                stmt.executeUpdate();
                System.out.println("Datos actualizados en la tabla de relaciones");

                conexion.close();

                stmt.close();
            } else {
                System.out.println("IdEstudainte fuera de rango intente de nuevo:");
                eliminarDatosEstudiante();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void eliminarDatosMateria() {
        Connection conexion = null;
        Statement instruccion = null;
        ResultSet conjuntodatos = null;

        Scanner lector = new Scanner(System.in);


        try {
            String consulta = "select count(*) from materias;";
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            instruccion = conexion.createStatement();
            conjuntodatos = instruccion.executeQuery(consulta);
            ResultSetMetaData metadatos = conjuntodatos.getMetaData();
            //System.out.println("Total "+ conjuntodatos.getObject("count(*)"));

            // meto dentro de un while ya que si no da error 
            // e imprimo los datos y paso auna variable pa controlar el limite de filas
            int numfilas = 0;
            while (conjuntodatos.next()) {
                for (int i = 1; i <= 1; i++) {
                    System.out.printf("Total de filas: %-8s\t", conjuntodatos.getInt(i));
                    numfilas = (int) conjuntodatos.getInt(i);
                }
                System.out.println();
            }
            // con esat get int para devuelve el numero de filas hecho por la consulta
            int idMaterias;

            System.out.println("Ingrese un IdMateria empezando desde 1");
            idMaterias = lector.nextInt();

            if (idMaterias > 0) {
                instruccion.close();
                conjuntodatos.close();
                consulta = "delete from materias where IdMaterias= " + idMaterias + ";";


                PreparedStatement stmt = conexion.prepareStatement(consulta);
                stmt.executeUpdate();
                System.out.println("Datos borrados");

                // para actualizar y eliminar los datos ke ya no existen en la tabla de relacion
                consulta = "delete from idrelacionestudiantemateria where idMateria = " + idMaterias + ";";
                stmt = conexion.prepareStatement(consulta);
                stmt.executeUpdate();
                System.out.println("Datos actualizados en la tabla de relaciones");

                conexion.close();
                stmt.close();
            } else {
                System.out.println("IdMateria fuera de rango intente de nuevo:");
                eliminarDatosMateria();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void asignarMaterias() {
        Scanner lectura = new Scanner(System.in);
        System.out.println("\t\t TABLA DE ESTIDIANTES");
        consultaEstudiantes();
        System.out.println("\t\t TABLA DE MATERIAS");
        consultaMaterias();

        String consulta = " ";

        Connection conexion = null;
        PreparedStatement stmt = null;

        //  CODIGO PARA INSERTAR LOS DATOS EN LA TABLA
        int IdEst = 0, IdMat = 0, numMateria = 0;// para ingresar los ide del estudiante yd espues de  las materias asignadas
        int auxMat = 0;
        String llamadoAsignacionEingresoNewoAsignacion = "y";
        while (llamadoAsignacionEingresoNewoAsignacion.equalsIgnoreCase("y")) {

            System.out.println("Ingrese el Id del Estudiante ");

            IdEst = lectura.nextInt();
            try {
                if (IdEst > 0) {
                    System.out.println("Ingrese el numero de materias del Estudiante");
                    numMateria = lectura.nextInt();

                    for (int i = 1; i <= numMateria; i++) {
                        System.out.println("Ingrese el Id de la materia:");
                        IdMat = lectura.nextInt();

                        if ((IdMat > 0) && (IdMat != auxMat)) {

                            Class.forName(CONTROLADOR);
                            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);

                            consulta = "insert into idrelacionestudiantemateria values ("
                                    + IdEst + "," + IdMat + ");";
                            stmt = conexion.prepareStatement(consulta);
                            stmt.executeUpdate();

                            auxMat = IdMat;//es para evitar que se repita la misma materia en el mismo alumno
                        } else {
                            System.out.println("\n\n--------------Id fuera de rango o ya existente intenta de nuevo-------------\n");
                            asignarMaterias();
                        }
                    }
                    conexion.close();
                    stmt.close();
                } else {
                    asignarMaterias();
                    System.out.println("Id fuera de rango intenta de nuevo");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Desea ingresar otra asignacion? y/n aux mat  " + auxMat);
            llamadoAsignacionEingresoNewoAsignacion = lectura.next();
        }
        // llamo al metodo de mostrar la asignacion        
        System.out.println("Desea ver la tabla de asignaciones? y/n");
        llamadoAsignacionEingresoNewoAsignacion = lectura.next();
        if (llamadoAsignacionEingresoNewoAsignacion.equalsIgnoreCase("y")) {
            mostrarAsignaciones();
        }

    }

    // mostrar tabla de asignaciones
    public void mostrarAsignaciones() {
        System.out.println("\t\t TABLA DE ASIGNACIONES");

        String consulta = " ";

        Connection conexion = null;

        // MUESTRO LA TABLA DE LAS ASIGNACIONES;
        try {
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            Statement instruccion = null;
            ResultSet conjuntodatos = null;

            consulta = "SELECT * FROM idrelacionestudiantemateria;";

            instruccion = conexion.createStatement();
            conjuntodatos = instruccion.executeQuery(consulta);
            //procesa los resultados de la consulta
            ResultSetMetaData metadatos = conjuntodatos.getMetaData();

            // imprimo los titulos de las columnas de la tabla
            int numColumnas = metadatos.getColumnCount();

            for (int i = 1; i <= numColumnas; i++) {
                System.out.printf("%-8s\t", metadatos.getColumnName(i));
            }
            System.out.println();

            // imprimo los valores de la tabla
            while (conjuntodatos.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    System.out.printf("%-8s\t", conjuntodatos.getObject(i));
                }
                System.out.println();
            }
            conexion.close();
            instruccion.close();
            conjuntodatos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // codifico metodos propios de esta clase
    public void consultaMaterias() {
        Connection conexion = null;//maneja la conexion
        Statement instruccion = null;//instruccion de consulta
        ResultSet conjuntoResultados = null;// maneja los resultados

        String consulta = "SELECT * FROM materias;";
        try {
            // carga la clase CONTROLADOR
            Class.forName(CONTROLADOR);
            // establesco la conexion a la base de datos
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            //creo el obj statment para consultar la base de datos
            instruccion = conexion.createStatement();
            // consulto la base de datos
            conjuntoResultados = instruccion.executeQuery(consulta);

            //procesa los resultados de la consulta
            ResultSetMetaData metadatos = conjuntoResultados.getMetaData();

            // imprimo los titulos de las columnas de la tabla
            int numColumnas = metadatos.getColumnCount();

            for (int i = 1; i <= numColumnas; i++) {
                System.out.printf("%-8s\t", metadatos.getColumnName(i));
            }
            System.out.println();

            // imprimo los valores de la tabla
            while (conjuntoResultados.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    System.out.printf("%-8s\t", conjuntoResultados.getObject(i));
                }
                System.out.println();
            }
            conexion.close();
            instruccion.close();
            conjuntoResultados.close();
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }

    }

    public void consultaEstudiantes() {
        Connection conexion = null;//maneja la conexion
        Statement instruccion = null;//instruccion de consulta
        ResultSet conjuntoResultados = null;// maneja los resultados

        String consulta = "SELECT * FROM estudiante;";
        try {
            // carga la clase CONTROLADOR
            Class.forName(CONTROLADOR);
            // establesco la conexion ala base de datos
            conexion = DriverManager.getConnection(URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA);
            //creo el obj statment para consultar la base de datos
            instruccion = conexion.createStatement();
            // consulto la base de datos
            conjuntoResultados = instruccion.executeQuery(consulta);

            //procesa los resultados de la consulta
            ResultSetMetaData metadatos = conjuntoResultados.getMetaData();

            // imprimo los titulos de las columnas de la tabla
            int numColumnas = metadatos.getColumnCount();

            for (int i = 1; i <= numColumnas; i++) {
                System.out.printf("%-8s\t", metadatos.getColumnName(i));
            }
            System.out.println();

            // imprimo los valores de la tabla
            while (conjuntoResultados.next()) {
                for (int i = 1; i <= numColumnas; i++) {
                    System.out.printf("%-8s\t", conjuntoResultados.getObject(i));
                }
                System.out.println();
            }
            conexion.close();
            instruccion.close();
            conjuntoResultados.close();
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //creo un obj de la clase Estudiante
        Estudiante objEstudiante = new Estudiante();

        System.out.println("\nBienvenido las materias ofertadas este periodo son:\n");
        objEstudiante.consultaMaterias();
        System.out.println("\n\nEstudiantes inscriptos hasta la fecha en este periodo son:\n");
        objEstudiante.consultaEstudiantes();

        // cre variable para leer el literal escogido x usuario
        Scanner lector = new Scanner(System.in);
        String interactuar = "Y";
        while (interactuar.equalsIgnoreCase("Y")) {
            int literalAccion = 0;
            System.out.println("\n\nSi desea hacer alguna de las sigientes acciones por favor digite "
                    + "el literal de la lista a continuacion:\n\n");
            System.out.println("1. Ingresar Datos Estudiante\n"
                    + "2. Ingresar Datos Materia\n"
                    + "3. Mostrar Datos Estudiante Materia\n"
                    + "4. Actualizar Datos Estudiante\n"
                    + "5. Actualizar Datos Materia\n"
                    + "6. Eliminar Datos Estudiante\n"
                    + "7. Eliminar Datos Materia\n"
                    + "8. Ver Lista Estudiante\n"
                    + "9. Ver Lista Materia\n"
                    + "10.Asignar Materias\n");
            literalAccion = lector.nextInt();
            if ((literalAccion > 0) && (literalAccion <= 10)) {
                String Ingresar = "y";
                switch (literalAccion) {
                    case 1:

                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.ingresarDatosEstudiante();
                            System.out.println("Desea ingresar otro estudiante y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 2:
                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.ingresarDatosMateria();
                            System.out.println("Desea ingresar otra materia y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 3:
                        objEstudiante.mostrarDatosEstudianteMaterias();
                        break;
                    case 4:
                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.actualizarDatosEstudiante();
                            System.out.println("Desea actualizar otro estudiante y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 5:
                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.actualizarDatosMateria();
                            System.out.println("Desea actualizar otra materia y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 6:
                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.eliminarDatosEstudiante();
                            System.out.println("Desea eliminar otro estudiante y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 7:
                        while (Ingresar.equalsIgnoreCase("y")) {
                            objEstudiante.eliminarDatosMateria();
                            System.out.println("Desea eliminar otra materia y/n");
                            Ingresar = lector.next();
                        }
                        break;
                    case 8:
                        objEstudiante.consultaEstudiantes();
                        break;
                    case 9:
                        objEstudiante.consultaMaterias();
                        break;
                    case 10:
                        objEstudiante.asignarMaterias();
                        break;
                    /*default:
                     System.out.println("Ingreso mal el literal\n\n");
                     interactuar =true;                       
                     break;*/
                }
            } else {
                interactuar = "Y";
                continue;// es para que salte el resto de codigo
            }
            System.out.println("\nDesea realizar otra operacion? Y/N");
            interactuar = lector.next();

        }

    }
}
