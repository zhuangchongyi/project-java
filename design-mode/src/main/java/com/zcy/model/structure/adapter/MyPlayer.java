package com.zcy.model.structure.adapter;
/**
 * @Author zhuangchongyi
 * @Description 适配器模式
 * 我们在现实生活中使用适配器很多。例如，我们使用存储卡适配器连接存储卡和计算机，因为计算机仅支持一种类型的存储卡，并且我们的卡与计算机不兼容。
 * 适配器是两个不兼容实体之间的转换器。适配器模式是一种结构模式。
 * 在Java设计模式中，适配器模式作为两个不兼容接口之间的桥梁。
 * 通过使用适配器模式，我们可以统一两个不兼容的接口。
 * @Date 2020/7/2 14:17
 */
public class MyPlayer implements Player {
    private AudioPlayer audioPlayer = new MyAudioPlayer();
    private VideoPlayer videoPlayer = new MyVideoPlayer();

    @Override
    public void play(String audioType, String fileName) {
        if ("avi".equalsIgnoreCase(audioType)) {
            videoPlayer.playVideo(fileName);
        } else if ("mp3".equalsIgnoreCase(audioType)) {
            audioPlayer.playAudio(fileName);
        }
    }
}
