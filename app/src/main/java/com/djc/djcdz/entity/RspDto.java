package com.djc.djcdz.entity;

/**
 * Created by Administrator
 * on 2018/3/21 星期三.
 */

public class RspDto {

    /**
     * 名师信息
     */
    public static class Master {
        public int id;
        public String url = "";
        public String name = "";
        public Boolean isFollow = false;
    }


    /**
     * 最新研究成果，操盘路线
     */
    public static class Route {
        public String title;
        public String time;
        public String imgUrl;
    }

    /**
     * 操作日志
     */
    public static class Log {
        public String content;
        public String time;
    }


    /**
     * 操作记录
     */
    public static class Record {
        public String name = "雅百特";
        public String code = "002033";
        public String newPrice = "23.25";  //最新股价
        public String newProfit = "24.55%"; //最新收益
        public String buyPrice = "19.15"; //买入价格
        public String buyTime = "2-23 2:45";  //买入时间
        public float star = 4;    //风险提示
        public String depotSug = "20%"; //仓位建议
        public String stopSug = "-7.00%";  //止损建议
        public String salePrice = "59.35";   //卖出价格
        public String saleTime = "2-28 05:45"; //卖出时间
    }

    /**
     * 机构情报
     */
    public static class News {
        public String photoUrl = "";
        public String title = "";
        public String content = "";
        public String count = "";
        public String time = "";
    }

    /**
     * 名师解盘
     */
    public static class Comment {
        public String commnets = "刘主席周末打大鳄，市场波动在所难免...";
        public String time = "";
        public String photoUrl = "";
        public String name = "";
    }

    /**
     * 名师解盘
     */
    public static class Rank {
        public int rank;
        public String photoUrl = "";
        public String name = "";
        public String newProfit = "22.24%"; //最新收益
        public String monthProfit = "91.45%";    //30天盈利率
        public String totalProfit = "92.54%"; //整体盈利率
        public String star = "94.52%";   //追踪度
        public String monthSuccess = "94.15%";    //30天成功率
        public String totalSuccess = "95.18%";    //  整体成功率
    }


}
