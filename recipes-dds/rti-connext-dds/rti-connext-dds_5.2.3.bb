# Copyright (C) 2017 Mentor Graphics
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "RTI Connext DDS package including the prebuilt shared libraries for runtime \
               and header files, prebuild static and shared libraries for development"
LICENSE = "CLOSED"

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} = "already-stripped ldflags dev-so"

PACKAGES += "                                    \
    ${PN}-cpp                                    \
    ${PN}-dl                                     \
    ${PN}-dl-cpp                                 \
    ${PN}-mon                                    \
    ${PN}-tcp                                    \
    ${PN}-routing                                \
    ${PN}-infra                                  \
    ${PN}-transf                                 \
"

RDEPENDS_${PN}-cpp      += "${PN}"
RDEPENDS_${PN}-dl       += "${PN}"
RDEPENDS_${PN}-dl-cpp   += "${PN}-cpp ${PN}-dl"
RDEPENDS_${PN}-mon      += "${PN}"
RDEPENDS_${PN}-tcp      += "${PN}"
RDEPENDS_${PN}-routing  += "${PN}-dl"
RDEPENDS_${PN}-infra    += "${PN}-dl"
RDEPENDS_${PN}-transf   += "${PN}-dl"

FILES_${PN} = "                            \
    ${datadir}/rti-connext-dds/*           \
"

FILES_${PN} += "                           \
    ${libdir}/libnddsc.so                  \
    ${libdir}/libnddscore.so               \
    ${libdir}/librticonnextmsgc.so         \
"

FILES_${PN}-cpp = "                        \
    ${libdir}/libnddscpp.so                \
    ${libdir}/libnddscpp2.so               \
    ${libdir}/librticonnextmsgcpp.so       \
"

FILES_${PN}-dl = "                         \
    ${libdir}/librtidlc.so                 \
"

FILES_${PN}-dl-cpp = "                     \
    ${libdir}/librtidlcpp.so               \
"

FILES_${PN}-mon = "                        \
    ${libdir}/librtimonitoring.so          \
"

FILES_${PN}-tcp = "                        \
    ${libdir}/libnddstransporttcp.so       \
"

FILES_${PN}-routing = "                    \
    ${libdir}/librtiroutingservice.so      \
"

FILES_${PN}-infra = "                      \
    ${libdir}/librtirsinfrastructure.so    \
"

FILES_${PN}-transf = "                     \
    ${libdir}/librtirsassigntransf.so      \
"

FILES_${PN}-dbg = "                        \
    ${libdir}/*d.so                        \
"

FILES_${PN}-dev = "                        \
    ${includedir}/*                        \
"

do_fetch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

NDDS_ARCH = "INVALID"
NDDS_ARCH_x86-64 = "x64"

do_install() {
    ndds_libs_path=$(find ${NDDSHOME}/lib/ -name "*${NDDS_ARCH}Linux*" | head -n 1)

    if [ ! -d "${ndds_libs_path}" ]; then
        bbfatal "Could not find the valid shared libraries directory for" \
                "required architecture. Possible reasons could be that either" \
                "NDDSHOME is incorrect or pointing to a directory that" \
                "contains the RTI Connext DDS for some other architecture but" \
                "not for the required architecture."
    fi

    install -d ${D}/${libdir}/

    cp -fd ${ndds_libs_path}/*.so ${D}/${libdir}/
    rm ${D}/${libdir}/libnddsjava.so \
       ${D}/${libdir}/librtirsjniadapter.so \
       ${D}/${libdir}/libnddsjavad.so

    cp -fd ${ndds_libs_path}/*.a ${D}/${libdir}/

    install -d ${D}/${includedir}
    cp -fdr ${NDDSHOME}/include/* ${D}/${includedir}/

    install -d ${D}/${datadir}/rti-connext-dds/
    if [ -f "${RTI_LICENSE_FILE}" ]; then
        cp -f ${RTI_LICENSE_FILE} ${D}/${datadir}/rti-connext-dds/
    fi
}

python () {
    nddshome = d.getVar('NDDSHOME', True)
    if not nddshome or not os.path.isdir(nddshome):
        raise bb.parse.SkipPackage('To build this recipe, NDDSHOME must be set to a valid directory where RTI Connext DDS is installed.')
}
