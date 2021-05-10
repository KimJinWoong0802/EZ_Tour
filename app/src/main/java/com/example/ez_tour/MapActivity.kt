package com.example.ez_tour

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ez_tour.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.IOException


class MapActivity : AppCompatActivity(), MapView.POIItemEventListener {

    private lateinit var binding: ActivityMapBinding
    private var latitude : ArrayList<Double> = ArrayList()
    private var longtitude : ArrayList<Double> = ArrayList()
    private var locationManager: LocationManager? = null
    private var myLatitude: Double ?= null
    private var myLongtitude:Double ?= null
    private var invoiceMapItems = ArrayList<InvoiceMapItem>()
    private var clickLatitude: Double ?= null
    private var clickLongtitude: Double ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        var mapPoints: ArrayList<MapPoint>
        locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //사용자 현재 위치
        val userLocation = getMyLocation()
        if (userLocation != null) {
            val latitude = userLocation?.latitude
            val longitude = userLocation?.longitude
            myLatitude = latitude
            myLongtitude = longitude
        }

        //kakao map 띄우기
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 주소 가져오기
        var dummyList : ArrayList<InvoiceItem> = getDummyData()

        // 해당주소의 위도, 경도 가져오기
        mapPoints = getLocation(dummyList)

        // 운송장 + 위도,경도 합체
        invoiceMapItems = makeInvoiceItems(dummyList, mapPoints)

        //내위치
        mapView.setMapCenterPoint(mapPoints[0], true)

        // 마커찍기
        setMarker(mapView, invoiceMapItems)


        //Help 이미지 초기화
        val img_hlep = findViewById<View>(R.id.img_hlep) as ImageView
        img_hlep.visibility = View.INVISIBLE
        //Help 버튼 초기화
        val btn_help = findViewById<Button>(R.id.btn_help) as ImageButton
        var counter = 0
        //Help 버튼 클릭리스너
        btn_help.setOnClickListener {
            if (counter == 0) {
                counter++
                btn_help.setImageResource(R.drawable.kakao_login_large_narrow)
                img_hlep.visibility = View.VISIBLE
            }
            else{
                counter--
                btn_help.setImageResource(R.drawable.common_google_signin_btn_icon_dark)
                img_hlep.visibility = View.INVISIBLE
            }
        }


    }
    //현재 위치 확인
    private fun getMyLocation(): Location? {
        var currentLocation: Location? = null
        val REQUEST_CODE_LOCATION = 2

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
            getMyLocation()
        } else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLocation = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLocation
    }

    //주소 가져오기
    private fun getDummyData(): ArrayList<InvoiceItem> {
        val testAddrList :ArrayList<InvoiceItem> = ArrayList()

        testAddrList.add(InvoiceItem("306728799823", "경기도 수원시 팔달구 교동 130-4", "홍길동1"))
        testAddrList.add(InvoiceItem("306728799845", "경기도 수원시 팔달구 교동 매산로 132", "홍길동2"))
        testAddrList.add(InvoiceItem("306728799834", "경기도 수원시 팔달구 교동 91-1", "홍길동3"))

        return testAddrList
    }

    //위도 경도 가져오기.
    private fun getLocation(invoiceItems: ArrayList<InvoiceItem>): ArrayList<MapPoint> {
        val mapList = ArrayList<MapPoint>()
        val addrList : ArrayList<Address> = ArrayList()

        try {
            val geoCoder = Geocoder(this)
            var tmpAddrList: List<Address>

            for (i in invoiceItems.indices) {
                tmpAddrList = geoCoder.getFromLocationName(invoiceItems[i].address, 1)
                for (j in tmpAddrList.indices) {
                    val lat = tmpAddrList[j].latitude
                    val lon = tmpAddrList[j].longitude
                    mapList.add(MapPoint.mapPointWithGeoCoord(lat, lon))
                }
                addrList.addAll(tmpAddrList)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        for (i in addrList.indices) {

            val lat = addrList[i].latitude
            val lon = addrList[i].longitude

            mapList.add(MapPoint.mapPointWithGeoCoord(lat, lon))

        }
        return mapList
    }

    //위도 경도 합체
    private fun makeInvoiceItems(dummyList: ArrayList<InvoiceItem>, mapPoints: ArrayList<MapPoint>): ArrayList<InvoiceMapItem> {
        val list = ArrayList<InvoiceMapItem>()
        var item: InvoiceMapItem
        for (i in dummyList.indices) {
            item = InvoiceMapItem()
            item.invoiceItem = dummyList[i]
            item.mapPoint = mapPoints[i]
            list.add(item)
        }
        return list
    }

    //마커 설정
    private fun setMarker(mapView: MapView, invoiceMapItems: ArrayList<InvoiceMapItem>) {
        var marker: MapPOIItem
        for (i in invoiceMapItems.indices) {
            marker = MapPOIItem()
            marker.itemName = invoiceMapItems[i].invoiceItem?.name
            marker.tag = 0
            marker.mapPoint = invoiceMapItems[i].mapPoint
            marker.markerType = MapPOIItem.MarkerType.BluePin
            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
            mapView.addPOIItem(marker)
        }
    }


    // POIItem 전용
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
