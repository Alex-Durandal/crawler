package main.java;
import java.awt.*;
import java.awt.event.InputEvent;
/**
 * Created by Alex on 2017/2/14.
 */

//（1）用于爬取微信公众号数据信息模拟操作：
public class WechatSimulationAction {
    //初始位置：
    public void Init()throws AWTException, InterruptedException{
        MouseClick mousemove = new MouseClick();
        mousemove.mouseclick(591, 313,739, 390);
    }
    //点击完第一行的6个：
    public void ActionsOne() throws AWTException, InterruptedException {
        MouseClick mousemove = new MouseClick();
        mousemove.mouseclick(948, 297, 1037, 524);
        mousemove.mouseclick(1024, 292,1108, 516);
//        mousemove.mouseclick(1101,289,1171,544);
//        mousemove.mouseclick(1177,289,1260,552);
//        mousemove.mouseclick(1250,292,1334,514);
//        mousemove.mouseclick(1325,293,1407,563);
//        mousemove.mouseclick(948,419,1041,674);
//        mousemove.mouseclick(1024,405,1102,627);
//        mousemove.mouseclick(1104,408,1189,644);
//        mousemove.mouseclick(1180,410,1276,633);
//        mousemove.mouseclick(1250,413,1354,671);
//        mousemove.mouseclick(1323,415,1409,638);
//        mousemove.mouseclick(945,529,1037,806);
//        mousemove.mouseclick(1023,522,1131,753);
//        mousemove.mouseclick(1099,525,1185,781);
//        mousemove.mouseclick(1174,525,1252,769);
//        mousemove.mouseclick(1251,531,1318,753);
//        mousemove.mouseclick(1331,531,1404,787);
//        mousemove.mouseclick(952,643,1054,917);
//        mousemove.mouseclick(1026,642,1122,870);
//        mousemove.mouseclick(1102,641,1190,877);
//        mousemove.mouseclick(1176,644,1257,915);
//        mousemove.mouseclick(1250,643,1339,915);
//        mousemove.mouseclick(1322,646,1400,876);
//        mousemove.mouseclick(949,757,1033,707);
//        mousemove.mouseclick(1024,754,1097,698);
//        mousemove.mouseclick(1104,760,1195,979);
//        mousemove.mouseclick(1174,760,1249,711);
//        mousemove.mouseclick(1257,758,1322,711);
//        mousemove.mouseclick(1328,759,1385,703);
//        mousemove.mouseclickOne(898,715);
//        Roll();
//        mousemove.mouseclick(950,353,1051,597);
//        mousemove.mouseclick(1022,356,1107,627);
//        mousemove.mouseclick(1098,355,1185,600);
//        mousemove.mouseclick(1178,357,1248,586);
//        mousemove.mouseclick(1249,355,1313,578);
//        mousemove.mouseclick(1332,357,1406,600);
//        mousemove.mouseclick(954,471,1035,695);
//        mousemove.mouseclick(1028,471,1113,691);
//        mousemove.mouseclick(1101,470,1185,710);
//        mousemove.mouseclick(1180,473,1245,697);
//        mousemove.mouseclick(1255,471,1320,699);
//        mousemove.mouseclick(1331,473,1391,744);
//        mousemove.mouseclick(956,585,1021,858);
//        mousemove.mouseclick(1025,591,1115,866);
//        mousemove.mouseclick(1101,588,1174,828);
//        mousemove.mouseclick(1175,588,1252,845);
//        mousemove.mouseclick(1253,589,1322,861);
//        mousemove.mouseclick(1327,586,1400,828);
//        mousemove.mouseclick(950,706,1055,980);
//        mousemove.mouseclick(1028,705,1103,978);
//        mousemove.mouseclick(1100,702,1159,975);
//        mousemove.mouseclick(1176,701,1258,958);
//        mousemove.mouseclick(1252,704,1335,976);
//        mousemove.mouseclick(1330,705,1390,977);
//        mousemove.mouseclick(944,823,1016,772);
    }

    // 408
    //点击事件
    class MouseClick {
        //单次点击
        public void mouseclickOne(int x, int y) throws AWTException, InterruptedException {
            Robot r = new Robot();
            Thread.sleep(500);
            r.mouseMove(x, y);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(500);
        }
        public void mouseclick(int x1, int y1, int x2, int y2) throws AWTException, InterruptedException {
            Robot r = new Robot();
            Thread.sleep(500);
            r.mouseMove(x1, y1);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(500);
            Thread.sleep(500);
            r.mouseMove(x2, y2);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(500);
            r.mouseMove(1919, 23);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(500);
        }
//        public void close() throws AWTException, InterruptedException {
//            Robot r = new Robot();
//            Thread.sleep(500);
//            r.mouseMove(1919, 23);
//            r.mousePress(InputEvent.BUTTON1_MASK);
//            r.mouseRelease(InputEvent.BUTTON1_MASK);
//            Thread.sleep(500);
//        }
    }
    //获取坐标：
    public void GetPosition() throws AWTException {
        Robot r = new Robot();
        r.delay(2000);
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        System.out.println(mousePoint);
    }
    //滚动：8
    public void Roll()throws AWTException{
        Robot r = new Robot();
        r.delay(1000);
        for (int i = 0;i<9;i++){
            r.mouseWheel(1);
            r.delay(500);
        }
    }

    //主函数
    public static void main(String[] args) throws Exception {
        WechatSimulationAction test = new WechatSimulationAction();
        test.Init();
        test.ActionsOne();
//        test.Init(); //初始化
//        //模拟点击完第一行的6个：
//       test.ActionsOne();
        //        //模拟点击完第一行的6个：
//       test.ActionsTwo();
//        test.Actions3();
        //       test.ActionsOne();
//        //获取坐标：
//        test.GetPosition();
//        //移动到第二行：
//        test.Roll();
//        test.Actions();

    }
}