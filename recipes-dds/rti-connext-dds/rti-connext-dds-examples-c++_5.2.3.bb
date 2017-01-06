# Copyright (C) 2017 Mentor Graphics
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "RTI Connext DDS examples/sample applications developed in C++."
LICENSE = "CLOSED"

SRC_URI_x86-64 = " \
    file://0001-examples-cpp-add-makefile-for-x64MEL.patch \
"

NDDS_ARCH = "INVALID"
NDDS_ARCH_x86-64 = "x64MEL"
MAKEFOLDER="make"


S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} = " \
    ${bindir}/${PN}/* \
    ${bindir}/${PN}/* \
"

FILES_${PN}-dbg += " \
    ${bindir}/${PN}/*/.debug \
"

do_unpack() {
    install -d ${S}
    cp -fdr ${NDDSHOME}/resource/template/rti_workspace/examples/connext_dds/c++/* ${WORKDIR}/${PN}-${PV}/
    #Removing these two folders becuase currently we are not suppoting "rtiddsgen" of RTI DDS 
    rm -rf ${WORKDIR}/${PN}-${PV}/hello_idl ${WORKDIR}/${PN}-${PV}/hello_world_request_reply
}


do_compile() {
    export NDDSHOME="${PKG_CONFIG_SYSROOT_DIR}/usr"
    export NDDS_ARCH=${NDDS_ARCH}
    cd ${S}
    for folder in ./*/ ; do
        cd "$folder"
        if [ -d ./${MAKEFOLDER} ]; then
                make -f ${MAKEFOLDER}/Makefile.${NDDS_ARCH}
        fi
        cd ..
    done
}

do_install() {
    set -x
    cd ${S}
    for folder in * ; do
        install -d ${D}/${bindir}/${PN}/"$folder"
        for executable in `find ./"$folder"/objs/${NDDS_ARCH}/ -type f`; do
                if [ -x "$executable" ]; then
                        install -m 755 "$executable" ${D}/${bindir}/${PN}/"$folder"/
                fi
        done
    done
}


python () {
    nddshome = d.getVar('NDDSHOME', True)
    if not nddshome or not os.path.isdir(nddshome):
        raise bb.parse.SkipPackage('To build this recipe, NDDSHOME must be set to a valid directory where RTI Connext DDS is installed.')
}
