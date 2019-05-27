package com.example.mygame.models


interface InteractionScoreObject {
    fun updateY(speed : Int)
    fun setNewXAndY(stairBeginning : Int, stairWidth : Int, currentStairPositionY: Int)
}