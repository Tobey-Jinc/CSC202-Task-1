package com.jinc.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jinc.geoquiz.databinding.ActivityCheatBinding
import com.jinc.geoquiz.databinding.ActivityMainBinding
import java.security.AccessControlContext

const val EXTRA_ANSWER_SHOWN = "com.jinc.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.jinc.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding

    private val cheatViewModel: CheatViewModel by viewModels()

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        if (cheatViewModel.isAnswerShown) {
            showAnswer()
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer()

            cheatViewModel.isAnswerShown = true
            setAnswerShownResult()
        }
    }

    private fun showAnswer() {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, cheatViewModel.isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}