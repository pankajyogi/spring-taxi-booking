package com.github.pankajyogi.spring.taxibooking.agent;

public enum AgentCommand {

    UPDATE_AVAILABLE("Update status of this taxi to AVAILABLE"),

    UPDATE_BOOKED("Update status of this taxi to BOOKED"),

    CHECK_NEW_BOOKINGS("Check for new bookings requests from server"),

    QUIT("Quit the application");

    private final String description;

    private AgentCommand(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
