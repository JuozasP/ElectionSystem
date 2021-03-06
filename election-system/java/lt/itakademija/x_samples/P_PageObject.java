package lt.itakademija.x_samples;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Class for having all functions which required for Selenium Page Object: waits, setters, getters
 * Created by pauliusl on 2016-12-19.
 */
public class PageObject {
	   /** Time in seconds for selenium to wait expected element in javascript to appear */
    public static final int WAIT_FOR_ELEMENT = 60;

    /** Web browser driver. */
    protected WebDriver webDriver = null;

    /** Base URL of the server. Valid value example: {@code http://localhost:8080/}. */
    protected String baseUrl = "";

    /**
     * Creates page object instance. Sets {@code WebDriver} and {@code baseUrl}.
     * @param baseUrl       Web browser driver.
     * @param webDriver     Base URL of the server.
     */
    public PageObject(WebDriver webDriver, String baseUrl) {
        this.baseUrl = baseUrl;
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    //==================================================================================================================
    //--- waits --------------------------------------------------------------------------------------------------------
    //==================================================================================================================

    /**
     * Checks if the page represented by this page object is loaded. Uses JUnit assertions and throws {@link AssertionError} if page is not loaded.
     * Try to avoid using Thread.sleep() - all below waitFor... functions should be tried at first and only then if none is working use sleep
     */
    public void checkIsLoaded() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(pageLoadCondition);
    }

    /**
     * Looks for element in page
     */
    public boolean isElementPresent(By by) {
        try {
            webDriver.findElement(by);
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Function that checks if element is displayed
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Wait for element while it is listed in page's DOM structure and is visible
     */
    public void waitForElementToBeInDOM(WebElement element) {
        WebDriverWait wait = new WebDriverWait(webDriver, WAIT_FOR_ELEMENT);
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element, this is not check whether element is clickable;
     */
    public void waitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(webDriver, WAIT_FOR_ELEMENT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Wait until element is clickable
     */
    public void waitUntilElementToBeClickable(By by) {
        WebDriverWait wait = new WebDriverWait(webDriver, WAIT_FOR_ELEMENT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    //==================================================================================================================
    //--- setters ------------------------------------------------------------------------------------------------------
    //==================================================================================================================

    /**
     *  Just sets value in field
     * @param textField     Selenium web element object of text field.
     * @param value         Value to set to text field.
     */
    protected void setTextFieldValue(WebElement textField, String value)  {
        waitForElementToBeInDOM(textField);
        textField.sendKeys(value);
    }

    /**
     * Clears and sets {@code value} to {@code textField}.
     * @param textField     Selenium web element object of text field.
     * @param value         Value to set to text field.
     */
    protected void setTextFieldValueWithClear(WebElement textField, String value) {
        waitForElementToBeInDOM(textField);
        textField.clear();
        textField.sendKeys(value);
    }

    /**
     * Selects {@code visibleText} in {@code dropDownList}.
     * @param dropDownList  Selenium web element object of drop down list.
     * @param visibleText   Text to select in {@code dropDownList}.
     */
    protected void selectFromDropDown(WebElement dropDownList, String visibleText) {
        new Select(dropDownList).selectByVisibleText(visibleText);
    }

    /**
     * Selects {@code index} element in {@code dropDownList}.
     * @param dropDownList  Selenium web element object of drop down list.
     * @param index         index of element to select {@code dropDownList}.
     */
    protected void selectFromDropDownByIndex(WebElement dropDownList, int index) {
        new Select(dropDownList).selectByIndex(index);
    }

    /**
     * Selects {@code index} element in {@code dropDownList}.
     * @param dropDownList  Selenium web element object of drop down list.
     * @param value         value of element to select {@code dropDownList}.
     */
    protected void selectFromDropDownByValue(WebElement dropDownList, String value) {
        new Select(dropDownList).selectByValue(value);
    }

    /**
     * Selects checkbox if required
     * @param valueElement  webElement which is checked if element is selected or not
     * @param clickElement  webElement which is actually clicked
     * @param value
     */
    protected void setCheckBox(WebElement valueElement, WebElement clickElement, String value) {
        if(!getFieldAttributeValue(valueElement, "ng-reflect-model").equals(value)) {
            clickElement.click();
        }
    }

    protected void setTextFieldValue(String xpath, String value) {
        WebElement element = getWebElement(xpath);
        setTextFieldValueWithClear(element, value);
    }

    protected void setTextFieldValueWithClick(String xpath, String value) {
        WebElement element = getWebElement(xpath);
        element.click();
        setTextFieldValue(element, value);
    }

    protected void setTextFieldValueWithClick(WebElement element, String value) {
        element.click();
        setTextFieldValue(element, value);
    }

    protected void setTextFieldValueWithClickClear(WebElement element, String value) {
        element.click();
        element.clear();
        setTextFieldValue(element, value);
    }

    //==================================================================================================================
    //--- getters ------------------------------------------------------------------------------------------------------
    //==================================================================================================================

    public WebElement getWebElement(String xpath) {
        WebElement element = null;
        try {
            element  = webDriver.findElement(By.xpath(xpath));
            waitForElementToBeInDOM(element);
            isElementDisplayed(element);
            return element;
        } catch (Exception e) {
            System.out.println("Can't find '" + xpath + "' element");
            return null;
        }
    }

    /**
     * Returns text from given webElement
     * @param element
     * @return
     */
    protected String getTextFieldValue(WebElement element) {
        try {
            waitForElementToBeInDOM(element);
            return element.getText();
        } catch (Exception e) {
        	System.out.println("Can't get text for '" + element.getTagName() + "' element");
            return "";
        }
    }

    /**
     * Returns text from given xpath
     * @param xpath
     * @return
     */
    protected String getTextFieldValue(String xpath) {
        String value = "";
        try {
            WebElement element = getWebElement(xpath);
            value = element.getText();

            if (value == null) value = "";
            return value;
        } catch (Exception e) {
        	System.out.println("Can't get text for '" + xpath + "' element");
            return "";
        }
    }

    /**
     * Return attribute's value from given webElement
     * @param webElement
     * @param attribute
     * @return
     */
    protected String getFieldAttributeValue(WebElement webElement, String attribute) {
        String value = "";
        try {
            waitForElementToBeInDOM(webElement);
            value = webElement.getAttribute(attribute);

            if (value == null) value = "";
            return value;
        } catch (Exception e) {
        	System.out.println("Can't get '" + attribute + "' attribute for '" + webElement.getTagName() + "' element");
            return "";
        }
    }

    /**
     * Return attribute's value from given xpath
     * @param xpath
     * @param attribute
     * @return
     */
    protected String getFieldAttributeValue(String xpath, String attribute) {
        try {
            WebElement element = getWebElement(xpath);
            waitForElementToBeInDOM(element);
            return element.getAttribute(attribute);
        } catch (Exception e) {
        	System.out.println("Can't get '" + attribute + "' attribute for '" + xpath + "' element");
            return "";
        }
    }
}
