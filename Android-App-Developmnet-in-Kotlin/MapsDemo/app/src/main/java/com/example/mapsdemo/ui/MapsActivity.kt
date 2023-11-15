package com.example.mapsdemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.Equalizer
import android.media.audiofx.PresetReverb
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Contacts
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapsdemo.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.provider.Settings;
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude   = 0.0
    private var longitude  = 0.0
    private var TAG = "loclog"
    private var currentLocation: LatLng? = null // You should initialize this with the current location

    // GeeksforGeeks coordinates
    private var originLatitude: Double = 28.5021359
    private var originLongitude: Double = 77.4054901

    // Coordinates of a park nearby
    private var destinationLatitude: Double = 28.5151087
    private var destinationLongitude: Double = 77.3932163



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocation()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/
    }



    private fun getCurrentLocation() {

        if (checkPermissions()) {

            if (isLocationEnabled()) {
                //final latitude and longitude
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                   requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->

                    val location : Location =task.result
                    if (location == null){
                        Toast.makeText(this, "null Recieved", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                        latitude= location.latitude
                        Toast.makeText(this, "latitude : $latitude", Toast.LENGTH_LONG).show()
                        longitude = location.longitude
                        Toast.makeText(this, "longitude : $longitude", Toast.LENGTH_LONG).show()

                        val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this)


                        Log.d(TAG, "getCurrentLocation: $latitude ")
                        Log.d(TAG, "getCurrentLocation: $longitude")
                    }
                }

            } else {

                // setting open hear
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)

            }

        } else {
            //req per
            requestPermission()

        }
    }

    private fun isLocationEnabled():Boolean{
        val  locationManager: LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {

            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_LOCATION

            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }else{
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()

            }

        }
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
        mMap = googleMap!!

        val originLocation = LatLng(originLatitude, originLongitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(originLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))


/*
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(latitude, longitude)
       *//* mMap.addMarker(MarkerOptions().position(sydney).title("current location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*//*


        val marker=MarkerOptions().position(sydney).title("current location")
        //set custom icon
        marker.icon(BitmapFromVector(getApplicationContext(), R.drawable.locaion_icon))
        //add marker
        mMap.addMarker(marker)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }


    private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,MapData::class.java)
                val path =  ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }


    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }


    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun checkPermissions():Boolean{

        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED){

            return true
        }
        return false
    }

    private fun BitmapFromVector(context:Context,vectorResId:Int): BitmapDescriptor? {
        //drawable generator
        var vectorDrawable: Drawable
        vectorDrawable= ContextCompat.getDrawable(context,vectorResId)!!
        vectorDrawable.setBounds(0,0,vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight)
        //bitmap genarator
        var bitmap: Bitmap
        bitmap= Bitmap.createBitmap(vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        //canvas genaret
        var canvas: Canvas
        //pass bitmap in canvas constructor
        canvas= Canvas(bitmap)
        //pass canvas in drawable
        vectorDrawable.draw(canvas)
        //return BitmapDescriptorFactory
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
/*
android.Manifest.permission.ACCESS_COARSE_LOCATION,
android.Manifest.permission.ACCESS_FINE_LOCATION*/
