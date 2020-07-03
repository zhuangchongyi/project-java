package com.zcy.model.structure.adapter;

public class MyAudioPlayer implements AudioPlayer {
    @Override
    public void playAudio(String fileName) {
        System.out.println("Palying name=" + fileName);

    }
}
