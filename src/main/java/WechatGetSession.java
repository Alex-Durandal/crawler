package main.java;

import java.awt.*;
import java.io.File;
import java.io.IOException;
/**
 * Created by Alex on 2017/2/14.
 */
public class WechatGetSession {
    public  void getSession(String start_bat,String stop_bat) {
        File file = new File("D://Sessions.txt");
        //如果文件存在，则先删除，再创建。保证文件中的内容是最新的。
        if(file.exists()&&file.isFile()){
            file.delete();
        }
        Runtime run = Runtime.getRuntime();
        try {
            Process start_process = run.exec(start_bat);
            start_process.waitFor();
            WechatSimulationAction action = new WechatSimulationAction();
            action.Init();
            action.ActionsOne();
         //   Process stop_process = run.exec(stop_bat);
         //   stop_process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args){
        WechatGetSession wgs = new WechatGetSession();
        wgs.getSession("D:/start.bat","D:/stop.bat");
    }
}

