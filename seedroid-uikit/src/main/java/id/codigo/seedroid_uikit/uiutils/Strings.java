package id.codigo.seedroid_uikit.uiutils;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import id.codigo.seedroid_uikit.SEEDROIDUI;
import id.codigo.seedroid_uikit.sdkutils.FileUtil;
import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;

/**
 * Created by papahnakal on 01/03/18.
 */

public class Strings {
    private static final String TAG = Strings.class.getSimpleName();
    private static final String STRINGS_NAME = "string_%1$s.xml";
    private static final String LOAD_FLAG = "LoadFlag";
    private static final String LOADED = "Loaded";

    private static HashMap<String, String> stringsHashMap = new HashMap<String, String>();
    private static HashMap<String, String> needTransferHashMap = new HashMap<String, String>();
    private static Strings instance = new Strings();

    public static Strings getInstance()
    {
        return instance;
    }

    public Strings()
    {
        needTransferHashMap.put("&amp;", "&");
        needTransferHashMap.put("&lt;", "<");
        needTransferHashMap.put("&gt;", ">");
        needTransferHashMap.put("\\'", "'");
        needTransferHashMap.put("\\\"", "\"");
        needTransferHashMap.put("\\n", System.getProperty("line.separator"));
    }

    /**
     * Load string_XX.xml and parse it.
     *
     * @param lang
     */
    public void parse(String lang)
    {
        if (ResourceUtils.isNeedLoadResource() && !LOADED.equals(stringsHashMap.get(LOAD_FLAG +
                "#" + lang)))
        {
            String stringsName = String.format(STRINGS_NAME, lang);
            String stringsPath = ResourceUtils.getResourcePath() +
                    ResourceUtils.getStringPath() + File.separator + stringsName;
            File stringsFile = new File(stringsPath);
            if (stringsFile.exists())
            {
                InputStream is = null;
                try
                {
                    is = new FileInputStream(stringsFile);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(is, null);
                    parser.nextTag();

                    int depth = parser.getDepth();
                    while (parser.getEventType() != XmlPullParser.END_TAG || parser.getDepth() >
                            depth)
                    {
                        parser.next();

                        if (parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            String name = parser.getAttributeValue(null, "name");
                            parser.next();

                            String value = parser.getText();
                            Set set = needTransferHashMap.keySet();
                            for (Iterator it = set.iterator(); it.hasNext(); )
                            {
                                String key = (String) it.next();
                                if (value.contains(key))
                                {
                                    value = value.replace(key, needTransferHashMap.get
                                            (key));
                                    break;
                                }
                            }

                            stringsHashMap.put(name + "#" + lang, value);
                        }
                    }

                    stringsHashMap.put(LOAD_FLAG + "#" + lang, LOADED);
                }
                catch (XmlPullParserException e)
                {
                    Log.e(TAG, e.toString());
                }
                catch (IOException e)
                {
                    Log.e(TAG, e.toString());
                }
                finally
                {
                    FileUtil.closeInputStream(is);
                }
            }
        }
    }

    /**
     * Get the string by name
     *
     * @param name
     * @param defaultStr
     * @return
     */
    private String getString(String name, String defaultStr)
    {
        if (!SEEDROIDUI.isIsInEditMode() && ResourceUtils.isNeedLoadResource())
        {
            String lang = ResourceUtils.getCurrentLanguage();
            if (!LOADED.equals(stringsHashMap.get(LOAD_FLAG + "#" + lang)))
            {
                parse(lang);
            }

            String key = name + "#" + lang;
            if (stringsHashMap.containsKey(key))
            {
                return stringsHashMap.get(key);
            }
        }
        return defaultStr;
    }


    /**
     * Get string by string res id
     * @param res
     * @param resId
     * @return
     */
    public String getString(Resources res, int resId)
    {
        String name = res.getResourceEntryName(resId);
        String defaultStr = res.getString(resId);
        return getString(name, defaultStr);
    }


    public String getString(Resources res, int resId, Object... formatArgs)
    {
        String name = res.getResourceEntryName(resId);
        String value = getString(name, res.getString
                (resId));
        return String.format(value, formatArgs);
    }
}
