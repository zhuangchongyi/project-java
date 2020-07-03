package com.zcy.model.structure.adapter;

public class MyVideoPlayer implements VideoPlayer {
    @Override
    public void playVideo(String fileName) {
        System.out.println("video name=" + fileName);
    }
}
