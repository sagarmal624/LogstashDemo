package com.ttnd

class RequestLoggingInterceptor {
    RequestLoggingInterceptor() {
        matchAll()
    }

    boolean before() {
        String jsonStr = "{" +
                "\"Controller And Action\":\"${params}\"," +
                "\"requestURI\":\"${request.requestURI}\"," +
                "\"Request Method\":\"${request.method}\"," +
                "\"Server Name\":\"${request.serverName}\"," +
                "\"ServerPort\":\"${request.serverPort}\"," +
                "\"Request Id\":\"${request.requestedSessionId}\"," +
                "\"RequestId\":\"${request.remotePort}\"," +
                "\"Remote Host\":\"${request.remoteHost}\"," +
                "\"Response Status\":\"${response.status}\"," +
                "\"User-Agent\":\"${request.getHeader("User-Agent")}\"," +
                "\"Remote Host\":\"${request.remoteHost}\"," +
                "\"LocalPort\":\"${request.localPort}\"," +
                "\"Buffer Size(Byte)\":\"${response.bufferSize}\"}"

        log.debug jsonStr
        params.startTime = System.currentTimeMillis()
        true
    }

    boolean after() {
        true
    }
}