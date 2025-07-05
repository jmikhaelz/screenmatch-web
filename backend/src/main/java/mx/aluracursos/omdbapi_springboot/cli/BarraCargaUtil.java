package mx.aluracursos.omdbapi_springboot.cli;

public class BarraCargaUtil {
    public static void mostrarBarraCarga(int tamano, int tiempoEspera) {
        System.out.print(" ");
        for (int i = 0; i < tamano; i++) {
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("█ ");
        }
        System.out.println("\n");
    }
}
