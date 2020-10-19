package no.kristiania.androidprogrammering.noforeignlandapp.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties
import no.kristiania.androidprogrammering.noforeignlandapp.R
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationDetails
import no.kristiania.androidprogrammering.noforeignlandapp.viewmodel.LocationDetailsViewModel


const val ARG_PLACE_ID = "location_id"
const val ARG_PLACE_NAME = "location_name"

class LocationDetailsFragment : Fragment(){
    private lateinit var locationProperties: LocationProperties
    private lateinit var locationDetails: LocationDetails
    private lateinit var locationButton: ImageButton
    private lateinit var name: TextView
    private lateinit var banner: ImageView
    private lateinit var description: TextView
    private lateinit var locationDetailsViewModel: LocationDetailsViewModel


    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationDetailsViewModel = ViewModelProviders.of(this).get(LocationDetailsViewModel::class.java)
        locationProperties = LocationProperties()
        locationProperties.name = arguments?.getSerializable(ARG_PLACE_NAME) as String
        locationProperties.id = arguments?.getSerializable(ARG_PLACE_ID) as String
        locationDetailsViewModel.loadPlace("https://www.noforeignland.com/home/api/v1/place?id=${locationProperties.id}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_place_info, container, false)

        name = view.findViewById(R.id.place_name) as TextView
        banner = view.findViewById(R.id.location_banner) as ImageView
        description = view.findViewById(R.id.location_description) as TextView
        locationButton = view.findViewById(R.id.detail_location_button) as ImageButton

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationDetailsViewModel.locationInformationLiveData.observe(
            viewLifecycleOwner,
            Observer { locationDetails ->
                locationDetails?.let {
                    this.locationDetails = locationDetails
                    updateUI()
                }
            }
        )
    }


    private fun updateUI() {
        name.setText(locationProperties.name)
        if(locationDetails.banner != "") {
            Picasso.get()
                .load(locationDetails.banner)
                .into(banner)
        }

        //Parses the API-endpoints returned HTML to text
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.text = Html.fromHtml(locationDetails.comments, FROM_HTML_MODE_LEGACY)
        }

        locationButton.setOnClickListener {
            callbacks?.onLocationSelected(locationProperties)
        }
    }

    override fun onDetach(){
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(locationProperties: LocationProperties): LocationDetailsFragment {
            val args = Bundle().apply {
                putSerializable(ARG_PLACE_ID, locationProperties.id)
                putSerializable(ARG_PLACE_NAME, locationProperties.name)
            }
            return LocationDetailsFragment()
                .apply {
                arguments = args
            }
        }
    }
}