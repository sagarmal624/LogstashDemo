import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.ApplicationPid
import java.nio.charset.Charset
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.encoder.LogstashEncoder

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

conversionRule 'clr', org.springframework.boot.logging.logback.ColorConverter
conversionRule 'wex', org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        // Define pattern with clr converter to get colors.
        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                        '%clr(%5p) ' + // Log level
                        '%clr(%property{PID}){magenta} ' + // PID
                        '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                        '%clr(%-40.40logger{39}){cyan} %clr(:){faint} ' + // Logger
                        '%m%n%wex' // Message
    }
}
appender("stash", LogstashTcpSocketAppender) {
    destination = "127.0.0.1:9000"
    encoder(LogstashEncoder)
            {
                prefix(ch.qos.logback.core.encoder.LayoutWrappingEncoder) {
                    layout(ch.qos.logback.classic.PatternLayout) {
                        pattern = "%m"
                    }
                }
            }
}
root(ERROR, ['stash'])

def targetDir = BuildSettings.TARGET_DIR

if (Environment.isDevelopmentMode() && targetDir) {
    logger "StackTrace", ERROR, ['stash'], false
    logger 'grails.app.services', TRACE, ['stash'], false
    logger 'grails.app.controllers', TRACE, ['stash'], false
} else {
    logger 'grails.app.controllers', TRACE, ['stash'], false
    logger 'grails.app.services', TRACE, ['stash'], false
}
logger "StackTrace", ERROR, ['stash'], false
logger 'com.ttnd.testapp', DEBUG, ['stash'], false
logger 'com.ttnd.testapp.storage', INFO, ['stash'], false