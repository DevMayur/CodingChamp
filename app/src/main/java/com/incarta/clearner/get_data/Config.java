package com.incarta.clearner.get_data;

public class Config {
    public static class API {

        public static final String BASE_URL = "http://creatorpro.xyz/capi/";
        public static final String TITLES_URL = BASE_URL + "get_all_titles.php";
        public static final String SUBTITLES_URL = BASE_URL + "get_subtitles_by_title_id.php/?id=";
        public static final String CONTENT_URL = BASE_URL + "get_content_by_subtitle_id.php/?id=";
        public static final String EXAMPLE_BY_SUBTITLE_URL = BASE_URL + "get_example_by_subtitle_id.php/?id=";
        public static final String ALL_EXAMPLE_URL = BASE_URL + "get_all_examples.php";
        public static final String DISCORD_SERVER_INVITE = "https://discord.gg/w6ccJdufcw";

        public static final String IMAGE_URL =BASE_URL + "images/" ;
    }
}
