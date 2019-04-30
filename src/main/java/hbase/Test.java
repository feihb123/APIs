package hbase;

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\feihb\\OneDrive\\桌面\\B卷\\hbaseDemo.txt";
        String[][] strings = readFileByLines(filePath);
        HBaseUtils.putGsod(strings);
    }

    public static String[][] readFileByLines(String fileName) {
        String[][] data = new String[8][8];
        File file = new File(fileName);
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            //去掉第一行
             reader.readLine();
            //一次读一行，读入null时文件结束
            int i = 0;
            while ((tempString = reader.readLine()) != null) {
                System.out.println(tempString);
                String[] strings = tempString.split("/");
                for (int x = 0;x < 8;x++){
                    data[i][x] = strings[x];
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return data;
    }
}
