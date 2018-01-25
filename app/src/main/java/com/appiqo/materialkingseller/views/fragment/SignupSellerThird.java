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
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.helper.MultiSelectionSpinner;
import com.appiqo.materialkingseller.helper.MyApplication;
import com.appiqo.materialkingseller.helper.PrefsData;
import com.appiqo.materialkingseller.helper.ProgressView;
import com.appiqo.materialkingseller.helper.Validation;
import com.appiqo.materialkingseller.helper.VolleyMultipartRequest;
import com.appiqo.materialkingseller.helper.WebApis;
import com.appiqo.materialkingseller.model.CategoryModel;
import com.appiqo.materialkingseller.model.SubCategory;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/19/2018.
 */

public class SignupSellerThird extends Fragment {
    View view;
    Toolbar toolbar;
    AppCompatButton btnContinue;
    String firmName, contactName, businessAddress, telephone, state, city, pincode, area, citiesToServe, businessTpye, BusinessRegistrationNo, Quantity;
    @BindView(R.id.et_annual_turnover)
    AppCompatEditText etAnnualTurnover;
    @BindView(R.id.et_specialities)
    AppCompatEditText etSpecialities;
    @BindView(R.id.et_certification)
    AppCompatEditText etCertification;
    @BindView(R.id.et_attach_pics)
    AppCompatEditText etAttachPics;
    @BindView(R.id.et_gst_number)
    AppCompatEditText etGstNumber;
    @BindView(R.id.iv_attach_pic)
    ImageView ivAttachPic;
    @BindView(R.id.multispinner_category)
    MultiSelectionSpinner multispinnerCategory;
    @BindView(R.id.tv_subcategory)
    AppCompatTextView tvSubcategory;
    @BindView(R.id.multispinner_subcategory)
    MultiSelectionSpinner multispinnerSubcategory;
    @BindView(R.id.btn_signup_seller_third_continue)
    AppCompatButton btnSignupSellerThirdContinue;

    String annualTurnover, specialities, certification,categories, gstNumber;

    ProgressView progressView;

  //  ArrayList<SubCategory> subcategoryModelList=new ArrayList<>();
    List<CategoryModel> categoryModelList=new ArrayList<>();


    List<String>mainCategoryData=new ArrayList<>();
    List<String> subCategoryData=new ArrayList<>();
    List<String> subCategoryDataID=new ArrayList<>();



    Unbinder unbinder;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_third, container, false);
        unbinder = ButterKnife.bind(this, view);

        initialize();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Extras");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("3/4");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        getCategoryFromApi();

        multispinnerCategory.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                Log.e("selected",indices.toString());
                if (!(indices.size()<0)){

                    tvSubcategory.setVisibility(View.VISIBLE);
                    multispinnerSubcategory.setVisibility(View.VISIBLE);
                subCategoryData.clear();
                subCategoryDataID.clear();


                for (int i=0;i<indices.size();i++){

                    ArrayList<SubCategory> mSubCategory = categoryModelList.get(indices.get(i)).getSubCategories();
                    Log.e("mSubCategory",""+mSubCategory.size());


                    for (int j = 0; j < mSubCategory.size();j++)
                    {
                        subCategoryData.add(mSubCategory.get(j).getSubcategoryName());
                        subCategoryDataID.add(mSubCategory.get(j).getSubCategoryId());
                    }
                }

                    if (subCategoryData.size()>0){
                        multispinnerSubcategory.setItems(subCategoryData);
                    }else {
                        tvSubcategory.setVisibility(View.GONE);
                        multispinnerSubcategory.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "This Category does not have any sub category", Toast.LENGTH_SHORT).show();
                    }





                }else{
                    Toast.makeText(getActivity(), "Please select the category to continue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });



        multispinnerSubcategory.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {

            }
        });



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            firmName = bundle.getString("firmName");
            contactName = bundle.getString("contactName");
            businessAddress = bundle.getString("businessAddress");
            telephone = bundle.getString("telephone");
            state = bundle.getString("state");
            city = bundle.getString("city");
            pincode = bundle.getString("pincode");
            area = bundle.getString("area");
            citiesToServe = bundle.getString("citiesToServe");
            businessTpye = bundle.getString("businessTpye");
            BusinessRegistrationNo = bundle.getString("BusinessRegistrationNo");
            Quantity = bundle.getString("Quantity");

            Log.e("firmName", firmName);
            Log.e("contactName", contactName);
            Log.e("businessAddress", businessAddress);
            Log.e("telephone", telephone);
            Log.e("state", state);
            Log.e("city", city);
            Log.e("pincode", pincode);
            Log.e("area", area);
            Log.e("citiesToServe", citiesToServe);
            Log.e("businessTpye", businessTpye);
            Log.e("BusinessRegistrationNo", BusinessRegistrationNo);
            Log.e("Quantity", Quantity);
        }

        etAttachPics.setOnClickListener(new View.OnClickListener() {
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


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                annualTurnover = etAnnualTurnover.getText().toString();
                specialities = etSpecialities.getText().toString();
                certification=etCertification.getText().toString();
                gstNumber = etGstNumber.getText().toString();


                if (Validation.nameValidator(annualTurnover) || Validation.nameValidator(specialities) || Validation.nameValidator(gstNumber)) {
                    ConnectApiMultipart();
                } else {
                    Toast.makeText(getActivity(), "Please fill all the data", Toast.LENGTH_SHORT);
                }
            }
        });


        return view;
    }

    private void getCategoryFromApi() {

        progressView = new ProgressView(getActivity());
        progressView.showLoader();

        MyApplication.getInstance().cancelPendingRequests("getList");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.CATEGORY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("get_category", response);

                progressView.hideLoader();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {

                        categoryModelList.clear();

                        mainCategoryData.clear();

                        JSONArray jarray=mObject.getJSONArray("result");
                        for (int i=0;i<jarray.length();i++){

                            ArrayList<SubCategory> subcategoryModelList = new ArrayList<>();

                            JSONObject json=jarray.getJSONObject(i);
                            String catId=json.getString("id");
                            String catName=json.getString("name");

                            JSONArray subArray=json.getJSONArray("sub");
                            for (int j=0;j<subArray.length();j++){

                                JSONObject subobject=subArray.getJSONObject(j);
                                String subcatId=subobject.getString("id");
                                String mainCatId=subobject.getString("cat_id");
                                String subCatName=subobject.getString("name");

                                SubCategory subCategory = new SubCategory();
                                subCategory.setCategoryId(mainCatId);
                                subCategory.setSubCategoryId(subcatId);
                                subCategory.setSubcategoryName(subCatName);
                                subcategoryModelList.add(subCategory);
                            }

                            CategoryModel  model = new CategoryModel();
                            model.setId(catId);
                            model.setCategoryName(catName);
                            model.setSubCategories(subcategoryModelList);
                            categoryModelList.add(model);
                            Log.e("model",""+subcategoryModelList.size());

                        }

                        for (int z=0;z<categoryModelList.size();z++){
                            mainCategoryData.add(categoryModelList.get(z).getCategoryName());
                            Log.e("categoryModelList",""+categoryModelList.get(z).getSubCategories().size());

                        }
                        multispinnerCategory.setItems(mainCategoryData);
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void initialize() {
        toolbar = view.findViewById(R.id.toolbar);
        btnContinue = view.findViewById(R.id.btn_signup_seller_third_continue);
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

   /* private void connectApi() {
       *//* progressView = new ProgressView(getActivity());
        progressView.showLoader();


        for (int i = 0 ; i < subCategoryDataID.size(); i++)
        {
            if(i==0)
            {
                categories  = subCategoryDataID.get(0);
                Log.e("category",""+categories);
            }
            else
            {
                categories+= ","+subCategoryDataID.get(i);
                Log.e("category",""+categories);
            }
        }*//*

       *//* MyApplication.getInstance().cancelPendingRequests("getList");*//*
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                WebApis.USERREGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                *//*Log.e("get_signup_ird", response);

                progressView.hideLoader();
                try {
                    JSONObject mObject = new JSONObject(response);
                    String status = mObject.getString("status");
                    String message = mObject.getString("message");
                    if (status.equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                        ((SignupHandler) getActivity()).changeFragment(new SignupSellerFourth(), "signupfourth");

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }*//*


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                *//*progressView.hideLoader();
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
                }*//*

            }
        }) {

            *//**
             * Passing some request headers
             * *//*
            @Override
            public Map<String, String> getParams() {
               *//* HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("firm_name", firmName);
                headers.put("contact_name", contactName);
                headers.put("business_address", businessAddress);
                headers.put("telephone", telephone);
                headers.put("state", state);
                headers.put("city", city);
                headers.put("pincode", pincode);
                headers.put("area", area);
                headers.put("cities_to_serve", citiesToServe);
                headers.put("business_type", businessTpye);
                headers.put("business_registration_no", BusinessRegistrationNo);
                headers.put("latest_bill", "attach bill");
                headers.put("minimum_order", Quantity);

               // headers.put("userfile2",);
                headers.put("annual_turnover", annualTurnover);
                headers.put("specialities", specialities);
                headers.put("certifications",certification);
                headers.put("categories", categories);
                headers.put("gstNumber", gstNumber);
                //headers.put("userfile1",)
                headers.put("fcm_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN));
                headers.put("deviceType", "android");
                headers.put("id", MyApplication.readStringPref(PrefsData.PREF_USERID));

                *//**//*SignupHandler.BILLPICTURE;
                SignupHandler.ATTACHPIC;*//**//*


                Log.e("POST DATA", headers.toString());

                return headers;*//*
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, "getList");

    }*/

    private void ConnectApiMultipart(){
        progressView = new ProgressView(getActivity());
        progressView.showLoader();


        for (int i = 0 ; i < subCategoryDataID.size(); i++)
        {
            if(i==0)
            {
                categories  = subCategoryDataID.get(0);
                Log.e("category",""+categories);
            }
            else
            {
                categories+= ","+subCategoryDataID.get(i);
                Log.e("category",""+categories);
            }
        }

        MyApplication.getInstance().cancelPendingRequests("getList");
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, WebApis.USERREGISTER,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("get_signup_ird", new String(response.data));

                        progressView.hideLoader();
                        try {
                            JSONObject mObject = new JSONObject(new String(response.data));
                            String status = mObject.getString("status");
                            String message = mObject.getString("message");
                            if (status.equalsIgnoreCase("1")) {
                                Toast.makeText(getActivity(), "Sucess", Toast.LENGTH_SHORT).show();

                                ((SignupHandler) getActivity()).changeFragment(new SignupSellerFourth(), "signupfourth");

                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressView.hideLoader();
                        error.printStackTrace();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("firm_name", firmName);
                headers.put("contact_name", contactName);
                headers.put("business_address", businessAddress);
                headers.put("telephone", telephone);
                headers.put("state", state);
                headers.put("city", city);
                headers.put("pincode", pincode);
                headers.put("area", area);
                headers.put("cities_to_serve", citiesToServe);
                headers.put("business_type", businessTpye);
                headers.put("business_registration_no", BusinessRegistrationNo);
                headers.put("latest_bill", "attach bill");
                headers.put("minimum_order", Quantity);
                headers.put("annual_turnover", annualTurnover);
                headers.put("specialities", specialities);
                headers.put("certifications",certification);
                headers.put("categories", categories);
                headers.put("gstNumber", gstNumber);
                headers.put("fcm_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN));
                headers.put("deviceType", "android");
                headers.put("id", MyApplication.readStringPref(PrefsData.PREF_USERID));
                Log.e("POST DATA", headers.toString());
                return headers;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Bitmap bitmap1 = null;
                Bitmap bitmap2 = null;
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Uri.fromFile(new File(SignupHandler.ATTACHPIC)));
                    bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(new File(SignupHandler.BILLPICTURE)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                params.put("userfile1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap1)));
                params.put("userfile2", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap2)));
                return params;
            }
        };

        Volley.newRequestQueue(MyApplication.getInstance()).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // do your logic here...
            if (images.size() > 0) {
                etAttachPics.setText("Change pic");
                SignupHandler.ATTACHPIC = compressImage(images.get(0).getPath());
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivAttachPic);

            } else {
                etAttachPics.setText("Select Certificate Picture");
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
