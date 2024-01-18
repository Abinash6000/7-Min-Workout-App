package com.project.a7minworkout.activities

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.a7minworkout.Constants
import com.project.a7minworkout.model.ExerciseModel
import com.project.a7minworkout.R
import com.project.a7minworkout.adapter.ExerciseStatusAdapter
import com.project.a7minworkout.databinding.ActivityExerciseBinding
import com.project.a7minworkout.databinding.DialogCustomBackConfirmationBinding


class ExerciseActivity : AppCompatActivity()/*, TextToSpeech.OnInitListener*/ {
    var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration = 3L

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration = 3L

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

//    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding!!.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding!!.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

//        tts = TextToSpeech(this, this, "com.google.android.tts")
        val soundUri = Uri.parse("android.resource://com.project.a7minworkout/" + R.raw.press_start)
        player = MediaPlayer.create(applicationContext, soundUri)
        player?.isLooping = false

        setUpRestView()
        setupExerciseStatusRecyclerView()


        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            /* override back pressing */
            override fun handleOnBackPressed() {
                //Your code here
                customDialogForBackButton()
            }
        })
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    // to make sure we are not running into an already running timer
    private fun setUpRestView() {

        try {
            player?.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        restTimer?.cancel()
        restProgress = 0

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setUpExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        exerciseTimer?.cancel()
        exerciseProgress = 0

//        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(restTimerDuration*1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseView()
            }

        }.start()
    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(exerciseTimerDuration*1000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExercisePosition < exerciseList?.size!! -1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }

    override fun onStop() {
        super.onStop()

        player?.stop()

    }

    override fun onDestroy() {
        super.onDestroy()

        restTimer?.cancel()
        restProgress = 0

        exerciseTimer?.cancel()
        exerciseProgress = 0

//        tts?.let {
//            it.stop()
//            it.shutdown()
//        }

        binding = null
    }

//    override fun onInit(status: Int) {
//        if(status == TextToSpeech.SUCCESS) {
//
//            for(voice: Voice in tts!!.voices)
//                Log.d("onInit_InstalledVoice", voice.name)
//
//            // set US English as language for tts
//            val result = tts?.setLanguage(Locale.US)
//            var hs: Set<String> = HashSet()
//            hs.plus("male")
//
//            val v = Voice("en-au-x-aud-network", Locale("en", "US"), 400, 200, true, hs)
//
//            tts!!.voice = v
//            tts!!.setSpeechRate(1.1f)
//
//            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "The language is not supported!")
//            }
//        } else {
//            Log.e("TTS", "Initialization Failed!")
//        }
//    }
//
//    private fun speakOut(text: String) {
//        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
//    }
}