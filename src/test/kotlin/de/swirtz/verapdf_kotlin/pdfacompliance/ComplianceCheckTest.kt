package de.ndesign.signproxy.verapdfanalysis.tester

import de.ndesign.signproxy.verapdfanalysis.tester.pdfacompliance.PdfAComplianceChecker
import junit.framework.TestCase.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.io.File

/**
 * Created on 01.06.2017.
 * @author: simon-wirtz
 */

class ComplianceCheckTest {

    @Test
    fun testGematikReferenceDocument() {
        val validationSummary = PdfAComplianceChecker.validate(files = toFile("sample.pdf")).validationSummary
        assertEquals(1, validationSummary.compliantPdfaCount)
        assertEquals(0, validationSummary.failedJobCount)
    }

    private fun toFile(fileName: String): File {
        return File("src/main/resources/$fileName")
    }

}