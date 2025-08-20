/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.java.dp.command;

public class Keyboard {
	private Command playCommand;
	private Command rewindCommand;
	private Command stopCommand;
    
    public void setPlayCommand(Command playCommand) {
        this.playCommand = playCommand;
    }
    
    public void setRewindCommand(Command rewindCommand) {
        this.rewindCommand = rewindCommand;
    }
    
    public void setStopCommand(Command stopCommand) {
        this.stopCommand = stopCommand;
    }
    
    
    /**
     * 执行播放方法
     */
    public void play(){
        playCommand.execute();
    }
    
    
    /**
     * 执行倒带方法
     */
    public void rewind(){
        rewindCommand.execute();
    }
    
    
    /**
     * 执行播放方法
     */
    public void stop(){
        stopCommand.execute();
    }


}
