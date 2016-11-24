package com.softpo.md5demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private TextView showEncryptResult;

    private MessageDigest mMessageDigest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        initMessageDigest();

    }

//    private void initMessageDigest() {
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

    private void initView() {
        showEncryptResult = (TextView) findViewById(R.id.showEncryptResult);
    }

    public void btnEncrypt(View view) {

        switch (view.getId()){
            case R.id.btnMD5:

                byte[] data = "青岛".getBytes();
                String result = mdEncrypt("MD5",data);

                showEncryptResult.setText(result);
                break;

            case R.id.btnSHA1:

                byte[] bytes = "青岛".getBytes();

                String encrypt = mdEncrypt("SHA-1", bytes);

                showEncryptResult.setText(encrypt);

                break;

        }


    }

    private String mdEncrypt(String algorithm, byte[] data) {

        try {
            mMessageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//                加载数据
        mMessageDigest.update(data);

//                处理数据
        byte[] digest = mMessageDigest.digest();

        Log.d("flag","--------------------->digest.length: "+digest.length);

        for (int i = 0; i < digest.length; i++) {
            Log.d("flag","---------------->digest: "+digest[i]);
//                    字节转换成二进制
            String binaryString = Integer.toBinaryString(digest[i]);
            Log.d("flag","---------------->binaryString: "+binaryString);
        }

//                byte[] digest1 = md5.digest(data);

//                网站进行MD5加密：  05 EC 55 F0 37 39 1B 53 F4 C5 37 C3 3E 0A 24 BB
//                代码中进行运算结果：5 -20 85 -16…… 10

//                展示数据以十六进制进行展示
//                -20 二进制：11111111111111111111111111101100

//                需要进行&运算
//                跟255这个数进行&运算 00000000000000000000000000011111111------>十六进制0xFF

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            int result = digest[i] & 0xFF;
            if(result<=0x0F){
                builder.append("0");
            }
//                    将result转换成十六进制表示
            String hexResult = Integer.toHexString(result);
            builder.append(hexResult);
        }
        return builder.toString();
    }
}
