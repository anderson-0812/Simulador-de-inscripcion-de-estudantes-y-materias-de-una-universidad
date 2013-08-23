/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioDelTipoList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.org.mozilla.javascript.internal.ast.ContinueStatement;

public class Ejercicio1_List {

    public static void main(String[] args) {
        //repaso de un Arraylist
        ArrayList list = new ArrayList();
        Scanner lector = new Scanner(System.in);
        String nombre;
        String apellido;
        String cedula;
        String mail;
        String cel;

        // la variable y el while para que el usuario pueda interactuar
        String limiteEntradas = "y";
        while (limiteEntradas.equalsIgnoreCase("y")) {
            System.out.println("Ingrese el nombre:");
            nombre = lector.next();
            System.out.println("Ingrese el apellido:");
            apellido = lector.next();
            System.out.println("Ingrese su cedula:");
            cedula = lector.next();
            //valido si la cedula esta correcta
            if (validarCedua(cedula)) {
                list.add(nombre);
                list.add(apellido);
                list.add(cedula);
                System.out.println("Ingrese su correo electronico");
                mail= lector.next();
                if(validarMail(mail))
                {
                    list.add(mail);                   
                    System.out.println("Ingrese su numero celular");
                    cel= lector.next();
                    if(validarCel(cel))
                    {
                        list.add(cel);
                        System.out.println("\nSi desea continuar presione y o si no presione n");
                        limiteEntradas = lector.next();
                    }
                    else{
                        System.out.println("\nINTENTA DE NUEVO INGRESAR SUS DATOS");
                    }
                }
                else
                {
                    System.out.println("\nCORREO MAL INGRESADO INTENTA DE NUEVO INGRESAR SUS DATOS");
                }
                
            } else {
                System.out.println("\nINTENTA DE NUEVO INGRESAR SUS DATOS");
            }
        }

        System.out.println("\tDatos Antes de Eliminar Datos");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        // codigo para eliminar datos
        System.out.println("\n********************");
        String datoEliminar;
        System.out.println("Ingrese el campo que desea eliminar#");
        datoEliminar = lector.next();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(datoEliminar)) {
                list.remove(datoEliminar);
            }
        }
        System.out.println("\tDatos Aactuales");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static boolean validarCedua(String cedu) {
        char ced[] = new char[cedu.length()];//le doy limite al arreglo con los numeros de cedu
        ced = cedu.toCharArray();// convierto la cadena en array
        int cedula[];
        cedula = new int[cedu.length()];// le doy limite al array cedula int
        int res = 0;
        // paso de char a int en el arreglo cedula
        for (int i = 0; i < ced.length; i++) {
            cedula[i] = Integer.parseInt(String.valueOf(ced[i]));
        }

        if (cedula.length == 10) {
            for (int i = 0; i < ced.length; i++) {
                cedula[i] = Integer.parseInt(String.valueOf(cedu.charAt(i)));
                int r = i % 2;

                if (r == 0) {
                    cedula[i] = cedula[i] * 2;
                    if (cedula[i] > 9) {
                        cedula[i] = cedula[i] - 9;
                    }
                }

            }

            for (int i = 0; i < cedula.length - 1; i++) {
                res = res + cedula[i];
            }

            res = res % 10;

            if (res != 0) {
                res = 10 - res;
            }

            if (res == cedula[9]) {
                System.out.println("\nCEDULA VALIDA");
                return true;
            } else {
                System.out.println("\nCEDULA INVALIDA");
                return false;
            }
        } else {
            System.out.println("\nCEDULA INVALIDA");
            return false;
        }
    }

    public static boolean validarMail(String mail) {

        // Establecer el patron
        Pattern p = Pattern.compile("[-\\w\\.]+[@\\w+\\.]+[\\w]{2,4}");// y la combinacion del la expresion regular

        // compara el string con el patron
        Matcher m = p.matcher(mail);

        // Comprobar si encaja
        return m.matches();

    }
    public static boolean validarCel(String cel) {

        // Establecer el patron
        Pattern p = Pattern.compile("^09\\d{8}");// y la combinacion del la expresion regular

        // compara el string con el patron
        Matcher m = p.matcher(cel);

        // Comprobar si encaja
        if(m.matches()==true){
            System.out.println("NUMERO CELULAR CORRECTO");
            return m.matches();
        }
        else
            System.out.println("NUMERO CELULAR INCORRECTO");
        return false;

    }
}
