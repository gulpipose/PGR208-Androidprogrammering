package no.kristiania.androidprogrammering.noforeignlandapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import no.kristiania.androidprogrammering.noforeignlandapp.R
import no.kristiania.androidprogrammering.noforeignlandapp.Utils
import no.kristiania.androidprogrammering.noforeignlandapp.service.api.NoForeignLandRepository


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Implementation of checking network connection as demonstrated in class-material
        if(Utils().isNetworkAvailable(this)){
            NoForeignLandRepository().getPlaces()
        } else {
            Toast.makeText(this, getString(R.string.no_connection_message), Toast.LENGTH_LONG).show()
        }

        //Adds intent, starting activity and finishing to messagequeue when delay has passed
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}
