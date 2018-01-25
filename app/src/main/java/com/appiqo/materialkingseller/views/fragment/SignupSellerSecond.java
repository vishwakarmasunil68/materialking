package com.appiqo.materialkingseller.views.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.AdjustableLayout;
import com.appiqo.materialkingseller.helper.MultiSelectionSpinner;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerSecond extends Fragment implements View.OnClickListener {
    View view;
    Toolbar toolbar;

    AppCompatButton btnContinue;

    @BindView(R.id.et_company_name)
    AppCompatEditText etCompanyName;

    @BindView(R.id.et_contact_name)
    AppCompatEditText etContactName;

    @BindView(R.id.et_address)
    AppCompatEditText etAddress;

    @BindView(R.id.et_telephone)
    AppCompatEditText etTelephone;

    @BindView(R.id.tv_state)
    AppCompatEditText tvState;

    @BindView(R.id.tv_city)
    AppCompatEditText tvCity;

    @BindView(R.id.et_pincode)
    AppCompatEditText etPincode;

    @BindView(R.id.et_area)
    AppCompatEditText etArea;

    @BindView(R.id.et_cities_to_serve)
    AppCompatEditText etCitiesToServe;

    @BindView(R.id.et_registration_no)
    AppCompatEditText etRegistrationNo;

    @BindView(R.id.et_quantity)
    AppCompatEditText etQuantity;

    @BindView(R.id.et_bill_picture)
    AppCompatEditText etBillPicture;

    @BindView(R.id.multiselectionspinner)
    MultiSelectionSpinner multiselectionspinner;

    @BindView(R.id.iv_selected_bill)
    ImageView ivSelectedBill;

    public int CITIES_TO_SERVE_REQUEST = 150;

    public int PLACE_PICKER_REQUEST = 99;

    String firmName, contactName, businessAddress, telephone, state, city, pincode, area, citiesToServe, businessTpye, BusinessRegistrationNo, Quantity;
    Double lat;
    Double longi;

    Double citieslat;
    Double citieslongi;
    String citiesserve = "";

    Unbinder unbinder;
    List<String> stringList = new ArrayList<>();

    private AdjustableLayout adjustableLayout;
    ProgressView progressView;
    String businessString = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_second, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("2/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        List<String> businesstypeList = new ArrayList<String>();
        businesstypeList.add("manufacture");
        businesstypeList.add("retailer");
        businesstypeList.add("wholseler");
        businesstypeList.add("importer");
        businesstypeList.add("c & f");

        multiselectionspinner.setItems(businesstypeList);


        multiselectionspinner.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {
                Log.e("strings", strings.toString());
                for (int i = 0; i < strings.size(); i++) {
                    if (i == 0) {
                        businessString = strings.get(0);
                        Log.e("business",businessString);
                    } else {
                        businessString += "," + strings.get(i);
                        Log.e("business",businessString);
                    }
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(stringList.size() > 0)) {
                    Toast.makeText(getActivity(), "Please Enter Your cities to serve.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firmName = etCompanyName.getText().toString();
                contactName = etContactName.getText().toString();
                businessAddress = etAddress.getText().toString();
                telephone = etTelephone.getText().toString();
                state = tvState.getText().toString();
                city = tvCity.getText().toString();
                pincode = etPincode.getText().toString();
                area = etArea.getText().toString();
                BusinessRegistrationNo = etRegistrationNo.getText().toString();
                Quantity = etQuantity.getText().toString();

                String citiesString = "";
                for (int i = 0; i < stringList.size(); i++) {
                    if (i == 0) {
                        citiesString = stringList.get(0);
                    } else {
                        citiesString += "," + stringList.get(i);
                    }
                }

                if (Validation.nameValidator(firmName) || Validation.nameValidator(contactName) || Validation.nameValidator(businessAddress) || Validation.nameValidator(telephone)
                        || Validation.nameValidator(state) || Validation.nameValidator(city) || Validation.nameValidator(pincode) || Validation.nameValidator(area)
                        || Validation.nameValidator(BusinessRegistrationNo)) {

                    SignupSellerThird fragmentThird = new SignupSellerThird();
                    Bundle bundle = new Bundle();

                    bundle.putString("firmName", firmName);
                    bundle.putString("contactName", contactName);
                    bundle.putString("businessAddress", businessAddress);
                    bundle.putString("telephone", telephone);
                    bundle.putString("state", state);
                    bundle.putString("city", city);
                    bundle.putString("pincode", pincode);
                    bundle.putString("area", area);
                    bundle.putString("citiesToServe", citiesString);
                    bundle.putString("businessTpye", businessString);
                    bundle.putString("BusinessRegistrationNo", BusinessRegistrationNo);
                    bundle.putString("Quantity", Quantity);

                    fragmentThird.setArguments(bundle);
                    ((SignupHandler) getActivity()).changeFragment(fragmentThird, "signupthird");

                } else {
                    Toast.makeText(getActivity(), "Please fill all the data described above", Toast.LENGTH_SHORT).show();
                }
            }
        });


        etAddress.setOnClickListener(this);
        etCitiesToServe.setOnClickListener(this);

        adjustableLayout = (AdjustableLayout) view.findViewById(R.id.container); //Custom layout file
        // addViewInALoop();


        etBillPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(getActivity())                         //  Initialize ImagePicker with activity or fragment context
                        .setToolbarColor("#212121")         //  Toolbar color
                        .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                        .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                        .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                        .setProgressBarColor("#4CAF50")     //  ProgressBar color
                        .setBackgroundColor("#212121")      //  Background color
                        .setCameraOnly(false)               //  Camera mode
                        .setMultipleMode(false)              //  Select multiple images or single image
                        .setFolderMode(true)                //  Folder mode
                        .setShowCamera(true)                //  Show camera button
                        .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                        .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                        .setDoneTitle("Done")               //  Done button title
                        .setKeepScreenOn(true)              //  Keep screen on when selecting images
                        .start();
            }
        });


        setDefaults();


        return view;
    }

    private void setDefaults() {
        addRandomViewDefaults();

    }

    private void addRandomViewDefaults() {
        for (int i = 0; i < stringList.size(); i++) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_images, null);
            TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
            tvNumber.setText(stringList.get(i));
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

            newView.setTag(stringList.get(i));
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(stringList.get(i));
            adjustableLayout.addView(newView);
        }
    }

    private void addButtons() {
        Button button = new Button(getActivity());
        button.setText("Button");
        adjustableLayout.addView(button);
    }

    private void addRandomView() {
        String name = citiesserve;
        if (!TextUtils.isEmpty(name)) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_images, null);
            TextView tvNumber = (TextView) newView.findViewById(R.id.tvNumber);
            tvNumber.setText(name);
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);

            newView.setTag(name);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                    removeFromArrayList(newView);
                }
            });
            tvNumber.setText(name);
            adjustableLayout.addView(newView);
            stringList.add(name);
            Toast.makeText(getActivity(), "Added : " + name, Toast.LENGTH_SHORT).show();

            etCitiesToServe.getText().clear();
        } else {
            Toast.makeText(getActivity(), "Enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromArrayList(View newView) {

        Log.e("newView.getTag()", newView.getTag().toString());
        stringList.remove(newView.getTag());

    }

    private void addChipsView() {
        String name = etCitiesToServe.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_chip_text, null);
            TextView tvName = (TextView) newView.findViewById(R.id.tvName);
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                }
            });
            tvName.setText(name);
            adjustableLayout.addView(newView);
        } else {
            Toast.makeText(getActivity(), "Enter some text", Toast.LENGTH_SHORT).show();
        }
    }

    private void addViewInALoop() {

        Log.e("ListSize", "" + stringList.size());

        for (int i = 0; i < stringList.size(); i++) {
            final View newView = LayoutInflater.from(getActivity()).inflate(R.layout.view_chip_text, null);
            TextView tvName = (TextView) newView.findViewById(R.id.tvName);
            ImageView ivRemove = (ImageView) newView.findViewById(R.id.ivRemove);
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adjustableLayout.removeView(newView);
                }
            });
            tvName.setText(stringList.get(i));
            adjustableLayout.addingMultipleView(newView);
        }
        adjustableLayout.invalidateView();
    }

    private void initialize() {

        toolbar = view.findViewById(R.id.toolbar);
        btnContinue = view.findViewById(R.id.btn_signup_seller_second_continue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_address:
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    getActivity().startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.et_cities_to_serve:
                PlacePicker.IntentBuilder citiesbbuilder = new PlacePicker.IntentBuilder();
                try {
                    getActivity().startActivityForResult(citiesbbuilder.build(getActivity()), CITIES_TO_SERVE_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);

                Log.e("lat", place.getLatLng().toString());

                lat = place.getLatLng().latitude;
                longi = place.getLatLng().longitude;

                etAddress.setText(place.getAddress());
                connectApi();
            }
        }

        if (requestCode == 150) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                citieslat = place.getLatLng().latitude;
                citieslongi = place.getLatLng().longitude;

                connectApiForCities();
            }
        }

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // do your logic here...
            if (images.size() > 0) {
                etBillPicture.setText("Change Bill");
                SignupHandler.BILLPICTURE = compressImage(images.get(0).getPath());
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivSelectedBill);

            } else {
                etBillPicture.setText("Select Bill Picture");
            }
        }
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
        final int REQUIRED_SIZE = 100;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }


    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;


        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;


        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }


        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();


        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MaterialKingSeller/Profile");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    private void connectApi() {
        progressView = new ProgressView(getActivity());
        progressView.showLoader();

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + longi + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU";

        Log.e("url", url);

        MyApplication.getInstance().cancelPendingRequests("getList");
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("xyz", response.toString());
                progressView.hideLoader();

                try {
                    JSONObject json = new JSONObject(response);

                    if (json.getString("status").equalsIgnoreCase("ok")) {
                        JSONArray array = json.getJSONArray("results");

                        progressView.hideLoader();
                        JSONObject item = array.getJSONObject(0);

                        JSONArray mJsonArrayAddress = new JSONArray(item.getString("address_components"));

                        ArrayList<String> mStringsTypes = new ArrayList<>();

                        String city = "", state = "", pinCode = "", locality = "",
                                sublocality = "";

                        for (int i = 0; i < mJsonArrayAddress.length(); i++) {

                            mStringsTypes.clear();
                            JSONObject mObject = mJsonArrayAddress.getJSONObject(i);
                            JSONArray mArrayTypes = new JSONArray(mObject.getString("types"));

                            for (int j = 0; j < mArrayTypes.length(); j++) {
                                mStringsTypes.add(mArrayTypes.get(j).toString());
                            }


                            if (mStringsTypes.contains("postal_code")) {
                                pinCode = mObject.getString("long_name");
                            }

                            if (mStringsTypes.contains("administrative_area_level_1")) {
                                state = mObject.getString("long_name");
                            }

                            if (mStringsTypes.contains("administrative_area_level_2")) {
                                city = mObject.getString("long_name");
                            }

                            if (mStringsTypes.contains("locality")) {
                                locality = mObject.getString("long_name");
                            }

                            if (mStringsTypes.contains("sublocality_level_1")) {
                                sublocality = mObject.getString("long_name");
                            }

                        }

                        tvCity.setText(city);
                        tvState.setText(state);
                        etPincode.setText(pinCode);
                        if (sublocality.equalsIgnoreCase("")) {
                            etArea.setText(locality);
                        } else {
                            etArea.setText(sublocality);
                        }

                        Log.e("postalCode", pinCode);
                        Log.e("state", state);
                        Log.e("city", city);

                        Toast.makeText(getActivity(), "Please verify auto filled State,City and Pincode ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Address Not Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressView.hideLoader();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                progressView.hideLoader();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //  MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));

                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "getList");

    }

    private void connectApiForCities() {

        progressView = new ProgressView(getActivity());
        progressView.showLoader();

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + citieslat + "," + citieslongi + "&sensor=true&key=AIzaSyC44YXpePl5MHdJicOzT7qEGwO4BWfH-tU";

        Log.e("url", url);

        MyApplication.getInstance().cancelPendingRequests("getList");
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("xyz", response.toString());
                progressView.hideLoader();

                try {
                    JSONObject json = new JSONObject(response);

                    if (json.getString("status").equalsIgnoreCase("ok")) {
                        JSONArray array = json.getJSONArray("results");

                        progressView.hideLoader();
                        JSONObject item = array.getJSONObject(0);

                        JSONArray mJsonArrayAddress = new JSONArray(item.getString("address_components"));

                        ArrayList<String> mStringsTypes = new ArrayList<>();

                        for (int i = 0; i < mJsonArrayAddress.length(); i++) {

                            mStringsTypes.clear();
                            JSONObject mObject = mJsonArrayAddress.getJSONObject(i);
                            JSONArray mArrayTypes = new JSONArray(mObject.getString("types"));

                            for (int j = 0; j < mArrayTypes.length(); j++) {
                                mStringsTypes.add(mArrayTypes.get(j).toString());
                            }

                            if (mStringsTypes.contains("administrative_area_level_2")) {
                                citiesserve = mObject.getString("long_name");
                            }
                        }

                        addRandomView();
                        //tvCity.setText(city);

                        Log.e("cities", citiesserve);

                    } else {
                        Toast.makeText(getActivity(), "Address Not Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressView.hideLoader();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                progressView.hideLoader();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //  MyApplication.showError(getActivity(),getString(R.string.noConnection),getString(R.string.checkInternet));

                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    // MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));

                } else if (error instanceof ParseError) {
                    //TODO
                    Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    //MyApplication.showError(getActivity(),getString(R.string.error),getString(R.string.tryAfterSomeTime));
                }

            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "getList");


    }

}
