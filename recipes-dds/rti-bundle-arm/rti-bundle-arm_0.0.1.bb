# Copyright (C) 2016 Chris Hallinan <challinan@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Install the RTI connext_dds binary package"
LICENSE = "CLOSED"

EXCLUDE_FROM_SHLIBS = "1"
INSANE_SKIP_${PN} = "debug-files already-stripped ldflags staticdev file-rdeps"

DDS_BUNDLE_NAME = "armv7aLinux3.12gcc4.9.3cortex-a9"
SRC_URI_mx6q = "file://${DDS_BUNDLE_NAME}-bundle.tar.gz;unpack=false"

do_configure[noexec] = "1" 
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/opt
    # Install the RTI bundle on the target
    tar xzf ${WORKDIR}/${DDS_BUNDLE_NAME}-bundle.tar.gz -C ${D}/opt
}

sysroot_stage_all_append() {
    sysroot_stage_dir ${D}/opt ${SYSROOT_DESTDIR}/opt
}

FILES_${PN} = "/opt/* ${bindir}/*"
