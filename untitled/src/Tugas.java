import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *@author Romadhon Wiratama
 *program seleksi integer dan string
 *tugas modul 6
 */
public class Tugas {
    /**Reset warna
     *
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**ubah warna jadi merah
     *
     */
    public static final String ANSI_RED = "\u001B[31m";

    public String[] inString = {};
    public int[] inNumber = {};

    String numbers = "number.txt";
    String strings = "string.txt";
    int indexString = 0, indexNumber = 0;

    String tmpInput;
    char entry;
    boolean next = true;

    public static void main(String[] args) {
        Tugas main = new Tugas();
        try {
            main.menu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void menu() throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.println("Data Terkini : ");
        try {
            read(numbers, "inNumber");
            read(strings, "inString");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nInput data ? y / n");
        char pilih = input.next().charAt(0);
        if (pilih == 'y') {
            do {

                System.out.print("\nData : ");
                tmpInput = input.next();

                if (containsNum(tmpInput)) {
                    inNumber = Arrays.copyOf(inNumber, inNumber.length + 1);
                    inNumber[indexNumber] = Integer.parseInt(tmpInput);
                    indexNumber++;
                } else {
                    inString = Arrays.copyOf(inString, inString.length + 1);
                    inString[indexString] = tmpInput;
                    indexString++;
                }

                System.out.print("Lagi ? ");
                entry = input.next().charAt(0);
                if (entry != 'y') {
                    next = false;
                }
            } while (next);
        }
        appendNum(inNumber);
        appendString(inString);
        try {
            read(numbers, "inNumber");
            read(strings, "inString");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**@param text
     *untuk mengecek inputan angka
     */
    static boolean containsNum(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**Writing file menggunakan FileWriter
     *
     */
    static void appendNum(int[] x) throws IOException {

        FileWriter w = new FileWriter("number.txt", true);

        for (int j : x) {
            w.write(String.format("%s|\n", j));
        }
        w.flush();
        w.close();
    }

    static void appendString(String[] x) throws IOException {

        FileWriter w = new FileWriter("string.txt", true);

        for (String s : x) {
            w.write(String.format("%s|\n", s));
        }
        w.flush();
        w.close();
    }

    /**@param file, category
     *untuk read data
     */
    static void read(String file, String category) throws Exception {
        try {
            FileReader reads = new FileReader(file);
            BufferedReader buffread = new BufferedReader(reads);
            String IOnum = buffread.readLine();
            System.out.print(category + " = ");
            do {
                StringTokenizer st = new StringTokenizer(IOnum, "|");
                System.out.print(st.nextToken() + " ");
                IOnum = buffread.readLine();
            } while (IOnum != null);
            System.out.print("\n");
            buffread.close();
            reads.close();
        } catch (FileNotFoundException | NullPointerException exp) {
            System.out.println(ANSI_RED + "Database " + category + " Kosong" + ANSI_RESET);
        }
    }
}