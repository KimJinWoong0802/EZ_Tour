package com.example.ez_tour

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MapActivity : AppCompatActivity(), MapView.POIItemEventListener {

    private var latitude: ArrayList<Double> = ArrayList()  //위도
    private var longtitude: ArrayList<Double> = ArrayList()   //경도
    private var locationManager: LocationManager? = null   // 우치 정보 매니저
    private var myLatitude: Double? = null
    private var myLongtitude: Double? = null
    private var clickLatitude: Double? = null
    private var clickLongtitude: Double? = null
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //kakao map 띄우기
        val mapView = MapView(this)
        val map_view = findViewById<View>(R.id.map_View) as RelativeLayout
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)


        //Help 이미지 초기화
        val img_hlep = findViewById<View>(R.id.img_hlep) as ImageView
        img_hlep.visibility = View.INVISIBLE
        //Help 버튼 초기화
        val btn_help = findViewById<Button>(R.id.btn_help) as ImageButton
        var counter = 0
        // GPS 마커 초기화.
        val gpsmarker = MapPOIItem()
        val btn_gps = findViewById<Button>(R.id.btn_gps) as ImageButton


            //Help 버튼 클릭리스너
        btn_help.setOnClickListener {
            if (counter == 0) {
                counter++
                btn_help.setImageResource(R.drawable.kakao_login_large_narrow)
                   img_hlep.visibility = View.VISIBLE
            } else {
                counter--
                btn_help.setImageResource(R.drawable.common_google_signin_btn_icon_dark)
                img_hlep.visibility = View.INVISIBLE
            }
        }
        // GPS 버튼 클릭리스너  (현재위치 가져오기)
        btn_gps.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val lm: LocationManager =
                    getSystemService(Context.LOCATION_SERVICE) as LocationManager
                try {
                    val userNowLocation: Location =
                        lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    val uLatitude = userNowLocation.latitude
                    val uLongitude = userNowLocation.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)

                    mapView.setMapCenterPoint(uNowPosition, true)
                  //  gpsmarker.mapPoint = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                    gpsmarker.itemName= "현재위치"
                    gpsmarker.tag = 0
                    gpsmarker.markerType = MapPOIItem.MarkerType.CustomImage
                    gpsmarker.customImageResourceId = R.drawable.marker
                    gpsmarker.selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                    gpsmarker.customSelectedImageResourceId = R.drawable.marker
                    gpsmarker.isCustomImageAutoscale = false
                    gpsmarker.setCustomImageAnchor(0.5f,1.0f)
                    mapView.addPOIItem(gpsmarker)

                } catch (e: NullPointerException) {
                    Log.e("LOCATION_ERROR", e.toString())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.finishAffinity(this)
                    } else {
                        ActivityCompat.finishAffinity(this)
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    System.exit(0)
                }
            } else {
                Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }



        }


    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        TODO("Not yet implemented")
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        TODO("Not yet implemented")
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        TODO("Not yet implemented")
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        TODO("Not yet implemented")
    }
}

