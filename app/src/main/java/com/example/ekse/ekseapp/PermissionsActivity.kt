package com.example.ekse.ekseapp

import android.Manifest
import android.R
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ekse.ekseapp.MapActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class PermissionsActivity : AppCompatActivity() {
    private var btnGrant: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
        if (ContextCompat.checkSelfPermission(
                this@PermissionsActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(Intent(this@PermissionsActivity, MapActivity::class.java))
            finish()
            return
        }
        btnGrant = findViewById(R.id.btn_grant)
        btnGrant.setOnClickListener(View.OnClickListener {
            Dexter.withActivity(this@PermissionsActivity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        startActivity(Intent(this@PermissionsActivity, MapActivity::class.java))
                        finish()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        if (response.isPermanentlyDenied) {
                            val builder: AlertDialog.Builder = Builder(this@PermissionsActivity)
                            builder.setTitle("Permission Denied")
                                .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        val intent = Intent()
                                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                        intent.data = Uri.fromParts(
                                            "package",
                                            packageName, null
                                        )
                                    })
                                .show()
                        } else {
                            Toast.makeText(
                                this@PermissionsActivity,
                                "Permission Denied",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                })
                .check()
        })
    }
}