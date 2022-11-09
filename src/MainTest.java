import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.Objects;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selenide.open;


public class MainTest {
    String[] firstFace = {"я","меня","мне","меня","мной","мною","мы","нас","нам","нами"};
    String[] others = {"ты","тебя","тебе","тобой","тобою","вы","вас","вам",
            "вами","он","его","нему","им","ним","нём","нем","она","ее","её",
            "нее","неё","ей","ней","ею","оно","они","их","них","ими","ними"};

    @Test
    public void main() {
        open("https://www.sportmaster.ru/about/");
        String text = $(By.xpath("/html/body")).getText();
        String[] strings = Arrays.
                stream(Arrays.stream(text.split("[^А-Яа-я]")).toArray()).
                map(Object::toString).
                filter(obj -> !Objects.equals(obj, "")).
                toArray(String[]::new);
        int wordsCount = 0;
        for (String string : strings)
            wordsCount += checkWord(string.toLowerCase());
        assertTrue(wordsCount > 0);
    }

    public int checkWord(String word){
        if (Arrays.asList(firstFace).contains(word))
            return 1;
        if (Arrays.asList(others).contains(word))
            return -1;
        return 0;
    }
}
