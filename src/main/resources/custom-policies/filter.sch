<?xml version="1.0" encoding="UTF-8"?>
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt">
    <sch:pattern name="Check compressions used in the document">
        <sch:rule context="/report/jobs/job/featuresReport/documentResources/xobjects">
            <sch:report test="xobject/filters/filter = 'FlateDecode'">No Filter other than FlateDecode
                found</sch:report>
            <sch:assert test="xobject/filters/filter = 'FlateDecode'">only FlateDecode compression is OK
            </sch:assert>
        </sch:rule>
    </sch:pattern>
</sch:schema>


