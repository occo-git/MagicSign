package com.softigress.magicsigns._system.Utils;

import android.graphics.Paint;
import android.graphics.Typeface;

public abstract class TextUtils {

    public final static float textShadowOffset = .00125f;
    public final static int default_font_size = 25;
    public final static float multiline_text_interval = 1.25f;          // межстрочный интервал

    public final static int main_drop_message = 30;            // главный экран: помошник - текст сообщений
    public final static int game_drop_message = 30;            // игра: помошник - текст сообщений
    public final static int rating_drop_message = 35;          // рейтинг: помощник - текст сообщений

    public final static int load_game_title = 80;              // экран загрузки: название игры
    public final static int load_version = 14;                 // экран загрузки: версия программы
    public final static int load_company_title = 20;           // экран загрузки: название компании
    public final static int load_age_title = 60;               // экран возраста: название игры

    public final static int intro_text_big = 48;
    public final static int intro_text_normal = 34;
    public final static int intro_text_small = 32;
    public final static int intro_skip = 25;

    public final static int main_caption = 30;                 // главный экран: заголовок
    public final static int main_comment = 35;                 // главный экран: комментарий
    public final static int main_score = 70;                  // главный экран: счет пользователя в рейтинге
    public final static int main_index = 35;                   // главный экран: место пользователя в рейтинге
    public final static int main_login = 30;                   // главный экран: войти

    public final static int controls_game_wave_id = 60;                 // индекс волны
    public final static int controls_game_wave_ready = 40;              // приготовтесь!
    public final static int controls_game_record_break_score = 160;     // сообщение о побитии рекорда: счет
    public final static int controls_game_record_break_comment = 40;    // сообщение о побитии рекорда: комментарий // 60
    public final static int controls_game_cell_score = 60;              // SignCell счет
    public final static int controls_game_bonus_cell_score = 40;        // бонус: счет
    public final static int controls_game_bonus_title = 40;             // бонус: текст
    public final static int controls_game_bonus_score = 160;            // бонус: счет
    public final static int controls_game_ach_title = 60;               // достижение: текст
    public final static int controls_game_result_title = 60;            // диалог результата: заголовок
    public final static int controls_game_result_score = 150;           // диалог результата: счет
    public final static int controls_count = 35;                        // счетчик
    public final static int controls_inv_count = 40;                    // счетчик инвентарь
    public final static int controls_multimplier = 35;                  // множитель очков
    public final static int controls_progress_index = 15;               // прогресс: индекс

    public final static int dialog_button_text = 40;          // диалог: метки кнопок
    public final static int dialog_login = 25;                // диалог: логин
    public final static int dialog_login_button_label = 20;   // диалог: метки кнопок входа (Google, Facebook)
    public final static int dialog_text_big = 50;             // диалог: текст большым шрифтом
    public final static int dialog_text_normal = 40;          // диалог: текст нормальным шрифтом
    public final static int dialog_text_small = 30;           // диалог: текст маленьким шрифтом
    public final static int dialog_text_xsmall = 28;          // диалог: текст очень маленьким шрифтом
    public final static int dialog_text_xxsmall = 20;         // диалог: текст экстра маленьким шрифтом
    public final static int dialog_help_text = 20;            // диалог помощи: текст
    public final static int dialog_age_text = 100;            // диалог возраста: возраст

    public final static int rating_caption = 60;              // рейтинг: заголовок
    public final static int rating_comment = 35;              // рейтинг: комментарий
    public final static int rating_row_index = 50;            // рейтинг: строка с результатом - индекс
    public final static int rating_row_name = 25;             // рейтинг: строка с результатом - имя
    public final static int rating_row_score = 40;            // рейтинг: строка с результатом - счет

    public final static int lab_caption = 60;                   // лаборатория: заголовок
    public final static int lab_comment = 35;                   // лаборатория: комментарий
    public final static int lab_math = 50;                      // лаборатория: математические знаки
    public final static int lab_index = 50;                     // лаборатория: индекс
    public final static int lab_item_index = 70;                // лаборатория: item индекс
    public final static int lab_help_secret = 40;               // лаборатория: знак секрета в диалоге помощи
    public final static int lab_potion_videos_label = 15;       // лаборатория: метка кнопка просмотра видео
    public final static int lab_dialog_video_label = 10;        // диалог: метки кнопок просмотра видео

    public final static String multi_text_splitter = "/";               // разделитель строк;

    public static Typeface defaultTypeface = Typeface.DEFAULT;
    public static Typeface typeface_Nano;

    public static void loadTypefaces() {
        typeface_Nano = Typeface.createFromAsset(Utils.assetManager, "nano-normal.otf");
        defaultTypeface = typeface_Nano;
    }

    public static float getFontSize(float fontSize) {
        return fontSize * MetrixUtils.scale;// * MetrixUtils.screen_K / MetrixUtils.screen_K_default;//
    }

    public static String ellipsize(String text, Paint p, float maxWidth) {
        if (text != null && p != null) {
            float textWidth = p.measureText(text);
            if (textWidth > maxWidth) {
                int endIndex = text.length() - 1;
                String ellipsizedText = text;
                while (textWidth > maxWidth && endIndex > 0) {
                    ellipsizedText = text.substring(0, endIndex) + "...";
                    textWidth = p.measureText(ellipsizedText);
                    endIndex--;
                }
                return ellipsizedText;
            }
        }
        return text;
    }

    public static float getFontSizeK(int index) {
        if (index < 4)
            return 1f;
        else if (index < 10)
            return .8f;
        else if (index < 100)
            return .7f;
        else if (index < 1000)
            return .6f;
        else if (index < 10000)
            return .5f;
        else if (index < 100000)
            return .385f;
        else if (index < 1000000)
            return .3f;
        else if (index < 10000000)
            return .26f;
        else
            return .15f;
    }
}
