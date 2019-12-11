package com.example.bottomtabber.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.PhotoUtil;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Model_Identification extends AppCompatActivity {
    private static final String TAG = Model_Identification.class.getName();
    private static final int USE_PHOTO = 1001;
    private static final int START_CAMERA = 1002;
    private String camera_image_path;
    private ImageView show_image;
    private TextView result_text;
    private boolean load_result = false;
    private int[] ddims = {1, 3, 224, 224};
    private int model_index = 0;
    private List<String> resultLabel = new ArrayList<>();
    private Interpreter tflite = null;
    private String picture_path = null;

    private static final String[] PADDLE_MODEL = {
            "mobilenet_v1",
            "mobilenet_v2"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model__identification);
        init_view();
        readCacheLabelFromLocalFile();
    }
    // initialize view
    private void init_view() {
        request_permissions();
        show_image = findViewById(R.id.show_image);
        result_text = findViewById(R.id.result_text);
        result_text.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button load_model =  findViewById(R.id.load_model);
        Button use_photo =  findViewById(R.id.use_photo);
        Button start_photo =  findViewById(R.id.start_camera);
        Button infer00 = findViewById(R.id.infer100);

        infer00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!load_result) {
                    Toast.makeText(Model_Identification.this, "请先加载模型", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(picture_path.equals("")){
                        Toast.makeText(Model_Identification.this, "请选择相册或拍照功能", Toast.LENGTH_SHORT).show();
                    }else{
                        predict_image(picture_path);
                    }

                }

            }
        });

        load_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        // use photo click
        use_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!load_result) {
                    Toast.makeText(Model_Identification.this, "never load model", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhotoUtil.use_photo(Model_Identification.this, USE_PHOTO);
            }
        });

        // start camera click
        start_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!load_result) {
                    Toast.makeText(Model_Identification.this, "never load model", Toast.LENGTH_SHORT).show();
                    return;
                }
                camera_image_path = PhotoUtil.start_camera(Model_Identification.this, START_CAMERA);
            }
        });
    }

    /**
     * Memory-map the model file in Assets.
     */
    //模型文件读取成MappedByteBuffer，之后给Interpreter类初始化模型
    private MappedByteBuffer loadModelFile(String model) throws IOException {
        AssetFileDescriptor fileDescriptor = getApplicationContext().getAssets().openFd(model + ".tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    // 加载内置模型
    private void load_model(String model) {
        try {

            tflite = new Interpreter(loadModelFile(model));
            Toast.makeText(Model_Identification.this, model + " model load success", Toast.LENGTH_SHORT).show();
            Log.d(TAG, model + " model load success");
            tflite.setNumThreads(4);
            load_result = true;
        } catch (IOException e) {
            Toast.makeText(Model_Identification.this, model + " model load fail", Toast.LENGTH_SHORT).show();
            Log.d(TAG, model + " model load fail");
            load_result = false;
            e.printStackTrace();
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Model_Identification.this);

        // set dialog title
        builder.setTitle("Please select model");

        // set dialog icon
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // able click other will cancel
        builder.setCancelable(true);

        // cancel button
        builder.setNegativeButton("cancel", null);

        // set list
        builder.setSingleChoiceItems(PADDLE_MODEL, model_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                model_index = which;
                load_model(PADDLE_MODEL[model_index]);
                dialog.dismiss();
            }
        });

        // show dialog
        builder.show();
    }


    // copy file from asset to sdcard
    public void copy_file_from_asset(Context context, String oldPath, String newPath) {
        try {
            String[] fileNames = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {
                // directory
                File file = new File(newPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                // copy recursivelyC
                for (String fileName : fileNames) {
                    copy_file_from_asset(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
                Log.d(TAG, "copy files finish");
            } else {
                // file
                File file = new File(newPath);
                // if file exists will never copy
                if (file.exists()) {
                    return;
                }

                // copy file to new path
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取文件分类标签对应的名称
    private void readCacheLabelFromLocalFile() {
        try {
            AssetManager assetManager = getApplicationContext().getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("cacheLabel.txt")));
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                resultLabel.add(readLine);
            }
            reader.close();
        } catch (Exception e) {
            Log.e("labelCache", "error " + e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String image_path;
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case USE_PHOTO:
                    if (data == null) {
                        Log.w(TAG, "user photo data is null");
                        return;
                    }
                    Uri image_uri = data.getData();
                    Glide.with(Model_Identification.this).load(image_uri).apply(options).into(show_image);
                    // get image path from uri
                    image_path = PhotoUtil.get_path_from_URI(Model_Identification.this, image_uri);
                    picture_path=image_path;
                    break;
                case START_CAMERA:
                    // show photo
                    Glide.with(Model_Identification.this).load(camera_image_path).apply(options).into(show_image);
                    picture_path = camera_image_path;
                    break;
            }
        }
    }

    //  获取图片的路径，对图片进行压缩
    //  图片转换成ByteBuffer格式的数据，最后调用tflite.run()方法进行预
    private void predict_image(String image_path) {
        // picture to float array
        Bitmap bmp = PhotoUtil.getScaleBitmap(image_path);
        ByteBuffer inputData = PhotoUtil.getScaledMatrix(bmp, ddims);
        try {
            // Data format conversion takes too long
            // Log.d("inputData", Arrays.toString(inputData));
            float[][] labelProbArray = new float[1][1001];
            long start = System.currentTimeMillis();
            // get predict result
            tflite.run(inputData, labelProbArray);
            long end = System.currentTimeMillis();
            long time = end - start;
            float[] results = new float[labelProbArray[0].length];
            System.arraycopy(labelProbArray[0], 0, results, 0, labelProbArray[0].length);
            // show predict result and time
            int r = get_max_result(results);
            String show_text = "result：" + r + "\nname：" + resultLabel.get(r) + "\nprobability：" + results[r] + "\ntime：" + time + "ms";
            result_text.setText(show_text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // get max probability label
    private int get_max_result(float[] result) {
        float probability = result[0];
        int r = 0;
        for (int i = 0; i < result.length; i++) {
            if (probability < result[i]) {
                probability = result[i];
                r = i;
            }
        }
        return r;
    }

    // request permissions
    private void request_permissions() {

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // if list is not empty will request permissions
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {

                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            String s = permissions[i];
                            Toast.makeText(this, s + " permission was denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
