package cn.textwar.client


class CommandUtils {

    static String execute(String command){
        Process process = command.execute()
        return process.in.text
    }

    static String executeMany(String[] cmds){
        return Runtime.getRuntime().exec(cmds).text
    }

    static ProcessBuilder execute(String file,String command){
        ProcessBuilder builder = new ProcessBuilder(command.split(" ")).directory(new File(file))
        builder.start()
        builder
    }

    static void sleep(int millis){
        Thread.sleep(millis)
    }
}
