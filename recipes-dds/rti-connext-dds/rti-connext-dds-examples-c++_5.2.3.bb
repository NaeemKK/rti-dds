# Copyright (C) 2017 Mentor Graphics
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "RTI Connext DDS examples/sample applications developed in C++."
LICENSE = "CLOSED"

SRC_URI_x86-64 = " \
    file://0001-hello_simple_cpp-add-makefile-for-x64MEL.patch \
"

NDDS_ARCH = "INVALID"
NDDS_ARCH_x86-64 = "x64MEL"

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} = " \
    ${bindir}/${PN}/hello_simple/HelloPublisher \
    ${bindir}/${PN}/hello_simple/HelloSubscriber \
"

FILES_${PN}-dbg += " \
    ${bindir}/${PN}/hello_simple/.debug \
"

do_fetch() {
    echo "WORKDIR = ${WORKDIR}"
    cp -fdr ${NDDSHOME}/resource/template/rti_workspace/examples/connext_dds/c++/* ${WORKDIR}/${PN}-${PV}/
}

do_compile() {
    export NDDSHOME="${PKG_CONFIG_SYSROOT_DIR}/usr"
    export NDDS_ARCH=${NDDS_ARCH}
    cd ${S}/hello_simple/
    make -f make/Makefile.${NDDS_ARCH}
}

do_install() {
    install -d ${D}/${bindir}/${PN}/hello_simple
    install -m 755 ${S}/hello_simple/objs/${NDDS_ARCH}/HelloPublisher ${D}/${bindir}/${PN}/hello_simple/
    install -m 755 ${S}/hello_simple/objs/${NDDS_ARCH}/HelloSubscriber ${D}/${bindir}/${PN}/hello_simple/
}

python () {
    nddshome = d.getVar('NDDSHOME', True)
    if not nddshome or not os.path.isdir(nddshome):
        raise bb.parse.SkipPackage('To build this recipe, NDDSHOME must be set to a valid directory where RTI Connext DDS is installed.')
}
