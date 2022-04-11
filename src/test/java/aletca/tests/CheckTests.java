package aletca.tests;

import aletca.helpers.DriverUtils;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckTests extends TestBase {

    @Test
    @DisplayName("Проверка наличия лого.")
    public void logoCheckTest() {
        step("перейти на страницу", () -> {
            open("https://smolensk.hh.ru/employer/3323853");
        });

        step("проверить правильность отображения лого", () -> {
            $(".employer-sidebar__logo").shouldHave(attribute("alt", "NABIX"));
        });
    }

    @Test
    @DisplayName("Проверка наличия заголовка.")
    void titleTest() {
        step("перейти на страницу", () ->
                open("https://smolensk.hh.ru/employer/3323853"));

        step("проверить наличие текста 'Вакансии компании NABIX'", () -> {
            String expectedTitle = "Вакансии компании NABIX";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Поиск вакансии")
    void jobSearch() {
        step("перейти на страницу", () ->
                open("https://smolensk.hh.ru/employer/3323853"));

        step("кликнуть на кнопку поиск", () -> {
            $(withText("Поиск")).click();
        });

        step("ввести в строку поиска junior selenide", () -> {
            $("[data-qa = 'search-input']").setValue("junior selenide").pressEnter();
        });

        step("получить результат поиска", () -> {
            $(".bloko-header-section-3").shouldHave(text("По запросу «junior selenide» ничего не найдено"));
        });
    }

    @Test
    @DisplayName("Проверка отсутствия ошибок в логах")
    @Issue("GLOUD-10054")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url 'https://smolensk.hh.ru/employer/3323853'", () ->
                open("https://smolensk.hh.ru/employer/3323853"));

        step("Проверка отсутствия в логах слова 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }
}