package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        open("http://localhost:9999/");

        $("[placeholder=\"Город\"]").setValue(DataGenerator.generateCity("ru"));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[name=\"name\"]").setValue(DataGenerator.generateName("ru"));
        $("[name=\"phone\"]").setValue(DataGenerator.generatePhone("ru"));
        $(".checkbox__box").click();
        $(byText("Запланировать")).click();

        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText(firstMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));

        $(".icon_size_s").click();
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(DataGenerator.generateDate(daysToAddForSecondMeeting));
        $(byText("Запланировать")).click();

        $(byText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Перепланировать")).click();


        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно запланирована на")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText(secondMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));


    }


}