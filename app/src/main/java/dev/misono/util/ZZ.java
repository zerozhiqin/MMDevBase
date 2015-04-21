package dev.misono.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.util.Log;

public class ZZ {

    // trueΪ��log
    public static boolean DebugMode = true;

    // trueΪ��log
    public static boolean NetWork = true;

    public static String mYtAG = "OYU";

    private ZZ() {

    }

    public static void z(String string) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[1];
        String classNameString = ste.getClassName();
        classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());

        android.util.Log.v(mYtAG, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber() + "] >>	"
                + string);
    }

    public static void e(String string) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[1];
        String classNameString = ste.getClassName();
        classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());

        android.util.Log.e(mYtAG, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber() + "] >>	"
                + string);
    }

    public static void nl(String string) {
        if (!DebugMode || !NetWork) {
            return;
        }
//		final StackTraceElement[] stack = new Throwable().getStackTrace();
//		final StackTraceElement ste = stack[1];
//		String classNameString = ste.getClassName();
//		classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());

        android.util.Log.d(mYtAG+"NW", "[HTTP] "
                + string);
    }

    /**
     * �Զ���Tag��ͬʱ�������䱻���õ�λ��
     *
     * @param tag
     * @param string
     */
    public static void z(String tag, String string) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[1];
        String classNameString = ste.getClassName();
        classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());

        android.util.Log.v(tag, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber() + "] >>	"
                + string);
    }

    /**
     * ��ӡ���еĶ�ջ��Ϣ��ͬʱ�������䱻���õ�λ��
     *
     * @param string
     */
    public static void all(String string) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        android.util.Log.v(mYtAG, "  --------------------------------------------------------------------  ");
        for (int i = 0; i < stack.length; i++) {
            final StackTraceElement ste = stack[i];
            String classNameString = ste.getClassName();
            classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());

            android.util.Log.v(mYtAG, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber()
                    + "] >>	" + string);
        }
    }

    /**
     * ͬʱ��������õĺ��������õ�λ��
     *
     * @param string
     */
    public static void who(String string) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[2];
        String classNameString = ste.getClassName();
        classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());
        android.util.Log.v(mYtAG, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber() + "] >>	"
                + string);
    }

    /**
     * ͬʱ�����N�����õ�λ�� stage ȡֵ [1, N]
     *
     * @param string
     */
    public static void who(String string, int stage) {
        if (!DebugMode) {
            return;
        }
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StackTraceElement ste = stack[stage];
        String classNameString = ste.getClassName();
        classNameString = classNameString.substring(classNameString.lastIndexOf(".") + 1, classNameString.length());
        android.util.Log.v(mYtAG, "[" + classNameString + "." + ste.getMethodName() + " line:" + ste.getLineNumber() + "] >>	"
                + string);
    }

    /**
     * �������䱻���õ�ʱ��
     *
     * @param string
     */
    public static void time(String string) {
        if (!DebugMode) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        Log.v(mYtAG, string + "			" + (currentTime));

    }

    private static long time = 0;

    /**
     * ��ǰ����ִ�е�ʱ��
     *
     * @param steps
     */
    public static void stime(String steps) {
        if (!DebugMode) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        who(steps + " :		" + (currentTime - time));
        time = currentTime;
    }

    /**
     * ��һ�������浽����filePath
     *
     * @param is
     * @param filePath
     */
    public static void cache(InputStream is, String filePath) {
        File localFile = new File(filePath);
        File localFileDir = new File(filePath.substring(0, filePath.lastIndexOf('/')));
        if (!localFileDir.exists()) {
            localFileDir.mkdirs();
        }
        if (localFileDir.isFile()) {
            z(" make OK~?  " + localFileDir.mkdirs());
        }
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(localFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                fo.write(buf, 0, len);
            }
            fo.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * ���ļ�������MD5����
     *
     * @param filename
     * @return
     */
    private static String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;

        try {
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ��MD5���ֽ�����ת��Ϊ�ַ���
     *
     * @param b
     * @return
     */
    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * ͨ��MD5���ж������ļ��Ƿ���ͬ
     *
     * @param File1
     * @param File2
     * @return
     */
    public static boolean isSameFile(String File1, String File2) {
        String md51 = md5sum(File1);
        String md52 = md5sum(File2);
        z(md51 + ", " + md52);
        if (md51 == null) {
            return false;
        }
        return md51.equals(md52);
    }

}
