/**/
package EjercicioDelTipoList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {

    private String nombre;
    private String apellido;
    private String cedula;
    private String correoElectronico;
    private String celular;
    
    public Persona(String nom, String ape, String cedu, String mail, String cel) {
        establecerNombre(nom);
        establecerApellido(ape);
        establecerCedula(cedu);
        establecerCorreoElectronico(mail);
        establecerCelular(cel);
    }
    
    public void establecerNombre(String nom) {
        nombre = nom;
    }

    public String obtenerNombre() {
        return nombre;
    }
    
    public void establecerApellido(String ape) {
        apellido = ape;
    }

    public String obtenerApellido() {
        return apellido;
    }
    
    public void establecerCedula(String cedu) {
        if (validarCedua(cedu)) {
            cedula = cedu;
        } else {
            System.out.println("\nCEDULA INVALIDA");
            cedula = " ";
        }
    }

    public String obtenerCedula() {
        return cedula;
    }
    
    public void establecerCelular(String celu) {
        if (validarCel(celu)) {
            celular = celu;            
        }
        else{
            //System.out.println("NUMERO CELULAR INCORRECTO");
            celular=" ";
        }
    }

    public String obtenerCelular() {
        return celular;
    }
    
    public void establecerCorreoElectronico(String mail) {
        if (validarMail(mail)) {
            correoElectronico = mail;
        }
        else
            correoElectronico=" ";
    }

    public String obtenerCorreoElectronico() {
        return correoElectronico;
    }

    // METODOS DE VALIDACION
    public static boolean validarCedua(String cedu) {
        char ced[] = new char[cedu.length()];//le doy limite al arreglo con los numeros de cedu
        ced = cedu.toCharArray();// convierto la cadena en array
        int cedula[];
        cedula = new int[cedu.length()];// le doy limite al array cedula int
        int res = 0;
        try // para evitar datos ke no se puedan convertir a int como " "
        {
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
        } catch (Exception e) {
            return false;
        }
    }

    // VALIDAR MAIL
    public static boolean validarMail(String mail) {

        // Establecer el patron
        Pattern p = Pattern.compile("[\\w-\\.]{3,}@([\\w-]{2,}\\.)*([\\w-]{2,}\\.)[\\w-]{2,4}");// y la combinacion del la expresion regular

        // compara el string con el patron
        Matcher m = p.matcher(mail);
        
        if (m.matches() == true) {
            System.out.println("CORREO CORRECTO");
            return m.matches();
        } else {
            System.out.println("CORREO INCORRECTO");
        }
        return false;
        
    }

    // VALIDAR CELULAR
    public static boolean validarCel(String cel) {

        // Establecer el patron
        Pattern p = Pattern.compile("^09\\d{8}");// y la combinacion del la expresion regular

        // compara el string con el patron
        Matcher m = p.matcher(cel);

        // Comprobar si encaja
        if (m.matches() == true) {
            System.out.println("NUMERO CELULAR CORRECTO");
            return m.matches();
        } else {
            System.out.println("NUMERO CELULAR INCORRECTO");
        }
        return false;
        
    }
    
    public static void main(String[] args) {
        
    }
}
