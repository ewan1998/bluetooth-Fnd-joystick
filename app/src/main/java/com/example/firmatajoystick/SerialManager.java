package com.example.firmatajoystick;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.List;

public class SerialManager {
    private static UsbManager manager;
    private static UsbDevice device;
    private static UsbDeviceConnection usbConnection;
    private static UsbSerialPort port;

    public static UsbSerialPort getPort(Activity activity, int baudRate) {
        manager = (UsbManager)activity.getSystemService(Context.USB_SERVICE);
        Context ctx = activity;

        /*
         * 아두이노 연결 확인
         */
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.size() == 0) {
            Toast.makeText(ctx, "아두이노를 USB에 연결하세요", Toast.LENGTH_LONG).show();
            return null;
        }

        /*
         * USB 권한 확인
         */
        UsbSerialDriver driver = availableDrivers.get(0);

        if (!manager.hasPermission(driver.getDevice())) {
            Intent intent = new Intent(ctx, activity.getClass());
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT);
            manager.requestPermission(driver.getDevice(), pendingIntent);
            return null;
        }
        /*
         * 아두이노 연결
         */
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        port = driver.getPorts().get(0);
        try {
            if (!port.isOpen()) {
                port.open(connection);
                port.setParameters(baudRate, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                port.setDTR(false);
                port.setDTR(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }
}
