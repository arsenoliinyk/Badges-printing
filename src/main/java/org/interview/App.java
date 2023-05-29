import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Badge {
    public Badge() {}

    private static String makeFirstLetterCapital(String word, boolean getFullName) {
        String lowercaseWord = word.toLowerCase();
        String fistLetter = lowercaseWord.substring(0, 1).toUpperCase();
        String wordWithoutFirstLetter = lowercaseWord.substring(1);
        return getFullName
                ? fistLetter + wordWithoutFirstLetter
                : fistLetter;
    }

    public static List<String> convertEmailsToFullNames(String emails) {
        //Pattern pattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
        //Pattern pattern = Pattern.compile("^(?=.{1,64}@)[\\\\p{L}0-9_-]+(\\\\.[\\\\p{L}0-9_-]+)*@[^-][\\\\p{L}0-9-]+(\\\\.[\\\\p{L}0-9-]+)*(\\\\.[\\\\p{L}]{2,})$");


        //Pattern pattern = Pattern.compile("([A-Za-z]+)\\.([A-Za-z]+)@([A-Za-z0-9.-]+)");
        Pattern pattern = Pattern.compile("([\\p{L}]+)\\.([\\p{L}]+)@([\\p{L}0-9.-]+)");

        Matcher matcher = pattern.matcher(emails);
        List<String> fullName = new ArrayList<>();
        while (matcher.find()) {
            String fistName = makeFirstLetterCapital(matcher.group(1), true);
            String lastName = makeFirstLetterCapital(matcher.group(2), false);
            fullName.add(fistName + " " + lastName);
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
        String stringInput = "john.doe@abc.com; Alex.Boe@cde.org; Årsén.öliynyk@example.com; іван.Квітка@фірма.укр";

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