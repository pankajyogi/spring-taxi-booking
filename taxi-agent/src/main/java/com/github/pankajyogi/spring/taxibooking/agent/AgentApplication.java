package com.github.pankajyogi.spring.taxibooking.agent;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootApplication
public class AgentApplication {

    private static final PrintStream SysOut = System.out;

    private static final InputStream SysIn = System.in;

    private static final Scanner scanner = new Scanner(SysIn);

    private static final AgentCommand[] commands = AgentCommand.values();

    public static void main(String[] args) {
        SysOut.println("Program Started");
        while (loop()) ;
    }

    private static boolean loop() {
        for (AgentCommand command : commands) {
            SysOut.printf("[%d]\t%s - %s %n", command.ordinal(), command.name(), command.description());
        }
        SysOut.println("Enter your choice:");
        int choice = scanner.nextInt();
        if (AgentCommand.QUIT.ordinal() == choice) {
            return false;
        } else {
            SysOut.println("relooping");
        }
        return true;
    }
}
