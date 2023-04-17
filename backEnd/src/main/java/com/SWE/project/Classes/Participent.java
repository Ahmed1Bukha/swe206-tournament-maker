package com.SWE.project.Classes;

interface Participent {
    abstract void win(int GoalsMade,int goalsRecieved);
    abstract void draw(int GoalsMade,int goalsRecieved);
    abstract void lost(int GoalsMade,int goalsRecieved);
    abstract String getName();
    abstract int getPoints();
}
