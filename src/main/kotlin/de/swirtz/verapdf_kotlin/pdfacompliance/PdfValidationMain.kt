package de.ndesign.signproxy.verapdfanalysis.tester.pdfacompliance


import de.ndesign.signproxy.verapdfanalysis.tester.pdfacompliance.PdfAComplianceChecker.createProcessorConfig
import de.ndesign.signproxy.verapdfanalysis.tester.pdfacompliance.PdfAComplianceChecker.validate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Paths

private val LOG: Logger = LoggerFactory.getLogger("VeraPDF-Analysis-Logger")
private val PDF_PATH = Paths.get("src/main/resources/", "sample.pdf").toString()

fun ByteArrayOutputStream.asUtf8String(): String {
    return this.toString(Charsets.UTF_8.name())
}

/**
 * Created on 30.05.2017.
 * @author: simon-wirtz
 */
fun main(args: Array<String>) {
    LOG.info("Hello from TestVeraPDFLowLevel")
    validate(createProcessorConfig(), files = File(PDF_PATH))
}

