package no.kristiania.androidprogrammering.noforeignlandapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import no.kristiania.androidprogrammering.noforeignlandapp.*
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties

class MainActivity : AppCompatActivity(),
    Callbacks {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Checks if fragments inside container already exists, if true creates a fragment
        val isEmpty = savedInstanceState == null
        if(isEmpty){
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    LocationListFragment()
                )
                .commit()
        }
    }

    override fun onPlaceSelected(locationProperties: LocationProperties) {
       val fragment =
           LocationDetailsFragment.newInstance(
               locationProperties
           )
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    override fun onLocationSelected(locationProperties: LocationProperties) {
        val intent =
            LocationMapActivity.newIntent(
                this@MainActivity,
                locationProperties
            )
        startActivity(intent)
    }
}

interface Callbacks {
    fun onPlaceSelected(locationProperties: LocationProperties)
    fun onLocationSelected(locationProperties: LocationProperties)
}