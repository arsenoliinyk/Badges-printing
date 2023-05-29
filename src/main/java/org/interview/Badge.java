package org.interview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Badge {

    public static List<String> convertEmailsToFullNames(String emails) {
        Pattern patternEmailDefault = Pattern.compile("(^|\\s)([\\p{L}0-9]+)\\.([\\p{L}0-9]+)(\\.([\\p{L}0-9]+))*@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithoutLastname = Pattern.compile("(^|\\s)([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithSubaddressing = Pattern.compile("(^|\\s)([\\p{L}0-9]+)\\.([\\p{L}0-9]+)\\+([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");

        List<String> namesFromDefaultEmails = extractNamesFromEmailsListByPattern(emails, patternEmailDefault, true);
        List<String> namesEmailWithoutLastname = extractNamesFromEmailsListByPattern(emails, patternEmailWithoutLastname,  false);
        List<String> namesFromEmailWithSubaddressing = extractNamesFromEmailsListByPattern(emails, patternEmailWithSubaddressing,  true);

        return Stream.of(namesFromDefaultEmails, namesFromEmailWithSubaddressing, namesEmailWithoutLastname)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<String> extractNamesFromEmailsListByPattern(String emails, Pattern pattern, boolean withLastName) {
        Matcher matcher = pattern.matcher(emails);
        return extractNames(matcher, withLastName);
    }

    private static List<String> extractNames(Matcher matcher, boolean withLastname) {
        List<String> fullName = new ArrayList<>();
        while (matcher.find()) {
            String firstName =  wordFormatting(matcher.group(2), true, withLastname);
            String spaceLastName = withLastname
                    ? " " + wordFormatting(matcher.group(3), false, true)
                    : "";
            fullName.add(firstName + spaceLastName);
        }
        return fullName;
    }

    public static String wordFormatting(String word, boolean getFullName, boolean withLastname) {
        String lowercaseWord = word.toLowerCase();
        String firstLetter = lowercaseWord.substring(0, 1).toUpperCase();
        String wordWithoutFirstLetter = maximumLengthWord(lowercaseWord, withLastname);
        return getFullName
                ? firstLetter + wordWithoutFirstLetter
                : firstLetter;
    }

    private static String maximumLengthWord(String word, boolean withLastname){
        return withLastname
                ? word.substring(1,Math.min(word.length(), 13))
                : word.substring(1,Math.min(word.length(), 15));

    };

    public static void printFullNamesList(List<String> fullNames) {
        String list = fullNames.stream()
                .map(fullName -> "\"" + fullName + "\"")
                .collect(Collectors.joining(", "));
        System.out.println(list);
    }

}
