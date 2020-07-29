package dev.sandrocaseiro.template.configs

import dev.sandrocaseiro.template.utils.MAPPER
import feign.Client
import feign.Feign
import feign.Request
import feign.Retryer
import feign.codec.Decoder
import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class FeignConfig {
    @Value("\${feign.connectTimeout}")
    private var connectTimeout: Long = 0
    @Value("\${feign.readTimeout}")
    private var readTimeout: Long = 0
    @Value("\${feign.followRedirects}")
    private var followRedirects: Boolean = false
    @Value("\${feign.ignore-ssl}")
    private var ignoreSSL: Boolean = false

    @Bean
    fun feignBuilder(): Feign.Builder = Feign.builder()
        .options(options())

    @Bean
    fun client(): Client = feign.okhttp.OkHttpClient(httpClient())

    @Bean
    fun feignEncoder(): Encoder = SpringFormEncoder(JacksonEncoder())

    @Bean
    fun feignDecoder(): Decoder = JacksonDecoder(MAPPER)

    @Bean
    fun retryer(): Retryer = Retryer.NEVER_RETRY

    private fun options(): Request.Options = Request.Options(
        connectTimeout,
        TimeUnit.MILLISECONDS,
        readTimeout,
        TimeUnit.MILLISECONDS,
        followRedirects
    )

    private fun httpClient(): OkHttpClient = OkHttpClient.Builder()
        .followRedirects(followRedirects)
        .followSslRedirects(followRedirects)
        .retryOnConnectionFailure(false)
        .ignoreSSLCertificates(ignoreSSL)
        .build()

    private fun OkHttpClient.Builder.ignoreSSLCertificates(ignore: Boolean): OkHttpClient.Builder {
        if (!ignore) return this

        val trustAllCerts = arrayOf(object: X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return this
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0])
            .hostnameVerifier { _, _ -> true}
    }
}
