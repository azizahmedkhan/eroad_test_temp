package com.eroad_test.datetime;

import com.google.maps.GeoApiContext;
import com.google.maps.TimeZoneApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by azizahmed.khan on 28/02/18.
 */
public class CSVParser {

    //private final String csvName = "timeAndLocation.csv";
    private String csvFile = CSVParser.class.getClassLoader().getResource("timeAndLocation.csv").getFile();
    //2013-07-10 02:52:49,-33.912167,151.215820
    //2013-07-10 02:52:49,-33.912167,151.215820,Australia/Sydney,2013-07-10T12:52:49
    private static Pattern pattern =
            Pattern.compile("(^20\\d{2}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}),(\\-?\\d+\\.\\d+?),\\s*(\\-?\\d+\\.\\d+?)");


    public void readWriteCSVFile() {
        RandomAccessFile aFile     = null;
        try {

            aFile = new RandomAccessFile(csvFile, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (FileChannel inChannel = aFile.getChannel()) {
            //FileChannel.open(aFile.get)
            //inChannel.
            long lines = aFile.length()/41;
            System.out.println("files size"+aFile.length());
            System.out.println(lines);
            ByteBuffer buf = ByteBuffer.allocate(41);
            inChannel.read(buf);

            LocalizedDataTime localizedDataTime = parseTimeLine(new String(buf.array(), "UTF-8"));
            localizedDataTime.setTimeZone(getTimeZone(localizedDataTime.getLatitude(),
                    localizedDataTime.getLongitude()));
            localizedDataTime.setLocalDateTime(getLocalizeTime(localizedDataTime.getUTCDateTime(),
                    localizedDataTime.getTimeZone()));
            System.out.println("Result ["+localizedDataTime);

            //buf.clear();
            buf = ByteBuffer.allocate(localizedDataTime.toString().length());
            buf.clear();
            buf.put(localizedDataTime.toString().getBytes());
            buf.flip();
            inChannel.position(0);
           // while(buf.hasRemaining()) {
                inChannel.write(buf);
            //aFile.write();
            //}

            inChannel.force(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

        public ArrayList<String> readCSVFile() {
            ArrayList<String> lines = new ArrayList<String>();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(csvFile)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        LocalizedDataTime localizedDataTime = parseTimeLine(line);
                        localizedDataTime.setTimeZone(getTimeZone(localizedDataTime.getLatitude(),
                                localizedDataTime.getLongitude()));
                        localizedDataTime.setLocalDateTime(getLocalizeTime(localizedDataTime.getUTCDateTime(),
                                localizedDataTime.getTimeZone()));
                        lines.add(localizedDataTime.toString());
                        System.out.println("Result [" + localizedDataTime);
                    }catch(Exception e) {
                            e.printStackTrace();
                    }

                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        }

    public void writeCSVFile(ArrayList<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))){

            for (String line :lines ) {
                bw.write(line);
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalizedDataTime parseTimeLine(String timeLine) {
        Matcher matcher =
                pattern.matcher(timeLine);
        //Supplier<LocalizedDataTime> localizedDataTime = null;
        LocalizedDataTime localizedDataTime = null;
        if(matcher.find()) {
            //localizedDataTime =  LocalizedDataTime::new;
            localizedDataTime = new LocalizedDataTime();
            localizedDataTime.setUTCDateTime(matcher.group(1));
            localizedDataTime.setLatitude(matcher.group(2));
            localizedDataTime.setLongitude(matcher.group(3));

        }
        return localizedDataTime;
    }

    public String getTimeZone(String latitude, String longitude   ) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCWSCp65hEy0FrtSBntgnZGK_TM0HAfTB8")
                .build();
        LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
        TimeZone tz = null;
        try {
            tz = TimeZoneApi.getTimeZone(context, latLng).await();
      } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tz.getID();
    }


    public String getLocalizeTime(String utcDateTime, String timeZone) {
        //2013-07-10T12:52:49
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime utcDateTime1 = LocalDateTime.parse(utcDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime utcZonedTime = ZonedDateTime.of(utcDateTime1,ZoneOffset.UTC);

        ZonedDateTime localDateTime= utcZonedTime.withZoneSameInstant(ZoneId.of(timeZone));// ZoneId.of(tz.getID()));
        String tt= localDateTime.format( formatter);
        return tt;
    }

    }
