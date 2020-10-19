package no.kristiania.androidprogrammering.noforeignlandapp.view

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties
import no.kristiania.androidprogrammering.noforeignlandapp.R
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationLatLon
import no.kristiania.androidprogrammering.noforeignlandapp.viewmodel.LocationLatLonViewModel

private const val EXTRA_PLACE_ID = "place_id"
private const val EXTRA_PLACE_NAME = "place_name"

class LocationMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationProperties: LocationProperties
    private lateinit var locationLatLon: LocationLatLon

    private lateinit var locationLatLonViewModel: LocationLatLonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_map)


        locationProperties =
            LocationProperties()
        locationProperties.name = intent.getStringExtra(EXTRA_PLACE_NAME)!!
        locationProperties.id = intent.getStringExtra(EXTRA_PLACE_ID)!!
        locationLatLonViewModel = ViewModelProviders.of(this).get(LocationLatLonViewModel::class.java)

        locationLatLonViewModel.loadPlace("https://www.noforeignland.com/home/api/v1/place?id=${locationProperties.id}")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        locationLatLonViewModel.locationDetailsLiveData.observe(this, androidx.lifecycle.Observer {
            locationLatLon = locationLatLonViewModel.locationDetailsLiveData.value!!
            val location = LatLng(locationLatLon.lat.toDouble(), locationLatLon.lon.toDouble())
            mMap.addMarker(MarkerOptions().position(location).title(locationProperties.name))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10.0f))
        })
    }


    companion object {
        fun newIntent(packageContext: Context, locationProperties: LocationProperties): Intent {
            return Intent(packageContext, LocationMapActivity::class.java).apply{
                putExtra(EXTRA_PLACE_ID, locationProperties.id)
                putExtra(EXTRA_PLACE_NAME, locationProperties.name)
            }
        }
    }
}
