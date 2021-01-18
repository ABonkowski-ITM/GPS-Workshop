package com.example.gps

import android.Manifest
import android.Manifest.*
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gpsworkshop.R
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {

    val btn_Location  = findViewById<Button>(R.id.location_btn)
    var TextViewLocation = findViewById<TextView>(R.id.location_txt)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_Location.setOnClickListener { checkingPermissons() }
    }


    private fun checkingPermissons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    updateLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialog()
                }
                else -> {
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PackageManager.PERMISSION_GRANTED)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location : Location -> onLocationReceived(location) }
    }

    private fun onLocationReceived(location: Location){
        val location_text = location.latitude.toString() + "|" + location.longitude.toString()
        TextViewLocation.text = location_text
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your GPS is required for this App")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PackageManager.PERMISSION_GRANTED)
            }
        }
    }
}