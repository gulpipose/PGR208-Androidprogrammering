package no.kristiania.androidprogrammering.noforeignlandapp.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties
import no.kristiania.androidprogrammering.noforeignlandapp.R
import no.kristiania.androidprogrammering.noforeignlandapp.viewmodel.LocationListViewModel


class LocationListFragment : Fragment() {

    private var callbacks: Callbacks? = null

    private lateinit var locationListViewModel: LocationListViewModel
    private lateinit var placeRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        locationListViewModel = ViewModelProviders.of(this).get(LocationListViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_place_list, container, false)
        placeRecyclerView = view.findViewById(R.id.place_recycler_view) as RecyclerView
        placeRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observe livedata from database, use the adapter to create listItems
        locationListViewModel.locationListLiveData.observe(
            viewLifecycleOwner,
            Observer { placeItemProperties ->
               placeRecyclerView.adapter = PlaceListAdapter(placeItemProperties)
            }
        )
    }

    override fun onDetach(){
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_place_list, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_location_search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE;

        //Set listeners on searchview
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    locationListViewModel.searchPlaces(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    locationListViewModel.searchPlaces(newText)
                    return true
                }
            })
        }
    }


    private inner class PlaceListHolder(view: View) : RecyclerView.ViewHolder(view){
        private lateinit var locationProperties: LocationProperties

        val nameTextView: TextView = itemView.findViewById(R.id.place_name)
        val locationButton: ImageButton = itemView.findViewById(R.id.location_button)

        init {
            //When location name clicked, callback to activity to inflate loactionDetailsFragment.
            nameTextView.setOnClickListener{
                callbacks?.onPlaceSelected(locationProperties)
            }
            //When location map-icon clicked, callback to activity to start LocationMapActivity.
            locationButton.setOnClickListener{
                callbacks?.onLocationSelected(locationProperties)
            }
        }

        fun bind(locationProperties: LocationProperties) {
            this.locationProperties = locationProperties
            nameTextView.text = locationProperties.name
        }

    }

    private inner class PlaceListAdapter(private val locationProperties: List<LocationProperties?>) : RecyclerView.Adapter<PlaceListHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListHolder {
            val view = layoutInflater.inflate(R.layout.list_item_place, parent, false)
            return PlaceListHolder(view)
        }

        override fun getItemCount(): Int = locationProperties.size

        override fun onBindViewHolder(holder: PlaceListHolder, position: Int) {
            val placeItemProperty = locationProperties[position]
            holder.bind(placeItemProperty!!)
        }

    }
}
