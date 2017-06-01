package de.ndesign.signproxy.verapdfanalysis.tester.pdfacompliance

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.verapdf.features.FeatureFactory
import org.verapdf.features.FeatureObjectType
import org.verapdf.metadata.fixer.FixerFactory
import org.verapdf.pdfa.VeraGreenfieldFoundryProvider
import org.verapdf.pdfa.flavours.PDFAFlavour
import org.verapdf.pdfa.validation.validators.ValidatorFactory
import org.verapdf.processor.FormatOption
import org.verapdf.processor.ProcessorConfig
import org.verapdf.processor.ProcessorFactory
import org.verapdf.processor.TaskType
import org.verapdf.processor.plugins.PluginsCollectionConfig
import org.verapdf.processor.reports.BatchSummary
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

/**
 * Created on 01.06.2017.
 * @author: simon-wirtz
 */
object PdfAComplianceChecker {

    //    val FILTER_PATH = java.nio.file.Paths.get("src/main/resources/", "custom-policies/filter.sch").toString()
    private val CHECKER_LOG: Logger = LoggerFactory.getLogger("VeraPDF-Analysis-Logger-PDF-A_Check")

    init {
        VeraGreenfieldFoundryProvider.initialise()
    }

    fun validate(processorConfig: ProcessorConfig = createProcessorConfig(), verbose: Boolean = true, vararg files: File):
            BatchSummary {

        ByteArrayOutputStream().use { reportOut ->
            ProcessorFactory.fileBatchProcessor(processorConfig).use {
                val batchHandler = ProcessorFactory.getHandler(FormatOption.MRR, true, reportOut, 1,
                        processorConfig.validatorConfig.isRecordPasses)
                val batchSummary = it.process(files.toList(), batchHandler)
                CHECKER_LOG.debug("Duration of Validation: ${batchSummary.duration.duration}")
                CHECKER_LOG.debug("Compliant PDF-A-2B Tests: ${batchSummary.validationSummary.compliantPdfaCount}")
                val reportBytes = reportOut.asUtf8String()
                if (verbose) CHECKER_LOG.debug("Generated Report: \n$reportBytes")

//                ByteArrayOutputStream().use { polReportOut ->
//                    PolicyChecker.applyPolicy(FileInputStream(FILTER_PATH),
//                            ByteArrayInputStream(reportBytes.toByteArray(Charsets.UTF_8)), polReportOut, false)
//                    if (verbose) CHECKER_LOG.debug("Generated PolicyReport: \n${polReportOut.asUtf8String()}")
//                }
                return batchSummary
            }
        }
    }

    fun createProcessorConfig(): ProcessorConfig {
        //Configure Profile, maxFails (speeds the whole thing up!) & if successful checks shall be reported
        val validatorConfig = ValidatorFactory.createConfig(PDFAFlavour.PDFA_2_B, false, 10)
        //Which features shall be extracted?
        val featureConfig = FeatureFactory.configFromValues(EnumSet.of(FeatureObjectType.INFORMATION_DICTIONARY,
                FeatureObjectType.IMAGE_XOBJECT, FeatureObjectType.EMBEDDED_FILE, FeatureObjectType.COLORSPACE))
        val tasks = EnumSet.of(TaskType.VALIDATE, TaskType.EXTRACT_FEATURES)
        val processorConfig = ProcessorFactory.fromValues(validatorConfig, featureConfig, PluginsCollectionConfig.defaultConfig(),
                FixerFactory.defaultConfig(), tasks)
        return processorConfig
    }
}


