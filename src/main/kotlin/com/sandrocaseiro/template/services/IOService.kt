package com.sandrocaseiro.template.services

import org.beanio.BeanReaderException
import org.beanio.InvalidRecordException
import org.beanio.StreamFactory
import org.beanio.UnexpectedRecordException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@Service
class IOService {
//    private val LOGGER = LoggerFactory.getLogger(IOService::class.java)
//
//    fun readFile(): IArquivoExtrato? {
//        val factory = StreamFactory.newInstance()
//        factory.load("src/main/resources/io/layout-file.xml")
//        val br = factory.createReader("extrato-arquivo", File("src/main/resources/io/data"))
//
//        var extrato: IArquivoExtrato? = null
//        try {
//            extrato = br.read() as IArquivoExtrato?
//            LOGGER.info("{}", extrato)
//            return extrato
//        } catch (e: InvalidRecordException) {
//            treatException(e)
//        } catch (e: UnexpectedRecordException) {
//            treatException(e)
//        } finally {
//            br.close()
//        }
//        return extrato
//    }
//
//    @Throws(IOException::class)
//    fun generateFile(extrato: IArquivoExtrato?) {
//        val factory = StreamFactory.newInstance().apply {
//            load("src/main/resources/io/layout-file.xml")
//        }
//        val destPath = "src/main/resources/io/new-data"
//        Files.delete(Paths.get(destPath))
//        factory.createWriter("extrato-arquivo", File(destPath)).let {
//            it.write(extrato)
//            it.flush()
//            it.close()
//        }
//    }
//
//    private fun treatException(e: BeanReaderException) {
//        val sb = StringBuilder()
//        for (i in 0 until e.recordCount) {
//            val context = e.getRecordContext(i)
//            if (!context.hasErrors()) continue
//            sb.append(String.format("Record %s errors", context.recordName))
//            sb.append("\r\n")
//            if (context.hasRecordErrors()) {
//                sb.append(String.format("    >> Record errors: %s", java.lang.String.join(", ", context.recordErrors)))
//                sb.append("\r\n")
//            }
//            if (context.hasFieldErrors()) {
//                for ((key, value) in context.fieldErrors) {
//                    sb.append(String.format("    >> Field %s errors: %s", key, java.lang.String.join(", ", value)))
//                    sb.append("\r\n")
//                }
//            }
//        }
//        LOGGER.error("{}", sb)
//    }
}
