package cn.textwar.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.util.*;

public class Protocol {

    private long beforeTime;

    public TextWarProtocol decode(InputStream stream) throws IOException {
        try {
            Map<String, String> requestMapping = new HashMap<>();
            StringBuilder builder = new StringBuilder();
            int b;
            byte[] json = new byte[0];
            int before = -1;
            int line = 0;
            int index = 0;
            String beforeKey = "";
            int length = 0;
            int jsonIndex = 0;
            boolean jsonMode = false;
            boolean first = false;
            while ((b = stream.read()) != -1) {
                if (b >= 255) continue;
                if (!jsonMode) {
                    if (b == '\r' && before == '\n') {
                        int n = stream.read();
                        if (n == '\n') {
                            jsonMode = true;
                            length = Integer.parseInt(requestMapping.get(TextWarProtocol.DATA_LENGTH).trim());
                            json = new byte[length];
                        } else {
                            throw new ProtocolException("CRLF Error! No \\n line: " + line + " index: " + index);
                        }
                    }
                    if (b == '\n') {
                        if (before != '\r') {
                            throw new ProtocolException("CRLF Error! No \\r line: " + line + " index: " + index);
                        } else {
                            if (!first && builder.toString().equals(TextWarProtocol.HEAD)) {
                                first = true;
                            } else {
                                if (!first) {
                                    throw new ProtocolException("No Head Value [TextWar]CRLF");
                                }

                            }
                            if (!builder.toString().equals(TextWarProtocol.HEAD)) {
                                requestMapping.put(beforeKey, builder.toString());
                            }
                            builder = new StringBuilder();
                            line++;
                            index = 0;
                        }
                    } else {
                        if (before == '\r') {
                            throw new ProtocolException("CRLF Error! No \\n line: " + line + " index: " + index);
                        }
                    }
                    if (before == ':') {
                        if (b != ' ') {
                            throw new ProtocolException("MIDDLE ':' Error! No white space line: " + line + " index: " + index);
                        } else {
                            beforeKey = builder.toString();
                            builder = new StringBuilder();
                        }
                    }
                    if (b != '\r' && b != '\n' && b != ' ' && b != ':') {
                        builder.append((char) b);
                    }

                } else {
                    json[jsonIndex] = (byte) b;
                    jsonIndex++;
                    if (jsonIndex >= length) {
                        break;
                    }

                }
                before = b;
                index++;
            }
            if (requestMapping.isEmpty()) {
                return null;
            }
            byte[] jsonBytes = Base64.getDecoder().decode(json);
            long beforeTime = Long.parseLong(requestMapping.get(TextWarProtocol.TIME_STAMP).trim());
            if (beforeTime >= this.beforeTime) {
                this.beforeTime = beforeTime;
                return new TextWarProtocol().addAll(new String(jsonBytes));
            } else {
                return null;
            }
        }catch (Exception e){
            if(e instanceof ProtocolException)throw e;
            return null;
        }
    }
}
