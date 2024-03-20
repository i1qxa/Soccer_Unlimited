package com.soerrrunlimi.unlimifootb.scsfoa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soerrrunlimi.unlimifootb.scsfoa.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel:ViewModel() {

    val clickLeftLD = MutableLiveData<Int>()

    val timeLeftLD = MutableLiveData<Int>()

    val gameResult = MutableLiveData<Boolean>()

    val ballImgResourceLD = MutableLiveData<Int>()

    private var clickLeft = 0

    private var timeLeft = 0

    val listOfBallImgResources:MutableList<Int> = mutableListOf(
        R.drawable.ball1,
        R.drawable.ball2,
        R.drawable.ball3,
        R.drawable.ball4,
        R.drawable.ball5,
        R.drawable.ball6,
        R.drawable.ball7,
        R.drawable.ball8,
        R.drawable.ball9,
        R.drawable.ball10,
        R.drawable.ball11,
        R.drawable.ball12,
    )

    fun decreaseClick(){
        clickLeft--
        clickLeftLD.value = clickLeft
        if (clickLeft<=0) gameResult.value = true
    }

    fun setupGame(level:Int){
        when(level){
            1 -> {
                timeLeft = 10
                clickLeft = 10 + Random.nextInt(10)
            }
            2 -> {
                timeLeft = 20
                clickLeft = 30 + Random.nextInt(10)
            }
            else -> {
                timeLeft = 30
                clickLeft = 40 + Random.nextInt(20)
            }
        }
        timeLeftLD.value = timeLeft
        clickLeftLD.value = clickLeft
        ballImgResourceLD.value = listOfBallImgResources.shuffled()[0]
        startGame()
    }

    private fun startGame(){
        viewModelScope.launch {
            while (timeLeft>0){
                delay(1000)
                timeLeft--
                timeLeftLD.postValue(timeLeft)
                if (timeLeft<=0) gameResult.postValue(false)
            }
        }
    }

}