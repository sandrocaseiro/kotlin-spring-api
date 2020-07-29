package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.properties.InfoProperties
import org.apache.logging.log4j.ThreadContext
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class LogService(private val infoProperties: InfoProperties) {
    private val PROJECT_ID = "project_id"
    private val USER = "user"
    private val IP = "src_ip"
    private val ACTIVITY = "activity"
    private val ACTION = "action"
    private val APPLOG = "applog"
    private val SAVE_TO_FILE = "save_to_file"

    private val APP_LOG = "true"
    private val HEADERS_TO_TRY = arrayOf("X-Forwarded-For", "Client-Ip", "Proxy-Client-IP", "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR")

    fun build(request: HttpServletRequest) {
        ThreadContext.put(PROJECT_ID, infoProperties.app.id)
        ThreadContext.put(USER, getUser(request))
        ThreadContext.put(IP, getRemoteAddress(request))
        ThreadContext.put(ACTIVITY, request.requestURI)
        ThreadContext.put(ACTION, request.method)
        ThreadContext.put(APPLOG, APP_LOG)
        ThreadContext.put(SAVE_TO_FILE, "true")
    }

    fun clear() {
        ThreadContext.clearAll()
    }

    fun getUser(request: HttpServletRequest): String? {
        for (header in HEADERS_TO_TRY) {
            val ip = request.getHeader(header)
            if (ip != null && ip.length != 0 && !"unknown".equals(ip, ignoreCase = true)) {
                return ip
            }
        }
        return request.remoteAddr
    }

    fun getRemoteAddress(request: HttpServletRequest): String? {
        for (header in HEADERS_TO_TRY) {
            val ip = request.getHeader(header)
            if (ip != null && ip.length != 0 && !"unknown".equals(ip, ignoreCase = true)) {
                return ip
            }
        }
        return request.remoteAddr
    }
}
