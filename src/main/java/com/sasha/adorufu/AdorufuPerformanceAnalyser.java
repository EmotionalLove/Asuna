package com.sasha.adorufu;

import java.util.ArrayList;

/**
 * Created by Sasha on 11/08/2018 at 6:36 PM
 **/
public class AdorufuPerformanceAnalyser {

    private ArrayList<Integer> performanceTimesNormal;
    private ArrayList<Integer> performanceTimesRender;
    private int counterNormal=0;
    private int counterRender=0;

    public AdorufuPerformanceAnalyser(){
        performanceTimesNormal = new ArrayList<>();
        performanceTimesRender = new ArrayList<>();
    }

    public void recordNewNormalTime(int totalTime) {
        performanceTimesNormal.add(totalTime);
    }
    public void recordNewRenderTime(int totalTime) {
        performanceTimesRender.add(totalTime);
    }

    public int getCurrentMsNormal(){
        return performanceTimesNormal.get(performanceTimesNormal.size()-1);
    }
    public double getAvgMsNormal(){
        int sum=0;
        for (int i : performanceTimesNormal) {
            sum+=i;
        }
        return (double)sum/performanceTimesNormal.size();
    }
    public int getCurrentMsRender(){
        return performanceTimesRender.get(performanceTimesRender.size()-1);
    }
    public double getAvgMsRender(){
        int sum=0;
        for (int i : performanceTimesRender) {
            sum+=i;
        }
        return (double)sum/performanceTimesRender.size();
    }

}
