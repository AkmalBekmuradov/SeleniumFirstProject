import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class FirstTest {
    WebDriver driver = new ChromeDriver();

    void scrollTo(int height){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0, " + height + ")");
    }

    //Описание элементов страницы
    By cardFrom = By.xpath("//input[@data-qa-node='numberdebitSource']");
    By payerName = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
    By payerSurname = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
    By expDate = By.xpath("//input[@data-qa-node='expiredebitSource']");
    By cvv = By.xpath("//input[@data-qa-node='cvvdebitSource']");
    By cardTo = By.xpath("//input[@data-qa-node='numberreceiver']");
    By receiverName = By.xpath("//input[@data-qa-node='firstNamereceiver']");
    By receiverSurname = By.xpath("//input[@data-qa-node='lastNamereceiver']");
    By amount = By.xpath("//input[@data-qa-node='amount']");
    By toggleComment = By.xpath("//span[@data-qa-node='toggle-comment']");
    By inputComment = By.xpath("//textarea[@data-qa-node='comment']");
    By termsLink = By.xpath("//a[@href='https://privatbank.ua/terms']");
    By submitBtn = By.xpath("//button[@type='submit']");
    // UI elements for assertions
    By actualCardFrom = By.xpath("//span[@data-qa-node='payer-card']");
    By actualCardTo = By.xpath("//span[@data-qa-node='receiver-card']");
    By actualReceiverFIO = By.xpath("//div[@data-qa-node='receiver-name']");
    By actualReceiverAmount = By.xpath("//div[@data-qa-node='receiver-amount']");
    By commentActual = By.xpath("//div[@data-qa-node='comment']");
    By SumInputMobiPay = By.xpath("//input[@data-qa-node='amount']");
    By TelNumberMobiPay = By.xpath("//input[@data-qa-node='phone-number']");
    By cardFromMobiPay = By.xpath("//input[@data-qa-node='numberdebitSource']");
    By expDateMobiPay = By.xpath("//input[@data-qa-node='expiredebitSource']");
    By cvvMobiPay = By.xpath("//input[@data-qa-node='cvvdebitSource']");
    By payerNameMobiPay = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
    By payerSurnameMobiPay = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
    By submitBtnMobiPay = By.xpath("//button[@data-qa-node='submit']");

    @Test
    void checkAddToBasketMinPaymentSum() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get("https://next.privat24.ua/money-transfer/card");
        driver.manage().window().fullscreen();
        driver.findElement(cardFrom).sendKeys("4004159115449003");
        driver.findElement(payerName).sendKeys("Petro");
        driver.findElement(payerSurname).sendKeys("Petrovich");
        driver.findElement(expDate).sendKeys("1225");
        driver.findElement(cvv).sendKeys("111");
        driver.findElement(cardTo).sendKeys("5309233034765085");
        driver.findElement(receiverName).sendKeys("Stepan");
        driver.findElement(receiverSurname).sendKeys("Stepanovich");
        driver.findElement(amount).sendKeys("500");
        driver.findElement(toggleComment).click();
        driver.findElement(inputComment).sendKeys("hillel qa course");
        driver.findElement(submitBtn).submit();
        //Assertions
        Assertions.assertEquals("4004 1591 1544 9003", driver.findElement(actualCardFrom).getText());
        Assertions.assertEquals("5309 2330 3476 5085", driver.findElement(actualCardTo).getText());
        Assertions.assertEquals("STEPAN STEPANOVICH", driver.findElement(actualReceiverFIO).getText());
        Assertions.assertEquals("500 UAH", driver.findElement(actualReceiverAmount).getText());
        Assertions.assertEquals("hillel qa course", driver.findElement(commentActual).getText());
        driver.close();
    }

    @Test
    void checkTermsAndConditions() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get("https://next.privat24.ua/money-transfer/card");
        driver.findElement(termsLink).click();
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

        //Костыль для скролла вверх
        scrollTo(280);

        driver.switchTo().frame("frame");

        Assertions.assertEquals("https://privatbank.ua/terms", driver.getCurrentUrl());
        Assertions.assertEquals("Умови та правила", driver.getTitle());
        Assertions.assertEquals("Умови та правила надання банківських послуг",
                driver.findElement(By.xpath("//a[@href='/main/?lang=uk']")).getText());

    }

    @Test
    void checkPrivatMobiPayment() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get("https://next.privat24.ua/mobile");
        driver.manage().window().fullscreen();
        //code to clear input field
        driver.findElement(TelNumberMobiPay).sendKeys("997114177");
        driver.findElement(SumInputMobiPay).sendKeys(Keys.CONTROL + "a");
        driver.findElement(SumInputMobiPay).sendKeys(Keys.DELETE);
        driver.findElement(SumInputMobiPay).sendKeys("50");
        driver.findElement(cardFromMobiPay).sendKeys("4004159115449003");
        driver.findElement(expDateMobiPay).sendKeys("0325");
        driver.findElement(cvvMobiPay).sendKeys("222");
        driver.findElement(payerNameMobiPay).sendKeys("Test");
        driver.findElement(payerSurnameMobiPay).sendKeys("Testovich");
        driver.findElement(submitBtnMobiPay).submit();
    }
}
