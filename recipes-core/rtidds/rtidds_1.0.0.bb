# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "RTI DDS binary installer"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3.run"

do_install() {
    install -d -m 0755 ${D}/home/root
    install -m 0755 ${WORKDIR}/rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3.run ${D}/home/root/rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3.run
}

INHIBIT_PACKAGE_STRIP = "1"
INSANE_SKIP_${PN} = "arch"
FILES_${PN} = "/home/root/rti_connext_dds-5.2.3-eval-x64Linux3.xgcc4.6.3.run"
