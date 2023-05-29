package org.interview;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testApp() {
        //Arrange
        String stringInput = "steward@cde.org; john.doe@abc.com; Alex.Boe@cde.org; fredrich@cde.org; Johnny.Depp+reg@cde.org; Årsén.öliynyk@example.com; Ruslan.Chao+reg@cde.org; іван.Квітка@фірма.укр; ölaf@example.com; juan2002@inbox.io; Rosa.Rio05@server5.io; AndrewAlexander.Fox@server.io; domenicusverduis@server.ro; Jack.Sparrow.Captain@tortuga.is";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("John D", "Alex B", "Årsén Ö", "Іван К", "Rosa R", "Andrewalexand F", "Jack S", "Johnny D", "Ruslan C", "Steward", "Fredrich", "Ölaf", "Juan2002", "Domenicusverdui");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingDefaultEmails() {
        //Arrange
        String stringInput = "john.doe@abc.com; Alex.Boe@cde.org";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("John D", "Alex B");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingInternationalEmails() {
        //Arrange
        String stringInput = "Årsén.öliynyk@example.com; іван.Квітка@фірма.укр";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Årsén Ö", "Іван К");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingEmailsWithoutLastname() {
        //Arrange
        String stringInput = "steward@cde.org; fredrich@cde.org; ölaf@example.com";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Steward", "Fredrich", "Ölaf");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingEmailsWithPlusSigns() {
        //Arrange
        String stringInput = "Ruslan.Chao+reg@cde.org; Johnny.Depp+reg@cde.org";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Ruslan C", "Johnny D");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingEmailsWithDigits() {
        //Arrange
        String stringInput = "juan2002@inbox.io; Rosa.Rio05@server5.io";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Rosa R", "Juan2002");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingEmailsWithMoreThanFifteenSigns() {
        //Arrange
        String stringInput = "AndrewAlexander.Fox@server.io; domenicusverduis@server.ro";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Andrewalexand F", "Domenicusverdui");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testConvertingEmailsWithMultipleDots() {
        //Arrange
        String stringInput = "Jack.Sparrow.Captain@tortuga.is; Bruce.Wayne.Batman.Superhero@tortuga.is";
        //Act
        List<String> actualResult = Badge.convertEmailsToFullNames(stringInput);
        List<String> expectedResult = List.of("Jack S", "Bruce W");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWordFormatting() {
        //Arrange
        String firstNameInDefaultEmail = "Jack";
        String lastNameInDefaultEmail = "doe";
        String firstNameWithMoreThanFifteenSigns = "AndrewAlexander";
        String firstNameWithMoreThanFifteenSignsInEmailWithoutLastName = "domenicusverduis";
        //Act
        String formattedFirstNameInDefaultEmail = Badge.wordFormatting(firstNameInDefaultEmail, true, true);
        String formattedLastNameInDefaultEmail = Badge.wordFormatting(lastNameInDefaultEmail, false, true);
        String formattedFirstNameWithMoreThanFifteenSignsInDefaultEmail = Badge.wordFormatting(firstNameWithMoreThanFifteenSigns, true, true);
        String formattedFirstNameWithMoreThanFifteenSignsInEmailWithoutLastName = Badge.wordFormatting(firstNameWithMoreThanFifteenSignsInEmailWithoutLastName, true, false);
        List<String> actualResult = List.of(formattedFirstNameInDefaultEmail,formattedLastNameInDefaultEmail, formattedFirstNameWithMoreThanFifteenSignsInDefaultEmail, formattedFirstNameWithMoreThanFifteenSignsInEmailWithoutLastName);
        List<String> expectedResult = List.of("Jack", "D", "Andrewalexand", "Domenicusverdui");
        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPrintFullNamesList() {
        //Arrange
        ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleContent));
        List<String> listOfNotFormattedFullNames = Arrays.asList("John D", "Alex B", "Årsén Ö", "Іван К");
        //Act
        Badge.printFullNamesList(listOfNotFormattedFullNames);
        String actualResult = consoleContent.toString().trim();
        String expectedOutput = "\"John D\", \"Alex B\", \"Årsén Ö\", \"Іван К\"";
        //Assert
        assertEquals(expectedOutput, actualResult);
        System.setOut(System.out); // Reset System.out
    }
}
