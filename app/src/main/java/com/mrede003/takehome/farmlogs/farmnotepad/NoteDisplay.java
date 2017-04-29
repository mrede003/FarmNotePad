package com.mrede003.takehome.farmlogs.farmnotepad;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteDisplay extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private Bundle bundle;

    private EditText textArea;
    private TextView dateTime;
    private TextView gpsCoord;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;

    private String picPath1;
    private String picPath2;
    private String picPath3;

    private final int CAMERA_REQUEST_CODE=1;

    private boolean existingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_display);

        Intent intent= getIntent();
        existingNote=false;
        init();
        if(intent.hasExtra(getString(R.string.note_id)))
        {
            bundle=intent.getExtras();
            existingNote=true;
            setUpExisting();
        }else{

        }
        mGoogleApiClient = new GoogleApiClient.Builder(NoteDisplay.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();


    }
    public void init()
    {
        textArea=(EditText) findViewById(R.id.textArea);
        dateTime=(TextView) findViewById(R.id.date_time);
        gpsCoord=(TextView) findViewById(R.id.gps_coord);
        pic1=(ImageView) findViewById(R.id.imageview_1);
        pic2=(ImageView) findViewById(R.id.imageview_2);
        pic3=(ImageView) findViewById(R.id.imageview_3);
    }
    public void setUpExisting()
    {
        setTitle((String)bundle.get(getString(R.string.title)));                            //set title to existing
        dateTime.setText(getString(R.string.create)+bundle.get(getString(R.string.date)));
        gpsCoord.setText(getString(R.string.gps_colon)+bundle.get(getString(R.string.latitude))+" "
            +bundle.get(getString(R.string.longitude)));
        textArea.setText((String)bundle.get(getString(R.string.content)));

        picPath1=(String) bundle.get(getString(R.string.pic1));
        picPath2=(String) bundle.get(getString(R.string.pic2));
        picPath3=(String) bundle.get(getString(R.string.pic3));
    }
    public void save()
    {
        Note note=new Note();       //create a note object from the input
        note.setPic1(picPath1);     //the following values are values that could be updated if existing or new notes
        note.setPic2(picPath2);
        note.setPic3(picPath3);
        note.setContent(textArea.getText().toString());
        //Check to see if note already exists
        if(existingNote)                                                                    //is this a new note or existing one?
        {                                                                                   //if exisiting.....
            note.setId((int)bundle.get(getString(R.string.note_id)));                       //These values cannot change after a new note has been made
            note.setLatitude((double)bundle.get(getString(R.string.latitude)));
            note.setLongitude((double)bundle.get(getString(R.string.longitude)));
            note.setDate((String)bundle.get(getString(R.string.date)));
            note.setTitle((String)bundle.get(getString(R.string.title)));
            DatabaseHelper.getInstance(this).updateNote(note);          //saved changes to database
            finish();
        }else{
            if(Helper.hasLocationPermission(this)&&Helper.checkGPSStatus(this)) {
                note.setLatitude(currentLocation.getLatitude());         //These values cannot change after a new note has been made
                note.setLongitude(currentLocation.getLongitude());
            }else{
                note.setLatitude(-1.0);         //These values cannot change after a new note has been made
                note.setLongitude(-1.0);
            }
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.US);
            note.setDate(df.format(Calendar.getInstance().getTime()));
            titleDialog(note);
        }
    }
    public void titleDialog(Note note)
    {
        final Note finalNote=note;
        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        final EditText edittext = new EditText(this);
        alert.setMessage(getString(R.string.enter_title));
        alert.setTitle(getString(R.string.set_title));

        alert.setView(edittext);

        alert.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String title= edittext.getText().toString();
                if(title.equals(""))
                {
                    Toast.makeText(NoteDisplay.this, R.string.blank_title_error,Toast.LENGTH_SHORT).show();
                }else{
                    finalNote.setTitle(edittext.getText().toString());                      //These values cannot change after a new note has been made
                    DatabaseHelper.getInstance(NoteDisplay.this).addNote(finalNote);
                    finish();
                }
            }
        });

        alert.show();
    }
    public void delete()
    {
        //Check if note is existing
        if(existingNote)
        {
            Note note=new Note();
            note.setId((int)bundle.get(getString(R.string.note_id)));
            DatabaseHelper.getInstance(this).deleteNote(note);
        }
        finish();
    }
    public void launchMaps(View view)
    {
        double longitude=0.0, latitude=0.0;
        if(existingNote) {
            latitude = ((double) bundle.get(getString(R.string.latitude)));
            longitude = ((double) bundle.get(getString(R.string.longitude)));
        }else {
            latitude=currentLocation.getLatitude();
            longitude=currentLocation.getLongitude();
        }
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        this.startActivity(intent);
    }
    public void picture()
    {
        if(!isPicsFull())
        {
            Toast.makeText(NoteDisplay.this, R.string.pics_full_error,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                pic1.setImageBitmap(imageBitmap);
            }
        }
    }
    public boolean isPicsFull()
    {
        return picPath1==null&&picPath2==null&&
                picPath3==null;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.picture:
                picture();
                return true;
            case R.id.save:
                save();
                return true;
            case R.id.delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        if(!existingNote){
            mGoogleApiClient.connect();
            setPlayLocation();
        }
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.i(getString(R.string.noteDisplayTag), "Google Play Services connected");
        if(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(getString(R.string.noteDisplayTag), "Google Play Services suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(getString(R.string.noteDisplayTag), "Google Play Services connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        Log.d(getString(R.string.noteDisplayTag), location.toString());
    }
    public void setPlayLocation()
    {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    NoteDisplay.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

}