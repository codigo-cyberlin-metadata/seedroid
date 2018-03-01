package id.codigo.seedroid_uikit.uiutils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import id.codigo.seedroid_uikit.SEEDROIDUI;
import id.codigo.seedroid_uikit.bean.Color;
import id.codigo.seedroid_uikit.sdkutils.Constant;
import id.codigo.seedroid_uikit.sdkutils.FileUtil;
import id.codigo.seedroid_uikit.sdkutils.JsonParse;
import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;

/**
 * Created by papahnakal on 01/03/18.
 */

public class Colors {
    private static final String TAG = Colors.class.getSimpleName();
    private static final String LOAD_FLAG = "LoadFlag";
    private static final String LOADED = "Loaded";

    private static HashMap<String, String> colorHashMap = new HashMap<String, String>();

    private static Colors instance = new Colors();

    public static Colors getInstance()
    {
        return instance;
    }

    /**
     * Load resource.rc and parse color.
     */
    public void parse()
    {
        if (ResourceUtils.isNeedLoadResource() && !LOADED.equals(colorHashMap.get(LOAD_FLAG)))
        {
            String colorFileName = ResourceUtils.getResourcePath() + ResourceUtils.getColorPath()
                    + File.separator + Constant.RESOURCE_COLOR_FILENAME;
            String content = FileUtil.readFileByLines(colorFileName);
            if (null != content)
            {
                Color resource = JsonParse.toObject(Color.class, content);
                if (null != resource)
                {
                    List<Color.ColorEntity> colorList = resource.getColor();
                    for (Color.ColorEntity colorEntity : colorList)
                    {
                        try
                        {
                            colorHashMap.put(colorEntity.getName(), String.valueOf(android
                                    .graphics.Color.parseColor(colorEntity.getValue())));
                        }
                        catch (IllegalArgumentException e)
                        {
                            Log.e(TAG, "Invalid color, name=" + colorEntity.getName()+
                                    "value=" + colorEntity.getValue());
                        }
                    }

                    colorHashMap.put(LOAD_FLAG, LOADED);
                }
            }
        }
    }

    /**
     * Get the color by name
     *
     * @param name         color id
     * @param defaultColor color value
     * @return color value, not color id
     */
    private int getColor(String name, int defaultColor)
    {
        int color = defaultColor;

        if (null != colorHashMap)
        {
            if (!LOADED.equals(colorHashMap.get(LOAD_FLAG)))
            {
                parse();
            }

            if (colorHashMap.containsKey(name))
            {
                color = Integer.parseInt(colorHashMap.get(name));
            }
        }

        return color;
    }

    /**
     * Get the color by id
     *
     * @param res
     * @param colorId
     * @return color value, not color id
     */
    public int getColor(Resources res, int colorId)
    {
        String colorKeyN = "";
        int colorValue = res.getColor(colorId);
        if (!SEEDROIDUI.isIsInEditMode() && ResourceUtils.isNeedLoadResource())
        {
            try
            {
                colorKeyN = res.getResourceEntryName(colorId);
            }
            catch (Resources.NotFoundException e)
            {
                Log.e(TAG, e.toString());
            }

            if (!TextUtils.isEmpty(colorKeyN))
            {
                colorValue = getColor(colorKeyN, res.getColor(colorId));
            }
        }
        return colorValue;
    }
}
