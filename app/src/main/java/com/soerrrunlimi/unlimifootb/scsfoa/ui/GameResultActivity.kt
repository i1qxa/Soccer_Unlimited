package com.soerrrunlimi.unlimifootb.scsfoa.ui

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soerrrunlimi.unlimifootb.scsfoa.R
import com.soerrrunlimi.unlimifootb.scsfoa.data.IS_SOUND_ON
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL_CHOSEN
import com.soerrrunlimi.unlimifootb.scsfoa.data.PREFS_NAME
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityGameResultBinding

class GameResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityGameResultBinding.inflate(layoutInflater) }
    private val prefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    private val isWin by lazy { intent.getBooleanExtra("Result", false)}
    private val maxLevel by lazy { prefs.getInt(LEVEL, 1) }
    private val chosenLevel by lazy { prefs.getInt(LEVEL_CHOSEN, 1) }
    private val isSoundOn by lazy { prefs.getBoolean(IS_SOUND_ON, true) }
    private var soundPool: SoundPool? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        setupResult()
    }

    private fun setupResult(){
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, WelcomeGameActivity::class.java)
            startActivity(intent)
        }
        if (isWin){
            binding.gameResult.setImageResource(R.drawable.result_win)
            binding.btnResult.setImageResource(R.drawable.try_again)
            binding.btnResult.setOnClickListener {
                val intent = Intent(this, MyGameActivity::class.java)
                startActivity(intent)
            }
            playSound(soundPool!!.load(baseContext, R.raw.win_sound, 1))
            if (maxLevel==1){
                prefs.edit().putInt(LEVEL, (2)).apply()
            }
            if (maxLevel==2 && chosenLevel ==2){
                prefs.edit().putInt(LEVEL, (3)).apply()
            }
        }else{
            binding.gameResult.setImageResource(R.drawable.you_lose)
            binding.btnResult.setImageResource(R.drawable.btn_continue)
            playSound(soundPool!!.load(baseContext, R.raw.lose_sound, 1))
            binding.btnResult.setOnClickListener {
                val intent = Intent(this, WelcomeGameActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun playSound(sound:Int) {
        if (isSoundOn) {
            soundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
                if (status == 0) {
                    soundPool?.play(sound, 1F, 1F, 0, 0, 1F)
                }
            }
        }
    }
}