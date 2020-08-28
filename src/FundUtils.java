import com.google.gson.Gson;

import java.util.List;

public class FundUtils {


    public static void fundBuyResult(String josn, String startData, String name) {
        try {
//            String json = StreamTool.read(new FileInputStream(name)); // 直接读下载好的Json文件
            FundBean fundBean = new Gson().fromJson(josn, FundBean.class);

//一年250天 按3年 750天取平均值  累计净值
            List<Double> net_value_list = fundBean.getNet_value();
            int startInvestDayIndex = foundIndexByData(startData, fundBean.getDate_Line());
            int end = net_value_list.size();
            Double average = queryStart2EndAverage(startInvestDayIndex, end, net_value_list);
//            System.out.println(name+" 我的定投平均净值:"+average);
//            printDay(average, startInvestDayIndex, end, net_value_list, fundBean.getDate_Line());
            buyResult(net_value_list, average, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int foundIndexByData(String startData, List<String> date_line) {
        int index = date_line.indexOf(startData);
        String s = index + "";
        return index;
    }

    private static void buyResult(List<Double> net_value_list, Double average, String name) {
        Double lastNetValue = net_value_list.get(net_value_list.size() - 1);
        String result;
        if (lastNetValue <= average) {
            result = name + " 建议:可多买入" + " 当前净值:" + lastNetValue + " <(小于) 平均净值:" + average+"  差值:"+(lastNetValue-average);
        } else {
            result = "";
//            result = name + "  建议:正常定投" + " 当前净值:" + lastNetValue + " >(大于) 平均净值:" + average;
        }
        System.out.println(result);
    }

    private static void printDay(Double average, int start, int end, List<Double> net_value_list, List<String> date_line_list) {
        for (int i = start; i < end; i++) {
            Double currentValue = net_value_list.get(i);
            String currentDay = date_line_list.get(i);
            String s = "  " + currentDay + " 净值:" + currentValue;
            System.out.println(s);
        }
    }

    private static Double queryStart2EndAverage(int start, int end, List<Double> net_value) {
        Double SumNetValue = 0d;
        for (int i = start; i < end; i++) {
            SumNetValue += net_value.get(i);
        }
        Double average = SumNetValue / (end - start);
        return average;
    }
}
