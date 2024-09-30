package com.github.pankajyogi.spring.taxibooking.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootApplication
public class AgentApplication implements CommandLineRunner {

    private static final PrintStream SysOut = System.out;

    private static final InputStream SysIn = System.in;

    static final Scanner scanner = new Scanner(SysIn);

    private static final AgentCommand[] commands = AgentCommand.values();

    @Autowired
    private AgentCommandService agentCommandService;

    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);
    }

    private boolean loop() {
        for (AgentCommand command : commands) {
            SysOut.printf("[%d]\t%s - %s %n", command.ordinal(), command.name(), command.description());
        }
        SysOut.println("Enter your choice:");
        int choice = scanner.nextInt();
        Action action = getActionFor(commands[choice]);
        action.execute();
        if (commands[choice] == AgentCommand.QUIT) {
            System.exit(0);
        }
        return true;
    }

    private Action getActionFor(AgentCommand command) {
        return switch (command) {
            case UPDATE_BOOKED -> () -> agentCommandService.updateBooked();
            case UPDATE_AVAILABLE -> () -> agentCommandService.updateAvailable();
            case CHECK_NEW_BOOKINGS -> () -> agentCommandService.checkNewBookings();
            default -> () -> {
            };
        };
    }

    @Override
    public void run(String... args) throws Exception {
        while (loop());
    }
}
