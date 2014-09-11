package com.errorpoint.whereami;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class LauncherActivity extends ActionBarActivity {
	
	ActionBar actionbar;
	ImageView menuIcon;
	
	double latitude;
	double longitude;
	private GoogleMap googleMap;
	GPSTracker gps;
	LatLng currentPos;
	Marker marker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
//		actionbar = getActionBar();
//		actionbar.setDisplayShowCustomEnabled(true);
//		actionbar.setCustomView(R.layout.action_bar_layout);
//		menuIcon = (ImageView) findViewById(R.id.menuIcon);
		
		
		initializeMap(1);
		
		
	}
	
	private void initializeMap(int mapType){
		//marker = googleMap.addMarker(null);  // problem with re-initializing
		
		gps = new GPSTracker(LauncherActivity.this);
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		latitude = gps.getLatitude();
		longitude = gps.getLongitude();
		
		currentPos = new LatLng(latitude, longitude);
		
		googleMap.animateCamera(CameraUpdateFactory.zoomIn());
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20));
		
		marker = googleMap.addMarker(
				new MarkerOptions().position(currentPos)
                .title("Draggable Marker")
                .snippet("Long press and move the marker if needed.")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
		
		
		
		
		googleMap.setOnMarkerDragListener(new OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Dragging");
            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                LatLng markerLocation = marker.getPosition();
                Toast.makeText(LauncherActivity.this, markerLocation.toString(), Toast.LENGTH_LONG).show();
                Log.d("Marker", "finished");
            }

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("Marker", "Started");

            }
        });
		
		if(mapType==1){
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		} else if(mapType==2){
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		} else if(mapType==3){
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		} else if(mapType==4){
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		} else if(mapType==5){
			googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
		} else{
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.launcher, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.action_normal:
			initializeMap(1);
			Toast.makeText(this, "Loading Normal View...", Toast.LENGTH_SHORT).show();
			return true;
		
		case R.id.action_hybrid:
			initializeMap(2);
			Toast.makeText(this, "Loading Hybrid View...", Toast.LENGTH_SHORT).show();
			return true;
		
		case R.id.action_setellite:
			initializeMap(3);
			Toast.makeText(this, "Loading Setellite View...", Toast.LENGTH_SHORT).show();
			return true;
		
		case R.id.action_terrain:
			initializeMap(4);
			Toast.makeText(this, "Loading Terrain View...", Toast.LENGTH_SHORT).show();
			return true;
		
		case R.id.action_none:
			initializeMap(5);
			Toast.makeText(this, "Loading None View...", Toast.LENGTH_SHORT).show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
