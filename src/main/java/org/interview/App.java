package org.interview;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String stringInput = "steward@cde.org; john.doe@abc.com; Alex.Boe@cde.org; fredrich@cde.org; Johnny.Depp+reg@cde.org; Årsén.öliynyk@example.com; Ruslan.Chao+reg@cde.org; іван.Квітка@фірма.укр; ölaf@example.com; juan2002@inbox.io; Rosa.Rio05@server5.io; AndrewAlexander.Fox@server.io; domenicusverduis@server.ro; Jack.Sparrow.Captain@tortuga.is";

        List<String> fullNames = Badge.convertEmailsToFullNames(stringInput);
        Badge.printFullNamesList(fullNames);

        /*Badge badge = new Badge();
        ArrayList<String> actualList = badge.returnEmailList(stringInput);*/
        /*ArrayList<String> expectedBadgeList = new ArrayList<String>();
        expectedBadgeList.add( "John D" );
        expectedBadgeList.add( "Alex B" );
        System.out.println(expectedBadgeList);*/
    }

}