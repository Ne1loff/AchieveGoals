package com.example.achieve_goals.controller.frontend

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.concurrent.atomic.AtomicReference
import javax.servlet.http.HttpServletRequest

@RestController
class DevFrontendController(
    @Value("\${app.frontendAddress}") private val frontendAddress: String,
    @Value("\${app.frontendPort}") private val frontendPort: Int
) : FrontendController {

    @RequestMapping("/**")
    fun mirrorRest(request: HttpServletRequest): ResponseEntity<ByteArray> {

        val requestUrl = request.requestURI

        var uri = URI("http", null, frontendAddress, frontendPort, null, null, null)
        uri = if (
            requestUrl.contains("build") || requestUrl.contains("css") || requestUrl.contains("static")
        ) {
            UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.queryString)
                .build(true).toUri()
        } else {
            UriComponentsBuilder.fromUri(uri)
                .path("index.html")
                .query(request.queryString)
                .build(true).toUri()
        }

        val restTemplate = RestTemplate()
        return try {
            val headers = AtomicReference<HttpHeaders>()
            val stream = restTemplate.execute(
                uri, HttpMethod.GET, null
            ) { response1: ClientHttpResponse ->
                headers.set(response1.headers)
                response1.body.readAllBytes()
            }
            ResponseEntity.ok()
                .headers(headers.get())
                .body(stream ?: ByteArray(0))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(byteArrayOf())
        }
    }
}