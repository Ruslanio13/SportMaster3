import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

class BaseTest{
    BaseTest(){
        open("https://www.sportmaster.ru/about/");
    }
    public void close(){
        closeWebDriver();
    }
}
public class TestPronounsCount {
    private final String[] FIRST_FACE_PRONOUNS = {"я","меня","мне","меня","мной","мною","мы","нас","нам","нами"};
    private final String[] OTHER_PRONOUNS = {"ты","тебя","тебе","тобой","тобою","вы","вас","вам",
            "вами","он","его","нему","им","ним","нём","нем","она","ее","её",
            "нее","неё","ей","ней","ею","оно","они","их","них","ими","ними"};
    public int checkIsWordPronoun(String word){
        if (Arrays.asList(FIRST_FACE_PRONOUNS).contains(word))
            return 1;
        if (Arrays.asList(OTHER_PRONOUNS).contains(word))
            return -1;
        return 0;
    }
    @Test
    public void main() {
        BaseTest baseTest = new BaseTest();
        String allPageText = $(By.xpath("/html/body")).getText();
        String[] pageWords = Arrays.
                stream(Arrays.stream(allPageText.split("[^А-Яа-я]")).toArray()).
                map(Object::toString).
                filter(obj -> !Objects.equals(obj, "")).
                toArray(String[]::new);
        int pronounsCount = 0;
        for (String word : pageWords)
            pronounsCount += checkIsWordPronoun(word.toLowerCase());
        assertTrue(pronounsCount > 0);
        baseTest.close();
    }
}
