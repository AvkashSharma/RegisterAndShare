package handlers;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common class used for input scanner validation
 */
public class Common {

    final static String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    final static Pattern IP_PATTERN = Pattern.compile(IP_REGEX);

    /**
     * Validate if integer while inputing input
     * 
     * @return
     */
    public static int scanInt(Scanner scanner, String message) {
        int value = 0;
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            String input = scanner.next();
            System.out.printf("\t\"%s\" is not a valid number.\n", input);
            System.out.print(message);
        }
        value = scanner.nextInt();
        return value;
    }

    /**
     * Validate if string
     * 
     * @param scanner
     * @param message
     * @return
     */
    public static String scanString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.next();
    }

    /**
     * Validate ip address
     * @param scanner
     * @return
     */
    public static String scanIp(Scanner scanner) {
        String value = scanner.next();
        Matcher matcher = IP_PATTERN.matcher(value);

        while (!matcher.matches()) {
            System.out.println("\tInvalid Ip address, reenter valid Ip address(xxx.xxx.xxx.xxx)");
            value = scanner.next();
            matcher = IP_PATTERN.matcher(value);
        }
        return value;
    }
}
