// SplashActivity.kt
package mt.ui.uk // Make sure this matches your package name

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import mt.ui.uk.R // Make sure R is imported correctly based on your project structure

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // You'll create this layout next

        val videoView = findViewById<VideoView>(R.id.splash_video_view)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash2 // Assuming splash2.mp4 is in res/raw
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        videoView.setOnCompletionListener {
            // Video finished, navigate to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close SplashActivity so user can't go back to it
        }

        videoView.start()
    }
}