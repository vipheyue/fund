import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FundMain {
    public static void main(String[] args) {
        ArrayList<String[]> fundLists = new ArrayList<>();
        fundLists.add(new String[]{"519736", "2020-08-11"});
        fundLists.add(new String[]{"690007", "2020-08-11"});
        fundLists.add(new String[]{"161726", "2020-08-11"});
        fundLists.add(new String[]{"161725", "2020-08-25"});
        fundLists.add(new String[]{"163402", "2020-08-13"});
        fundLists.add(new String[]{"163415", "2020-08-14"});
        fundLists.add(new String[]{"161005", "2020-08-11"});
        fundLists.add(new String[]{"001985", "2020-08-11"});
        fundLists.add(new String[]{"519736", "2020-08-11"});
        fundLists.add(new String[]{"519069", "2020-08-11"});
        fundLists.add(new String[]{"470018", "2020-08-11"});
        fundLists.add(new String[]{"519050", "2020-08-14"});
        fundLists.add(new String[]{"501090", "2020-08-14"});
        fundLists.add(new String[]{"001410", "2020-08-19"});
        fundLists.add(new String[]{"001044", "2020-08-19"});
        fundLists.add(new String[]{"001044", "2020-08-19"});
        fundLists.add(new String[]{"008086", "2020-08-17"});
        fundLists.add(new String[]{"007474", "2020-08-17"});
        fundLists.add(new String[]{"001178", "2020-08-17"});
        fundLists.add(new String[]{"007300", "2020-08-11"});
        fundLists.add(new String[]{"001938", "2020-08-11"});
        fundLists.add(new String[]{"003095", "2020-08-12"});
//        fundLists.add(new String[]{"009777", "2020-08-11"});
        fundLists.add(new String[]{"004753", "2020-08-11"});
        fundLists.add(new String[]{"001469", "2020-08-11"});
        fundLists.add(new String[]{"180031", "2020-08-11"});
        fundLists.add(new String[]{"260108", "2020-08-11"});
        fundLists.add(new String[]{"213001", "2020-08-11"});
        fundLists.add(new String[]{"002482", "2020-08-11"});
        fundLists.add(new String[]{"002482", "2020-08-11"});
        fundLists.add(new String[]{"690007", "2020-08-11"});
        fundLists.add(new String[]{"001629", "2020-08-11"});
        fundLists.add(new String[]{"050026", "2020-08-11"});
        fundLists.add(new String[]{"050023", "2020-08-11"});
        fundLists.add(new String[]{"202101", "2020-08-11"});
        fundLists.add(new String[]{"001230", "2020-08-11"});
        fundLists.add(new String[]{"003110", "2020-08-11"});
        fundLists.add(new String[]{"003109", "2020-08-11"});


        fundLists.forEach(new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) {
                generateShell(strings[0]);
                query(strings[0], strings[1]);
            }
        });


    }

    private static void generateShell(String name) {
        String line1 = "curl -H 'Host: www.glpomelo.cn' -H 'Accept: application/json, text/javascript, */*; q=0.01' -H 'X-Requested-With: XMLHttpRequest' -H 'Accept-Language: zh-cn' -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' -H 'Origin: http://www.glpomelo.cn' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Safari/605.1.15' -H 'Referer: http://www.glpomelo.cn/k-analysis.html' -H 'Cookie: vertx-web.session=079edaf18b48ee40497c36825d4a49bc' --data-binary \"period=year&code="+name+"&col=net_value%2Cma10%2Cma30%2Cma60%2Cma90%2Cma120\" --compressed 'http://www.glpomelo.cn/data/k_line'\n";
        // 创建一个FileWriter对象，该对象一被初始化就必须明确被操作的文件。
        // 而且该文件会被创建到指定目录下。如果该目录下已有同名文件，将被覆盖。
        // 其实该步就是在明确数据要存放的目的地。
        try {
        FileWriter fw = new FileWriter("./data/"+name+".sh" );
        // 调用Write方法，将字符串写入到流中。
        fw.write( line1 );
        // 刷新流对象中的缓冲的数据，将数据刷到目的地中。
        fw.flush();
        // 关闭流资源，但是关闭之前会刷新一次内部的缓冲的数据。将数据刷到目的地中。
        // 和flush区别:flush刷新后，流可以继续使用，close刷新后，会将流关闭。
        fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String jsonResult = CommandUtil.run(new String[]{"chmod", "-R", "777", "./data/"});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void query(String name, String startData) {
        //        String name = "/Users/heyue/IdeaProjects/fund/data/690007民生加银景气行业混合.txt";
        String path = "/Users/heyue/IdeaProjects/fund/data/" + name+".sh";
        try {
            String jsonResult = CommandUtil.run(path);
//            System.out.println(jsonResult);
            FundUtils.fundBuyResult(jsonResult, startData, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
