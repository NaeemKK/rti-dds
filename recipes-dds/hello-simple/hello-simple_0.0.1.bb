# Copyright (C) 2016 Chris Hallinan <challinan@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "RTI Hello (hello_simple) demo"
LICENSE = "CLOSED"

do_compile[depends] += "rti-bundle-arm:do_populate_sysroot"
DEPENDS_append_intel-corei7-64 += "rti-bundle"

DDS_ARCH_intel-corei7-64 = "x64Linux3.xgcc4.6.3"
DDS_ARCH_mx6q = "armv7aLinux3.12gcc4.9.3cortex-a9"

SRC_URI = "file://HelloPublisher.cpp \
           file://HelloSubscriber.cpp"

SRC_URI_append_mx6q += "file://Makefile.mx6q"
SRC_URI_append_intel-corei7-64 += "file://Makefile.corei7-64"

do_configure() {
    # Easier to do this here than patch the Makefile
    mkdir ${S}/src
    mv ${WORKDIR}/Hello* ${S}/src

    # Grab the correct Makefile prior to do_compile()
    if [ -e ${WORKDIR}/Makefile.mx6q ]; then
        cp ${WORKDIR}/Makefile.mx6q ${WORKDIR}/Makefile
    fi

    if [ -e ${WORKDIR}/Makefile.corei7-64 ]; then
        cp ${WORKDIR}/Makefile.corei7-64 ${WORKDIR}/Makefile
    fi
    mv ${WORKDIR}/Makefile ${S}
}

do_install() {
    install -d ${D}/${bindir}
    install -m 0755 ${B}/objs/${DDS_ARCH}/HelloPublisher ${D}/${bindir}
    install -m 0755 ${B}/objs/${DDS_ARCH}/HelloSubscriber ${D}/${bindir}
}

EXTRA_OEMAKE = "NDDSHOME=${STAGING_DIR_TARGET}/opt/rti_connext_dds-5.2.3"

