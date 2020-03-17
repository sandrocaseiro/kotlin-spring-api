package com.sandrocaseiro.template.controllers

import com.sandrocaseiro.template.models.dto.file.DSaveFileReq
import com.sandrocaseiro.template.services.IOService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.annotation.RequestScope

@RestController
@RequestScope
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class IOController (
    private val ioService: IOService
) {
    @GetMapping("/v1/io")
    fun read() {
        //ioService.readFile()
    }

    @PostMapping("/v1/io")
    @ResponseStatus(HttpStatus.CREATED)
    fun generate() {
//        IArquivoExtrato extrato = ioService.readFile()
//        ioService.generateFile(extrato)
    }

    @PostMapping("/v1/io/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun newFile(file: DSaveFileReq) {
        Assert.notNull(file.attachment, "Uploaded");
    }
}
