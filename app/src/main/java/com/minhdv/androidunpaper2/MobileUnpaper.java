package com.minhdv.androidunpaper2;

import java.util.ArrayList;
import java.util.List;

public class MobileUnpaper {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("avformat");
        System.loadLibrary("avcodec");
        System.loadLibrary("avutil");
    }
    public native int nativeUnpaperExecute(final String[] arguments);

    public int execute(final String command) {
        return nativeUnpaperExecute(parseArguments(command));
    }
    static String[] parseArguments(final String command) {
        final List<String> argumentList = new ArrayList<>();
        StringBuilder currentArgument = new StringBuilder();

        boolean singleQuoteStarted = false;
        boolean doubleQuoteStarted = false;

        for (int i = 0; i < command.length(); i++) {
            final Character previousChar;
            if (i > 0) {
                previousChar = command.charAt(i - 1);
            } else {
                previousChar = null;
            }
            final char currentChar = command.charAt(i);

            if (currentChar == ' ') {
                if (singleQuoteStarted || doubleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else if (currentArgument.length() > 0) {
                    argumentList.add(currentArgument.toString());
                    currentArgument = new StringBuilder();
                }
            } else if (currentChar == '\'' && (previousChar == null || previousChar != '\\')) {
                if (singleQuoteStarted) {
                    singleQuoteStarted = false;
                } else if (doubleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else {
                    singleQuoteStarted = true;
                }
            } else if (currentChar == '\"' && (previousChar == null || previousChar != '\\')) {
                if (doubleQuoteStarted) {
                    doubleQuoteStarted = false;
                } else if (singleQuoteStarted) {
                    currentArgument.append(currentChar);
                } else {
                    doubleQuoteStarted = true;
                }
            } else {
                currentArgument.append(currentChar);
            }
        }

        if (currentArgument.length() > 0) {
            argumentList.add(currentArgument.toString());
        }

        return argumentList.toArray(new String[0]);
    }
}
