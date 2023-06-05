package com.github.rinkore1.iiyokoiyo.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Writer fileWriter;

    public static void setApplicationLogFile(Path applicationDir) throws IOException {
        Path logsDir = Paths.get(applicationDir + File.separator + "logs");
        Path logsFile = Paths.get(logsDir + File.separator + "lastest.log");

        if (!Files.exists(logsDir)) {
            Files.createDirectories(logsDir);
        }

        setLogFile(applicationDir.resolve(logsFile));
    }

    public static void setLogFile(Path path) {
        try {
            fileWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.printf("ERROR setting log file %s%n\r\n", e);
        }
    }

    enum Level {
        DEBUG(Out.FILE_ONLY), INFO(Out.STD_OUT), WARNING(Out.STD_ERR);
        final Out out;

        Level(Out out) {
            this.out = out;
        }
    }

    enum Out {
        FILE_ONLY,
        STD_OUT,
        STD_ERR
    }

    private static void log(Level level, String message, Throwable throwable) {
        String out = String.format("[%s] [%s]: %s%n", DATE_FORMAT.format(new Date()), level.name(), message);
        if (throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            out += sw.toString();
        }
        if (fileWriter != null) {
            try {
                fileWriter.write(out);
                fileWriter.flush();
            } catch (Exception e) {
                System.err.printf("Error writing log: %s%n", e);
            }
        }
        switch (level.out) {
            case STD_OUT:
                System.out.print(out);
                return;
            case STD_ERR:
                System.err.print(out);
        }
    }

    public static void debug(String message) {
        log(Level.DEBUG, message, null);
    }

    public static void debug(String format, Object... args) {
        debug(String.format(format, args));
    }

    public static void debug(String message, Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }

    public static void info(String message) {
        log(Level.INFO, message, null);
    }

    public static void info(String format, Object... args) {
        info(String.format(format, args));
    }

    public static void warning(String message) {
        log(Level.WARNING, message, null);
    }

    public static void warning(String format, Object... args) {
        warning(String.format(format, args));
    }

    public static void warning(String message, Throwable throwable) {
        log(Level.WARNING, message, throwable);
    }
}
