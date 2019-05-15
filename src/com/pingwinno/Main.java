package com.pingwinno;

import java.io.*;
import java.util.Scanner;

/*
This app finds a shortest paths between pairs of cities.
It uses adopted Queue-based Bellman-Ford algorithm taken from "Algorithms" book.
It expects exact data format that was given in the task.
For that reason there are no input validation. And for keep code simple and readable to.
 */
public class Main {

    public static void main(String[] args) {
        boolean cycle = true;
        while (cycle) {
            //Parse the path to a file.
            Scanner in = new Scanner(System.in);

            String filePath = "";
            try {
                //You can place paths to multiple files while run app via command line.
                //Output will be in app directory.
                if (args.length > 0) {
                    cycle = false;

                    try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
                        for (String path : args) {
                            in = new Scanner(new BufferedReader(new FileReader(path)));
                            writer.println("File " + path + " calculations in progress...");
                            for (Long cost : TestHandler.start(in)) {
                                writer.println(cost);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //If a file exists in the application directory, just a file name is acceptable.
                    System.out.println("Enter the path to a file with test cases or *exit* to close application");
                    filePath = in.nextLine();
                    in = new Scanner(new BufferedReader(new FileReader(filePath)));
                    for (Long cost : TestHandler.start(in)) {
                        System.out.println(cost);
                    }
                }
            } catch (FileNotFoundException e) {
                if (filePath.toLowerCase().equals("exit")) {
                    System.exit(0);
                }
                System.out.println("File " + filePath + " not found");
            }

        }
    }

}
