import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Badge {
    public Badge() {}

    public static List<String> convertEmailsToFullNames(String emails) {
        Pattern patternEmailDefault = Pattern.compile("([\\p{L}0-9]+)\\.([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithoutLastname = Pattern.compile("(^|\\s)([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithSubaddressing = Pattern.compile("([\\p{L}0-9]+)\\.([\\p{L}0-9]+)\\+([\\p{L}0-9]+)@([\\p{L}0-9.-]+)");
        Pattern patternEmailWithMultipleDots = Pattern.compile("([\\p{L}0-9]+)\\.([\\p{L}0-9]+)(\\.[\\p{L}0-9]+)@([\\p{L}0-9.-]+)");

        List<String> namesFromDefaultEmails = extractNamesFromEmailsListByPattern(emails, patternEmailDefault, true);
        List<String> namesEmailWithoutLastname = extractNamesFromEmailsListByPattern(emails, patternEmailWithoutLastname,  false);
        List<String> namesFromEmailWithSubaddressing = extractNamesFromEmailsListByPattern(emails, patternEmailWithSubaddressing,  true);
        List<String> namesFromEmailWithMultipleDots = extractNamesFromEmailsListByPattern(emails, patternEmailWithMultipleDots,  true);

        return Stream.of(namesFromDefaultEmails, namesFromEmailWithSubaddressing, namesEmailWithoutLastname, namesFromEmailWithMultipleDots)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static List<String> extractNamesFromEmailsListByPattern(String emails, Pattern pattern, boolean withFirstName) {
        Matcher matcher = pattern.matcher(emails);
        return extractNames(matcher, withFirstName);
    }

    public static List<String> extractNames(Matcher matcher, boolean withFirstname) {
        List<String> fullName = new ArrayList<>();
        while (matcher.find()) {
            String firstName =  withFirstname
                    ? wordFormatting(matcher.group(1), true, true)
                    : wordFormatting(matcher.group(2), true, false);
            String spaceLastName = withFirstname
                    ? " " + wordFormatting(matcher.group(2), false, true)
                    : "";
            fullName.add(firstName + spaceLastName);
        }
        return fullName;
    }

    private static String wordFormatting(String word, boolean getFullName, boolean withFirstname) {
        String lowercaseWord = word.toLowerCase();
        String firstLetter = lowercaseWord.substring(0, 1).toUpperCase();
        String wordWithoutFirstLetter = maximumLengthWord(lowercaseWord, withFirstname);
        return getFullName
                ? firstLetter + wordWithoutFirstLetter
                : firstLetter;
    }

    private static String maximumLengthWord(String word, boolean withFirstname){
        return withFirstname
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

public class Main {
    public static void main(String[] args) {
        String stringInput = "steward@cde.org; john.doe@abc.com; Alex.Boe@cde.org; fredrich@cde.org; Johnny.Depp+reg@cde.org; Årsén.öliynyk@example.com; Ruslan.Chao+reg@cde.org; іван.Квітка@фірма.укр; ölaf@example.com; juan2002@inbox.io; Rosa.Rio05@server5.io; AndrewAlexander.Fox@server.io; domenicusverduis@server.ro; Jack.Sparrow.Captain@tortuga.is";

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