import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Badge {
    public Badge() {}

    public static List<String> convertEmailsToFullNames(String emails) {
        Pattern patternEmailWithSubaddressing = Pattern.compile("([\\p{L}]+)\\.([\\p{L}]+)\\+([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailDefault = Pattern.compile("([\\p{L}]+)\\.([\\p{L}]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithoutFirstname = Pattern.compile("(^|\\s)([\\p{L}]+)@([\\p{L}0-9.-]+)");

        List<String> namesFromDefaultEmails = extractNamesFromEmailsListByPattern(emails, patternEmailDefault, true);
        List<String> namesEmailWithoutFirstname = extractNamesFromEmailsListByPattern(emails, patternEmailWithoutFirstname,  false);
        List<String> namesFromEmailWithSubaddressing = extractNamesFromEmailsListByPattern(emails, patternEmailWithSubaddressing,  true);

        return Stream.of(namesFromDefaultEmails, namesFromEmailWithSubaddressing, namesEmailWithoutFirstname)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static List<String> extractNamesFromEmailsListByPattern(String emails, Pattern pattern, boolean withLastName) {
        Matcher matcher = pattern.matcher(emails);
        return extractNames(matcher, withLastName);
    }

    private static String makeFirstLetterCapital(String word, boolean getFullName) {
        String lowercaseWord = word.toLowerCase();
        String fistLetter = lowercaseWord.substring(0, 1).toUpperCase();
        String wordWithoutFirstLetter = lowercaseWord.substring(1);
        return getFullName
                ? fistLetter + wordWithoutFirstLetter
                : fistLetter;
    }

    public static List<String> extractNames(Matcher matcher, boolean withLastname) {
        List<String> fullName = new ArrayList<>();
        while (matcher.find()) {
            String fistName =  withLastname
                    ? makeFirstLetterCapital(matcher.group(1), true)
                    : "";
            String spaceLastName = withLastname
                    ? " " + makeFirstLetterCapital(matcher.group(2), false)
                    : makeFirstLetterCapital(matcher.group(2), true) ;
            fullName.add(fistName + spaceLastName);
        }
        return fullName;
    }

    public static void printFullNamesList(List<String> fullNames) {
        String list = fullNames.stream()
                .map(fullName -> "\"" + fullName + "\"")
                .collect(Collectors.joining(", "));
        System.out.println(list);
    }

}

public class Main {
    public static void main(String[] args) {
        String stringInput = "melnyk@cde.org; john.doe@abc.com; Alex.Boe@cde.org; marych@cde.org; Johnny.Depp+reg@cde.org; Årsén.öliynyk@example.com; Ruslan.Chao+reg@cde.org; іван.Квітка@фірма.укр; öliynyk@example.com";

        List<String> fullNames = Badge.convertEmailsToFullNames(stringInput);
        Badge.printFullNamesList(fullNames);

        /*Badge badge = new Badge();
        ArrayList<String> actualList = badge.returnEmailList(stringInput);*/
        ArrayList<String> expectedBadgeList = new ArrayList<String>();
        expectedBadgeList.add( "John D" );
        expectedBadgeList.add( "Alex B" );
        System.out.println(expectedBadgeList);
    }

}