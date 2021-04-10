package space.mosil.demo.kmm.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import space.mosil.demo.kmm.Greeting
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import space.mosil.demo.kmm.GdgLogger
import space.mosil.demo.kmm.service.RssService

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName ?: ""
    private val service = RssService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        findViewById<Button>(R.id.btn_main_go_xml_layout).setOnClickListener(onButtonListener)
        findViewById<Button>(R.id.btn_main_go_compose_layout).setOnClickListener(onButtonListener)

        GdgLogger.i("GDG KMM", "Open App")
        lifecycleScope.launch {
            kotlin.runCatching {
                service.getFeeds()
            }.onSuccess {
                GdgLogger.i(TAG, it.toString())
            }.onFailure {
                GdgLogger.e(TAG, it.toString())
            }
        }
    }

    private val onButtonListener = View.OnClickListener {
        var intent: Intent? = null
        when (it.id) {
            R.id.btn_main_go_xml_layout -> {
                intent = Intent(this, RssXmlActivity::class.java)
            }
            R.id.btn_main_go_compose_layout -> {
                intent = Intent(this, RssComposeActivity::class.java)
            }
        }

        if (intent != null) {
            startActivity(intent)
        }

    }

}
