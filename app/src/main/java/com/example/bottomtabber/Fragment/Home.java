package com.example.bottomtabber.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Data.User;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.JsonFileReader;
import com.wildma.pictureselector.PictureSelector;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class Home extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView blurImageView;
    private ImageView avatarImageView;
    private User user;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        List<User> users;
        if(LitePal.where("username=?", Login.loginUser.getUsername()).find(User.class).isEmpty()){
            users = new ArrayList<>();
        }else{
            users = LitePal.where("username=?", Login.loginUser.getUsername()).find(User.class);
        }
        user = users.get(0);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(getContext(), "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        initView();
        return view;
    }

    private void initView(){
        Button exit = view.findViewById(R.id.bt_exit);
        blurImageView =  view.findViewById(R.id.iv_blur);
        avatarImageView = view.findViewById(R.id.iv_avatar);
        LinearLayout line2 = view.findViewById(R.id.line2);
        LinearLayout line3 = view.findViewById(R.id.line3);
        LinearLayout line4 = view.findViewById(R.id.line4);
        LinearLayout line5 = view.findViewById(R.id.line5);
        LinearLayout line6 = view.findViewById(R.id.line6);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        text6 = view.findViewById(R.id.text6);
        initUIData();
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
        line6.setOnClickListener(this);
        exit.setOnClickListener(this);
        avatarImageView.setOnClickListener(this);
        blurImageView.setOnClickListener(this);
    }
    private void initUIData(){
        Glide.with(this)
                .load(R.mipmap.picture)
                .into(blurImageView);

        Glide.with(this)
                .load(R.mipmap.boy)
                .into(avatarImageView);
        ((TextView)view.findViewById(R.id.text1)).setText(user.getUsername());
        if(!user.getBirth().equals("birth")){
           text2.setText(user.getBirth());
        }
        if(!user.getSex().equals("sex")){
            text3.setText(user.getSex());
        }
        if(!user.getCity().equals("city")){
            text4.setText(user.getCity());
        }
        if(!user.getEmail().equals("email")){
            text5.setText(user.getEmail());
        }
        if(!user.getFlag().equals("flag")){
            text6.setText(user.getFlag());
        }
    }

    private void photoAndCamera1() {
        PictureSelector
                .create(Home.this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(true, 200, 200, 1, 1);
    }
    private void photoAndCamera2() {
        PictureSelector
                .create(Home.this, PictureSelector.SELECT_REQUEST_CODE*2)
                .selectPicture(true, 200, 100, 1, 1);
    }

    private void quitCache(){
        RequestOptions requestOptions = RequestOptions
                .circleCropTransform()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).setDefaultRequestOptions(requestOptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                quitCache();
                Glide.with(this)
                        .load(picturePath)
                        .into(avatarImageView);
            }
        }
        else if(requestCode == PictureSelector.SELECT_REQUEST_CODE*2){
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                quitCache();
                blurImageView.setImageURI(Uri.fromFile(new File(picturePath)));
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_avatar:
                photoAndCamera1();
                break;
            case R.id.iv_blur:
                photoAndCamera2();
                break;
            case R.id.bt_exit:
                if(getActivity()!=null){
                    getActivity().finish();
                }
                break;
            case R.id.line2:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        text2.setText(dateStr);
                        user.setBirth(dateStr);
                        user.save();
                    }
                }).setCancelText("取消").setSubmitText("确定").build();
                pvTime.show();
                break;
            case R.id.line3:
                new MaterialDialog.Builder(getContext())
                        .title("请选择您的性别")
                        .items(new String[]{"男","女"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        text3.setText("男");
                                        user.setSex("男");
                                        user.save();
                                        break;
                                    case 1:
                                        text3.setText("女");
                                        user.setSex("女");
                                        user.save();
                                        break;
                                }
                                return true;
                            }
                        }).show();
                break;
            case R.id.line4:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String city = provinceBeanList.get(options1);
                        String address;
                        //  如果是直辖市或者特别行政区只设置市和区/县
                        if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                            address = provinceBeanList.get(options1)
                                    + " " + districtList.get(options1).get(option2).get(options3);
                        } else {
                            address = provinceBeanList.get(options1)
                                    + " " + cityList.get(options1).get(option2)
                                    + " " + districtList.get(options1).get(option2).get(options3);
                        }
                        text4.setText(address);
                        user.setCity(address);
                        user.save();
                    }
                }).setCancelText("取消").setSubmitText("确定").build();
                pvOptions.setPicker(provinceBeanList, cityList, districtList);
                pvOptions.show();
                break;
            case R.id.line5:
                new MaterialDialog.Builder(getContext())
                        .title("邮箱设置")
                        //限制输入的长度
                        .inputRangeRes(8, 25, R.color.colorPrimary)
                        //限制输入类型
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .input("请输入您的邮箱", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                text5.setText(input);
                                user.setEmail(input.toString());
                                user.save();
                            }
                        })
                        .positiveText("确定")
                        .show();

                break;
            case R.id.line6:
                new MaterialDialog.Builder(getContext())
                        .title("个性签名设置")
                        //限制输入的长度
                        .inputRangeRes(0, 40, R.color.colorPrimary)
                        //限制输入类型
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入您的个性签名", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                text6.setText(input);
                                user.setFlag(input.toString());
                                user.save();
                            }
                        })
                        .positiveText("确定")
                        .show();
                break;


        }
    }


    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();


    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

