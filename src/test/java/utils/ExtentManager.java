package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFileName = "PlivoAPITestReport.html";
    private static String filePath = System.getProperty("user.dir")+ "\\TestReport";
    private static String reportFile = filePath + "\\" + reportFileName;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports createInstance() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFile);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Plivo API Automation test results");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Plivo API Automation test results");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}
