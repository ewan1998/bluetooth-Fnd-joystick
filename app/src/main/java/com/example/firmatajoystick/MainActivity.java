package com.example.firmatajoystick;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.firmatajoystick.util.Firmata;
import com.example.firmatajoystick.R;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SerialInputOutputManager.Listener {

    static final int SYSEX_FND_1 =0x10;
    static final int SYSEX_FND_2 =0x20;
    static final int SYSEX_FND_3 =0x30;
    static final int SYSEX_FND_4 =0x40;
    static final int SYSEX_FND_5 =0x50;
    static final int SYSEX_FND_6 =0x60;
    static final int SYSEX_FND_7 =0x70;
    static final int SYSEX_FND_8 =0x80;
    static final int SYSEX_FND_9 =0x90;
    static final int SYSEX_FND_0 =0x99;

    static final byte VRX = 1;
    static final byte VRY = 2;
    static final byte VRZ = 3;

    UsbSerialPort com;
    SerialInputOutputManager ioManager;
    Firmata firmata;

    Switch          swConnect;
    LinearLayout    grpVersion;
    Button          btnF9;
    TextView        txtF9;

    LinearLayout    numLayout;
    TextView        printNum;

    LinearLayout    changecolorLay;
    Button          btntxtColor;

    LinearLayout    btnLay1;
    Button          btn1;
    Button          btn2;
    Button          btn3;
    Button          btn4;
    Button          btn5;

    LinearLayout    btnLay2;
    Button          btn6;
    Button          btn7;
    Button          btn8;
    Button          btn9;
    Button          btn0;

    LinearLayout    xyzLay;
    TextView        txtPrintX;
    TextView        txtPrintY;
    TextView        txtPrintZ;

    LinearLayout    movebtnLayout;
    Button          moveBtn;


    LinearLayout    ResetLay;
    Button          ResetBtn;

    Button[] btns = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0};
    static final int[] fnds ={SYSEX_FND_1, SYSEX_FND_2, SYSEX_FND_3, SYSEX_FND_4, SYSEX_FND_5, SYSEX_FND_6,
            SYSEX_FND_7, SYSEX_FND_8, SYSEX_FND_9, SYSEX_FND_0,};
    static final String[] Nums = {"1","2","3","4","5","6","7","8","9","0"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swConnect     = findViewById(R.id.swConnect);
        grpVersion    = findViewById(R.id.grpVersion);
        btnF9         = findViewById(R.id.btnF9);
        txtF9         = findViewById(R.id.txtF9);

        numLayout     = findViewById(R.id.numLayout);
        printNum      = findViewById(R.id.printNum);

        changecolorLay= findViewById(R.id.changcolorLay);
        btntxtColor   = findViewById(R.id.btntxtColor);

        btnLay1       = findViewById(R.id.btnLay1);
        btn1          = findViewById(R.id.btn1);
        btn2          = findViewById(R.id.btn2);
        btn3          = findViewById(R.id.btn3);
        btn4          = findViewById(R.id.btn4);
        btn5          = findViewById(R.id.btn5);

        btnLay2       = findViewById(R.id.btnlay2);
        btn6          = findViewById(R.id.btn6);
        btn7          = findViewById(R.id.btb7);
        btn8          = findViewById(R.id.btn8);
        btn9          = findViewById(R.id.btn9);
        btn0          = findViewById(R.id.btn0);

        xyzLay        = findViewById(R.id.xyzLay);
        txtPrintX     = findViewById(R.id.txtPrintX);
        txtPrintY     = findViewById(R.id.txtPrintY);
        txtPrintZ     = findViewById(R.id.txtPrintZ);

        movebtnLayout = findViewById(R.id.movebtnLayout);
        moveBtn       = findViewById(R.id.movebtn);

        ResetLay      = findViewById(R.id.ResetLay);
        ResetBtn      = findViewById(R.id.ResetBtn);

        btnF9.setOnClickListener(v-> {
            txtF9.setText("");
            firmata.sendRequestVersion();
        });

        swConnect.setOnCheckedChangeListener(this::connect);

        grpVersion.setVisibility(View.INVISIBLE);
        numLayout.setVisibility(View.INVISIBLE);
        changecolorLay.setVisibility(View.INVISIBLE);
        btnLay1.setVisibility(View.INVISIBLE);
        btnLay2.setVisibility(View.INVISIBLE);
        xyzLay.setVisibility(View.INVISIBLE);
        movebtnLayout.setVisibility(View.INVISIBLE);
        ResetLay.setVisibility(View.INVISIBLE);

    }


    void btnNum() {

        btntxtColor.setOnClickListener(view -> {
            int r =0;
            int g =0;
            int b =0;
            r=(int)(Math.random()*255);
            g=(int)(Math.random()*255);
            b=(int)(Math.random()*255);
            printNum.setTextColor(Color.rgb(r,g,b));
        });

        btn0.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_0,(byte)0,new byte[]{});
            printNum.setText("0");
        });

        btn1.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_1,(byte)0,new byte[]{});
            printNum.setText("1");
        });

        btn2.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_2,(byte)0,new byte[]{});
            printNum.setText("2");
        });

        btn3.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_3,(byte)0,new byte[]{});
            printNum.setText("3");
        });

        btn4.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_4,(byte)0,new byte[]{});
            printNum.setText("4");
        });

        btn5.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_5,(byte)0,new byte[]{});
            printNum.setText("5");
        });

        btn6.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_6,(byte)0,new byte[]{});
            printNum.setText("6");
        });

        btn7.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_7,(byte)0,new byte[]{});
            printNum.setText("7");
        });

        btn8.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_8,(byte)0,new byte[]{});
            printNum.setText("8");
        });

        btn9.setOnClickListener(v-> {
            firmata.sendSysex((byte)SYSEX_FND_9,(byte)0,new byte[]{});
            printNum.setText("9");
        });

        ResetBtn.setOnClickListener(v->{
            moveBtn.setRotationY(0);
            moveBtn.setRotationX(0);
        });

        moveBtn.setOnClickListener(view -> {
            int r =0;
            int g =0;
            int b =0;
            r=(int)(Math.random()*255);
            g=(int)(Math.random()*255);
            b=(int)(Math.random()*255);
            moveBtn.setBackgroundColor(Color.rgb(r,g,b));

        });
    }

    void connect(CompoundButton v, boolean checked) {
        if (checked)
            connected(v);
        else
            disconnected(v);
    }

    int count =0;
    void connected(CompoundButton v) {
        com = SerialManager.getPort(this, 115200);
        if (com==null) {
            v.setChecked(false);
            return;
        }

        ioManager = new SerialInputOutputManager(com, this);
        ioManager.start();

        firmata = new Firmata(com);
        firmata.attach((m, n) -> {
            runOnUiThread(() -> {
                txtF9.setText(String.format("Firmata V%d.%d, count : %d", m, n, count++));
            });
            firmata.sendString("Connected   ");
        });

        btnNum();

        firmata.attach(Firmata.ANALOG_MESSAGE,(pin,value)->{
            int changeX = Firmata.map(value,0,1023,-360,360);
            int changeY = Firmata.map(value,0,1023,-360,360);
            moveBtn.setText(String.format("X = %04d도, Y = %04d도", changeX, changeY));
            switch (pin){
                case VRX:
                    moveBtn.setRotationX(changeX);
                    txtPrintX.setText(String.format("moveX = %d", changeX));
                    break;
                case VRY:
                    moveBtn.setRotationY(changeY);
                    txtPrintY.setText(String.format("moveY = %d", changeY));
                    break;
            }
        });

        firmata.attach(Firmata.STRING_DATA,(str) -> {
            txtPrintZ.setText(str);
        });

        v.setText("Connected");
        grpVersion.setVisibility(View.VISIBLE);
        changecolorLay.setVisibility(View.VISIBLE);
        btnLay1.setVisibility(View.VISIBLE);
        btnLay2.setVisibility(View.VISIBLE);
        numLayout.setVisibility(View.VISIBLE);
        xyzLay.setVisibility(View.VISIBLE);
        movebtnLayout.setVisibility(View.VISIBLE);
        txtPrintX.setText("");
        txtPrintY.setText("");
        ResetLay.setVisibility(View.VISIBLE);

    }

    void disconnected(CompoundButton v) {
        try {
            if (com!=null && com.isOpen()) {
                txtPrintX.setText("");
                txtPrintY.setText("");
                firmata.sendString("Disconnected   ");
                com.close();
            }

            if (ioManager != null) {
                ioManager.stop();
            }

            v.setText("Disconnected   ");
            grpVersion.setVisibility(View.INVISIBLE);
            changecolorLay.setVisibility(View.INVISIBLE);
            btnLay1.setVisibility(View.INVISIBLE);
            btnLay2.setVisibility(View.INVISIBLE);
            xyzLay.setVisibility(View.INVISIBLE);
            numLayout.setVisibility(View.INVISIBLE);
            movebtnLayout.setVisibility(View.INVISIBLE);
            ResetLay.setVisibility(View.INVISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewData(byte[] data) {
        firmata.processInput(data);
    }

    @Override
    public void onRunError(Exception e) {
        runOnUiThread(() -> {
            swConnect.setChecked(false);
        });
    }
}