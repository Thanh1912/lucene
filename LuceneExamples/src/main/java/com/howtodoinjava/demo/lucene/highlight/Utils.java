package com.howtodoinjava.demo.lucene.highlight;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Utils {

    private static List<String> test(String path) throws IOException {
        List<String> cutContent = new ArrayList<>();

        String content = "Tạp chí Time cho biết, các chuyên gia răng hàm mặt có một phát kiến đặc biệt." +
                "Những chiếc răng bị gẫy bất ngờ, (chẳng hạn trong trận đấu quyền Anh), có thể bảo vệ bằng cách thả ngay vào sữa tươi." +
                "Sữa sẽ giữ cho răng khó bị vi khuẩn xâm nhập, đồng thời nuôi sống răng." +
                "Khi đó bạn còn khoảng 1 giờ đồng hồ để chạy đến nha sĩ trồng lại chúng." +
                "Theo ông Daniel Lavenchy ở Tổ chức Sức khoẻ Thế giới thì hiện đã có 170 triệu người mắc bệnh viêm gan C trên trên toàn thế giới." +
                "Nhưng một số phương pháp điều trị mới đang được nghiên cứu ở Mỹ đã có những kết quả thử nghiệm đầu tiên." +
                "Theo tiến sĩ Lawrence Rothman, một bác sĩ đường ruột có danh tiếng, thì đây là phương pháp tốt nhất hiện có." +
                "Phương pháp Rebetron này kết hợp rivavirin và interferon cùng một lúc.";
        String[] arr = content.split("[//.]");
        cutContent = Arrays.asList(arr);
        return cutContent;
    }

    public static String getContent(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }

    public static String[] convertStringFileToList(String path) throws Exception {
        File file = new File(path);
        String content = getContent(file);
        String[] arr = content.split("[//.]");
        return arr;
    }

    public static double percentSentence(String Tag, String content) throws Exception {
        if (content == null) return 0;
        double count = content.split(Tag).length-1;
        double countWord = countWordsUsingStringTokenizer(content);
        return (count / countWord)*100;
    }

    public static double countWord(String content) throws Exception {
        if (content == null) return 0;
        int count = content.split("\\s+").length;
        return count;
    }

    public static int countWordsUsingStringTokenizer(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(sentence);
        return tokens.countTokens();
    }

    public static void main(String[] args) throws IOException {
//        List<String> cutContent = test("");
////        cutContent.forEach((arr) -> {
////            System.out.println(arr.toString());
////        });
        try {
            double percent = percentSentence("<B>", "Cứ như <B>lộ</B> <B>trình <B>chúng <B>tôi</B> <B>tôi</B> đã đi từ VN sang thì <B>excited</B> <B>by</B> <B>private</B>");
            System.out.println(percent);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
