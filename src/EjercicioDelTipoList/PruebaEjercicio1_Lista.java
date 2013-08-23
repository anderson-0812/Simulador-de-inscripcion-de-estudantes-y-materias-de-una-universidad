/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioDelTipoList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.org.mozilla.javascript.internal.ast.ContinueStatement;

public class PruebaEjercicio1_Lista {

    public static void main(String[] args) {

        int lim = 2;
        IngresarDatos(lim);
    }

    public static void IngresarDatos(int limiteobj) {

        //repaso de un Arraylist
        ArrayList list = new ArrayList();

        Scanner lector = new Scanner(System.in);
        String nombre;
        String apellido;
        String cedula;
        String mail;
        String cel;

        int contObj = 1, interadorValidarDatos = 0;
        boolean imprimir = false;

        // el for para que el usuario pueda interactuar
        for (int i = 0; i < limiteobj; i++) {
            System.out.println("\nIngrese el nombre:");
            nombre = lector.next();
            System.out.println("Ingrese el apellido:");
            apellido = lector.next();
            System.out.println("Ingrese su cedula:");
            cedula = lector.next();
            System.out.println("Ingrese su correo electronico");
            mail = lector.next();
            System.out.println("Ingrese su numero celular");
            cel = lector.next();

            // da la señala para habilitar la impresion en cuanto acabe de ejecutarse el 
            //almacenamiento de todos los datos persona
            if (i + 1 == limiteobj) {
                imprimir = true;
            }

            // llamo al metodo validarDatosObj para llenar la list y validarlos
            ValidarDatosObj(nombre, apellido, cedula, mail, cel,
                    contObj, imprimir, interadorValidarDatos, list, limiteobj);//esats variiables de esta lineal
            //son para controlar
            //limiteObj, cuando se imprime la list
            //y el iterador de for del otro metodo
            //aumento el contador contObj para controlar el for del metodo llamado en este 
            contObj++;
            interadorValidarDatos++;
        }

    }

    public static void ValidarDatosObj(String nom, String ape, String cedu, String mail, String cel,
            int limObj, boolean imprimir, int iteradorFor, ArrayList list, int limiteParametroIngreso) {

        // creo arreglo de obejtos
        Persona[] arrayObj = new Persona[limObj];

        //creo los obejos dentro del arrayObj y 
        for (int i = iteradorFor; i < limObj; i++) {
            arrayObj[i] = new Persona(nom, ape, cedu, mail, cel);
            // valido los datos  lleno el list
            if ((arrayObj[i].obtenerCedula() != null)&&(arrayObj[i].obtenerCedula() != " ")
                    && (arrayObj[i].obtenerCorreoElectronico() != null)
                    &&(arrayObj[i].obtenerCorreoElectronico() !=" ")
                    && (arrayObj[i].obtenerCelular() != null)&&(arrayObj[i].obtenerCelular() != " ")) {
                list.add(arrayObj[i]);// lleno los datos                    
            } else {
                System.out.println("El obj del array no se guardo debido aun dato invalido vuelva a intentar");
                IngresarDatos(limiteParametroIngreso);
                imprimir=false;//para que no lalme alos metodos de continuacion  del codigo
            }
        }
        // es para que imprima solo si ya estab almacenados todos los datos
        if (imprimir) {
            imprimirObj(list);
            eliminarDatos(list);
        }
    }
    // Eliminar datos

    public static void eliminarDatos(ArrayList list) {
        Scanner lector = new Scanner(System.in);
        // codigo para eliminar datos
        System.out.println("\n********************");
        String datoEliminar = null;
        System.out.println("Puede eliminar por listas completas o por campos \n"
                + "si es por listas presione un numero de entre 1 a  "
                + (list.size()) + "  o -1 para pasara escoger por campos");
        int numList = lector.nextInt();// lee si escogio eliminar por listas       

        if (numList >= 0) {
            System.out.println("LA LISTA:\t"+numList+"\tA SIDO ELIMINADA");
            list.remove(numList-1);// borra la lista completa
        } else {
            System.out.println("Si desea eliminar un campo escriba el literal de entre estos:\n"
                + "a. nombre\nb. apellido\n c. cedula\n"
                + "d. correo electrinico\n"
                + "e. numero celular\n");
            String campoObj = lector.next();// lee si escogio eliminar x campos del obj
            switch (campoObj) {
                case "a":
                    System.out.println("Ingrese el nombre:\n");
                    datoEliminar = lector.next();
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        Persona pz = (Persona) it.next();
                        if(pz.obtenerNombre().equalsIgnoreCase(datoEliminar))
                        pz.establecerNombre(" ");
                    }
                    break;
                case "b":
                    System.out.println("Ingrese el apellido:\n");
                    datoEliminar = lector.next();
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        Persona pz = (Persona) it.next();
                        if(pz.obtenerApellido().equalsIgnoreCase(datoEliminar))
                        pz.establecerApellido(" ");
                    }
                    break;
                case "c":
                    System.out.println("Ingrese el cedula:\n");
                    datoEliminar = lector.next();
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        Persona pz = (Persona) it.next();
                        if(pz.obtenerCedula().equalsIgnoreCase(datoEliminar))
                        pz.establecerCedula(" ");
                    }
                    break;
                case "d":
                    System.out.println("Ingrese el correo electronico:\n");
                    datoEliminar = lector.next();
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        Persona pz = (Persona) it.next();
                        if(pz.obtenerCorreoElectronico().equalsIgnoreCase(datoEliminar))
                        pz.establecerCorreoElectronico(" ");
                    }
                    break;
                case "e":
                    System.out.println("Ingrese el numero celular:\n");
                    datoEliminar = lector.next();
                    for (Iterator it = list.iterator(); it.hasNext();) {
                        Persona pz = (Persona) it.next();
                        if(pz.obtenerCelular().equalsIgnoreCase(datoEliminar))
                        pz.establecerCelular(" ");
                    }
                    break;
                default:
                    System.out.println("Ingreso mal el literal\n\n");
                    break;
            }
        }



        System.out.println("\tLISTA CON DATOS ACTUALES");
        imprimirObj(list);
    }

    public static void imprimirObj(ArrayList list) {
        System.out.println("\nLISTA DE DATOS ALMACENADOS:");
        int itera = 1;
        //defino un interador para extraer los datos e imprimirlos
        for (Iterator it = list.iterator(); it.hasNext();) {
            Persona pz = (Persona) it.next();
            System.out.println("\nLista nuemero \t" + itera + "\n" + pz.obtenerNombre() + "\n" + pz.obtenerApellido()
                    + "\n" + pz.obtenerCedula() + "\n" + pz.obtenerCorreoElectronico() + "\n" + pz.obtenerCelular());
            itera++;
        }
    }
}
