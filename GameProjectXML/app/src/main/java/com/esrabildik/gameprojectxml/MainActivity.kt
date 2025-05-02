package com.esrabildik.gameprojectxml

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esrabildik.gameprojectxml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null
    private var gameActive = true
    var lastVisibleImage: ImageView? = null
    var score = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonStart.setOnClickListener {
            startTimer()
        }

        scoreClick()
    }


    private fun startTimer() {
        timer?.cancel() // Eğer daha önce bir sayaç varsa iptal et
        val time = 30

        timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                binding.textViewTime.text = "Time: ${p0 / 1000}"
            }

            override fun onFinish() {
                endGame()
            }
        }.start()
    }


    private fun endGame() {
        gameActive = false
        binding.textViewTime.text = "Time: 0"

        // Görselleri gizle ve tıklamayı kapat
        lastVisibleImage?.visibility = View.INVISIBLE
        lastVisibleImage?.isClickable = false

        // AlertDialog ile "Game Over" mesajı göster
        AlertDialog.Builder(this)
            .setTitle("Game Over!")
            .setMessage("Your Score: $score\nDo you want to play again?")
            .setPositiveButton("Restart") { _, _ ->
                restartGame()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }
            .setCancelable(false) //Kullanıcı pop-up dışında bir yere tıklayınca kapanmasın
            .show()
    }


    private fun restartGame() {
        score = 0
        binding.textViewScore.text = "Score: 0"
        gameActive = true
        startTimer()
    }


    private fun scoreClick() {

        val imageViews = listOf(
            binding.imageView0,
            binding.imageView1,
            binding.imageView2,
            binding.imageView3,
            binding.imageView4,
            binding.imageView5,
            binding.imageView6,
            binding.imageView7,
            binding.imageView8,
            binding.imageView9,
            binding.imageView10,
            binding.imageView11,
            binding.imageView12,
            binding.imageView13,
            binding.imageView14,
            binding.imageView17,
            binding.imageView18,
            binding.imageView19,
            binding.imageView20,
            binding.imageView21,
            binding.imageView22,
            binding.imageView23,
            binding.imageView24,
            binding.imageView25,
            binding.imageView26,
            binding.imageView27,
            binding.imageView28,
            binding.imageView29,
            binding.imageView30,
            binding.imageView31
        )

        // Başlangıçta tüm görselleri gizle ve tıklamayı kapat
        for (imageView in imageViews) {
            imageView.visibility = View.INVISIBLE
            imageView.isClickable = false
        }

        binding.root.setOnClickListener {
            // random açılan görseli tıklandıktan sonra tekrar gizle ve tıklanamaz yap
            lastVisibleImage?.let {
                it.visibility = View.INVISIBLE
                it.isClickable = false
            }
            // //rastgele bir görsel seç, göster ve tıklanabilir yap
            val randomImage = imageViews.random()
            randomImage.visibility = View.VISIBLE
            randomImage.isClickable = true // Seçilen görseli tıklanabilir yap
            lastVisibleImage = randomImage

        }

        // Seçili görsel tıklandığında skoru artır
        for (imageView in imageViews) {
            imageView.setOnClickListener {
                if (it == lastVisibleImage) { // Sadece seçili görsel tıklanabilir olacak
                    score++
                    binding.textViewScore.text = "Score: $score"

                    // Sonraki rastgele görseli seç
                    lastVisibleImage?.visibility = View.INVISIBLE
                    lastVisibleImage?.isClickable = false

                    val randomImage = imageViews.random()
                    randomImage.visibility = View.VISIBLE
                    randomImage.isClickable = true
                    lastVisibleImage = randomImage
                }
            }
        }
    }
}