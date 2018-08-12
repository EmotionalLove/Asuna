package com.sasha.xdolf;

/**
 * Created by Sasha on 11/08/2018 at 6:36 PM
 **/
public class XdolfPerformanceAnalyser {

    private int[] performanceTimesNormal;
    private int[] performanceTimesRender;
    private int counterNormal=0;
    private int counterRender=0;

    public XdolfPerformanceAnalyser(){
        performanceTimesNormal = new int[]{};
        performanceTimesRender = new int[]{};
    }

    public void recordNewNormalTime(int totalTime) {
        if (counterNormal > 1200) {
            counterNormal = 0;
        }
        performanceTimesNormal[counterNormal]=totalTime;
        counterNormal++;
    }
    public void recordNewRenderTime(int totalTime) {
        if (counterRender > 1200){
            counterRender =0;
        }
        performanceTimesRender[counterRender]=totalTime;
        counterRender++;
    }

    public int getCurrentMsNormal(){
        return performanceTimesNormal[counterNormal == 0 ? counterNormal : counterNormal-1];
    }
    public double getAvgMsNormal(){
        int sum=0;
        for (int i : performanceTimesNormal) {
            sum+=i;
        }
        return sum/performanceTimesNormal.length;
    }
    public int getCurrentMsRender(){
        return performanceTimesRender[counterRender == 0 ? counterRender : counterRender-1];
    }
    public double getAvgMsRender(){
        int sum=0;
        for (int i : performanceTimesRender) {
            sum+=i;
        }
        return sum/performanceTimesRender.length;
    }

}
