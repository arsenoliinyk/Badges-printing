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

        List<String> namesFromDefaultEmails = extractNamesFromEmailsListByPattern(emails, patternEmailDefault, true);
        List<String> namesEmailWithoutLastname = extractNamesFromEmailsListByPattern(emails, patternEmailWithoutLastname,  false);
        List<String> namesFromEmailWithSubaddressing = extractNamesFromEmailsListByPattern(emails, patternEmailWithSubaddressing,  true);

        return Stream.of(namesFromDefaultEmails, namesFromEmailWithSubaddressing, namesEmailWithoutLastname)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static List<String> extractNamesFromEmailsListByPattern(String emails, Pattern pattern, boolean withFirstName) {
        Matcher matcher = pattern.matcher(emails);
        return extractNames(matcher, withFirstName);
    }

    private static String makeFirstLetterCapital(String word, boolean getFullName) {
        String lowercaseWord = word.toLowerCase();
        String fistLetter = lowercaseWord.substring(0, 1).toUpperCase();
        String wordWithoutFirstLetter = lowercaseWord.substring(1,  maximumLengthWord(lowercaseWord));
        return getFullName
                ? fistLetter + wordWithoutFirstLetter
                : fistLetter;
    }

    private static int maximumLengthWord(String word){
        return Math.min(word.length(), 15);
    };

    public static List<String> extractNames(Matcher matcher, boolean withFirstname) {
        List<String> fullName = new ArrayList<>();
        while (matcher.find()) {
            String fistName =  withFirstname
                    ? makeFirstLetterCapital(matcher.group(1), true)
                    : makeFirstLetterCapital(matcher.group(2), true) ;
            String spaceLastName = withFirstname
                    ? " " + makeFirstLetterCapital(matcher.group(2), false)
                    : "";
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
        String stringInput = "steward@cde.org; john.doe@abc.com; Alex.Boe@cde.org; fredrich@cde.org; Johnny.Depp+reg@cde.org; Årsén.öliynyk@example.com; Ruslan.Chao+reg@cde.org; іван.Квітка@фірма.укр; ölaf@example.com; juan2002@inbox.io; Rosa.Rio05@server5.io; AndrewAlexandres.Fox@server.io; domenicusverduis@server.ro";

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