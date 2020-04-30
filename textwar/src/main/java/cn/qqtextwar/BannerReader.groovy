package cn.qqtextwar

import cn.qqtextwar.log.LogFormat
import groovy.transform.CompileStatic
import org.fusesource.jansi.Ansi

@CompileStatic
class BannerReader {

    static String readBanner(){
        InputStream input = BannerReader.class.getClassLoader().getResourceAsStream("banner.txt")
        if(input == null)return ""
        String banner = input.text
        return format(banner)
    }

    static String format(String banner){
        if(banner == null)return ""
        Ansi.Color[] colors = Ansi.Color.values()
        for(Ansi.Color color in colors){
            banner = banner.replace("[${color.name()}]", LogFormat.fg(color))
        }
        return banner
    }
}
