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
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import net.daum.mf.map.api.CalloutBalloonAdapter
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

        mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))

        //Help 이미지 초기화
        val img_hlep = findViewById<View>(R.id.img_hlep) as ImageView
        img_hlep.visibility = View.INVISIBLE
        //Help 버튼 초기화
        val btn_help = findViewById<Button>(R.id.btn_help) as ImageButton
        var counter = 0
        // GPS 마커 초기화.
        val gpsmarker = MapPOIItem()
        val btn_gps = findViewById<Button>(R.id.btn_gps) as ImageButton
        // 하단 버튼 초기화
        val btn_search = findViewById<Button>(R.id.btn_search)
        val btn_mypage = findViewById<Button>(R.id.btn_mypage)

        // search 버튼 클릭리스너
        btn_search.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }
        // mypage 버튼 클릭리스너
        btn_mypage.setOnClickListener {
            val intent = Intent(this, mypage::class.java)
            startActivity(intent)
        }

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
                    gpsmarker.mapPoint = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                    gpsmarker.itemName= "현재위치"
                    gpsmarker.tag = 0
                    gpsmarker.markerType = MapPOIItem.MarkerType.BluePin
                    gpsmarker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
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
    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.customballoon, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.text_name)
        val address: TextView = mCalloutBalloon.findViewById(R.id.text_address)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = "이름 설정"   // 해당 마커의 정보 이용 가능
            address.text = "주소 설정"

            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            return mCalloutBalloon
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

