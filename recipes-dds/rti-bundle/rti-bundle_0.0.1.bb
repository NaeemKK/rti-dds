# Copyright (C) 2016 Chris Hallinan <challinan@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Install the RTI connext_dds binary package"
LICENSE = "CLOSED"

EXCLUDE_FROM_SHLIBS = "1"
INSANE_SKIP_${PN} = "debug-files already-stripped ldflags staticdev file-rdeps"

SRC_URI_intel-corei7-64 = "file://rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3-partial.tar.gz;unpack=false"

do_configure[noexec] = "1" 
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/opt
    # Install the RTI bundle on the target
    tar xzf ${WORKDIR}/rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3-partial.tar.gz -C ${D}/opt
    bbnote "Adding files to target staging dir"
    cd ${D}
    # Yes, I know this is a kludge, but staging has requirements we can't meet
    tar c ./opt | tar x -C ${STAGING_DIR_TARGET}
}

do_clean_target_opt() {
    bbnote "Removing files from target staging directory"
    rm -rf ${STAGING_DIR_TARGET}/opt
}

# Make sure we remove files from staging on any clean
addtask clean_target_opt after do_clean
do_cleanall[depends] += "${PN}:do_clean_target_opt"
do_clean[depends] += "${PN}:do_clean_target_opt"

FILES_${PN} = "/opt/*"
