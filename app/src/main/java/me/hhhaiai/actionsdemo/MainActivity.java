package me.hhhaiai.actionsdemo;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.HandlerBase;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    String json="{\"a\":false,\"b\":1,\"c\":\"c1\",\"d\":[\"xx\",1,\"w1\",@(YES)],\"e\":False,\"f\":TRUE,\"g\":YES}";
    @Override
    protected void onResume() {
        super.onResume();
//        checX();
//        typeCheck();
//        formatXml();

        try {
            JSONObject js = new JSONObject(json);
            logs(js.toString(2));
        } catch (Throwable e) {
            logs(Log.getStackTraceString(e));
        }
    }

    private void formatXml() {
        logs("XML1:\r\n"+getFormatXml(xml1));
        logs("XML2:\r\n"+getFormatXml(xml2));
        logs("XML3:\r\n"+getFormatXml(xml3));
        logs("XML4:\r\n"+getFormatXml(xml4));
        logs("XML5:\r\n"+getFormatXml(xml5));
        logs("XML6:\r\n"+getFormatXml(xml6));
        logs("XML7:\r\n"+getFormatXml(xml7));
    }

    private void logs(String xxx) {
        Log.i("sanbo", xxx);
    }

    public static String getFormatXml(String temp) {
        StringReader t = null;
        StringWriter sw = null;
        try {
            t = new StringReader(temp);
            Source xmlInput = new StreamSource(t);

            sw = new StringWriter();
            StreamResult xmlOutput = new StreamResult(sw);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    String.valueOf(4));
            transformer.transform(xmlInput, xmlOutput);
            String sp = System.getProperty("line.separator");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                sp = System.lineSeparator();
            }
            String result = xmlOutput.getWriter().toString().replaceFirst(">", ">" + sp);
            if (TextUtils.isEmpty(result)) {
                return null;
            } else {
                return result;
            }
        } catch (Throwable e) {
        } finally {
//            Closer.close(t, sw);
        }
        return null;
    }
    private void typeCheck() {
        int a = 1;
        double b = 0.1D;
        float c = 1.1f;
        long d = 10L;
        Number n = 232;
        Integer I = 12;
        logs("int : " + getS(a));
        logs("double : " + getS(b));
        logs("float : " + getS(c));
        logs("long : " + getS(d));
        logs("Number : " + getS(n));
        logs("Integer : " + getS(I));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("sanbo", "lineSeparator [" + System.lineSeparator() + "]-[" + System.getProperty("line.separator") + "]: " + System.lineSeparator().equals(System.getProperty("line.separator")));
        } else {
            Log.i("sanbo", "lineSeparator [" + System.getProperty("line.separator") + "] ");
        }
    }


    private String getS(Number a) {
        return a.toString();
    }

    private void checX() {
        checkXmlFormat("xx");
        checkXmlFormat(xml1);
        checkXmlFormat(xml2);
        checkXmlFormat(xml3);
        checkXmlFormat(xml4);
        checkXmlFormat("");
        checkXmlFormat(xml5);
        checkXmlFormat(xml6);
        checkXmlFormat(xml7);
    }

    private void checkXmlFormat(String xx) {

        try {
//        Log.d("sanbo", "测试字符串：" + xx);
            InputStream is = new ByteArrayInputStream(xx.getBytes("UTF-8"));

            logs("方式1测试结果：" + m1(is));
            logs("方式2测试结果：" + m2(is));
            logs("方式3测试结果：" + m3(is));
            logs("方式4测试结果：" + m4(is));
            logs("方式5测试结果：" + m5(xx));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private boolean m1(InputStream is) {
        try {  //创建xmlPull解析器
            XmlPullParser parser = Xml.newPullParser();
            ///初始化xmlPull解析器
            parser.setInput(is, "utf-8");
            //读取文件的类型
            int type = parser.getEventType();
            Log.d("sanbo", "m2,type:" + type);
            return true;
        } catch (Throwable e) {
            // Log.d("sanbo",Log.getStackTraceString(e));
        }
        return false;
    }

    private boolean m2(InputStream is) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            //初始化Sax解析器
            SAXParser sp = spf.newSAXParser();
            //将解析交给处理器
            sp.parse(is, new HandlerBase());
//            Log.d("sanbo", "m2,sp:" + sp);
            return true;
        } catch (Throwable e) {
            //Log.d("sanbo",Log.getStackTraceString(e));
        }
        return false;
    }

    private boolean m3(InputStream is) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //获得Document对象
            Document document = builder.parse(is);
            Log.d("sanbo", "m3,document:" + document);
            return true;
        } catch (Throwable e) {
            // Log.d("sanbo",Log.getStackTraceString(e));
        }
        return false;
    }

    private boolean m4(InputStream is) {
        try {
            DocumentBuilderFactory foctory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = foctory.newDocumentBuilder();
            builder.parse(is);
            return true;
        } catch (Throwable e) {
            // Log.d("sanbo",Log.getStackTraceString(e));
        }
        return false;
    }

    private boolean m5(String is) {
        try {
            Source xmlInput = new StreamSource(new StringReader(is));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    String.valueOf(4));
            transformer.transform(xmlInput, xmlOutput);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                xmlOutput.getWriter().toString().replaceFirst(">", ">"
                        + System.lineSeparator());
            }
            return true;
        } catch (Throwable e) {

        }
        return false;
    }


    String xml1 = "<students>\n" +
            "    <student>\n" +
            "        <name sex=\"man\">小明</name>\n" +
            "        <nickName>明明</nickName>\n" +
            "    </student>\n" +
            "    <student>\n" +
            "        <name sex=\"woman\">小红</name>\n" +
            "        <nickName>红红</nickName>\n" +
            "    </student>\n" +
            "    <student>\n" +
            "        <name sex=\"man\">小亮</name>\n" +
            "        <nickName>亮亮</nickName>\n" +
            "    </student>\n" +
            "</students>";
    String xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<resources>\n" +
            "    <color name=\"purple_200\">#FFBB86FC</color>\n" +
            "    <color name=\"purple_500\">#FF6200EE</color>\n" +
            "    <color name=\"purple_700\">#FF3700B3</color>\n" +
            "    <color name=\"teal_200\">#FF03DAC5</color>\n" +
            "    <color name=\"teal_700\">#FF018786</color>\n" +
            "    <color name=\"black\">#FF000000</color>\n" +
            "    <color name=\"white\">#FFFFFFFF</color>\n" +
            "</resources>";
    String xml3 = "<resources xmlns:tools=\"http://schemas.android.com/tools\">\n" +
            "    <!-- Base application theme. -->\n" +
            "    <style name=\"Theme.ActionsDemo\" parent=\"Theme.MaterialComponents.DayNight.DarkActionBar\">\n" +
            "        <!-- Primary brand color. -->\n" +
            "        <item name=\"colorPrimary\">@color/purple_500</item>\n" +
            "        <item name=\"colorPrimaryVariant\">@color/purple_700</item>\n" +
            "        <item name=\"colorOnPrimary\">@color/white</item>\n" +
            "        <!-- Secondary brand color. -->\n" +
            "        <item name=\"colorSecondary\">@color/teal_200</item>\n" +
            "        <item name=\"colorSecondaryVariant\">@color/teal_700</item>\n" +
            "        <item name=\"colorOnSecondary\">@color/black</item>\n" +
            "        <!-- Status bar color. -->\n" +
            "        <item name=\"android:statusBarColor\" tools:targetApi=\"l\">?attr/colorPrimaryVariant</item>\n" +
            "        <!-- Customize your theme here. -->\n" +
            "    </style>\n" +
            "</resources>";
    String xml4 = "plugins {\n" +
            "    id 'com.android.application'\n" +
            "}\n" +
            "//apply plugin: 'com.android.application'\n" +
            "android {\n" +
            "    compileSdk 31\n" +
            "    defaultConfig {\n" +
            "        applicationId \"me.hhhaiai.actionsdemo\"\n" +
            "        minSdk 16\n" +
            "        targetSdk 31\n" +
            "        versionCode 1\n" +
            "        versionName \"1.0\"\n" +
            "        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"\n" +
            "    }\n" +
            "\n" +
            "    buildTypes {\n" +
            "        release {\n" +
            "            minifyEnabled false\n" +
            "            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'\n" +
            "        }\n" +
            "    }\n" +
            "    compileOptions {\n" +
            "        sourceCompatibility JavaVersion.VERSION_1_8\n" +
            "        targetCompatibility JavaVersion.VERSION_1_8\n" +
            "    }\n" +
            "    dexOptions {\n" +
            "        preDexLibraries false\n" +
            "        maxProcessCount 8\n" +
            "        javaMaxHeapSize \"4g\"\n" +
            "    }\n" +
            "    aaptOptions {\n" +
            "        cruncherEnabled = false\n" +
            "        useNewCruncher = false\n" +
            "    }\n" +
            "    testOptions {\n" +
            "        unitTests.returnDefaultValues = true\n" +
            "    }\n" +
            "    lintOptions {\n" +
            "        checkReleaseBuilds false\n" +
            "        abortOnError false\n" +
            "        warningsAsErrors false\n" +
            "        disable \"UnusedResources\", 'RestrictedApi'\n" +
            "        textOutput \"stdout\"\n" +
            "        textReport false\n" +
            "        check 'NewApi', 'InlinedApi'\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "tasks.withType(Javadoc) {\n" +
            "    options.addStringOption('Xdoclint:none', '-quiet')\n" +
            "    options.addStringOption('encoding', 'UTF-8')\n" +
            "    options.addStringOption('charSet', 'UTF-8')\n" +
            "}\n" +
            "\n" +
            "dependencies {\n" +
            "    implementation 'androidx.appcompat:appcompat:1.3.1'\n" +
            "    implementation 'com.google.android.material:material:1.4.0'\n" +
            "    implementation 'androidx.constraintlayout:constraintlayout:2.1.1'\n" +
            "    androidTestImplementation 'junit:junit:4.+'\n" +
            "    androidTestImplementation 'androidx.test.ext:junit:1.1.3'\n" +
            "    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'\n" +
            "}";
    String xml5 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<adaptive-icon xmlns:android=\"http://schemas.android.com/apk/res/android\">\n" +
            "    <background android:drawable=\"@drawable/ic_launcher_background\" />\n" +
            "    <foreground android:drawable=\"@drawable/ic_launcher_foreground\" />\n" +
            "</adaptive-icon>";
    String xml6 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<androidx.constraintlayout.widget.ConstraintLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
            "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
            "    android:layout_width=\"match_parent\"\n" +
            "    android:layout_height=\"match_parent\"\n" +
            "    tools:context=\".MainActivity\">\n" +
            "\n" +
            "</androidx.constraintlayout.widget.ConstraintLayout>";
    String xml7 = "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
            "    xmlns:aapt=\"http://schemas.android.com/aapt\"\n" +
            "    android:width=\"108dp\"\n" +
            "    android:height=\"108dp\"\n" +
            "    android:viewportWidth=\"108\"\n" +
            "    android:viewportHeight=\"108\">\n" +
            "    <path android:pathData=\"M31,63.928c0,0 6.4,-11 12.1,-13.1c7.2,-2.6 26,-1.4 26,-1.4l38.1,38.1L107,108.928l-32,-1L31,63.928z\">\n" +
            "        <aapt:attr name=\"android:fillColor\">\n" +
            "            <gradient\n" +
            "                android:endX=\"85.84757\"\n" +
            "                android:endY=\"92.4963\"\n" +
            "                android:startX=\"42.9492\"\n" +
            "                android:startY=\"49.59793\"\n" +
            "                android:type=\"linear\">\n" +
            "                <item\n" +
            "                    android:color=\"#44000000\"\n" +
            "                    android:offset=\"0.0\" />\n" +
            "                <item\n" +
            "                    android:color=\"#00000000\"\n" +
            "                    android:offset=\"1.0\" />\n" +
            "            </gradient>\n" +
            "        </aapt:attr>\n" +
            "    </path>\n" +
            "    <path\n" +
            "        android:fillColor=\"#FFFFFF\"\n" +
            "        android:fillType=\"nonZero\"\n" +
            "        android:pathData=\"M65.3,45.828l3.8,-6.6c0.2,-0.4 0.1,-0.9 -0.3,-1.1c-0.4,-0.2 -0.9,-0.1 -1.1,0.3l-3.9,6.7c-6.3,-2.8 -13.4,-2.8 -19.7,0l-3.9,-6.7c-0.2,-0.4 -0.7,-0.5 -1.1,-0.3C38.8,38.328 38.7,38.828 38.9,39.228l3.8,6.6C36.2,49.428 31.7,56.028 31,63.928h46C76.3,56.028 71.8,49.428 65.3,45.828zM43.4,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2c-0.3,-0.7 -0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C45.3,56.528 44.5,57.328 43.4,57.328L43.4,57.328zM64.6,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2s-0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C66.5,56.528 65.6,57.328 64.6,57.328L64.6,57.328z\"\n" +
            "        android:strokeWidth=\"1\"\n" +
            "        android:strokeColor=\"#00000000\" />\n" +
            "</vector>";
}
