package com.eeverest.util;

import com.eeverest.gui.Trait;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TextUtils {
    public TextUtils() {
    }

    public static String formatValueString(String text) {
        if (text != null && !text.isEmpty()) {
            StringBuilder converted = new StringBuilder();
            boolean convertNext = true;

            for(char ch : text.toCharArray()) {
                if (ch == '_') {
                    ch = ' ';
                }

                if (Character.isWhitespace(ch)) {
                    convertNext = true;
                } else if (convertNext) {
                    ch = Character.toTitleCase(ch);
                    convertNext = false;
                } else {
                    ch = Character.toLowerCase(ch);
                }

                converted.append(ch);
            }

            return converted.toString();
        } else {
            return text;
        }
    }
}
