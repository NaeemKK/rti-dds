# Copyright (C) 2016 Mentor Graphics
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Install the RTI connext_dds binary package"
LICENSE = "CLOSED"

EXCLUDE_FROM_SHLIBS = "1"
INSANE_SKIP_${PN} = "debug-files already-stripped ldflags staticdev file-rdeps"

PACKAGES = "                                   \
    rti-dds-core                               \
    rti-dds-core-dbg                           \
    rti-dds-cpp                                \
    rti-dds-cpp-dbg                            \
    rti-dds-java                               \
    rti-dds-java-dbg                           \
    rti-dds-dl                                 \
    rti-dds-dl-dbg                             \
    rti-dds-dl-cpp                             \
    rti-dds-dl-cpp-dbg                         \
    rti-dds-mon                                \
    rti-dds-mon-dbg                            \
    rti-dds-tcp                                \
    rti-dds-tcp-dbg                            \
    rti-dds-routing                            \
    rti-dds-routing-dbg                        \
    rti-dds-infra                              \
    rti-dds-infra-dbg                          \
"

RDEPENDS_rti-dds-core = "glibc"
FILES_rti-dds-core = "                         \
    ${libdir}/libnddsc.so                      \
    ${libdir}/libnddscore.so                   \
    ${libdir}/librticonnextmsgc.so             \
"

FILES_rti-dds-core-dbg = "                     \
    ${libdir}/.debug/libnddsc.so               \
    ${libdir}/.debug/libnddscore.so            \
    ${libdir}/.debug/librticonnextmsgc.so      \
"

RDEPENDS_rti-dds-cpp = "glibc"
FILES_rti-dds-cpp = "                          \
    ${libdir}/libnddscpp.so                    \
    ${libdir}/libnddscpp2.so                   \
    ${libdir}/librticonnextmsgcpp.so           \
"

FILES_rti-dds-cpp-dbg = "                      \
    ${libdir}/.debug/libnddscpp.so             \
    ${libdir}/.debug/libnddscpp2.so            \
    ${libdir}/.debug/librticonnextmsgcpp.so    \
"

RDEPENDS_rti-dds-java = "glibc"
FILES_rti-dds-java = "                         \
   ${libdir}/libnddsjava.so                    \
   ${libdir}/librtirsjniadapter.so             \
"

FILES_rti-dds-java-dbg = "                     \
   ${libdir}/.debug/libnddsjava.so             \
"

RDEPENDS_rti-dds-dl = "glibc"
FILES_rti-dds-dl = "                           \
    ${libdir}/librtidlc.so                     \
"

FILES_rti-dds-dl-dbg = "                       \
    ${libdir}/.debug/librtidlc.so              \
"

RDEPENDS_rti-dds-dl-cpp = "glibc"
FILES_rti-dds-dl-cpp = "                       \
    ${libdir}/librtidlcpp.so                   \
"

FILES_rti-dds-dl-cpp-dbg = "                   \
    ${libdir}/.debug/librtidlcpp.so            \
"

RDEPENDS_rti-dds-mon = "glibc"
FILES_rti-dds-mon = "                          \
    ${libdir}/librtimonitoring.so              \
"

FILES_rti-dds-mon-dbg = "                      \
    ${libdir}/.debug/librtimonitoring.so       \
"

RDEPENDS_rti-dds-tcp = "glibc"
FILES_rti-dds-tcp = "                          \
    ${libdir}/libnddstransporttcp.so           \
"

FILES_rti-dds-tcp-dbg = "                      \
    ${libdir}/.debug/libnddstransporttcp.so    \
"

RDEPENDS_rti-dds-routing = "glibc"
FILES_rti-dds-routing = "                      \
    ${libdir}/librtiroutingservice.so          \
"

FILES_rti-dds-routing-dbg = "                  \
    ${libdir}/.debug/librtiroutingservice.so   \
"

RDEPENDS_rti-dds-infra = "glibc"
FILES_rti-dds-infra = "                        \
    ${libdir}/librtirsinfrastructure.so        \
"

FILES_rti-dds-infra-dbg = "                    \
    ${libdir}/.debug/librtirsinfrastructure.so \
"

do_configure[noexec] = "1" 
do_compile[noexec] = "1"

do_install() {
    local pf=x64Linux3gcc4.8.2
    local i

    install -m 755 -d ${D}${libdir}
    install -m 755 -d ${D}${libdir}/.debug

    for i in c core cpp cpp2 java transporttcp
    do
       install -m 755 ${NDDSHOME}/lib/${pf}/libndds${i}.so ${D}${libdir}
       install -m 755 ${NDDSHOME}/lib/${pf}/libndds${i}d.so ${D}${libdir}/.debug/libndds${i}.so
    done

    for i in connextmsgc connextmsgcpp dlc dlcpp monitoring routingservice rsinfrastructure
    do
       install -m 755 ${NDDSHOME}/lib/${pf}/librti${i}.so ${D}${libdir}
       install -m 755 ${NDDSHOME}/lib/${pf}/librti${i}d.so ${D}${libdir}/.debug/librti${i}.so
    done

    for i in rsassigntransf rsjniadapter
    do
       install -m 755 ${NDDSHOME}/lib/${pf}/librti${i}.so ${D}${libdir}
    done
}

