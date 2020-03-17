package cn.qqtextwar.dsl

import org.apache.commons.io.IOUtils

class Utils {

    static String byInputStream(InputStream input,String charSet){
        StringBuilder builder = new StringBuilder()
        IOUtils.readLines(input,charSet).forEach{
            x->
                builder.append(x).append("\n")
        }
        return builder
    }
}
