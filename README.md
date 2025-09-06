# Test_Automation_Modular_Framework

## 📌 Overview
This is a **Modular Test Automation Framework** built using **Selenium WebDriver, TestNG, and Allure Reporting**.  
The framework follows **Page Object Model (POM)** and **Data-Driven Testing** to ensure reusability, maintainability, and scalability.

## Key Features 
* Modular design with Page Object Model (POM).
* Data-driven testing with JSON.
* Screenshot capture on failure.
* Configurable test data & environment settings.
* Parallel Execution → Supported using testng.xml + ThreadLocal WebDriver.
---

## 🏗 Project Structure
```text
Moduler\_Test\_Automation\_Framework/
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── actions
│   │   │   │   ├── BrowserActions.java
│   │   │   │   └── UIActions.java
│   │   │   │
│   │   │   ├── dataDriven
│   │   │   │   └── JsonDataReader.java
│   │   │   │
│   │   │   ├── pages
│   │   │   │   ├── BingHomePage.java
│   │   │   │   └── BingSearchResults.java
│   │   │   │
│   │   │   └── utils
│   │   │       ├── ConfigReader.java
│   │   │       └── ScreenShot.java
│   │   │
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   ├── baseTest
│       │   │   └── BaseTest.java
│       │   │
│       │   ├── bingTests
│       │   │   ├── BingTests.java
│       │   │   └── ScreenshotListener.java
│       │   │
│       │   └── utils
│       │
│       └── resources
│           ├── config.properties
│           └── testData.json
│
├── target/                  # Compiled files
├── test-output/             # TestNG reports
├── .gitignore
├── bingTests.xml            # TestNG suite file
├── pom.xml                  # Maven dependencies and plugins
└── testng.xml               # TestNG suite file for parallel execution

```

## Explanation:
* actions/ → Contains wrapper classes for browser and UI interactions.
* dataDriven/ → Handles test data input (JSON, etc.).
* pages/ → Page Object Model classes.
* utils/ → Utility classes like config readers and screenshots.
* baseTest/ → Common setup and teardown logic.
* bingTests/ → Test cases related to Bing.
* resources/ → Configuration and test data files.
* pom.xml → Maven configuration file.
* testng.xml → Test suite execution file.
---
## ⚙️ Tools & Libraries
- **Java 11+**
- **Maven** – build & dependency management
- **Selenium WebDriver** – browser automation
- **WebDriverManager** – automatic browser driver management
- **TestNG** – test execution framework
- **Allure** – advanced reporting
- **JSON + Properties Files** – external test data & configuration
---

## 🌐 Browser Support
The framework supports multiple browsers:
- **Google Chrome**
- **Mozilla Firefox**
- **Microsoft Edge**

Browser choice can be controlled via:
- `config.properties`
- TestNG `xml` parameters
- Maven command line `-Dbrowser=chrome`

---

## 🧪 Test Scenarios Implemented
1. **Search Test** – Validate Bing search functionality.
2. **Related Search Test** – Validate related searches are displayed.
3. **Pagination Test** – Validate results when navigating to next pages.
4. **Comparison Test** – Compare search results across different pages.

---

## ▶️ How to Run

### Run with Maven

## 1. You Can Do
```bash
mvn clean test
```
### 2. Or Choose The Test Browser
```bash
mvn test -Dbrowser=firefox
```
### 3. Or Run a Specific Test Suite
```bash
mvn test "-DsuiteFileXml=bingTests.xml"
```
## Generate Allure Report
```bash
allure serve
```

### 2. Run Tests from IDE
* Open project in IntelliJ IDEA.
* Right-click on `bingTests.xml` → **Run 'bingTests.xml'**.
* Or Right-click on `testng.xml` → **Run 'testng.xml' for parallel execution**
  

## Limitation
Tests can currently only be executed locally.CI/CD execution is not yet implemented.

