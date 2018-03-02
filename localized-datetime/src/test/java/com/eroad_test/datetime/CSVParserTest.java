package com.eroad_test.datetime;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by azizahmed.khan on 28/02/18.
 */

/**
 *EROAD

 Java Technical Test

 Please complete Part two of the test within 24 hours and send your response back via email
 within the time limit.

 PART TWO
 Develop a small application to read a CSV with a UTC datetime,
 latitude and longitude columns and append the timezone the vehicle is
 in and the localised datetime. See example of CSV input and output below.
 We will then run this over several test files with several rows of data.

 Input
 2013-07-10 02:52:49,-44.490947,171.220966
 2013-07-10 02:52:49,-33.912167,151.215820

 Output
 2013-07-10 02:52:49,-44.490947,171.220966,Pacific/Auckland,2013-07-10T14:52:49
 2013-07-10 02:52:49,-33.912167,151.215820,Australia/Sydney,2013-07-10T12:52:49

 2013-07-10 02:52:49,-33.912167,151.21582,Australia/Sydney,2013-07-10T12:52:49

 *
* */
public class CSVParserTest {

    @Test
    public void readCSVFile() {
        CSVParser csvParser = new CSVParser();
        // I am said that the following is not working, It should and must work. I need to see this in detail
        // csvParser.readWriteCSVFile();
        csvParser.writeCSVFile(csvParser.readCSVFile());
    }

    @Test
    public void parseTimeLine() {
        CSVParser csvParser = new CSVParser();
        LocalizedDataTime localizedDataTime = csvParser.parseTimeLine("2013-07-10 02:52:49,-44.490947,171.220966");
        assertNotNull(localizedDataTime);
        assertEquals("2013-07-10 02:52:49,-44.490947,171.220966",localizedDataTime.toStringBeforeLocalTime());
        //localizedDataTime = csvParser.parseTimeLine("2013-07-10 02:52:49,-44.490947,171.220966");
        //assertNotNull(localizedDataTime);
    }

    //public
}