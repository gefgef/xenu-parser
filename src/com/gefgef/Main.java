package com.gefgef;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "*.txt";
        String url = "https://www.domainname.com";
        List<String> fileContent = read(fileName);
        List<String> newFile = getRightStrings(fileContent, url);
        write("sitemap.txt", newFile);
    }

    public static void write(String fileName, List<String> text) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (String s : text) {
                    out.println(s);
                }
            }
            finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> read(String fileName) throws FileNotFoundException {
        List<String> list = new ArrayList<>();
        isExist(fileName);
        File file = new File(fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile() ));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    list.add(s);
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static void isExist(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }

    public static List<String> getRightStrings(List<String> content, String url) {
        List<String> parsedContent = new ArrayList<>();
        for (String string : content) {
            if (string.contains(url)) {
                int start = string.indexOf(url);
                int end = string.indexOf("\"", start);
                String finalString = string.substring(start, end);
                parsedContent.add( finalString.replace(url, "\"GET "));
            }
        }
        return parsedContent;
    }

}
