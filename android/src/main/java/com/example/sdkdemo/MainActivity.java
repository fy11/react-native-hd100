package com.example.sdkdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.reader.usbdevice.DeviceLib;
import com.reader.usbdevice.DeviceStatusCallback;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends Activity {
    private static final String TAG = "com.example.sdkdemo.MainActivity";

    private LinearLayout llGroup = null;
    private EditText etKey=null;
    private EditText etData=null;
    private EditText etTrack=null;
    private EditText etTime=null;

    private Spinner spSector = null, spAddress = null, spSound = null;
    private ArrayAdapter<String> secnrAdap, addrAdap, soundAdap;

    private DeviceLib mdev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitSpinner();
        InitEditText();
        InitButton();

    }

    private void showString(String msg) {
        TextView tv = new TextView(this);
        tv.setTextColor(Color.rgb(0x15, 0xA8, 0x95));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setText(msg);
        llGroup.addView(tv);
    }

    private void showToast(String msg) { Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); }

    private void InitSpinner() {
        String[] mItemsecnr = getResources().getStringArray(R.array.sector_items);
        String[] mItemaddr = getResources().getStringArray(R.array.address_items);
        String[] mSoundType = getResources().getStringArray(R.array.sound_types);
        spSector = (Spinner) findViewById(R.id.spSector);
        secnrAdap = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItemsecnr);
        secnrAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSector.setAdapter(secnrAdap);

        spAddress = (Spinner) findViewById(R.id.spAddress);
        addrAdap = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItemaddr);
        addrAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAddress.setAdapter(addrAdap);

        spSound = (Spinner) findViewById(R.id.spSound);
        soundAdap = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mSoundType);
        soundAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSound.setAdapter(soundAdap);
    }

    private void InitEditText() {
        etKey = (EditText)findViewById(R.id.etKey);
        etData = (EditText)findViewById(R.id.etData);
        etTrack = (EditText)findViewById(R.id.etTrack);
        etTime = (EditText)findViewById(R.id.etTimeout);
        llGroup = (LinearLayout)findViewById(R.id.ll);
    }

    private void InitButton() {
        Button btnOpenDevice = (Button)findViewById(R.id.btn_open);
        Button btnCloseDevice = (Button)findViewById(R.id.btn_close);
        Button btnPosBeep = (Button)findViewById(R.id.btn_beep);
        Button btnClear = (Button)findViewById(R.id.btn_clear);
        Button btnM1CardRead = (Button)findViewById(R.id.btn_m1read);
        Button btnM1CardWrite = (Button)findViewById(R.id.btn_m1write);
        Button btnMagCardRead = (Button)findViewById(R.id.btn_readMagCard);
        Button btn15693 = (Button)findViewById(R.id.btn_15693);
        Button btnSiCardRead = (Button)findViewById(R.id.btn_sicard);
        Button btnCertCardRead = (Button)findViewById(R.id.btn_certCard);
        Button btnCertCardIDRead = (Button)findViewById(R.id.btn_certCardID);
        Button btnbankCard = (Button)findViewById(R.id.btn_bankCard);
        Button btnQRCode = (Button)findViewById(R.id.btn_QRCode);
        Button btnGetInputPass = (Button)findViewById(R.id.btn_getInputpass);

        btnOpenDevice.setOnClickListener(new SampleOnClickListener());
        btnCloseDevice.setOnClickListener(new SampleOnClickListener());
        btnPosBeep.setOnClickListener(new SampleOnClickListener());
        btnClear.setOnClickListener(new SampleOnClickListener());
        btnM1CardRead.setOnClickListener(new SampleOnClickListener());
        btnM1CardWrite.setOnClickListener(new SampleOnClickListener());
        btnMagCardRead.setOnClickListener(new SampleOnClickListener());
        btn15693.setOnClickListener(new SampleOnClickListener());
        btnQRCode.setOnClickListener(new SampleOnClickListener());
        btnSiCardRead.setOnClickListener(new SampleOnClickListener());
        btnCertCardRead.setOnClickListener(new SampleOnClickListener());
        btnCertCardIDRead.setOnClickListener(new SampleOnClickListener());
        btnbankCard.setOnClickListener(new SampleOnClickListener());
        btnGetInputPass.setOnClickListener(new SampleOnClickListener());
    }

    private class SampleOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                switch (view.getId()) {
                    case R.id.btn_open:
                        mdev = new DeviceLib(getApplicationContext(), new DeviceStatusCallback() {
                            @Override
                            public void UsbAttach() { showToast(getString(R.string.dev_link_succ)); }
                            @Override
                            public void UsbDeAttach() { showToast(getString(R.string.dev_link_error)); }
                        });
                        mdev.openDevice(100);
                        break;
                    case R.id.btn_beep:
                        mdev.ICC_PosBeep((byte) 10);
                        break;
                    case R.id.btn_clear:
                        llGroup.removeAllViews();
                        break;
                    case R.id.btn_m1read:
                        FindCard();
                        readM1Card();
                        break;
                    case R.id.btn_m1write:
                        FindCard();
                        readM1Write();
                        break;
                    case R.id.btn_15693:
                        read15693();
                        break;
                    case R.id.btn_readMagCard:
                        readMagCard();
                        break;
                    case R.id.btn_certCard:
                        readIDCard();
                        break;
                    case R.id.btn_certCardID:
                        readIDCardUID();
                        break;
                    case R.id.btn_sicard:
                        readSiCard();
                        break;
                    case R.id.btn_bankCard:
                        readBankCard();
                        break;
                    case R.id.btn_QRCode:
                        readQRCode();
                        break;
                    default:
                        break;
                }
            } catch (SecurityException securityException) {
                showToast(getString(R.string.error));
            }
        }
    }

    private void FindCard(){
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        byte[]	uid		= new byte[10];
        byte	type 	= (byte)0x41; //0x41 表示TypeA、M1

        if( mdev.PICC_Reader_Request() != 0 ){
            showString("请求卡片失败"); return;
        }
        if( mdev.PICC_Reader_anticoll(uid) != 0 ){
            showString("防碰撞失败"); return;
        }
        showString("UID:" + mdev.arrayByteToString(uid, 4));

        if( mdev.PICC_Reader_Select(type) != 0 ){
            showString("选卡失败");
        }else {
            showString("寻卡成功");
        }

    }

    private void readM1Card() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte mode = 0x60; //这里默认用KeyA，如果使用KeyB值为0x61
        byte[] resp = new byte[30];
        byte[] bysKey  = new byte[10];
        byte addr = ((byte)spAddress.getSelectedItemPosition());
        byte secnr = ((byte)spSector.getSelectedItemPosition());

        String key = etKey.getText().toString().trim();
        bysKey = mdev.HexString2Bytes(key);
        nRt =  mdev.PICC_Reader_Authentication_Pass(mode, secnr, bysKey);
        if(nRt != 0) {
            showString("秘钥认证失败：" + nRt);
            return;
        }else {
            showString("秘钥认证成功");
        }
        Arrays.fill(resp, (byte)0);
        nRt =  mdev.PICC_Reader_Read((byte)addr, resp);
        if(nRt != 0) {
            showString("读卡失败：" + nRt);
        }else {
            showString("读卡成功,HEXSTR：" + new String( mdev.arrayByteToString(resp, 16)));
            try {
                showString("读卡成功：" + new String(resp, "gbk") );
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void readM1Write() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte mode = 0x60; //这里默认用KeyA，如果使用KeyB值为0x61
        byte[] data = new byte[30];
        byte[] bysKey  = new byte[10];
        byte addr = ((byte)spAddress.getSelectedItemPosition());
        byte secnr = ((byte)spSector.getSelectedItemPosition());

        String key = etKey.getText().toString().trim();
        bysKey = mdev.HexString2Bytes(key);
        try {
            Arrays.fill(data, (byte)0x00);
            data = etData.getText().toString().trim().getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        nRt =  mdev.PICC_Reader_Authentication_Pass(mode, secnr, bysKey);
        if(nRt != 0) {
            showString("秘钥认证失败：" + nRt);
            return;
        }else {
            showString("秘钥认证成功");
        }
        nRt =  mdev.PICC_Reader_Write((byte)addr, data);
        if(nRt != 0) {
            showString("写卡失败：" + nRt);
        }else {
            showString("写卡成功");
        }
    }

    private void read15693() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        byte[] uid = new byte[20];
        byte[] cardInfo = new byte[30];
        byte[] data = new byte[10];
        if( mdev.PICC_Reader_Inventory(uid) <= 0 ) {
            showString("寻卡失败"); return;
        }
        showString("UID: " + mdev.arrayByteToString(uid, 8));
        if( mdev.PICC_Reader_SystemInfor(cardInfo) <= 0 ) {
            showString("获取卡信息失败"); return;
        }
        showString("卡信息: " + mdev.arrayByteToString(uid, 14));
        if( mdev.PICC_Reader_15693_Read((byte) 0, data) <= 0 ) {
            showString("读卡失败"); return;
        }
        showString("成功读取第0块数据: " + mdev.arrayByteToString(data, 4));
    }

    private void readMagCard() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte[] magData = new byte[255];
        byte[]	resp = new byte[160];
        int[]	rlen = new int[2];

        int track = Integer.parseInt(etTrack.getText().toString().trim());
        int time = Integer.parseInt(etTime.getText().toString().trim());
        nRt = mdev.Rcard(time, track, rlen, resp);
        if( nRt != 0){
            showString("磁道" + track + "数据获取失败，ret=" + nRt); return;
        }
        showString("刷卡成功：" + new String( resp ));
    }

    private void readQRCode() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte[] magData = new byte[255];
        byte[]	resp = new byte[160];
        int[]	rlen = new int[2];

        int time = Integer.parseInt(etTime.getText().toString().trim());
        nRt = mdev.ICC_Reader_QRCode(resp);
        if( nRt != 0){
            showString( "扫码失败，ret=" + nRt); return;
        }
        showString("扫码成功：" + new String( resp ));
    }

    private void readIDCard() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        llGroup.removeAllViews();
        String pkName=this.getPackageName();
        String show="";
        int nRt = mdev.PICC_ReadIDCardMsg(pkName);
        if( nRt != 0){
            showString("身份证读取失败，ret=" + nRt);
            return;
        }

        Bitmap bm1 = mdev.getBmpfile();
        ImageView iv=new ImageView(this);
        iv.setImageBitmap(bm1);
        llGroup.addView(iv);

        if(mdev.GetCardType() == 0){
            showString("居民身份证");
            show = "姓名: "+ mdev.getName() +'\n'
                    +"性别: "+ mdev.getSex() +'\n'
                    +"民族: "+ mdev.getNation() +"族"+'\n'
                    +"出生日期: "+ mdev.getBirth() +'\n'
                    +"住址: "+ mdev.getAddress() +'\n'
                    +"身份证号码: "+ mdev.getIDNo() +'\n'
                    +"签发机关: "+ mdev.getDepartment() +'\n'
                    +"有效日期: "+  mdev.getEffectDate()  + "至" +  mdev.getExpireDate() +'\n';
        }
        if(mdev.GetCardType() == 1){
            showString("外国人永久居留证");
            show ="中文姓名: "+ mdev.getName() +'\n'
                    +"英文姓名: "+ mdev.getEnName() +'\n'
                    +"性别: "+ mdev.getSex() +'\n'
                    +"国籍代码: "+ mdev.getNationalityCode() +'\n'
                    +"永久居留证号码: "+ mdev.getIDNo() +'\n'
                    +"出生日期: "+ mdev.getBirth() +'\n'
                    +"有效日期: "+  mdev.getEffectDate()  + "至" +  mdev.getExpireDate() +'\n';
        }
        if(mdev.GetCardType() == 2){
            showString("港澳台居民居住证");
            show = "姓名: "+ mdev.getName() +'\n'
                    +"性别: "+ mdev.getSex() +'\n'
                    +"出生日期: "+ mdev.getBirth() +'\n'
                    +"住址: "+ mdev.getAddress() +'\n'
                    +"身份证号码: "+ mdev.getIDNo() +'\n'
                    +"签发机关: "+ mdev.getDepartment() +'\n'
                    +"通行证号码: "+ mdev.getTXZHM() +'\n'
                    +"通行证签发次数: "+ mdev.getTXZQFCS() +'\n'
                    +"有效日期: "+  mdev.getEffectDate()  + "至" +  mdev.getExpireDate() +'\n';
        }
        showString(show);
    }

    private void readIDCardUID() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        byte[]	uid		= new byte[20];
        byte	type 	= (byte)0x41; //0x41 表示TypeA、M1

        if( mdev.PICC_Reader_ID_ReadUID(uid) != 0 ){
            showString("身份证寻卡失败"); return;
        }
        showString("UID:" + mdev.arrayByteToString(uid, 8));

    }

    private void getPassWord() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte[] passWord = new byte[20];
        byte[]	rlen = new byte[2];

        byte soundType = ((byte)spSound.getSelectedItemPosition());
        int time = Integer.parseInt(etTime.getText().toString().trim());

        if (soundType == 0x01) {
            soundType = 0x04;
        }
        else if (soundType == 0x02) {
            soundType = 0x0b;
        }
        else if (soundType == 0x03) {
            soundType = 0x0d;
        }
        else if (soundType == 0x04) {
            soundType = 0x0c;
        }
        else {
            soundType = 0x00;
        }

        nRt =mdev.ICC_Reader_GetInputPass((byte)time, soundType, (byte)1, rlen, passWord);
        if ( nRt == -9) {
            showString("语音播报失败");
        }
        if (nRt == 0) {
            showString("密码获取成功：" + new String(passWord));
        }else {
            /*
            * 0：操作成功
            * -5：用户按取消键取消
            * -6：用户按确认键取消
            * -9：语音播报失败
            * -20：通信失败
            * */
            showString("密码获取失败：" + nRt);
        }

    }

    private void readSiCard() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte[] cardInfo = new byte[128];
        nRt = mdev.iReadSiCard((byte) 0x11, cardInfo);
        if (nRt != 0) {
            showString("读卡失败:"+ nRt); return;
        }
        try {
            showString("读卡成功："+ new String(cardInfo,"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void readBankCard() {
        if (!mdev.isOpen()) {
            showString("设备未连接!"); return;
        }
        int nRt = -99;
        byte[] cardInfo = new byte[128];
        nRt = mdev.ireadBankCardNo((byte)0x01, cardInfo);
        if (nRt != 0) {
            nRt = mdev.ireadBankCardNo((byte)0x02, cardInfo);
            if (nRt != 0) {
                showString("读卡失败:" + nRt);
                return;
            }
        }
        try {
            showString("读卡成功："+ new String(cardInfo,"gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
