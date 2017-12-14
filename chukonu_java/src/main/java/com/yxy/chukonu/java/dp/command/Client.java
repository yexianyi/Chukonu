package com.yxy.chukonu.java.dp.command;

public class Client {
    public static void main(String[]args){
        //Receiver
        AudioPlayer audioPlayer = new AudioPlayer();
        
        //concrete command
        Command playCommand = new PlayCommand(audioPlayer);
        Command rewindCommand = new RewindCommand(audioPlayer);
        Command stopCommand = new StopCommand(audioPlayer);
        
        //Invoker
        Keyboard keyboard = new Keyboard();
        keyboard.setPlayCommand(playCommand);
        keyboard.setRewindCommand(rewindCommand);
        keyboard.setStopCommand(stopCommand);
        
        //testing
        keyboard.play();
        keyboard.rewind();
        keyboard.stop();
        keyboard.play();
        keyboard.stop();
    }
}
