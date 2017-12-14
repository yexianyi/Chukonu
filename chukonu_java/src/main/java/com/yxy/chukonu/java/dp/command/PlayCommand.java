package com.yxy.chukonu.java.dp.command;

public class PlayCommand implements Command {

	private AudioPlayer myAudio;
	
	public PlayCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }
	
	
	@Override
	public void execute() {
		myAudio.play();
	}

}
