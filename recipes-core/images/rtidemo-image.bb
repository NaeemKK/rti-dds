SUMMARY = "Core image with rti DDS connect pro"

IMAGE_FEATURES += "package-management qt4-pkgs x11-base splash ssh-server-openssh"

LICENSE = "MIT"

# Make appropriate changes to syslinux.cfg
SYSLINUX_KERNEL_ARGS = ""
SYSLINUX_DEFAULT_CONSOLE = "console=tty0"
# Squash the initrd
INITRD = ""
SYSLINUX_ROOT = "root=/dev/sda2"
# Squash copying of rootfs to bootimg
ROOTFS = ""
NOISO = "1"
APPEND += " console=ttyS0,115200 rootwait rootdelay=5 "

CORE_IMAGE_EXTRA_INSTALL += "\
    directfb-examples \
    xauth \
    sysstat \
    x11vnc \
    rti-bundle \
    "

CORE_IMAGE_EXTRA_INSTALL += "packagegroup-xfce-base epiphany x11vnc rtidds"

inherit core-image

ROOTFS_POSTPROCESS_COMMAND_append = " copy_conf_to_image; zap_fotowall; "

copy_conf_to_image() {
        bbnote "Executing copy_conf_to_image"
        cp ${TOPDIR}/conf/local.conf ${IMAGE_ROOTFS}/boot
        cp ${TOPDIR}/conf/bblayers.conf ${IMAGE_ROOTFS}/boot
        echo "Built from: ${TOPDIR}  using ${PN}"
        echo "Built from: ${TOPDIR}  using ${PN}" >>${IMAGE_ROOTFS}/etc/issue
}

