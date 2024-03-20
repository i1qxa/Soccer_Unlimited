package com.soerrrunlimi.unlimifootb.scsfoa.ui

import android.content.Context
import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.soerrrunlimi.unlimifootb.scsfoa.R
import com.soerrrunlimi.unlimifootb.scsfoa.data.IS_SOUND_ON
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL_CHOSEN
import com.soerrrunlimi.unlimifootb.scsfoa.data.PREFS_NAME
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[GameViewModel::class.java] }
    private var soundPool: SoundPool? = null
    private val prefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    private val isSoundOn by lazy { prefs.getBoolean(IS_SOUND_ON, true) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initLevel()
        observeViewModel()
    }

    private fun initLevel(){
        val level = prefs.getInt(LEVEL_CHOSEN, 1)
        when(level){
            1 -> binding.progressTimer.max = 10
            2 -> binding.progressTimer.max = 20
            else -> binding.progressTimer.max = 30
        }
        viewModel.setupGame(level)
        setupBallClickListener()
    }

    private fun setupBallClickListener(){
        viewModel.decreaseClick()
        playMoveSound()
    }

    private fun playMoveSound() {
        if (isSoundOn) {
            val sound = soundPool!!.load(baseContext, R.raw.click_sound, 1)
            soundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
                if (status == 0) {
                    soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
                }
            }
        }
    }

    private fun observeViewModel(){
        viewModel.ballImgResourceLD.observe(this){
            binding.ball.setImageResource(it)
        }
        viewModel.timeLeftLD.observe(this){
            binding.progressTimer.setProgress(it, true)
        }
        viewModel.gameResult.observe(this){
            val intent = Intent(this, GameResultActivity::class.java)
            intent.putExtra("Result", it)
            startActivity(intent)
        }
    }

}