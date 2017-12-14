package com.yxy.chukonu.java.dp.command;

public class RewindCommand implements Command {

    private AudioPlayer myAudio;
    
    public RewindCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }
    
    
    @Override
    public void execute() {
        myAudio.rewind();
    }


}
