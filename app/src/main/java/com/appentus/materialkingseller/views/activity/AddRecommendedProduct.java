package com.appentus.materialkingseller.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.ToastClass;
import com.appentus.materialkingseller.Util.UtilityFunction;
import com.appentus.materialkingseller.pojo.ItemRecommendationPOJO;
import com.appentus.materialkingseller.pojo.order.OrderPOJO;
import com.bumptech.glide.Glide;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRecommendedProduct extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.iv_product_image)
    ImageView iv_product_image;
    @BindView(R.id.et_product_description)
    EditText et_product_description;
    @BindView(R.id.et_product_name)
    EditText et_product_name;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_dod)
    EditText et_dod;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_shipping_charges)
    EditText et_shipping_charges;
    @BindView(R.id.et_product_quantity)
    EditText et_product_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recommended_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        iv_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        et_dod.setText(UtilityFunction.getServerCurrentDate());

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_product_name.getText().toString().length()!=0
                        &&et_product_description.getText().toString().length()!=0
                        &&image_selected.length()!=0
                        &&et_product_quantity.getText().toString().length()!=0
                        &&et_dod.getText().toString().length()!=0
                        &&et_price.getText().toString().length()!=0
                        &&et_shipping_charges.getText().toString().length()!=0
                        ) {
                    ItemRecommendationPOJO itemRecommendationPOJO = new ItemRecommendationPOJO();
                    itemRecommendationPOJO.setProduct_name(et_product_name.getText().toString());
                    itemRecommendationPOJO.setDescription(et_product_description.getText().toString());
                    itemRecommendationPOJO.setImage_path(image_selected);
                    itemRecommendationPOJO.setQuantity(et_product_quantity.getText().toString());
                    itemRecommendationPOJO.setDelivered_on(et_dod.getText().toString());
                    itemRecommendationPOJO.setPrice(et_price.getText().toString());
                    itemRecommendationPOJO.setShipping_price(et_shipping_charges.getText().toString());


                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("recommend", itemRecommendationPOJO);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"please Enter all the required fields");
                }
            }
        });

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddRecommendedProduct.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Date of delivery");
            }
        });


    }
    String image_selected="";
    private void imagePicker() {
        ImagePicker.with(this)
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(false)
                .setFolderMode(true)
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                image_selected=images.get(0).getPath();
                Glide.with(this).load(images.get(0).getPath()).into(iv_product_image);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

//        String date = day + "-" + month + "-" + year;
        String date = year + "-" + month + "-" + day;
        et_dod.setText(date);
    }
}
