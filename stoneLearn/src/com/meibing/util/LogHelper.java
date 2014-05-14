package com.meibing.util;

import android.util.Log;

public final class LogHelper{
	private static boolean mIsDebugMode = true;//��ȡ��ջ��Ϣ��Ӱ�����ܣ�����Ӧ��ʱ�ǵùر�DebugMode
	private static String mLogTag = "meibing";

	private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()  Line:%d  (%s)";

	public static void trace() {
		if (mIsDebugMode) {
			StackTraceElement traceElement = Thread.currentThread()
					.getStackTrace()[3];//�Ӷ�ջ��Ϣ�л�ȡ��ǰ�����õķ�����Ϣ
			String logText = String.format(CLASS_METHOD_LINE_FORMAT,
					traceElement.getClassName(), traceElement.getMethodName(),
					traceElement.getLineNumber(), traceElement.getFileName());
			Log.d(mLogTag, logText);//��ӡLog
		}
	}

}
