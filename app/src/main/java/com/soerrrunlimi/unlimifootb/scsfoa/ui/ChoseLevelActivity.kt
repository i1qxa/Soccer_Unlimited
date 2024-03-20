package com.soerrrunlimi.unlimifootb.scsfoa.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soerrrunlimi.unlimifootb.scsfoa.R
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL_CHOSEN
import com.soerrrunlimi.unlimifootb.scsfoa.data.PREFS_NAME
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityChoseLevelBinding

class ChoseLevelActivity : AppCompatActivity() {

    private val binding by lazy { ActivityChoseLevelBinding.inflate(layoutInflater) }
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
        setupLevels()
        setupBtnBackClickListener()
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupLevels(){
        val level = prefs.getInt(LEVEL, 1)
        if (level>0){
            binding.levelEasy.setOnClickListener {
                prefs.edit().putInt(LEVEL_CHOSEN, 1).apply()
                finish()
            }
        }
        if (level>1){
            binding.levelMedium.setImageResource(R.drawable.level_medium_on)
            binding.levelMedium.setOnClickListener {
                prefs.edit().putInt(LEVEL_CHOSEN, 2).apply()
                finish()
            }
        }
        if (level>2){
            binding.levelHard.setImageResource(R.drawable.level_hard_on)
            binding.levelHard.setOnClickListener {
                prefs.edit().putInt(LEVEL_CHOSEN, 3).apply()
                finish()
            }
        }
    }

}