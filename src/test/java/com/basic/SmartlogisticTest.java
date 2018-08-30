package com.basic;

import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SmartlogisticTest extends TestCase {

    public static Actionwords actionwords = new Actionwords();
    @BeforeClass
    public static void SetUp() throws Exception {
        actionwords.iStartBrowser();
    }
    // 1. Go to home page
    // 2. Assert title on page
    // Tags: MainScenario Critical Front-basic
    public void testOpenHomePage() {
        // Given I start browser
        actionwords.iStartBrowser();
        // When I open home page "http://smart_logistic.k.atwinta.ru"
        actionwords.iOpenHomePageHomePageUrl("http://smart_logistic.k.atwinta.ru");
        // Then title "Новый взгляд на покупки в IKEA" is displayed on page
        actionwords.titleIkeaTitleIsDisplayedOnPage("Новый взгляд на покупки в IKEA");
        // Given I close browser
        actionwords.iCloseBrowser();
    }
    // 
    // Tags: Critical Front-basic
    public void testOpenAuthPage() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open home page "http://smart_logistic.k.atwinta.ru/"
        actionwords.iOpenHomePageHomePageUrl("http://smart_logistic.k.atwinta.ru/");
        // When I click profile button "//*[@id="js-header"]/div[1]/div/div[2]/div/div/a"
        actionwords.iClickProfileButtonProfile("//*[@id=\"js-header\"]/div[1]/div/div[2]/div/div/a");
        // Then page contains text "Вход в личный кабинет"
        actionwords.pageContainsTextTextOnPage("Вход в личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }
    // 
    // Tags: Auth Critical Front-basic
    public void testSigningIn() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // When I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "emofmoqh"
        actionwords.iEnterPasswordPassword1("emofmoqh");
        // And I click sign-in button "Войти"
        actionwords.iClickSigninButtonSingInButtonText("Войти");
        // Then page contains text "Личный кабинет"
        actionwords.pageContainsTextTextOnPage("Личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }
    // 
    // Tags: Pre-Auth Critical Front-basic
    public void testLogout() {
        // Given I start browser
        actionwords.iStartBrowser();
        // Given I open auth page "http://smart_logistic.k.atwinta.ru/auth"
        actionwords.iOpenAuthPageAuthPageUrl("http://smart_logistic.k.atwinta.ru/auth");
        // And I enter mobile phone "9516190942"
        actionwords.iEnterMobilePhonePhone1("9516190942");
        // And I enter password "emofmoqh"
        actionwords.iEnterPasswordPassword1("emofmoqh");
        // And I click sign-in button "Войти"
        actionwords.iClickSigninButtonSingInButtonText("Войти");
        // When I click logout button "Выйти"
        actionwords.iClickLogoutButtonLogoutButtonText("Выйти");
        // Then page contains text "Вход в личный кабинет"
        actionwords.pageContainsTextTextOnPage("Вход в личный кабинет");
        // Given I close browser
        actionwords.iCloseBrowser();
    }

    // Предусловия:
    // 1. Пользователь должен авторизоваться
    // 
    // Сценарий:
    // 1. Нажимаем на кнопку - "Каталог"
    // 2. Выбираем случайную категорию
    // 3. Добавляем несколько случайных товаров в корзину
    // 4. Нажимаем на кнопку корзины
    // 
    // Результат:
    // На странице корзины отображаются все добавленные пользователем товары
    // 
    // Доп. условия:
    // 1. Выбранная категория должна содержать как минимум 6 и более товаров
    // 2. Количество добавляемых товаров выбирается случайным образом, где n это необходимое количество товаров - 1 < N < 9
    // 
    // Tags: Pre__Auth Cart AddItemToCart Critical Front-basic
    @Test
    public void testAddProductToCart() throws Exception {
        actionwords.iStartBrowser();
        // Given Signing in
        actionwords.signingIn();
        // When I click button ""
        actionwords.iClickButtonButtonXpath("//*[@id=\"js-header\"]/div[2]/div/div[1]/a");
        // And I choosing random category
        actionwords.iChoosingRandomCategory();
        // And I adding random products to cart
        actionwords.iAddingRandomProductsToCart();
        // And I click button ""
        actionwords.iClickButtonButtonXpath("//*[@id=\"js-header\"]/div[1]/div/div[2]/div[2]/a");
        // Then Cart contains all added products
        actionwords.cartContainsAllAddedProducts();
    }
    // Предусловия:
    // 1. Пользователь добавил товары в корзину и перешел на страницу корзины
    // 
    // Сценарий:
    // 1. Нажать на кнопку - "Оформить заказ"
    // 2. Ввести контактную информацию
    // 3. Ввести адрес доставки
    // 4. Выбрать способ оплаты
    // 5. Выбрать дату и время доставки
    // 6. Нажать на кнопку - "Перейти к оплате"
    // 
    // Результат:
    // Заказ формируется. Все расчеты происходят корректно.
    // 
    // Доп. условия:
    // 1. Корзина не должна быть пустой
    // 2. Контактная информация заполняется только при её отсутствии
    // 3. Вся информация о заказе сохраняется на время выполнения тест рана.
    // Tags: Pre-Auth Pre__AddingProductToCart Basic Critical Front-basic
    public void testCreateOrder() throws Exception {
        actionwords.signingIn();
        actionwords.addedProductsToCart();
        // And I enter contact info "null" "null" "null"
        actionwords.iEnterContactInfo("null", "null", "null");
        // And I select delivery "null" "null" "null" "null" "null" "null" "null"
        actionwords.iSelectDelivery("null", "null", "null", "null", "null", "null", "null");
        // And I select payment type "null"
        actionwords.iSelectPaymentType("null");
        // And I select delivery date and time "null" "null"
        actionwords.iSelectDeliveryDateAndTime("null", "null");
        // Then checkout order is created
        actionwords.checkoutOrderIsCreated();
    }
    @AfterClass
    public static void setDown() throws Exception
    {
        actionwords.iCloseBrowser();
    }
}