package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;

import java.io.IOException;

public class main {

    public static void main(String[] args) {
        new LoggerComponent().debug("test");
        System.out.println(LogLevel.DEBUG.toString());
        System.out.println(new Throwable().toString());
    }
}
