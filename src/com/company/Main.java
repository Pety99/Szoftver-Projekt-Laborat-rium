package com.company;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    /**
     * A játékot elindítja
     * @throws IOException
     */
    public static void main(String[] args)throws IOException {

        Parser parser = new Parser();

        Kontroller kontroller = new Kontroller();
        try {
            parser.loadPalya(kontroller, "palya.json");
        }
        catch (IOException e){e.printStackTrace();}

        View view = new View(kontroller);
        kontroller.addView(view);

        kontroller.jatek();
    }
}
