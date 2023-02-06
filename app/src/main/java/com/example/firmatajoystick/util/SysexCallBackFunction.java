package com.example.firmatajoystick.util;

public interface SysexCallBackFunction {
	void call(byte command, byte argc, byte[] argv);
}
