

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene m�odos est�ticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {


    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);

            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }


    }

    /**
     * Metodo auxiliar para el nombre
     */
    private static String nombreFest(String nombre) {
        String[] nombres = nombre.trim().split("[.\\s]+");
        String nombreParse = "";
        for (int i = 0; i < nombres.length; i++) {
            char letra = nombres[i].toUpperCase().charAt(0);
            nombres[i] = letra + nombres[i].substring(1, nombres[i].length());
            nombreParse += nombres[i] + " ";
        }
        nombreParse = nombreParse.trim();
        return nombreParse;
    }

    /**
     * Metodo auxiliar para el lugar
     */

    private static String lugarFest(String lugar) {
        String sitio = lugar.trim().toUpperCase();
        return sitio;
    }

    /**
     * Metodo auxiliar para la fecha
     */
    private static LocalDate fechaFest(String fecha) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaReal = LocalDate.parse(fecha.trim(), formateador);
        return fechaReal;
    }

    /**
     * Metodo auxiliar para el estilo
     */
    public static Estilo estilosFest(String estilo) {
        Estilo estilos = Estilo.valueOf(estilo.trim().toUpperCase());
        return estilos;
    }

    /**
     * se parsea la l�nea extrayendo sus datos y creando y
     * devolviendo un objeto Festival
     *
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        String[] parse = lineaFestival.trim().split("[:]+");
        HashSet<Estilo> estilos = new HashSet<>();
        for (int i = 4; i < parse.length; i++) {
            estilos.add(estilosFest(parse[i]));
        }
        Festival festival = new Festival(nombreFest(parse[0]), lugarFest(parse[1]), fechaFest(parse[2]), Integer.parseInt(parse[3].trim()), estilos);

        return festival;
    }


}
