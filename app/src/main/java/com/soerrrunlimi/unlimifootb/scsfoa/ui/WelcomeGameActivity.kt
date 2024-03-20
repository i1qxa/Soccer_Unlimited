package com.soerrrunlimi.unlimifootb.scsfoa.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soerrrunlimi.unlimifootb.scsfoa.R
import com.soerrrunlimi.unlimifootb.scsfoa.data.IS_SOUND_ON
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL_CHOSEN
import com.soerrrunlimi.unlimifootb.scsfoa.data.PREFS_NAME
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityGameBinding
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityWelcomeGameBinding

class WelcomeGameActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeGameBinding.inflate(layoutInflater) }
    private val prefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupSoundIcon()
        setupTimerIcon()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        setupSoundIcon()
        setupTimerIcon()
    }

    private fun setupSoundIcon() {
        val musicOn = prefs.getBoolean(IS_SOUND_ON, true)
        if (musicOn) {
            binding.btnSound.setImageResource(R.drawable.icon_sound_on)
        } else {
            binding.btnSound.setImageResource(R.drawable.icon_sound_off)
        }
    }

    private fun setupTimerIcon() {
        val levelChosen = prefs.getInt(LEVEL_CHOSEN, 1)
        val iconId = when (levelChosen) {
            2 -> R.drawable.icon_timer20
            3 -> R.drawable.icon_timer30
            else -> R.drawable.icon_timer10
        }
        binding.btnTimer.setImageResource(iconId)
    }

    private fun setupClickListeners() {
        setupSoundClickListener()
        setupTimerClickListener()
        setupInfoClickListener()
        setupBtnPlayClickListener()
        setupAwardClickListener()
    }

    private fun setupSoundClickListener() {
        binding.btnSound.setOnClickListener {
            val musicOn = prefs.getBoolean(IS_SOUND_ON, true)
            prefs.edit().putBoolean(IS_SOUND_ON, !musicOn).apply()
            setupSoundIcon()
        }
    }

    private fun setupTimerClickListener() {
        binding.btnTimer.setOnClickListener {
            val intent = Intent(this, ChoseLevelActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupInfoClickListener() {
        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, GameInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupBtnPlayClickListener(){
        binding.btnPlay.setOnClickListener {
            val intent = Intent(this, MyGameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAwardClickListener(){
        binding.btnAwards.setOnClickListener{
            val intent = Intent(this, AwardsActivity::class.java)
            startActivity(intent)
        }
    }


}