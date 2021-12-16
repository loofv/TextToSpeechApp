package tutorial.texttospeechapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.Log.INFO
import android.widget.Toast
import tutorial.texttospeechapp.databinding.ActivityMainBinding
import java.util.*
import java.util.logging.Level.INFO

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech(this, this)

        binding?.btnSpeak?.setOnClickListener { view ->
            Toast.makeText(this, "button clicked", Toast.LENGTH_LONG).show()
            Log.e("btn", "button clicked")
            speakOut("This is a long text with text and stuff.")
        }
    }

    override fun onInit(status: Int) {
        Log.e("tts", "init starting")
        Log.e("tts", "status: $status")
        if (status == TextToSpeech.SUCCESS) {
            Log.e("tts", "got to success part")
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(this, "error problem, result was: $result", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.e("tts", "init failed!")
            Toast.makeText(this, "init failed!", Toast.LENGTH_LONG).show()
        }
    }
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}