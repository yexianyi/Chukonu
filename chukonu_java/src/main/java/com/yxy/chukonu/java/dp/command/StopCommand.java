package com.yxy.chukonu.java.dp.command;

public class StopCommand implements Command {
    private AudioPlayer myAudio;
    
    public StopCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }
    
    
    @Override
    public void execute() {
        myAudio.stop();
    }

}
